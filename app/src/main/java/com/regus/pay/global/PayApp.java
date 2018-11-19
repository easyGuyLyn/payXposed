package com.regus.pay.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.support.multidex.MultiDex;

public class PayApp extends Application {

    public static Handler handler = new Handler();

    public static Context mAppContext;

    //兼容 4.5版本以下 添加MultiDex分包，但未初始化的问题
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = this;
    }



}
