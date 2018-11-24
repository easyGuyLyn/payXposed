package com.regus.pay.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.support.multidex.MultiDex;

import com.blankj.utilcode.util.Utils;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class PayApplication extends Application {

    public static Handler handler = new Handler();

    public static Context mAppContext;

    public static final int DEFAULT_TIMEOUT_SECONDS = 7;
    public static final int DEFAULT_READ_TIMEOUT_SECONDS = 20;
    public static final int DEFAULT_WRITE_TIMEOUT_SECONDS = 20;

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
        Utils.init(this);
        //初始化DBFLOW
        FlowManager.init(this);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)//失败重连
                .build();

        OkHttpUtils.initClient(client);
    }



}
