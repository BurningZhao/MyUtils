package com.zhao.myutils.base;

import android.app.Application;

import com.zhao.myutils.utils.AppManager;

/**
 * Description: Application基类
 *
 * @author zhaoqingbo
 * @since 2016/4/27
 */
public class BaseApplication extends Application implements
        Thread.UncaughtExceptionHandler {
    // 是否抛出异常
    private boolean isThrowEx = false;
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        ex.printStackTrace();
        if (!isThrowEx && mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            AppManager.quit(getApplicationContext());
        }
    }

    public BaseApplication() {
        super();
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

}
