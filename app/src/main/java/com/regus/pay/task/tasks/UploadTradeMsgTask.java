package com.regus.pay.task.tasks;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class UploadTradeMsgTask extends BaseTask {


    protected String tradeNo;//交易号

    protected String consumerName;//消费者名字

    protected long orderTime;//订单时间

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public void setOrderTime(long orderTime) {
        this.orderTime = orderTime;
    }

    @Override
    protected void excuteChildTask() {
        super.excuteChildTask();
        uploadTradeMsg();
    }

    /**
     * 上传抓取好的订单信息
     */
    protected void uploadTradeMsg() {

        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("token", token);

        OkHttpUtils
                .post()
                .url("xxxxxxxx")
                .params(paramsMap)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        isFinish = true;
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        //todo    修改数据表中的....


                        setLastBillFinish();
                    }
                });

    }

}
