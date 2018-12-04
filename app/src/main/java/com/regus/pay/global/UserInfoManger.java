package com.regus.pay.global;

public class UserInfoManger {

    private static UserInfoManger mInstance;

    public static UserInfoManger getInstance() {
        if (mInstance == null) {
            synchronized (UserInfoManger.class) {
                if (mInstance == null) {
                    mInstance = new UserInfoManger();
                }
            }
        }
        return mInstance;
    }


    private String token;//token
    private String mchNo;//商户号
    private String merNo;//操作员




    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMchNo() {
        return mchNo;
    }

    public void setMchNo(String mchNo) {
        this.mchNo = mchNo;
    }

    public String getMerNo() {
        return merNo;
    }

    public void setMerNo(String merNo) {
        this.merNo = merNo;
    }





    public void clear() {
        token = null;
        mchNo = null;
        mchNo = null;
    }


}
