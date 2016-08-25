package com.zhao.myutils.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

/**
 * open settings view
 * Created by qingbo.zhao on 16/8/3.
 */
public class OpenSettingViewUtils {

    /**
     * 打开网络设置界面
     */
    public static void openWifiSetting(Context context) {
        Intent intent;
        //判断手机系统的版本:即API大于10 就是3.0或以上版本
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
            intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
        } else {
            intent = new Intent();
            ComponentName component = new ComponentName(
                    "com.android.settings", "com.android.settings.WirelessSettings");
            intent.setComponent(component);
            intent.setAction(Intent.ACTION_VIEW);
        }
        context.startActivity(intent);
    }

    /**
     * 打开settings的App详情页面
     */
    public static void openSettingAppDetails(Context context, String packageName) {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.fromParts("package", packageName, null));
        } else {
            intent.setAction(Intent.ACTION_VIEW);
            int sdkVersion = Build.VERSION.SDK_INT;
            intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            intent.putExtra((sdkVersion == Build.VERSION_CODES.FROYO ? "pkg"
                    : "com.android.settings.ApplicationPkgName"), packageName);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
