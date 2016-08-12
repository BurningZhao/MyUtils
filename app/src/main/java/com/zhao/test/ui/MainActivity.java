package com.zhao.test.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zhao.myutils.base.BaseFragmentActivity;
import com.zhao.myutils.utils.KeyBoardUtils;
import com.zhao.myutils.utils.PermissionCheckUtil;
import com.zhao.test.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseFragmentActivity {

    private static final int PERMISSIONS_REQUEST = 2;
    private Button mValidationCodeBtn;

    @Override
    public int setLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle bundle) {
        mValidationCodeBtn = (Button) findViewById(R.id.validation_code);
    }

    @Override
    protected void initListener() {
        setOnClickListener(mValidationCodeBtn);
    }

    @Override
    protected void initData(Bundle bundle) {
        // Marshmallow+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions();
        }
    }

    /**
     * Request the permission needed for the app for API>23
     */
    private void requestPermissions() {
        final ArrayList<String> permissionsList = new ArrayList<>();
        // add permission ->eg:
        addPermission(permissionsList, Manifest.permission.ACCESS_FINE_LOCATION);
        addPermission(permissionsList, Manifest.permission.ACCESS_COARSE_LOCATION);
        addPermission(permissionsList, Manifest.permission.READ_PHONE_STATE);
        PermissionCheckUtil.isRequestPermissions(this, permissionsList, PERMISSIONS_REQUEST);
    }

    /**
     * Add required permission to the list and check if not already granted access
     * @param permissionsList - the list with the permissions
     * @param permission - the permission the will be added
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
    protected void treatClickEvent(View view) {
        super.treatClickEvent(view);
        switch (view.getId()) {
            case R.id.validation_code:
                Intent intent = new Intent(this, VerificationCodeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                KeyBoardUtils.hideSoftInput(this, v);
            }
        }
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
}
