package com.zhao.myutils.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * 跟manifest相关的辅助类
 */
public class ManifestUtils {

    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context) {
        PackageInfo packageInfo = getPackageInfo(context);
        if (packageInfo != null) {
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        }
        return null;
    }

    /**
     * 获取PackageInfo
     *
     * @return PackageInfo
     */
    public static PackageInfo getPackageInfo(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo;
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context) {
        PackageInfo packageInfo = getPackageInfo(context);
        if (packageInfo != null) {
            return packageInfo.versionName;
        }
        return null;
    }

    /**
     * [获取应用程序版本code信息]
     *
     * @return 当前应用的版本Code
     */
    public static int getVersionCode(Context context) {
        PackageInfo packageInfo = getPackageInfo(context);
        if (packageInfo != null) {
            return packageInfo.versionCode;
        }
        return 0;
    }

    /**
     * 在Activity中获取MetaData
     *
     * @param key
     * @return Object
     */
    public static Object getMetaDataAsObject(Activity context, String key) {
        ActivityInfo info = getActivityInfo(context);
        return info == null ? null : info.metaData.get(key);
    }

    private static ActivityInfo getActivityInfo(Activity context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            return packageManager.getActivityInfo(context.getComponentName(),
                    PackageManager.GET_META_DATA);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 在Activity中获取MetaData
     *
     * @param key
     * @return String
     */
    public static String getMetaDataAsString(Activity context, String key) {
        ActivityInfo info = getActivityInfo(context);
        return info == null ? null : info.metaData.getString(key);
    }

    /**
     * 在Activity中获取MetaData
     *
     * @param key
     * @return int
     */
    public static int getMetaDataAsInt(Activity context, String key) {
        ActivityInfo info = getActivityInfo(context);
        return info == null ? 0 : info.metaData.getInt(key);
    }

    /**
     * 在Activity中获取MetaData
     *
     * @param context
     * @param key
     * @return boolean
     */
    public static boolean getMetaDataAsBoolean(Activity context, String key) {
        ActivityInfo info = getActivityInfo(context);
        return info != null && info.metaData.getBoolean(key);
    }

}
