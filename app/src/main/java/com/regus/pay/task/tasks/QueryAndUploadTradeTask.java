package com.regus.pay.task.tasks;

import android.text.TextUtils;

import com.regus.pay.util.ThreadUtils;
import com.regus.pay.util.XLogUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request.Builder;

public class QueryAndUploadTradeTask extends UploadTradeMsgTask {

    private String cookies;//支付宝用户cookie

    public void setCookies(String cookies) {
        this.cookies = cookies;
    }

    @Override
    protected void excuteChildTask() {
        super.excuteChildTask();
        queryTradeDetail();
    }

    /**
     * 查询 订单详情
     */
    private void queryTradeDetail() {

        if (TextUtils.isEmpty(cookies)) {
            XLogUtil.log("查询订单时，cookie 为空");
            return;
        }

        Map<String, String> header = new HashMap<>();
        header.put("Cookie", cookies);

        final Call newCall = new OkHttpClient().newCall(new Builder().url("https://tradeeportlet.alipay.com/wireless/tradeDetail.htm?tradeNo=" + tradeNo).addHeader("Cookie", cookies).build());

        ThreadUtils.newThread(new Runnable() {
            @Override
            public void run() {
                try {

                    Iterator it = Jsoup.parse(newCall.execute().body().string()).getElementsByTag("trade-info-item").iterator();
                    while (it.hasNext()) {
                        Element element = (Element) it.next();
                        String w = element.text();
                        if (w.contains("说") && w.contains("明")) {
                            remark = (element.getElementsByTag("trade-info-value").get(0)).html();
                            break;
                        }
                    }

                    //todo  数据库更新


//                    b bVar = (b) SQLite.select(new IProperty[0]).from(b.class).where(c.e.eq(a.this.d)).and(c.g.eq(Integer.valueOf(0))).querySingle();
//                    if (bVar != null) {
//                        bVar.e(a.this.f);
//                        bVar.a(1);
//                        bVar.update();
//                    }

                    uploadTradeMsg();


                } catch (Throwable th) {
                    // 设置 订单任务结束
                    setLastBillFinish();

                    th.printStackTrace();
                }
            }
        });
    }


}
