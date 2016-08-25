package com.zhao.test.ui;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.zhao.myutils.base.BaseFragmentActivity;
import com.zhao.myutils.utils.PermissionUtils;
import com.zhao.test.R;

/**
 * Permission M request demo
 */
public class PermissionTestActivity extends BaseFragmentActivity
        implements View.OnClickListener {

    @NonNull
    @Override
    public BaseFragmentActivity getActivity() {
        return this;
    }

    @Override
    public int setLayoutResId() {
        return R.layout.activity_permission_test;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        findViewById(R.id.btn_camera, this);
        findViewById(R.id.btn_call, this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_camera:

                requestPermission(new String[]{Manifest.permission.CAMERA}, new PermissionListener() {
                    @Override
                    public void onGranted() {
                        Intent intent = new Intent(); //调用照相机
                        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivity(intent);
                    }

                    @Override
                    public void onDenied() {
                        Toast.makeText(mContext, R.string.permissions_denied, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public boolean onNeverAsk() {
                        PermissionUtils.requestPermissionSettingDialog(mContext, getPackageName());
                        return true;
                    }

                });
                break;
            case R.id.btn_call:
                requestPermission(new String[]{Manifest.permission.CALL_PHONE}, new PermissionListener() {
                    @SuppressWarnings("MissingPermission")
                    @Override
                    public void onGranted() {
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        Uri data = Uri.parse("tel:10086");
                        intent.setData(data);
                        startActivity(intent);
                    }

                    @Override
                    public boolean onNeverAsk() {
                        PermissionUtils.requestPermissionSettingDialog(mContext, getPackageName());
                        return true;
                    }

                });
                break;
        }
    }
}
