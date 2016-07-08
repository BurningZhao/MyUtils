package com.zhao.myutils.base;

import android.app.Application;

import com.zhao.myutils.utils.CrashHandler;

/**
 * Description: Application基类
 *
 * @author zhaoqingbo
 * @since 2016/4/27
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initErrorHandler();
    }

    private void initErrorHandler() {
        CrashHandler handler = CrashHandler.getInstance();
        handler.init(this);
    }
}
