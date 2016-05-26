package com.zhao.myutils.utils;

import android.content.Context;
import android.os.Process;

/**
 * Description: 处理 uncheched Exception 导致程序的crash
 *
 * @author zhaoqingbo
 * @since 2016/5/26
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = CrashHandler.class.getSimpleName();

    private static CrashHandler instance = new CrashHandler();
    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    public static CrashHandler getInstance() {
        if (instance == null) {
            synchronized (CrashHandler.class) {
                if (instance == null) {
                    instance = new CrashHandler();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        mContext = context;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        // 自定义错误处理
        boolean isTreat = handleException(ex);
        if (!isTreat && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            System.gc();
            // 退出程序
            Process.killProcess(Process.myPid());
        }
    }


    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     * 开发者可以根据自己的情况来自定义异常处理逻辑
     *
     * @param ex Throwable
     * @return true:如果处理了该异常信息;否则返回false
     */
    private boolean handleException(final Throwable ex) {
        if (ex == null) {
            return false;
        }
        // TODO 处理异常信息
//        new Thread() {
//
//            @Override
//            public void run() {
//                Looper.prepare();
//                ex.printStackTrace();
//                String err = "[" + ex.getMessage() + "]";
//                Toast.makeText(mContext, "程序出现异常." + err, Toast.LENGTH_LONG)
//                        .show();
//                Looper.loop();
//            }
//
//        }.start();

        return true;
    }
}
