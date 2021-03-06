package com.zhao.myutils.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zhao.myutils.R;
import com.zhao.myutils.presenter.FragmentPresenter;
import com.zhao.myutils.utils.LogUtil;
import com.zhao.myutils.utils.PermissionUtils;

/**
 * Fragment 的基类
 *
 * @param <T>
 */
public abstract class BaseFragment<T extends Activity> extends Fragment
        implements FragmentPresenter {
    private static final String TAG = BaseFragment.class.getSimpleName();
    private static final int REQUEST_CODE = 1;

    /**
     * 添加该Fragment的Activity
     * <p>
     * 不能在子类中创建
     */
    protected BaseCompatActivity mContext = null;
    protected LayoutInflater mInflater = null;
    private View mContentView;
    private boolean mIsAlive;

    /**
     * 该Fragment在Activity添加的所有Fragment中的位置，通过ARGUMENT_POSITION设置
     * <p>
     * 只使用getPosition方法来获取position，保证position正确
     */
    private int mPosition = -1;
    protected Bundle mBundle;

    /**
     * 权限回调listener
     */
    private PermissionListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mContentView == null) {
            mContentView = inflater.inflate(setLayoutResId(), container, false);
            initView();
            initListener();
        } else {
            ViewGroup parent = (ViewGroup) mContentView.getParent();
            parent.removeView(mContentView);
        }

        return mContentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    /**
     * 销毁并回收内存
     * <p>
     * 子类如果要使用这个方法内用到的变量，应重写onDestroy方法并在super.onDestroy();前操作
     */
    @Override
    public void onDestroy() {
        dismissProgressDialog();
        if (mContentView != null) {
            try {
                mContentView.destroyDrawingCache();
            } catch (Exception e) {
                LogUtil.w(TAG, "onDestroy  view.destroyDrawingCache() \n" + e.getMessage());
            }
        }
        super.onDestroy();

        mIsAlive = false;
        mContentView = null;
        mInflater = null;
        mContext = null;
    }

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

    /**
     * 获取该Fragment在Activity添加的所有Fragment中的位置
     */
    public int getPosition() {
        if (mPosition < 0) {
            mBundle = getArguments();
            if (mBundle != null) {
                mPosition = mBundle.getInt(ARGUMENT_POSITION, mPosition);
            }
        }
        return mPosition;
    }

    @Override
    public final boolean isAlive() {
        return mIsAlive && mContext != null;// & ! isRemoving();导致finish，onDestroy内runUiThread不可用
    }

    /**
     * 通过id查找并获取控件，使用时不需要强转
     *
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    public <V extends View> V findViewById(int id) {
        return (V) mContentView.findViewById(id);
    }

    /**
     * 通过id查找并获取控件，并setOnClickListener
     *
     * @param id
     * @param onClickListener
     * @return view
     */
    public <V extends View> V findViewById(int id, View.OnClickListener onClickListener) {
        V v = findViewById(id);
        v.setOnClickListener(onClickListener);
        return v;
    }

    public Intent getIntent() {
        return mContext.getIntent();
    }

    /**
     * 在UI线程中运行，建议用这个方法代替runOnUiThread
     *
     * @param action
     */
    public final void runUiThread(Runnable action) {
        if (isAlive()) {
            mContext.runUiThread(action);
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
        //name, runnable);同一Activity出现多个同名Fragment可能会出错
        return mContext.runThread(name + getPosition(), runnable);
    }

    /**
     * 展示加载进度条,无标题
     *
     * @param stringResId
     */
    public void showProgressDialog(final int stringResId) {
        mContext.showProgressDialog(mContext.getResources().getString(stringResId));
    }

    /**
     * 展示加载进度条,无标题
     *
     * @param dialogMessage
     */
    public void showProgressDialog(final String dialogMessage) {
        mContext.showProgressDialog(dialogMessage);
    }

    /**
     * 展示加载进度条
     *
     * @param dialogTitle   标题
     * @param dialogMessage 信息
     */
    public void showProgressDialog(final String dialogTitle, final String dialogMessage) {
        mContext.showProgressDialog(dialogTitle, dialogMessage);
    }

    /**
     * 隐藏加载进度
     */
    public void dismissProgressDialog() {
        mContext.dismissProgressDialog();
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
                    Log.w(TAG, "startActivityWithAnimation  intent == null >> return;");
                    return;
                }
                //fragment中使用context.startActivity会导致在fragment中不能正常接收onActivityResult
                if (requestCode < 0) {
                    startActivity(intent);
                } else {
                    startActivityForResult(intent, requestCode);
                }
                if (showAnimation) {
                    mContext.overridePendingTransition(R.anim.right_push_in, R.anim.hold);
                } else {
                    mContext.overridePendingTransition(R.anim.null_anim, R.anim.null_anim);
                }
            }
        });
    }


    /**
     * 请求权限
     *
     * @param permissions 权限列表
     * @param listener    回调
     */
    protected void requestPermission(String[] permissions, PermissionListener listener) {
        if (PermissionUtils.hasSelfPermissions(getActivity(), permissions)) {
            listener.onGranted();
        } else {
            mListener = listener;
            ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_CODE);
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

        if (requestCode == REQUEST_CODE) {
            if (mListener == null) {
                return;
            }

            if (PermissionUtils.getTargetSdkVersion(getActivity()) < Build.VERSION_CODES.M
                    && !PermissionUtils.hasSelfPermissions(getActivity(), permissions)) {
                mListener.onDenied();
                return;
            }

            if (PermissionUtils.verifyPermissions(grantResults)) {
                mListener.onGranted();
            } else {
                if (!PermissionUtils.shouldShowRequestPermissionRationale(getActivity(), permissions)) {
                    if (!mListener.onNeverAsk()) {
                        Toast.makeText(getActivity(), R.string.give_permission_in_settings, Toast.LENGTH_SHORT).show();
                    }

                } else {
                    mListener.onDenied();
                }
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
