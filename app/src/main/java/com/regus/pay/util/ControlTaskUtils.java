package com.regus.pay.util;

import android.content.Intent;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.AppUtils;
import com.regus.pay.db.entity.SendTradeMsgEntity;
import com.regus.pay.global.UserInfoManger;
import com.regus.pay.task.TaskQueneManager;
import com.regus.pay.task.tasks.QueryAndUploadTradeTask;
import com.regus.pay.task.tasks.UploadTradeMsgTask;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class ControlTaskUtils {


    /**
     * 上传收款二维码
     */
    public static void upLoadQrInfo(Intent intent) {
        try {
            AppUtils.launchApp(AppUtils.getAppPackageName());
            String money = intent.getStringExtra("money");
            String remark = intent.getStringExtra("beizhu");
            String stringExtra3 = intent.getStringExtra("ufff");
            String apyUrl = intent.getStringExtra("payUrl");
            intent.getBooleanExtra("isTest", false);
            boolean booleanExtra = intent.getBooleanExtra("isGen", false);

            Map<String, String> paramsMap = new HashMap<>();
            paramsMap.put("token", "");

            OkHttpUtils
                    .post()
                    .url("xxxxxxxx")
                    .params(paramsMap)
                    .build()
                    .execute(new StringCallback() {

                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(String response, int id) {


                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    /**
     * 接收订单信息  并加入队列
     */
    public static void receiveTradeInfo(Intent intent) {

        String money = intent.getStringExtra("money");
        String tradeNo = intent.getStringExtra("tradeNo");
        String remark = intent.getStringExtra("beizhu");
        String consumerName = intent.getStringExtra("consumerName");
        if (TextUtils.isEmpty(consumerName)) {
            consumerName = "未知";
        }
        long orderTime = intent.getLongExtra("time", 0);
        try {

            SendTradeMsgEntity sendTradeMsgEntity = new SendTradeMsgEntity();
            sendTradeMsgEntity.setAccount(UserInfoManger.getInstance().getMerNo());
            sendTradeMsgEntity.setMchNo(UserInfoManger.getInstance().getMchNo());
            sendTradeMsgEntity.setConsumerName(consumerName);
            sendTradeMsgEntity.setStatus(1);
            sendTradeMsgEntity.setOrderTime(orderTime);
            sendTradeMsgEntity.setMoney(money);
            sendTradeMsgEntity.setTradeNo(tradeNo);
            sendTradeMsgEntity.setRemark(remark);
            sendTradeMsgEntity.insert();

            XLogUtil.log("MainActivity 接收到订单信息  并已经入库");

            UploadTradeMsgTask uploadTradeMsgTask = new UploadTradeMsgTask();
            uploadTradeMsgTask.setTradeNo(tradeNo);
            uploadTradeMsgTask.setRemark(remark);
            uploadTradeMsgTask.setOrderTime(orderTime);
            uploadTradeMsgTask.setConsumerName(consumerName);
            uploadTradeMsgTask.setMchNo(UserInfoManger.getInstance().getMchNo());
            uploadTradeMsgTask.setAccount(UserInfoManger.getInstance().getMerNo());
            uploadTradeMsgTask.setMoney(money);

            TaskQueneManager.getInstance().addTaskToQuene(uploadTradeMsgTask);

        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }


    /**
     * 查询订单信息
     */
    public static void queryTradeDetail(Intent intent) {

        int i = 0;

        JSONArray parseArray = JSON.parseArray(intent.getStringExtra("list"));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String cookies = intent.getStringExtra("cookies");
        while (true) {
            int i2 = i;
            if (i2 < parseArray.size() && i2 < 10) {
                try {
                    JSONObject jSONObject = parseArray.getJSONObject(i2);
                    if (!(jSONObject == null || TextUtils.isEmpty(jSONObject.getString("tradeNo")) || TextUtils.isEmpty(jSONObject.getString("tradeTransAmount")))) {

                        String money = jSONObject.getString("tradeTransAmount");
                        String tradeNo = jSONObject.getString("tradeNo");
                        String remark = jSONObject.toJSONString();
                        String consumerName = "未知";
                        if (!TextUtils.isEmpty(jSONObject.getString("buyerUserName"))) {
                            consumerName = jSONObject.getString("buyerUserName").trim();
                        }
                        long orderTime = simpleDateFormat.parse(jSONObject.getString("dateKey")).getTime();


                        SendTradeMsgEntity sendTradeMsgEntity = new SendTradeMsgEntity();
                        sendTradeMsgEntity.setAccount(UserInfoManger.getInstance().getMerNo());
                        sendTradeMsgEntity.setMchNo(UserInfoManger.getInstance().getMchNo());
                        sendTradeMsgEntity.setConsumerName(consumerName);
                        sendTradeMsgEntity.setStatus(0);
                        sendTradeMsgEntity.setOrderTime(orderTime);
                        sendTradeMsgEntity.setMoney(money);
                        sendTradeMsgEntity.setTradeNo(tradeNo);
                        sendTradeMsgEntity.setRemark(remark);
                        sendTradeMsgEntity.setCookies(cookies);
                        sendTradeMsgEntity.insert();


                        QueryAndUploadTradeTask queryAndUploadTradeTask = new QueryAndUploadTradeTask();
                        queryAndUploadTradeTask.setTradeNo(tradeNo);
                        queryAndUploadTradeTask.setRemark(remark);
                        queryAndUploadTradeTask.setOrderTime(orderTime);
                        queryAndUploadTradeTask.setConsumerName(consumerName);
                        queryAndUploadTradeTask.setMchNo(UserInfoManger.getInstance().getMchNo());
                        queryAndUploadTradeTask.setAccount(UserInfoManger.getInstance().getMerNo());
                        queryAndUploadTradeTask.setMoney(money);
                        queryAndUploadTradeTask.setCookies(cookies);

                        TaskQueneManager.getInstance().addTaskToQuene(queryAndUploadTradeTask);

                    }
                } catch (Exception e22) {
                    e22.printStackTrace();
                }
                i = i2 + 1;
            } else {
                return;
            }
        }

    }


}
