package com.zhao.myutils.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v4.app.ActivityCompat;

import com.zhao.myutils.R;

import java.util.ArrayList;

/**
 * Description:
 *
 * @author zhaoqingbo
 * @since 2016/4/28
 */
public class PermissionCheckUtil {

    /**
     * 判断是否需要某些权限并赋予，在onRequestPermissionsResult给出权限赋予结果
     */
    public static boolean isRequestPermissions(final Activity activity,
                                               final ArrayList<String> permissions, final int request) {
        boolean isRequestPermission = false;
        if (permissions.size() > 0) {
            if (shouldShowRequestPermissionRationale(activity, permissions)) {
                // Provide an additional rationale to the user if the permission was not granted
                // and the user would benefit from additional context for the use of the permission.
                // For example if the user has previously denied the permission.
                // Need Rationale
                DialogUtils.showDialog(activity, R.string.permissions_obtain_title, null,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(activity,
                                        permissions.toArray(new String[permissions.size()]), request);
                            }
                        });
                isRequestPermission = true;
            } else {
                if (permissions.size() > 0) {
                    ActivityCompat.requestPermissions(activity, permissions.toArray(new String[permissions.size()]),
                            request);
                    isRequestPermission = true;
                }
            }
        }
        return isRequestPermission;
    }

    public static boolean shouldShowRequestPermissionRationale(Activity activity, ArrayList<String> permissions) {
        boolean isShouldShow = false;
        for (int i = 0; i < permissions.size(); i++) {
            isShouldShow = ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions.get(i));
            if (isShouldShow) {
                break;
            }
        }
        return isShouldShow;
    }

}
