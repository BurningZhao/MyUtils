package com.zhao.myutils.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;

public class NetworkUtils {

    /**
     * 判断网络是否连接
     */
    public static boolean isNetWorkConnected(Context context) {
        NetworkInfo networkInfo = getNetworkInfo(context);
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    /**
     * 获得网络状态信息
     */
    private static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (cm != null) {
            networkInfo = cm.getActiveNetworkInfo();
        }
        return networkInfo;
    }

    /**
     * 活动当前网络连接类型
     */
    public static int getActiveNetworkType(Context context) {
        NetworkInfo networkInfo = getNetworkInfo(context);
        if (networkInfo != null) {
            return networkInfo.getType();
        }
        return -1;
    }

    /**
     * 判断是否是WIFI连接
     */
    public static boolean isWifiConnected(Context context) {
        NetworkInfo networkInfo = getNetworkInfo(context);
        return networkInfo != null
                && networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * 判断是否是移动网络连接
     */
    public static boolean isMobileConnected(Context context) {
        NetworkInfo networkInfo = getNetworkInfo(context);
        return networkInfo != null
                && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
    }

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

}
