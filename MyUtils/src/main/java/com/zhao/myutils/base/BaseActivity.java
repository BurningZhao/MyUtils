package com.zhao.myutils.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;

/**
 * Description: activity基类，继承FragmentActivity
 *
 * @since 2016/4/25
 */
public abstract class BaseActivity extends FragmentActivity {
    public Click click;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutResId());
        click = new Click();
        initView(savedInstanceState);
        initListener();
        initData(savedInstanceState);
    }

    /**
     * 布局资源id
     *
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

    /**
     * 对view设置监听
     */
    public void setOnClickListener(View view) {
        if (click == null) {
            click = new Click();
        }
        view.setOnClickListener(click);
    }

    /**
     * 对view的点击事件监听
     */
    protected void treatClickEvent(View view) {

    }

    private class Click implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            treatClickEvent(view);
        }

    }
}
