package com.regus.pay.global;

/**
 * 常量
 */

public interface ConstantValue {

    /**
     * network  error
     */
    String EVENT_TYPE_NETWORK_EXCEPTION = "EVENT_TYPE_NETWORK_EXCEPTION";


    /**
     * sp  用户配置信息
     */
    String SP_APP_INFO = "ttc_pay";


    /**
     * 广播 类型
     */

    int receiver_qr = 1; //上传二维码
    int receiver_trade = 2; //生产订单
    int receiver_query_trade = 3; //查询并生产订单
    int receiver_alipay_error = 10; //账户与绑定的支付宝账号不对应
    int receiver_alipay_inRisk = 110; //支付宝风控
    int receiver_unknown = 3652; //其他问题


}
