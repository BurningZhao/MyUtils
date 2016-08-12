package com.zhao.myutils.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Fragment 的基类
 *
 * @param <T>
 */
public abstract class BaseFragment<T extends Activity> extends Fragment {
    private View mContentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mContentView == null) {
            mContentView = inflater.inflate(setLayoutResId(), container, false);
            initData(savedInstanceState);
            initUI(inflater, container, savedInstanceState);
        } else {
            ViewGroup parent = (ViewGroup) mContentView.getParent();
            parent.removeView(mContentView);
        }

        return mContentView;
    }

    public abstract int setLayoutResId();

    public abstract void initData(Bundle savedInstanceState);

    public abstract void initUI(LayoutInflater inflater, @Nullable ViewGroup container,
                                @Nullable Bundle savedInstanceState);

    public View getContentView() {
        return mContentView;
    }

    public T getRealActivity() {
        Activity a = super.getActivity();
        if (a != null) {
            return (T) a;
        }

        return null;
    }

    public CharSequence getTitle() {
        if (getActivity() != null) {
            return getActivity().getTitle();
        }

        return null;
    }

    public View findViewById(int resId) {
        return mContentView.findViewById(resId);
    }

}
