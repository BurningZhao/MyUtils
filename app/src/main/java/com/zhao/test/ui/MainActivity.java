package com.zhao.test.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.zhao.myutils.base.BaseCompatActivity;
import com.zhao.myutils.utils.KeyBoardUtils;
import com.zhao.myutils.utils.PermissionCheckUtil;
import com.zhao.test.R;

import java.util.ArrayList;
import java.util.List;

import static com.zhao.test.R.layout.activity_main;

public class MainActivity extends BaseCompatActivity implements View.OnClickListener {

    private static final int PERMISSIONS_REQUEST = 2;

    @Override
    public int setLayoutResId() {
        return activity_main;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        // Marshmallow+
        // 初始化时请求程序需要的所有的Dangerous Permissions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions();
        }
    }

    @Override
    public void initListener() {
        findViewById(R.id.validation_code, this);
        findViewById(R.id.test_permission_m, this);
    }

    /**
     * Request the permission needed for the app for API>23
     */
    private void requestPermissions() {
        final ArrayList<String> permissionsList = new ArrayList<>();
        // add permission ->eg:
        // Location
        addPermission(permissionsList, Manifest.permission.ACCESS_FINE_LOCATION);
        //  Phone
        addPermission(permissionsList, Manifest.permission.READ_PHONE_STATE);
        PermissionCheckUtil.isRequestPermissions(this, permissionsList, PERMISSIONS_REQUEST);
    }

    /**
     * Add required permission to the list and check if not already granted access
     *
     * @param permissionsList - the list with the permissions
     * @param permission      - the permission the will be added
     */
    private void addPermission(List<String> permissionsList, String permission) {
        if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST) {
            if (grantResults.length > 0) {
                boolean granted = true;
                for (int grantResult : grantResults) {
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        // Permission Denied
                        Toast.makeText(this, getResources().getString(R.string.permissions_denied), Toast.LENGTH_SHORT).show();
                        granted = false;
                        break;
                    }
                }
                if (granted) {
                    Toast.makeText(this, getResources().getString(R.string.permissions_granted), Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // 点击editText外隐藏ime
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                KeyBoardUtils.hideSoftInput(this, v);
            }
        }
        // over
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 判断是或点击EDIT_TEXT 区域外隐藏键盘
     */
    protected boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                ((EditText) v).setCursorVisible(true);
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    @Override
    public BaseCompatActivity getActivity() {
        return this;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.validation_code:
                Intent intent = new Intent(this, VerificationCodeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityWithAnimation(intent);
                break;
            case R.id.test_permission_m:
                Intent testPermissionM = new Intent(this, PermissionTestActivity.class);
                testPermissionM.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityWithAnimation(testPermissionM);
                break;
            default:
                break;
        }
    }
}
