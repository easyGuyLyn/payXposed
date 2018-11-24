package com.regus.pay.util;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;

public class CommonUtil {

    @SuppressLint("WrongConstant")
    public static boolean isAppRunning(Context context, String str) {
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses()) {
            String str2 = runningAppProcessInfo.processName;
            if (str2 != null && str2.equals(str)) {
                return true;
            }
        }
        return false;
    }


}
