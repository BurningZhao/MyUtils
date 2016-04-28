package com.zhao.myutils.utils;

import android.content.Context;
import android.os.Build;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

/**
 * check google play service util
 * <p/>
 * Created by zhaoqingbo on 2015/6/11.
 */
public class GmsUtil {
    private static final String TAG = "GmsUtil";

    /**
     * 判断手机是否安装google套件
     * @param context 上下文环境
     * @return true 安装
     */
    public static boolean hasGooglePlayService(Context context) {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS) {
            LogUtil.d(TAG, "This device is not supported Gms.");
            return false;
        }
        return true;
    }

    /**
     * 判断手机版本是否是4.3及以上版本
     * @return true 是4.3及以上版本
     */
    public static boolean isSupportWearSdkVersion() {
        int supportWearVersion = Build.VERSION_CODES.JELLY_BEAN_MR2;
        return Build.VERSION.SDK_INT >= supportWearVersion;
    }

    /**
     * 判断手机版本是否是4.3及以上版本及是否安装了google套件
     */
    public static boolean isSupportWear(Context context) {
        return isSupportWearSdkVersion() && hasGooglePlayService(context);
    }
}
