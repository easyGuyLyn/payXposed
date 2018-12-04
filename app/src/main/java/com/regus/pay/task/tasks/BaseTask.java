package com.regus.pay.task.tasks;


import java.io.Serializable;

public abstract class BaseTask implements Serializable {

    protected String money;//金额

    protected String remark;//备注

    protected String mchNo; //商户号

    protected String account; //操作员

    protected String token; //token


    //------------


    protected boolean isFinish; //任务完成情况

    protected boolean isDoing;//正在执行

    protected long startTime = 0; //任务开始跑的时间



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



    public void setFinish(boolean finish) {
        this.isFinish = finish;
    }

    public void setDoing(boolean doing) {
        isDoing = doing;
    }


    /**
     * 执行父任务
     *
     * @return
     */

    public void startTask() {
        isFinish = false;
        isDoing = true;
        startTime = System.currentTimeMillis();
        excuteChildTask();
    }

    /**
     * 执行子任务
     *
     * @return
     */

    protected void excuteChildTask() {

    }

    /**
     * 任务是否结束
     *
     * @return
     */

    public boolean isFinish() {
        return isFinish;
    }

    /**
     * 任务是否正在跑
     *
     * @return
     */

    public boolean isDoing() {
        return isDoing;
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
    public void setLastBillFinish() {

//        if (tv_lastBill != null) {
//            PayApplication.handler.post(new Runnable() {
//                @Override
//                public void run() {
//                    tv_lastBill.setText("上次任务结束时间:" + new SimpleDateFormat("HH:mm:ss").format(new Date()));
//                }
//            });
//        }
        isFinish = true;
    }


}
