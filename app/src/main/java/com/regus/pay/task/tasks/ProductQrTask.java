package com.regus.pay.task.tasks;

import android.content.Intent;

import com.blankj.utilcode.util.AppUtils;
import com.regus.pay.global.PayApplication;
import com.regus.pay.util.XLogUtil;

public class ProductQrTask extends BaseTask {

    private String aliPayInfo;

    public void setAliPayInfo(String aliPayInfo) {
        this.aliPayInfo = aliPayInfo;
    }


    protected void excuteChildTask(){
        super.excuteChildTask();
        startProductQrTask();
    }

    /**
     * 开始 生产二维码任务
     */
    protected void startProductQrTask() {

        XLogUtil.log("Task  xposed: 生成码" + money + ";;" + token + ";;" + account);

        try {
            AppUtils.launchApp("com.eg.android.AlipayGphone");
            Intent intent = new Intent("start");
            intent.putExtra("money", money);
            intent.putExtra("beizhu", remark);
            intent.putExtra("account", account);
            intent.putExtra("name", aliPayInfo);
            PayApplication.mAppContext.sendBroadcast(intent);
        } catch (Throwable th) {
            setLastBillFinish();
        }

    }





}
