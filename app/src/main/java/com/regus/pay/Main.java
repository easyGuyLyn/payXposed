package com.regus.pay;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.AppUtils;
import com.regus.pay.util.XLogUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import okhttp3.Call;

import static com.regus.pay.global.ConstantValue.receiver_alipay_error;
import static com.regus.pay.global.ConstantValue.receiver_alipay_inRisk;
import static com.regus.pay.global.ConstantValue.receiver_qr;
import static com.regus.pay.global.ConstantValue.receiver_query_trade;
import static com.regus.pay.global.ConstantValue.receiver_trade;
import static com.regus.pay.global.ConstantValue.receiver_unknown;

/**

 */
public class Main implements IXposedHookLoadPackage {

    private static String e = "";
    private static long f = 0;
    private boolean a = false;
    private boolean b = false; //isTest
    private boolean c = false;//isgen
    private Context mContext;


    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {

        if (!TextUtils.isEmpty(loadPackageParam.packageName) && !TextUtils.isEmpty(loadPackageParam.processName)) {
            String str = loadPackageParam.packageName;
            final String str2 = loadPackageParam.processName;
            if (str.equals(AppUtils.getAppPackageName())) {
                XposedHelpers.findAndHookMethod("com.regus.pay.activity.LoginActivity", loadPackageParam.classLoader, "isModuleActive", new Object[]{XC_MethodReplacement.returnConstant(Boolean.valueOf(true))});
            }
            if (str.equals("com.eg.android.AlipayGphone")) {
                XposedHelpers.findAndHookMethod(Application.class, "attach", new Object[]{Context.class, new XC_MethodHook() {
                    protected void afterHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                        Context context = (Context) methodHookParam.args[0];
                        Main.this.mContext = context;
                        if (!Main.this.a && str2.equals("com.eg.android.AlipayGphone")) {
                            Main.this.a = true;
                            try {
                                BroadcastReceiver aVar = new StartBrocastRec();
                                IntentFilter intentFilter = new IntentFilter();
                                intentFilter.addAction("start");
                                context.registerReceiver(aVar, intentFilter);
                                Main.this.a(context);
                            } catch (Exception e) {
                                Main.this.a = false;
                            }
                        }
                    }
                }});
            }
        }
    }


    class StartBrocastRec extends BroadcastReceiver {
        StartBrocastRec() {
        }

        public void onReceive(final Context context, Intent intent) {
            Object callStaticMethod = XposedHelpers.callStaticMethod(XposedHelpers.findClass("com.alipay.android.widgets.asset.utils.UserInfoCacher", Main.this.mContext.getClassLoader()), "a", new Object[0]);
            if (callStaticMethod != null) {
                XposedHelpers.callMethod(callStaticMethod, "b", new Object[0]);
                try {
                    callStaticMethod = XposedHelpers.findField(callStaticMethod.getClass(), "a").get(callStaticMethod);
                    if (callStaticMethod != null) {
                        CharSequence stringExtra = intent.getStringExtra("name");
                        if (!(stringExtra == null || stringExtra.length() == 0)) {
                            XLogUtil.log("userType=" + XposedHelpers.findField(callStaticMethod.getClass(), "userType").get(callStaticMethod) + ";;;customerType=" + XposedHelpers.findField(callStaticMethod.getClass(), "customerType").get(callStaticMethod));
                            StringBuffer stringBuffer = new StringBuffer();
                            if (XposedHelpers.findField(callStaticMethod.getClass(), "loginEmail").get(callStaticMethod) != null) {
                                stringBuffer.append(XposedHelpers.findField(callStaticMethod.getClass(), "loginEmail").get(callStaticMethod));
                            }
                            if (XposedHelpers.findField(callStaticMethod.getClass(), "loginMobile").get(callStaticMethod) != null) {
                                stringBuffer.append(XposedHelpers.findField(callStaticMethod.getClass(), "loginMobile").get(callStaticMethod));
                            }
                            if (XposedHelpers.findField(callStaticMethod.getClass(), "logonId").get(callStaticMethod) != null) {
                                stringBuffer.append(XposedHelpers.findField(callStaticMethod.getClass(), "logonId").get(callStaticMethod));
                            }
                            if (XposedHelpers.findField(callStaticMethod.getClass(), "mobileNumber").get(callStaticMethod) != null) {
                                stringBuffer.append(XposedHelpers.findField(callStaticMethod.getClass(), "mobileNumber").get(callStaticMethod));
                            }
                            if (XposedHelpers.findField(callStaticMethod.getClass(), "realName").get(callStaticMethod) != null) {
                                stringBuffer.append(XposedHelpers.findField(callStaticMethod.getClass(), "realName").get(callStaticMethod));
                            }
                            if (XposedHelpers.findField(callStaticMethod.getClass(), "realNamed").get(callStaticMethod) != null) {
                                stringBuffer.append(XposedHelpers.findField(callStaticMethod.getClass(), "realNamed").get(callStaticMethod));
                            }
                            if (XposedHelpers.findField(callStaticMethod.getClass(), "userName").get(callStaticMethod) != null) {
                                stringBuffer.append(XposedHelpers.findField(callStaticMethod.getClass(), "userName").get(callStaticMethod));
                            }
                            if (!stringBuffer.toString().contains(stringExtra)) {
                                Intent intent2 = new Intent();
                                intent2.putExtra("type", receiver_alipay_error);
                                intent2.putExtra("values", stringBuffer.toString());
                                intent2.setAction("getResult");
                                context.sendBroadcast(intent2);
                                return;
                            }
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            if (!intent.getBooleanExtra("tasktask", false)) {
                String stringExtra2 = intent.getStringExtra("beizhu");
                if (!TextUtils.isEmpty(stringExtra2)) {
                    String stringExtra3 = intent.getStringExtra("money");
                    if (!TextUtils.isEmpty(stringExtra3)) {
                        Main.this.b = intent.getBooleanExtra("isTest", false);
                        Main.this.c = intent.getBooleanExtra("gen", false);
                        Main.e = intent.getStringExtra("account");
                        XLogUtil.log("beizhu=" + stringExtra2 + ";money=" + stringExtra3);
                        Intent intent3 = new Intent(context, XposedHelpers.findClass("com.alipay.mobile.payee.ui.PayeeQRSetMoneyActivity", context.getClassLoader()));
                        intent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent3.putExtra("beizhu", stringExtra2);
                        intent3.putExtra("money", stringExtra3);
                        context.startActivity(intent3);
                    }
                }
            } else if (System.currentTimeMillis() - Main.f >= 500) {
                Main.f = System.currentTimeMillis();
                XposedHelpers.callStaticMethod(XposedHelpers.findClass("com.alipay.mobile.common.transport.http.CookieAccessHelper", context.getClassLoader()), "flushCookie", new Object[0]);
                final Object callStaticMethod2 = XposedHelpers.callStaticMethod(XposedHelpers.findClass("com.alipay.mobile.common.transport.http.CookieAccessHelper", context.getClassLoader()), "getCookie", new Object[]{"https://mobilegw.alipay.com/mgw.htm"});
                if (callStaticMethod2 == null) {
                    XLogUtil.log("===那啥竟然是空的,不可思议===");
                    return;
                }


                Map<String, String> header = new HashMap<>();
                header.put("Cookie", callStaticMethod2.toString());
                header.put("Referer", "https://render.alipay.com/p/z/merchant-mgnt/simple-order.html?channelType=SQM");

                OkHttpUtils
                        .get()
                        .url("https://mbillexprod.alipay.com/enterprise/walletTradeList.json?lastTradeNo=&lastDate=&pageSize=20&shopId=&_inputcharset=gbk&ctoken&source=&_ksTS="
                                + Main.f + "_" + (Main.f % 9) + "29&_callback=json&_input_charset=utf-8")
                        .headers(header)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                XLogUtil.log("=======ApiException======" + e);
                            }

                            @Override
                            public void onResponse(String str, int id) {
                                try {
                                    if (!TextUtils.isEmpty(str)) {

                                        XLogUtil.log("======MSG-BOX  get 请求成功 onSuccess ======= str:" + str);

                                        if (str.contains("\"status\":\"deny\"")) {
                                            Intent intent = new Intent("android.intent.action.VIEW");
                                            intent.addCategory("android.intent.category.DEFAULT");
                                            intent.setData(Uri.parse("alipays://platformapi/startApp?appId=10000011&url=https://render.alipay.com/p/z/merchant-mgnt/simple-order.html?source=mdb_new_sqm_card"));
                                            Main.this.mContext.startActivity(intent);
                                            AppUtils.launchApp(AppUtils.getAppPackageName());
                                            return;
                                        }
                                        JSONArray jSONArray = JSON.parseObject(str.substring(str.indexOf("(") + 1, str.lastIndexOf(")"))).getJSONObject("result").getJSONArray("list");
                                        if (!jSONArray.isEmpty()) {
                                            Intent intent2 = new Intent();
                                            intent2.putExtra("list", jSONArray.toJSONString());
                                            intent2.putExtra("cookies", callStaticMethod2.toString());
                                            intent2.putExtra("type", receiver_query_trade);
                                            intent2.setAction("getResult");
                                            context.sendBroadcast(intent2);
                                        }
                                    }
                                } catch (Throwable th) {
                                    XposedBridge.log(th);
                                }
                            }
                        });
//
//                EasyHttp.init((Application) Main.this.mContext.getApplicationContext());
//                HttpHeaders httpHeaders = new HttpHeaders();
//                httpHeaders.put(HttpHeaders.HEAD_KEY_COOKIE, callStaticMethod2.toString());
//                httpHeaders.put("Referer", "https://render.alipay.com/p/z/merchant-mgnt/simple-order.html?channelType=SQM");
//                EasyHttp.getInstance().setBaseUrl("https://mbillexprod.alipay.com").addCommonHeaders(httpHeaders);
//
//                EasyHttp.get("/enterprise/walletTradeList.json?lastTradeNo=&lastDate=&pageSize=20&shopId=&_inputcharset=gbk&ctoken&source=&_ksTS=" + Main.f + "_" + (Main.f % 9) + "29&_callback=json&_input_charset=utf-8").execute(new SimpleCallBack<String>() {
//                    @Override
//                    public void onError(ApiException e) {
//                        XLogUtil.log("=======ApiException======" + e);
//                    }
//
//                    @Override
//                    public void onSuccess(String str) {
//                        try {
//                            if (!TextUtils.isEmpty(str)) {
//
//                                XLogUtil.log("======MSG-BOX  get 请求成功 onSuccess ======= str:" + str);
//
//                                if (str.contains("\"status\":\"deny\"")) {
//                                    Intent intent = new Intent("android.intent.action.VIEW");
//                                    intent.addCategory("android.intent.category.DEFAULT");
//                                    intent.setData(Uri.parse("alipays://platformapi/startApp?appId=10000011&url=https://render.alipay.com/p/z/merchant-mgnt/simple-order.html?source=mdb_new_sqm_card"));
//                                    Main.this.mContext.startActivity(intent);
//                                    AppUtils.launchApp(AppUtils.getAppPackageName());
//                                    return;
//                                }
//                                JSONArray jSONArray = JSON.parseObject(str.substring(str.indexOf("(") + 1, str.lastIndexOf(")"))).getJSONObject("result").getJSONArray("list");
//                                if (!jSONArray.isEmpty()) {
//                                    Intent intent2 = new Intent();
//                                    intent2.putExtra("list", jSONArray.toJSONString());
//                                    intent2.putExtra("cookies", callStaticMethod2.toString());
//                                    intent2.putExtra("type", 3);
//                                    intent2.setAction("getResult");
//                                    context.sendBroadcast(intent2);
//                                }
//                            }
//                        } catch (Throwable th) {
//                            XposedBridge.log(th);
//                        }
//                    }
//                });
            }
        }
    }

    private void a(final Context context) {
        Class findClass = XposedHelpers.findClass("com.alipay.mobile.base.security.CI", context.getClassLoader());
        XposedHelpers.findAndHookMethod(findClass, "a", new Object[]{String.class, String.class, String.class, new XC_MethodHook() {
            protected void afterHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                Object result = methodHookParam.getResult();
                XposedHelpers.setBooleanField(result, "a", false);
                methodHookParam.setResult(result);
                super.afterHookedMethod(methodHookParam);
            }
        }});
        XposedHelpers.findAndHookMethod(findClass, "a", new Object[]{Class.class, String.class, String.class, new XC_MethodReplacement() {
            protected Object replaceHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                return Byte.valueOf((byte) 1);
            }
        }});
        XposedHelpers.findAndHookMethod(findClass, "a", new Object[]{ClassLoader.class, String.class, new XC_MethodReplacement() {
            protected Object replaceHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                return Byte.valueOf((byte) 1);
            }
        }});
        XposedHelpers.findAndHookMethod(findClass, "a", new Object[]{new XC_MethodReplacement() {
            protected Object replaceHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                return Boolean.valueOf(false);
            }
        }});
        XposedBridge.hookAllMethods(XposedHelpers.findClass("com.alipay.android.phone.messageboxstatic.biz.sync.e", context.getClassLoader()), "onReceiveMessage", new XC_MethodHook() {
            protected void beforeHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                int i = 0;
                String str = (String) XposedHelpers.findField(methodHookParam.args[0].getClass(), "biz").get(methodHookParam.args[0]);
                String str2 = (String) XposedHelpers.findField(methodHookParam.args[0].getClass(), "msgData").get(methodHookParam.args[0]);
                Intent intent;
                if (str == null) {
                    XLogUtil.log("有点异常!biz" + methodHookParam.args[0]);
                } else if (str2 == null) {
                    XLogUtil.log("有点异常!msgData" + methodHookParam.args[0]);
                } else if (str.equals("MSG-BILL")) {
                    if (str2.contains("到账")) {
                        JSONObject parseObject = JSON.parseObject(JSON.parseObject(str2.substring(1, str2.length() - 1)).getString("pl"));
                        JSONObject parseObject2 = JSON.parseObject(parseObject.getString("content"));
                        JSONArray parseArray = JSON.parseArray(parseObject2.getString("content"));
                        str2 = "未知";
                        str = "空白";
                        while (i < parseArray.size()) {
                            String string = parseArray.getJSONObject(i).getString("title");
                            if (string != null) {
                                if (string.contains("付款人")) {
                                    str2 = parseArray.getJSONObject(i).getString("content");
                                } else if (string.contains("收款理由")) {
                                    str = parseArray.getJSONObject(i).getString("content");
                                }
                            }
                            i++;
                        }
                        String string2 = parseObject.getString("gmtCreate");
                        String string3 = parseObject2.getString("money");
                        String string4 = parseObject2.getString("goto");
                        string4 = string4.substring(string4.indexOf("tradeNO") + 8, string4.indexOf("&bizType"));
                        Intent intent2 = new Intent();
                        intent2.putExtra("time", string2);
                        intent2.putExtra("money", string3);
                        intent2.putExtra("beizhu", str);
                        intent2.putExtra("tradeNo", string4);
                        intent2.putExtra("consumerName", str2.trim());
                        intent2.putExtra("type", receiver_trade);
                        intent2.setAction("getResult");
                        context.sendBroadcast(intent2);
                        XLogUtil.log("到账: tradeNo:" + string4 + "money :" + string3 + "beizhu :" + str + "consumerName :" + str2.trim());
                        return;
                    }
                    XLogUtil.log("不是到账" + methodHookParam.args[0]);
                    intent = new Intent();
                    intent.putExtra("type", receiver_unknown);
                    intent.putExtra("msg", methodHookParam.args[0].toString());
                    intent.setAction("getResult");
                    context.sendBroadcast(intent);
                } else if (str.equals("MSG-BOX")) {
                    if (!str2.contains("到账")) {
                        XLogUtil.log("不是到账" + methodHookParam.args[0]);
                        intent = new Intent();
                        intent.putExtra("type", receiver_unknown);
                        intent.putExtra("msg", methodHookParam.args[0].toString());
                        intent.setAction("getResult");
                        context.sendBroadcast(intent);
                    } else if (System.currentTimeMillis() - Main.f >= 500) {
                        Main.f = System.currentTimeMillis();
                        XposedHelpers.callStaticMethod(XposedHelpers.findClass("com.alipay.mobile.common.transport.http.CookieAccessHelper", context.getClassLoader()), "flushCookie", new Object[0]);
                        final Object callStaticMethod2 = XposedHelpers.callStaticMethod(XposedHelpers.findClass("com.alipay.mobile.common.transport.http.CookieAccessHelper", context.getClassLoader()), "getCookie", new Object[]{"https://mobilegw.alipay.com/mgw.htm"});
                        if (callStaticMethod2 == null) {
                            XLogUtil.log("===那啥竟然是空的,不可思议===");
                            return;
                        }


                        Map<String, String> header = new HashMap<>();
                        header.put("Cookie", callStaticMethod2.toString());
                        header.put("Referer", "https://render.alipay.com/p/z/merchant-mgnt/simple-order.html?channelType=SQM");

                        OkHttpUtils
                                .get()
                                .url("https://mbillexprod.alipay.com/enterprise/walletTradeList.json?lastTradeNo=&lastDate=&pageSize=20&shopId=&_inputcharset=gbk&ctoken&source=&_ksTS="
                                        + Main.f + "_" + (Main.f % 9) + "29&_callback=json&_input_charset=utf-8")
                                .headers(header)
                                .build()
                                .execute(new StringCallback() {
                                    @Override
                                    public void onError(Call call, Exception e, int id) {
                                        XLogUtil.log("=======ApiException======" + e);
                                    }

                                    @Override
                                    public void onResponse(String str, int id) {
                                        try {
                                            if (!TextUtils.isEmpty(str)) {

                                                XLogUtil.log("======MSG-BOX  get 请求成功 onSuccess ======= str:" + str);

                                                if (str.contains("\"status\":\"deny\"")) {
                                                    Intent intent = new Intent("android.intent.action.VIEW");
                                                    intent.addCategory("android.intent.category.DEFAULT");
                                                    intent.setData(Uri.parse("alipays://platformapi/startApp?appId=10000011&url=https://render.alipay.com/p/z/merchant-mgnt/simple-order.html?source=mdb_new_sqm_card"));
                                                    mContext.startActivity(intent);
                                                    AppUtils.launchApp(AppUtils.getAppPackageName());
                                                    return;
                                                }
                                                JSONArray jSONArray = JSON.parseObject(str.substring(str.indexOf("(") + 1, str.lastIndexOf(")"))).getJSONObject("result").getJSONArray("list");
                                                if (!jSONArray.isEmpty()) {
                                                    Intent intent2 = new Intent();
                                                    intent2.putExtra("list", jSONArray.toJSONString());
                                                    intent2.putExtra("cookies", callStaticMethod2.toString());
                                                    intent2.putExtra("type", receiver_query_trade);
                                                    intent2.setAction("getResult");
                                                    context.sendBroadcast(intent2);
                                                }
                                            }
                                        } catch (Throwable th) {
                                            XposedBridge.log(th);
                                        }
                                    }
                                });

//
//                        EasyHttp.init((Application) Main.this.mContext.getApplicationContext());
//                        HttpHeaders httpHeaders = new HttpHeaders();
//                        httpHeaders.put(HttpHeaders.HEAD_KEY_COOKIE, callStaticMethod2.toString());
//                        httpHeaders.put("Referer", "https://render.alipay.com/p/z/merchant-mgnt/simple-order.html?channelType=SQM");
//                        EasyHttp.getInstance().setBaseUrl("https://mbillexprod.alipay.com").addCommonHeaders(httpHeaders);
//
//                        EasyHttp.get("/enterprise/walletTradeList.json?lastTradeNo=&lastDate=&pageSize=20&shopId=&_inputcharset=gbk&ctoken&source=&_ksTS=" + Main.f + "_" + (Main.f % 9) + "29&_callback=json&_input_charset=utf-8").execute(new SimpleCallBack<String>() {
//                            @Override
//                            public void onError(ApiException e) {
//                                XLogUtil.log("=======ApiException======" + e);
//                            }
//
//                            @Override
//                            public void onSuccess(String str) {
//                                try {
//                                    if (!TextUtils.isEmpty(str)) {
//
//                                        XLogUtil.log("======MSG-BOX  get 请求成功 onSuccess ======= str:" + str);
//
//                                        if (str.contains("\"status\":\"deny\"")) {
//                                            Intent intent = new Intent("android.intent.action.VIEW");
//                                            intent.addCategory("android.intent.category.DEFAULT");
//                                            intent.setData(Uri.parse("alipays://platformapi/startApp?appId=10000011&url=https://render.alipay.com/p/z/merchant-mgnt/simple-order.html?source=mdb_new_sqm_card"));
//                                            Main.this.mContext.startActivity(intent);
//                                            AppUtils.launchApp(AppUtils.getAppPackageName());
//                                            return;
//                                        }
//                                        JSONArray jSONArray = JSON.parseObject(str.substring(str.indexOf("(") + 1, str.lastIndexOf(")"))).getJSONObject("result").getJSONArray("list");
//                                        if (!jSONArray.isEmpty()) {
//                                            Intent intent2 = new Intent();
//                                            intent2.putExtra("list", jSONArray.toJSONString());
//                                            intent2.putExtra("cookies", callStaticMethod2.toString());
//                                            intent2.putExtra("type", 3);
//                                            intent2.setAction("getResult");
//                                            context.sendBroadcast(intent2);
//                                        }
//                                    }
//                                } catch (Throwable th) {
//                                    XposedBridge.log(th);
//                                }
//                            }
//                        });
                    }
                } else if (str2.contains("赌博")) {
                    intent = new Intent();
                    intent.putExtra("type", receiver_alipay_inRisk);
                    intent.setAction("getResult");
                    context.sendBroadcast(intent);
                } else {
                    XLogUtil.log("未知[" + methodHookParam.args[0]);
                    intent = new Intent();
                    intent.putExtra("type", receiver_unknown);
                    intent.putExtra("msg", methodHookParam.args[0].toString());
                    intent.setAction("getResult");
                    context.sendBroadcast(intent);
                }
            }
        });
        XposedHelpers.findAndHookMethod("com.alipay.mobile.payee.ui.PayeeQRSetMoneyActivity", context.getClassLoader(), "onCreate", new Object[]{Bundle.class, new XC_MethodHook() {
            protected void afterHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                super.afterHookedMethod(methodHookParam);
                try {
                    Object obj = XposedHelpers.findField(methodHookParam.thisObject.getClass(), "b").get(methodHookParam.thisObject);
                    Object obj2 = XposedHelpers.findField(methodHookParam.thisObject.getClass(), "c").get(methodHookParam.thisObject);
                    Intent intent = ((Activity) methodHookParam.thisObject).getIntent();
                    XposedHelpers.callMethod(obj, "setText", new Object[]{intent.getStringExtra("money")});
                    XposedHelpers.callMethod(obj2, "setText", new Object[]{intent.getStringExtra("beizhu")});
                    ((Button) XposedHelpers.findField(methodHookParam.thisObject.getClass(), "e").get(methodHookParam.thisObject)).performClick();
                } catch (Throwable th) {
                }
            }
        }});
        XposedHelpers.findAndHookMethod("com.alipay.mobile.payee.ui.PayeeQRSetMoneyActivity", context.getClassLoader(), "a", new Object[]{XposedHelpers.findClass("com.alipay.transferprod.rpc.result.ConsultSetAmountRes", context.getClassLoader()), new XC_MethodHook() {
            protected void afterHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                String str = null;
                String str2 = null;
                String str3 = null;
                String str4 = null;
                super.afterHookedMethod(methodHookParam);
                try {
                    str = (String) XposedHelpers.findField(methodHookParam.thisObject.getClass(), "g").get(methodHookParam.thisObject);
                    str2 = (String) XposedHelpers.callMethod(XposedHelpers.findField(methodHookParam.thisObject.getClass(), "c").get(methodHookParam.thisObject), "getUbbStr", new Object[0]);
                    str3 = (String) XposedHelpers.findField(methodHookParam.args[0].getClass(), "printQrCodeUrl").get(methodHookParam.args[0]);
                    str4 = "";
                    Object callStaticMethod = XposedHelpers.callStaticMethod(XposedHelpers.findClass("com.alipay.android.widgets.asset.utils.UserInfoCacher", Main.this.mContext.getClassLoader()), "a", new Object[0]);
                    if (callStaticMethod != null) {
                        XposedHelpers.callMethod(callStaticMethod, "b", new Object[0]);
                        callStaticMethod = XposedHelpers.findField(callStaticMethod.getClass(), "a").get(callStaticMethod);
                        if (callStaticMethod != null) {
                            callStaticMethod = XposedHelpers.findField(callStaticMethod.getClass(), "userId").get(callStaticMethod);
                            if (callStaticMethod != null) {
                                str4 = callStaticMethod.toString();
                            }
                        }
                    } else {
                        XposedBridge.log("==========o == null=======");
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (Throwable th) {
                    return;
                }
                if (str != null && str3 != null) {
                    Intent intent = new Intent();
                    intent.putExtra("money", str);
                    intent.putExtra("beizhu", str2);
                    intent.putExtra("payUrl", str3);
                    intent.putExtra("type", receiver_qr);
                    intent.putExtra("isTest", Main.this.b);
                    intent.putExtra("isGen", Main.this.c);
                    intent.putExtra("ufff", str4);
                    intent.setAction("getResult");
                    context.sendBroadcast(intent);
                }
            }
        }});
    }


}
