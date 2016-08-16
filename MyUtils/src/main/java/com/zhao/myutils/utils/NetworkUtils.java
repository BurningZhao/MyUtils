package com.zhao.myutils.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

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
     * 获得当前网络连接类型
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

}
