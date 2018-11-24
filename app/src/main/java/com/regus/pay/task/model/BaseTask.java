package com.regus.pay.task.model;


import android.widget.TextView;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseTask implements Serializable {

    protected String money;//金额

    protected String remark;//备注

    protected String mchNo; //商户号

    protected String account; //操作员

    protected String token; //token

    protected long time; //任务下发的服务器时间

    protected boolean isFinish; //任务完成情况

    protected boolean isDoing;//正在执行

    protected long startTime = 0; //任务开始跑的时间

    protected TextView tv_lastBill; // 上次到账时间


    public void setMoney(String money) {
        this.money = money;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setMchNo(String mchNo) {
        this.mchNo = mchNo;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setFinish(boolean finish) {
        this.isFinish = finish;
    }

    public void setDoing(boolean doing) {
        isDoing = doing;
    }


    public void setTv_lastBill(TextView tv_lastBill) {
        this.tv_lastBill = tv_lastBill;
    }

    /**
     * 任务是否结束
     * @return
     */

    public boolean isFinish() {
        return isFinish;
    }

    /**
     * 任务是否正在跑
     * @return
     */

    public boolean isDoing() {
        return isDoing;
    }



    /**
     * 设置 任务在跑中
     *
     * @return
     */
    public void setOnDoing() {
        isFinish = false;
        isDoing = true;
        startTime = System.currentTimeMillis();
    }


    /**
     * 是否超时
     *
     * @return
     */
    public boolean isTaskOverTime() {
        return System.currentTimeMillis() - startTime > 10000 && startTime != 0;
    }


    /**
     * 设置 订单任务结束
     */
    public void setLastBillTime() {
        if (tv_lastBill != null) {
            tv_lastBill.setText("上次任务结束时间:" + new SimpleDateFormat("HH:mm:ss").format(new Date()));
        }
        isFinish = true;
    }

}
