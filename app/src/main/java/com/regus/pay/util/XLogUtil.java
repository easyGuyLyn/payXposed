package com.regus.pay.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.robv.android.xposed.XposedBridge;

public class XLogUtil {


    /**
     * log
     *
     * @param obj
     */
    public static void log(Object obj) {
        XposedBridge.log("xposed debug  " + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "  [" + obj.toString() + "]");
    }



}
