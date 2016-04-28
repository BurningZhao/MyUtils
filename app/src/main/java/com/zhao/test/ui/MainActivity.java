package com.zhao.test.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zhao.myutils.base.BaseActivity;
import com.zhao.myutils.utils.PermissionCheckUtil;
import com.zhao.test.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private static final int PERMISSIONS_REQUEST = 2;
    private Button mVrificationcodeBtn;

    @Override
    public int setLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle bundle) {
        mVrificationcodeBtn = (Button) findViewById(R.id.verification_code);
    }

    @Override
    protected void initListener() {
        setOnClickListener(mVrificationcodeBtn);
    }

    @Override
    protected void initData(Bundle bundle) {
        // Marshmallow+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions();
        } else {
            // TODO 满足需要特定权限才能进行的操作
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
        addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        addPermission(permissionsList, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (!PermissionCheckUtil.isRequestPermissions(this, permissionsList, PERMISSIONS_REQUEST)) {
            // TODO 满足需要特定权限才能进行的操作
        }
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
                    // TODO 满足需要特定权限才能进行的操作
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
            case R.id.verification_code:
                Intent intent = new Intent(this, VerficationCodeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
