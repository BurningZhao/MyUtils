package com.zhao.myutils.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.zhao.myutils.R;
import com.zhao.myutils.manager.ThreadManager;
import com.zhao.myutils.presenter.ActivityPresenter;
import com.zhao.myutils.utils.KeyBoardUtils;
import com.zhao.myutils.utils.LogUtil;
import com.zhao.myutils.utils.PermissionUtils;

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
     * <p/>
     * 不能在子类中创建
     */
    protected View mContentView = null;
    /**
     * 布局解释器
     * <p/>
     * 不能在子类中创建
     */
    protected LayoutInflater mInflater = null;
    /**
     * Activity 是否alive
     */
    private boolean mIsAlive = false;
    /**
     * 进度弹窗
     */
    protected ProgressDialog mProgressDialog = null;
    /**
     * activity退出时隐藏软键盘需要，需要在调用finish方法前赋值
     */
    protected View mWindowTokenView = null;
    /**
     * 线程名列表
     */
    protected List<String> mThreadNameList;

    /**
     * 退出时之前的界面进入动画,可在finish();前通过改变它的值来改变动画效果
     */
    protected int mEnterAnim = R.anim.fade;
    /**
     * 退出时该界面动画,可在finish();前通过改变它的值来改变动画效果
     */
    protected int mExitAnim = R.anim.right_push_out;

    /**
     * 权限回调listener
     */
    private PermissionListener mListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        mInflater = getLayoutInflater();
        mIsAlive = true;
        mThreadNameList = new ArrayList<>();
        setContentView(setLayoutResId());
    }

    /**
     * 设置该Activity界面布局
     *
     * @param layoutResID
     */
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        mContentView = mInflater.inflate(layoutResID, null);
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
                if (mProgressDialog == null) {
                    mProgressDialog = new ProgressDialog(mContext);
                }
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                if (dialogTitle != null && !"".equals(dialogTitle.trim())) {
                    mProgressDialog.setTitle(dialogTitle);
                }
                if (dialogMessage != null && !"".equals(dialogMessage.trim())) {
                    mProgressDialog.setMessage(dialogMessage);
                }
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.show();
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
                if (mProgressDialog == null || !mProgressDialog.isShowing()) {
                    LogUtil.w(TAG, "dismissProgressDialog  progressDialog == null"
                            + " || progressDialog.isShowing() == false >> return;");
                    return;
                }
                mProgressDialog.dismiss();
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

        if (!mThreadNameList.contains(name)) {
            mThreadNameList.add(name);
        }
        return handler;
    }

    @Override
    public final boolean isAlive() {
        return mIsAlive && mContext != null;
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
                if (mWindowTokenView != null) {
                    KeyBoardUtils.hideSoftInput(mContext, mWindowTokenView);
                }
                if (mEnterAnim > 0 && mExitAnim > 0) {
                    try {
                        overridePendingTransition(mEnterAnim, mExitAnim);
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
        ThreadManager.getInstance().destroyThread(mThreadNameList);
        if (mContentView != null) {
            try {
                mContentView.destroyDrawingCache();
            } catch (Exception e) {
                Log.w(TAG, "onDestroy  try { view.destroyDrawingCache();" +
                        " >> } catch (Exception e) {\n" + e.getMessage());
            }
        }
        super.onDestroy();
        mIsAlive = false;
        mInflater = null;
        mContentView = null;
        mWindowTokenView = null;
        mProgressDialog = null;
        mThreadNameList = null;
        mContext = null;
    }

    /**
     * 请求权限
     *
     * @param permissions 权限列表
     * @param listener    回调
     */
    protected void requestPermission(String[] permissions, PermissionListener listener) {
        if (PermissionUtils.hasSelfPermissions(this, permissions)) {
            listener.onGranted();
        } else {
            mListener = listener;
            ActivityCompat.requestPermissions(this, permissions, 001);
        }
    }


    /**
     * 权限请求结果
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (mListener == null) {
            return;
        }

        if (PermissionUtils.getTargetSdkVersion(this) < Build.VERSION_CODES.M
                && !PermissionUtils.hasSelfPermissions(this, permissions)) {
            mListener.onDenied();
            return;
        }

        if (PermissionUtils.verifyPermissions(grantResults)) {
            mListener.onGranted();
        } else {
            if (!PermissionUtils.shouldShowRequestPermissionRationale(this, permissions)) {
                if (!mListener.onNeverAsk()) {
                    Toast.makeText(this, R.string.give_permission_in_settings, Toast.LENGTH_SHORT).show();
                }

            } else {
                mListener.onDenied();
            }
        }
    }

    /**
     * 权限回调接口
     */
    public abstract class PermissionListener {
        /**
         * 权限通过
         */
        public abstract void onGranted();

        /**
         * 权限拒绝
         */
        public void onDenied() {
        }

        /**
         * 不再询问
         *
         * @return 如果要覆盖原有提示则返回true
         */
        public boolean onNeverAsk() {
            return false;
        }
    }
}
