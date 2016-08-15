package com.zhao.myutils.utils;

import android.util.Log;

/**
 * log 输出控制工具, 默认显示Log.DEBUG之上的
 *
 * @author zhaoqingbo
 */
public class LogUtil {
    public static int level = Log.DEBUG;

    public static void d(String tag, String message) {
        if (level <= Log.DEBUG) {
            Log.d(tag, message);
        }
    }

    public static void i(String tag, String message) {
        if (level <= Log.INFO) {
            Log.i(tag, message);
        }
    }

    public static void e(String tag, String message) {
        if (level <= Log.ERROR) {
            Log.e(tag, message);
        }
    }

    public static void w(String tag, String message) {
        if (level <= Log.WARN) {
            Log.w(tag, message);
        }
    }

    public static int getDebugLevel() {
        return level;
    }

    public static void setDebugLevel(int debugLevel) {
        level = debugLevel;
    }
}
