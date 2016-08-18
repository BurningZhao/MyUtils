package com.zhao.myutils.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Description: Toast工具
 */
public class KeyBoardUtils {

    /**
     * 切换软键盘状态
     */
    public static void toggleSoftInput(Context context) {
        getInputMethodManager(context).toggleSoftInput(
                InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 获取软键盘控制器
     *
     * @return 软键盘控制器
     */
    public static InputMethodManager getInputMethodManager(Context context) {
        Object obj = context.getSystemService(Context.INPUT_METHOD_SERVICE);
        InputMethodManager inputMethodManager = null;
        if (obj instanceof InputMethodManager) {
            inputMethodManager = (InputMethodManager) obj;
        }
        return inputMethodManager;
    }

    /**
     * 显示软键盘
     */
    public static void showSoftInput(Context context, View view) {
        getInputMethodManager(context).showSoftInput(view,
                InputMethodManager.SHOW_FORCED);
    }

    /**
     * 显示软键盘
     */
    public static void showSoftInput(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            showSoftInput(activity, view);
        }
    }

    /**
     * 隐藏软键盘
     */
    public static void hideSoftInput(Context context, View view) {
        getInputMethodManager(context).hideSoftInputFromWindow(
                view.getWindowToken(), 0);
    }

    /**
     * 隐藏软键盘
     */
    public static void hideSoftInput(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            hideSoftInput(activity, view);
        }
    }

    /**
     * 输入法是否激活
     */
    public static boolean isActive(Context context) {
        InputMethodManager imm = getInputMethodManager(context);
        return imm.isActive();
    }
}