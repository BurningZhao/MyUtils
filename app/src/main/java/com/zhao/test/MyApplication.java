package com.zhao.test;

import com.zhao.myutils.base.BaseApplication;

/**
 *
 * Created by qingbo on 16/8/11.
 */
public class MyApplication extends BaseApplication {

    private static MyApplication mInstance;

    public static MyApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
}
