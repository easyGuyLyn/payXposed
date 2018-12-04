package com.regus.pay.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ThreadUtils {
    private static ExecutorService fixedThreadPool = Executors.newScheduledThreadPool(5);

    public static void newThread(Runnable runnable){
        fixedThreadPool.execute(runnable);
    }
}
