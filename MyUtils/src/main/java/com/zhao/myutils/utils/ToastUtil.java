package com.zhao.myutils.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Description: Toast工具,在子线程使用
 */
public class ToastUtil {
    static Handler mHandler;

    /**
     * 在主线程弹出toast,可以设置时长
     */
    public static void showMidToast(final Context context, int resId, int duration) {
        final String msg = context.getString(resId);
        showMidToast(context, msg, duration);
    }

    /**
     * 在主线程弹出toast,可以设置时长
     */
    public static void showMidToast(final Context context, final String msg, final int duration) {
        if (mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper());
        }
        mHandler.post(new Runnable() {
            public void run() {
                Toast toast = Toast.makeText(context.getApplicationContext(), msg, duration);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.show();
            }
        });
    }
}
