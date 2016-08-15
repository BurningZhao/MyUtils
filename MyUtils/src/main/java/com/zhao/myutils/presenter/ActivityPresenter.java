package com.zhao.myutils.presenter;

import android.support.annotation.NonNull;

import com.zhao.myutils.base.BaseFragmentActivity;

/**
 * Description: Activity的公共逻辑接口
 *
 * @author qingbo
 * @since 16/8/15
 */
public interface ActivityPresenter extends Presenter {
    /**
     * 获取Activity
     *
     * @return BaseFragmentActivity，因为非BaseFragmentActivity的子类不需要这个方法
     * 在非抽象Activity中 return this;
     */
    @NonNull
    public BaseFragmentActivity getActivity();
}
