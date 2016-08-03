package com.zhao.myutils.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.Toast;

/**
 * An EditText with length limit
 */
public class NumberLimitEditText extends EditText {
    public static final int DEFAULT_TEXT_LIMIT_COUNT = 256;
    public static final String DEFAULT_LIMIT_TEXT = "Word reached the maximum length";

    private Context mContext;

    /**
     * The text shown after text size out of limit.
     */
    private String mToastStr;

    /**
     * The limit count of input text
     */
    private int mTextLimitCount;

    public NumberLimitEditText(Context context) {
        this(context, null);
    }

    public NumberLimitEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberLimitEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        mTextLimitCount = DEFAULT_TEXT_LIMIT_COUNT;
        mToastStr = DEFAULT_LIMIT_TEXT;
    }

    @Override
    public boolean onPreDraw() {
        if (getText().length() > mTextLimitCount) {
            setText(getText().subSequence(0, mTextLimitCount));
            setSelection(mTextLimitCount);
            Toast.makeText(mContext, mToastStr, Toast.LENGTH_SHORT).show();
        }
        return super.onPreDraw();
    }

    public String getmToastStr() {
        return mToastStr;
    }

    public void setmToastStr(String mToastStr) {
        this.mToastStr = mToastStr;
    }

    public int getmTextLimitCount() {
        return mTextLimitCount;
    }

    public void setmTextLimitCount(int mTextLimitCount) {
        this.mTextLimitCount = mTextLimitCount;
    }
}
