package com.regus.pay.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.gyf.barlibrary.ImmersionBar;
import com.regus.pay.R;
import com.regus.pay.util.ActivityStackManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * activity的基本类
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected Context mContext;
    private ImmersionBar mImmersionBar;
    private Unbinder mBind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImmersionBar = ImmersionBar.with(this).statusBarColor(R.color.colorAccent);
        mImmersionBar.init();   //所有子类都将继承这些相同的属性
        mContext = this;
        ActivityStackManager.getInstance().addActivity(this);
        createLayoutView();
        mBind = ButterKnife.bind(this); // 初始化ButterKnife
        initViews();
        initData();
    }

    protected abstract void createLayoutView();

    protected abstract void initViews();

    protected abstract void initData();


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null)
            mImmersionBar.destroy();  //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
        mBind.unbind();
        ActivityStackManager.getInstance().removeActivity(this);
    }

}
