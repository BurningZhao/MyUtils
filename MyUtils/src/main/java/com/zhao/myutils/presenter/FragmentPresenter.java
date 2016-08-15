package com.zhao.myutils.presenter;

import android.app.Activity;

/**
 * Description:Fragment的逻辑接口
 *
 * @since 16/8/15
 */
public interface FragmentPresenter extends Presenter {
    /**
     * 该Fragment在Activity添加的所有Fragment中的位置
     */
    static final String ARGUMENT_POSITION = "ARGUMENT_POSITION";

    static final int RESULT_OK = Activity.RESULT_OK;
    static final int RESULT_CANCELED = Activity.RESULT_CANCELED;
}
