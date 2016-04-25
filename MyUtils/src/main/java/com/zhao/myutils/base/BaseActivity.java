package com.zhao.myutils.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

/**
 * Description: activity基类，继承FragmentActivity
 *
 * @since 2016/4/25
 */
public abstract class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutResId());
        initView(savedInstanceState);
        initListener();
        initData(savedInstanceState);
    }

    /**
     * 布局资源id
     * @return resId
     */
    public abstract int setLayoutResId();

    /**
     * 初始化布局和控件
     */
    protected abstract void initView(Bundle bundle);

    /**
     * 初始化监听
     */
    protected abstract void initListener();

    /**
     * 初始化数据
     */
    protected abstract void initData(Bundle bundle);

}
