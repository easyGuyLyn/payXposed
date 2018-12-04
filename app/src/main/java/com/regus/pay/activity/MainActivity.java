package com.regus.pay.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.KeyEvent;

import com.dhh.websocket.RxWebSocket;
import com.dhh.websocket.WebSocketSubscriber;
import com.regus.pay.R;
import com.regus.pay.task.TaskQueneManager;
import com.regus.pay.util.ControlTaskUtils;
import com.regus.pay.util.XLogUtil;


import okhttp3.WebSocket;
import okio.ByteString;
import rx.Subscription;

import static com.regus.pay.global.ConstantValue.receiver_alipay_error;
import static com.regus.pay.global.ConstantValue.receiver_alipay_inRisk;
import static com.regus.pay.global.ConstantValue.receiver_qr;
import static com.regus.pay.global.ConstantValue.receiver_query_trade;
import static com.regus.pay.global.ConstantValue.receiver_trade;
import static com.regus.pay.global.ConstantValue.receiver_unknown;

public class MainActivity extends BaseActivity {


    private Subscription mSubscription;


    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {
        initWebSocket();

    }


    /**
     * 初始化 webSocket
     */
    private void initWebSocket() {

        mSubscription = RxWebSocket.get("ws://10.20.176.123:12346")
                .subscribe(new WebSocketSubscriber() {
                    @Override
                    public void onOpen(@NonNull WebSocket webSocket) {
                        XLogUtil.log("MainActivity webSocket onOpen");
                    }

                    @Override
                    public void onMessage(@NonNull String text) {
                        XLogUtil.log("MainActivity onMessage onMessage : " + text);

                    }

                    @Override
                    public void onMessage(@NonNull ByteString byteString) {

                    }

                    @Override
                    protected void onReconnect() {
                        XLogUtil.log("MainActivity onReconnect");
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        XLogUtil.log("MainActivity webSocket onError: " + e.getMessage());
                    }
                });

    }

    /**
     * close socket
     */
    private void shoutDownWebSocket() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }


    /**
     * 业务接收  执行任务
     */
    private class exeuteTaskBroadcastRv extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            int typeValue = intent.getIntExtra("type", 0);

            switch (typeValue) {
                case receiver_qr:
                    TaskQueneManager.getInstance().endTopTask();
                    ControlTaskUtils.upLoadQrInfo(intent);
                    break;
                case receiver_trade:
                    ControlTaskUtils.receiveTradeInfo(intent);
                    break;
                case receiver_query_trade:
                    ControlTaskUtils.queryTradeDetail(intent);
                    break;
                case receiver_alipay_error:


                    break;
                case receiver_alipay_inRisk:


                    break;
                case receiver_unknown:


                    break;


            }


        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4) {
            return super.onKeyDown(keyCode, event);
        }
        moveTaskToBack(true);
        return false;
    }

    @Override
    protected void onDestroy() {

        shoutDownWebSocket();
        super.onDestroy();

    }
}
