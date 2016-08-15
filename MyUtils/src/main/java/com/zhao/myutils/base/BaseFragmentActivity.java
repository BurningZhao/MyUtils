package com.zhao.myutils.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.zhao.myutils.R;
import com.zhao.myutils.manager.ThreadManager;
import com.zhao.myutils.presenter.ActivityPresenter;
import com.zhao.myutils.utils.KeyBoardUtils;
import com.zhao.myutils.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: activity基类，继承FragmentActivity
 *
 * @since 2016/4/25
 */
public abstract class BaseFragmentActivity extends FragmentActivity
        implements ActivityPresenter {
    private static final String TAG = BaseFragmentActivity.class.getSimpleName();

    protected BaseFragmentActivity mContext = null;
    /**
     * 该Activity的界面，即contentView
     * <p>
     * 不能在子类中创建
     */
    protected View view = null;
    /**
     * 布局解释器
     * <p>
     * 不能在子类中创建
     */
    protected LayoutInflater inflater = null;
    /**
     * Activity 是否alive
     */
    private boolean isAlive = false;
    /**
     * 进度弹窗
     */
    protected ProgressDialog progressDialog = null;
    /**
     * activity退出时隐藏软键盘需要，需要在调用finish方法前赋值
     */
    protected View toGetWindowTokenView = null;
    /**
     * 线程名列表
     */
    protected List<String> threadNameList;

    /**
     * 退出时之前的界面进入动画,可在finish();前通过改变它的值来改变动画效果
     */
    protected int enterAnim = R.anim.fade;
    /**
     * 退出时该界面动画,可在finish();前通过改变它的值来改变动画效果
     */
    protected int exitAnim = R.anim.right_push_out;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        inflater = getLayoutInflater();
        isAlive = true;
        threadNameList = new ArrayList<>();
        setContentView(setLayoutResId());
    }

    /**
     * 设置该Activity界面布局
     *
     * @param layoutResID
     */
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        view = inflater.inflate(layoutResID, null);
        initView();
        initData();
        initListener();
    }

    /**
     * 通过id查找并获取控件，并setOnClickListener
     *
     * @param id
     * @param clickListener
     * @return view
     */
    @SuppressWarnings("unchecked")
    public <V extends View> V findViewById(int id, View.OnClickListener clickListener) {
        V v = (V) findViewById(id);
        v.setOnClickListener(clickListener);
        return v;
    }

    /**
     * 展示加载进度条,无标题
     *
     * @param stringResId
     */
    public void showProgressDialog(int stringResId) {
        showProgressDialog(null, mContext.getResources().getString(stringResId));
    }

    /**
     * 展示加载进度条,无标题
     *
     * @param dialogMessage
     */
    public void showProgressDialog(String dialogMessage) {
        showProgressDialog(null, dialogMessage);
    }

    /**
     * 展示加载进度条
     *
     * @param dialogTitle   标题
     * @param dialogMessage 信息
     */
    public void showProgressDialog(final String dialogTitle, final String dialogMessage) {
        runUiThread(new Runnable() {
            @Override
            public void run() {
                if (progressDialog == null) {
                    progressDialog = new ProgressDialog(mContext);
                }
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                if (dialogTitle != null && !"".equals(dialogTitle.trim())) {
                    progressDialog.setTitle(dialogTitle);
                }
                if (dialogMessage != null && !"".equals(dialogMessage.trim())) {
                    progressDialog.setMessage(dialogMessage);
                }
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
            }
        });
    }

    /**
     * 隐藏加载进度
     */
    public void dismissProgressDialog() {
        runUiThread(new Runnable() {
            @Override
            public void run() {
                //把判断写在runOnUiThread外面导致有时dismiss无效，可能不同线程判断progressDialog.isShowing()结果不一致
                if (progressDialog == null || !progressDialog.isShowing()) {
                    LogUtil.w(TAG, "dismissProgressDialog  progressDialog == null"
                            + " || progressDialog.isShowing() == false >> return;");
                    return;
                }
                progressDialog.dismiss();
            }
        });
    }

    /**
     * 在UI线程中运行，建议用这个方法代替runOnUiThread
     *
     * @param action Runnable
     */
    public final void runUiThread(Runnable action) {
        if (isAlive()) {
            runOnUiThread(action);
        }
    }

    /**
     * 运行线程
     *
     * @param name
     * @param runnable
     * @return
     */
    public final Handler runThread(String name, Runnable runnable) {
        if (!isAlive()) {
            LogUtil.w(TAG, "runThread  isAlive() == false >> return null;");
            return null;
        }
        name = name.trim();
        Handler handler = ThreadManager.getInstance().runThread(name, runnable);
        if (handler == null) {
            LogUtil.e(TAG, "runThread handler == null >> return null;");
            return null;
        }

        if (!threadNameList.contains(name)) {
            threadNameList.add(name);
        }
        return handler;
    }

    @Override
    public final boolean isAlive() {
        return isAlive && mContext != null;
    }

    /**
     * 打开新的Activity，向左滑入效果
     *
     * @param intent
     */
    public void startActivityWithAnimation(final Intent intent) {
        startActivityWithAnimation(intent, true);
    }

    /**
     * 打开新的Activity
     *
     * @param intent
     * @param showAnimation
     */
    public void startActivityWithAnimation(final Intent intent, final boolean showAnimation) {
        startActivityWithAnimation(intent, -1, showAnimation);
    }

    /**
     * 打开新的Activity，向左滑入效果
     *
     * @param intent
     * @param requestCode
     */
    public void startActivityWithAnimation(final Intent intent, final int requestCode) {
        startActivityWithAnimation(intent, requestCode, true);
    }

    /**
     * 打开新的Activity
     *
     * @param intent
     * @param requestCode
     * @param showAnimation
     */
    public void startActivityWithAnimation(final Intent intent, final int requestCode, final boolean showAnimation) {
        runUiThread(new Runnable() {
            @Override
            public void run() {
                if (intent == null) {
                    Log.w(TAG, "toActivity  intent == null >> return;");
                    return;
                }
                //fragment中使用context.startActivity会导致在fragment中不能正常接收onActivityResult
                if (requestCode < 0) {
                    startActivity(intent);
                } else {
                    startActivityForResult(intent, requestCode);
                }
                if (showAnimation) {
                    overridePendingTransition(R.anim.right_push_in, R.anim.hold);
                } else {
                    overridePendingTransition(R.anim.null_anim, R.anim.null_anim);
                }
            }
        });
    }

    @Override
    public void finish() {
        super.finish();//必须写在最前才能显示自定义动画
        runUiThread(new Runnable() {
            @Override
            public void run() {
                if (toGetWindowTokenView != null) {
                    KeyBoardUtils.hideSoftInput(mContext, toGetWindowTokenView);
                }
                if (enterAnim > 0 && exitAnim > 0) {
                    try {
                        overridePendingTransition(enterAnim, exitAnim);
                    } catch (Exception e) {
                        LogUtil.e(TAG, "finish overridePendingTransition(enterAnim, exitAnim): " + e.getMessage());
                    }
                }
            }
        });
    }

    /**
     * 销毁并回收内存
     * <p>
     * 子类如果要使用这个方法内用到的变量，应重写onDestroy方法并在super.onDestroy();前操作
     */
    @Override
    protected void onDestroy() {
        dismissProgressDialog();
        ThreadManager.getInstance().destroyThread(threadNameList);
        if (view != null) {
            try {
                view.destroyDrawingCache();
            } catch (Exception e) {
                Log.w(TAG, "onDestroy  try { view.destroyDrawingCache();" +
                        " >> } catch (Exception e) {\n" + e.getMessage());
            }
        }
        isAlive = false;
        super.onDestroy();
        inflater = null;
        view = null;
        toGetWindowTokenView = null;
        progressDialog = null;
        threadNameList = null;
        mContext = null;
    }
}
