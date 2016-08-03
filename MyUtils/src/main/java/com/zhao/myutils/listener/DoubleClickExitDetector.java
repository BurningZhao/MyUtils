package com.zhao.myutils.listener;

import android.content.Context;
import android.widget.Toast;

/**
 * Description: 双击退出app，默认是2s内
 *
 */
public class DoubleClickExitDetector {
    public static final String DEFAULT_HINT_MESSAGE = "再按一次退出程序";
    public static final long DEFAULT_SPACE_TIME = 2000L;
    private long mSpaceTime;
    private long mLastClickTime;
    private String mHintMessage;
    private Context mContext;

    public DoubleClickExitDetector(Context context) {
        this(context, DEFAULT_HINT_MESSAGE, DEFAULT_SPACE_TIME);
    }

    public DoubleClickExitDetector(Context context, String hintMessage) {
        this(context, hintMessage, DEFAULT_SPACE_TIME);
    }

    public DoubleClickExitDetector(Context context, String hintMessage, long space_time) {
        this.mContext = context;
        this.mHintMessage = hintMessage;
        this.mSpaceTime = space_time;
    }

    public boolean onClick() {
        long currentTime = System.currentTimeMillis();
        boolean result = (currentTime - mLastClickTime) < mSpaceTime;
        mLastClickTime = currentTime;
        if (!result) {
            Toast.makeText(mContext, mHintMessage, Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    public void setSpaceTime(long spaceTime) {
        this.mSpaceTime = spaceTime;
    }

    public void setHintMessage(String hintMessage) {
        this.mHintMessage = hintMessage;
    }
}