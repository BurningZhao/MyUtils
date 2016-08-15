package com.zhao.myutils.presenter;

/**
 * Description: Activity和Fragment的公共逻辑接口
 *
 * @since 16/8/15
 */
public interface Presenter {

    static final String INTENT_TITLE = "INTENT_TITLE";
    static final String INTENT_ID = "INTENT_ID";
    static final String RESULT_DATA = "RESULT_DATA";

    /**
     * 布局资源id
     *
     * @return resId
     */
    int setLayoutResId();

    /**
     * 初始化布局和控件
     */
    void initView();

    /**
     * 初始化布局和控件
     */
    void initData();

    /**
     * 初始化Listener事件监听方法
     */
    void initListener();

    /**
     * 是否存活(已启动且未被销毁)
     */
    boolean isAlive();

    /**
     * 是否在运行
     */
    boolean isRunning();
}
