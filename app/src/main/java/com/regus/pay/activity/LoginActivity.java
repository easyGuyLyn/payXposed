package com.regus.pay.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.SPUtils;
import com.guoqi.iosdialog.IOSDialog;
import com.regus.pay.R;
import com.regus.pay.util.CommonUtil;
import com.regus.pay.util.SingleToast;

import butterknife.BindView;
import butterknife.OnClick;


public class LoginActivity extends BaseActivity {

    @BindView(R.id.mch_no)
    EditText mchNo;
    @BindView(R.id.account)
    EditText account;
    @BindView(R.id.login_button)
    Button loginButton;
    private IOSDialog mTipDialog;


    private static boolean isModuleActive() {
        return false;
    }


    @Override
    protected void createLayoutView() {
        if (!isModuleActive()) {
            mTipDialog = new IOSDialog(this);
            mTipDialog
                    .setMsg("请在Xposed中打开本应用并在Xposed中开启本模块!")
                    .setCancelable(false)
                    .setPositiveButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mTipDialog.dismiss();
                            finish();
                        }
                    }).builder().show();

        } else if (!AppUtils.isAppInstalled("com.eg.android.AlipayGphone")) {
            mTipDialog = new IOSDialog(this);
            mTipDialog
                    .setMsg("请安装支付宝!")
                    .setCancelable(false)
                    .setPositiveButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mTipDialog.dismiss();
                            finish();
                        }
                    }).builder().show();

        } else if (CommonUtil.isAppRunning(mContext, "com.eg.android.AlipayGphone")) {
            setContentView(R.layout.activity_login);

            Object token = SPUtils.getInstance("supay_v3").getString("token");

            if (!TextUtils.isEmpty((CharSequence) token)) {
                mTipDialog = new IOSDialog(this);
                mTipDialog
                        .setMsg("请稍后...")
                        .setCancelable(false)
                        .builder().show();
                //token登录  todo


            }


        } else {
            mTipDialog = new IOSDialog(this);
            mTipDialog
                    .setMsg("请运行支付宝并登录相应账号!")
                    .setCancelable(false)
                    .setPositiveButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mTipDialog.dismiss();
                            finish();
                        }
                    }).builder().show();

        }

    }

    @OnClick(R.id.login_button)
    public void login() {

        loginButton.setClickable(false);
        String shanghu = mchNo.getText().toString();
        String shebei = account.getText().toString();

        if (TextUtils.isEmpty(shanghu)) {
            SingleToast.showMsg("商户号不能为空！");
            loginButton.setEnabled(true);
            return;
        } else if (TextUtils.isEmpty(shebei)) {
            SingleToast.showMsg("操作员不能为空！");
            loginButton.setEnabled(true);
            return;
        }

        //login  todo


    }


    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


}
