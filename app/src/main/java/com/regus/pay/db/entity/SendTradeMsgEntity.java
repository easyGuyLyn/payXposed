package com.regus.pay.db.entity;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.regus.pay.db.config.DbConfig;

@Table(database = DbConfig.class)
public class SendTradeMsgEntity extends BaseModel {

    @PrimaryKey(autoincrement = true)
    private int id;

    /**
     * common
     */

    @Column
    private String mchNo; //商户号

    @Column
    private String account; //操作员

    @Column
    private String token; //token

    @Column
    private long time; //任务发生的时间

    @Column
    private boolean status; //任务完成情况


    /**
     * private
     */

    @Column
    private String money; //金额

    @Column
    private String tradeNo; //交易号

    @Column
    private String consumerName; //消费者名字

    @Column
    private String remark; //备注,详细信息

    @Column
    private String cookies; //cookies


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMchNo() {
        return mchNo;
    }

    public void setMchNo(String mchNo) {
        this.mchNo = mchNo;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCookies() {
        return cookies;
    }

    public void setCookies(String cookies) {
        this.cookies = cookies;
    }
}
