package com.regus.pay.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.SPUtils;
import com.guoqi.iosdialog.IOSDialog;
import com.regus.pay.R;
import com.regus.pay.util.CommonUtil;
import com.regus.pay.util.SingleToast;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

import static com.regus.pay.global.ConstantValue.SP_APP_INFO;


public class LoginActivity extends BaseActivity {

    @BindView(R.id.mch_no)
    EditText mchNo;
    @BindView(R.id.account)
    EditText account;
    @BindView(R.id.login_button)
    Button loginButton;
    @BindView(R.id.pwd)
    EditText etPwd;
    @BindView(R.id.tv_version_name)
    TextView tvVersionName;

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

            Object token = SPUtils.getInstance(SP_APP_INFO).getString("token");

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
        String memNo = account.getText().toString();
        String pwd = etPwd.getText().toString();

        if (TextUtils.isEmpty(shanghu)) {
            SingleToast.showMsg("商户号不能为空！");
            loginButton.setEnabled(true);
            return;
        } else if (TextUtils.isEmpty(memNo)) {
            SingleToast.showMsg("操作员不能为空！");
            loginButton.setEnabled(true);
            return;
        } else if (TextUtils.isEmpty(pwd)) {
            SingleToast.showMsg("密码不能为空！");
            loginButton.setEnabled(true);
            return;
        }


        //login  todo

        OkHttpUtils.get().build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response, int id) throws Exception {
                return null;
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(Object response, int id) {

            }
        });


    }


    @Override
    protected void initViews() {
        tvVersionName.setText(String.format(getResources().getString(R.string.app_version_name), AppUtils.getAppVersionName()));
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
