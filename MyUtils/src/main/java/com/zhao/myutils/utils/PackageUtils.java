package com.zhao.myutils.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;

import java.io.File;
import java.util.List;

/**
 * 应用程序安装与卸载相关工具类
 */
@SuppressWarnings("SameParameterValue")
public class PackageUtils {

    /**
     * Uninstall return code<br/>
     * uninstall fail if the system failed to delete the package because it is the active DevicePolicy manager.
     */
    public static final int DELETE_FAILED_DEVICE_POLICY_MANAGER = -2;
    private static final int APP_INSTALL_AUTO = 0;
    private static final int APP_INSTALL_INTERNAL = 1;
    private static final int APP_INSTALL_EXTERNAL = 2;
    /**
     * Installation return code<br/>
     * install success.
     */
    private static final int INSTALL_SUCCEEDED = 1;
    /**
     * Installation return code<br/>
     * the package is already installed.
     */
    private static final int INSTALL_FAILED_ALREADY_EXISTS = -1;
    /**
     * Installation return code<br/>
     * the package archive file is invalid.
     */
    private static final int INSTALL_FAILED_INVALID_APK = -2;
    /**
     * Installation return code<br/>
     * the URI passed in is invalid.
     */
    private static final int INSTALL_FAILED_INVALID_URI = -3;
    /**
     * Installation return code<br/>
     * the package manager service found that the device didn't have enough storage space to install the app.
     */
    private static final int INSTALL_FAILED_INSUFFICIENT_STORAGE = -4;
    /**
     * Installation return code<br/>
     * a package is already installed with the same name.
     */
    private static final int INSTALL_FAILED_DUPLICATE_PACKAGE = -5;
    /**
     * Installation return code<br/>
     * the requested shared user does not exist.
     */
    private static final int INSTALL_FAILED_NO_SHARED_USER = -6;
    /**
     * Installation return code<br/>
     * a previously installed package of the same name has a different signature than the new package (and the old
     * package's data was not removed).
     */
    private static final int INSTALL_FAILED_UPDATE_INCOMPATIBLE = -7;
    /**
     * Installation return code<br/>
     * the new package is requested a shared user which is already installed on the device and does not have matching
     * signature.
     */
    private static final int INSTALL_FAILED_SHARED_USER_INCOMPATIBLE = -8;
    /**
     * Installation return code<br/>
     * the new package uses a shared library that is not available.
     */
    private static final int INSTALL_FAILED_MISSING_SHARED_LIBRARY = -9;
    /**
     * Installation return code<br/>
     * the new package uses a shared library that is not available.
     */
    private static final int INSTALL_FAILED_REPLACE_COULDNT_DELETE = -10;
    /**
     * Installation return code<br/>
     * the new package failed while optimizing and validating its dex files, either because there was not enough storage
     * or the validation failed.
     */
    private static final int INSTALL_FAILED_DEXOPT = -11;
    /**
     * Installation return code<br/>
     * the new package failed because the current SDK version is older than that required by the package.
     */
    private static final int INSTALL_FAILED_OLDER_SDK = -12;
    /**
     * Installation return code<br/>
     * the new package failed because it contains a content provider with the same authority as a provider already
     * installed in the system.
     */
    private static final int INSTALL_FAILED_CONFLICTING_PROVIDER = -13;
    /**
     * Installation return code<br/>
     * the new package failed because the current SDK version is newer than that required by the package.
     */
    private static final int INSTALL_FAILED_NEWER_SDK = -14;
    /**
     * Installation return code<br/>
     * the new package failed because it has specified that it is a test-only package and the caller has not supplied
     */
    private static final int INSTALL_FAILED_TEST_ONLY = -15;
    /**
     * Installation return code<br/>
     * the package being installed contains native code, but none that is compatible with the the device's CPU_ABI.
     */
    private static final int INSTALL_FAILED_CPU_ABI_INCOMPATIBLE = -16;
    /**
     * Installation return code<br/>
     * the new package uses a feature that is not available.
     */
    private static final int INSTALL_FAILED_MISSING_FEATURE = -17;
    /**
     * Installation return code<br/>
     * a secure container mount point couldn't be accessed on external media.
     */
    private static final int INSTALL_FAILED_CONTAINER_ERROR = -18;
    /**
     * Installation return code<br/>
     * the new package couldn't be installed in the specified install location.
     */
    private static final int INSTALL_FAILED_INVALID_INSTALL_LOCATION = -19;
    /**
     * Installation return code<br/>
     * the new package couldn't be installed in the specified install location because the media is not available.
     */
    private static final int INSTALL_FAILED_MEDIA_UNAVAILABLE = -20;
    /**
     * Installation return code<br/>
     * the new package couldn't be installed because the verification timed out.
     */
    private static final int INSTALL_FAILED_VERIFICATION_TIMEOUT = -21;
    /**
     * Installation return code<br/>
     * the new package couldn't be installed because the verification did not succeed.
     */
    private static final int INSTALL_FAILED_VERIFICATION_FAILURE = -22;
    /**
     * Installation return code<br/>
     * the package changed from what the calling program expected.
     */
    private static final int INSTALL_FAILED_PACKAGE_CHANGED = -23;
    /**
     * Installation return code<br/>
     * the new package is assigned a different UID than it previously held.
     */
    private static final int INSTALL_FAILED_UID_CHANGED = -24;
    /**
     * Installation return code<br/>
     * if the parser was given a path that is not a file, or does not end with the expected '.apk' extension.
     */
    private static final int INSTALL_PARSE_FAILED_NOT_APK = -100;
    /**
     * Installation return code<br/>
     * if the parser was unable to retrieve the AndroidManifest.xml file.
     */
    private static final int INSTALL_PARSE_FAILED_BAD_MANIFEST = -101;
    /**
     * Installation return code<br/>
     * if the parser encountered an unexpected exception.
     */
    private static final int INSTALL_PARSE_FAILED_UNEXPECTED_EXCEPTION = -102;
    /**
     * Installation return code<br/>
     * if the parser did not find any certificates in the .apk.
     */
    private static final int INSTALL_PARSE_FAILED_NO_CERTIFICATES = -103;
    /**
     * Installation return code<br/>
     * if the parser found inconsistent certificates on the files in the .apk.
     */
    private static final int INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES = -104;
    /**
     * Installation return code<br/>
     * if the parser encountered a CertificateEncodingException in one of the files in the .apk.
     */
    private static final int INSTALL_PARSE_FAILED_CERTIFICATE_ENCODING = -105;
    /**
     * Installation return code<br/>
     * if the parser encountered a bad or missing package name in the manifest.
     */
    private static final int INSTALL_PARSE_FAILED_BAD_PACKAGE_NAME = -106;
    /**
     * Installation return code<br/>
     * if the parser encountered a bad shared user id name in the manifest.
     */
    private static final int INSTALL_PARSE_FAILED_BAD_SHARED_USER_ID = -107;
    /**
     * Installation return code<br/>
     * if the parser encountered some structural problem in the manifest.
     */
    private static final int INSTALL_PARSE_FAILED_MANIFEST_MALFORMED = -108;
    /**
     * Installation return code<br/>
     * if the parser did not find any actionable tags (instrumentation or application) in the manifest.
     */
    private static final int INSTALL_PARSE_FAILED_MANIFEST_EMPTY = -109;
    /**
     * Installation return code<br/>
     * if the system failed to install the package because of system issues.
     */
    private static final int INSTALL_FAILED_INTERNAL_ERROR = -110;
    /**
     * Installation return code<br/>
     * other reason
     */
    private static final int INSTALL_FAILED_OTHER = -1000000;
    /**
     * Uninstall return code<br/>
     * uninstall success.
     */
    private static final int DELETE_SUCCEEDED = 1;
    /**
     * Uninstall return code<br/>
     * uninstall fail if the system failed to delete the package for an unspecified reason.
     */
    private static final int DELETE_FAILED_INTERNAL_ERROR = -1;
    /**
     * Uninstall return code<br/>
     * uninstall fail if pcakge name is invalid
     */
    private static final int DELETE_FAILED_INVALID_PACKAGE = -3;
    /**
     * Uninstall return code<br/>
     * uninstall fail if permission denied
     */
    private static final int DELETE_FAILED_PERMISSION_DENIED = -4;

    /**
     * 安装应用
     * <ul>
     * <li>如果是系统应用或者有Root权限, see {@link #installSilent(Context, String)}</li>
     * <li>否则 see {@link #installNormal(Context, String)}</li>
     * </ul>
     *
     * @param filePath APK文件路径
     */
    public static int install(Context context, String filePath) {
        if (PackageUtils.isSystemApplication(context) || ShellUtils.checkRootPermission()) {
            return installSilent(context, filePath);
        }
        return installNormal(context, filePath) ? INSTALL_SUCCEEDED : INSTALL_FAILED_INVALID_URI;
    }

    /**
     * 使用系统Intent安装应用
     *
     * @param filePath APK文件路径
     * @return APK是否存在
     */
    private static boolean installNormal(Context context, String filePath) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        File file = new File(filePath);
        if (!file.exists() || !file.isFile() || file.length() <= 0) {
            return false;
        }

        i.setDataAndType(Uri.parse("file://" + filePath), "application/vnd.android.package-archive");
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
        return true;
    }

    /**
     * Root权限静默安装
     * <ul>
     * <strong>注意:</strong>
     * <li>不要在主线程调用这个方法,会很耗费时间</li>
     * <li>如果你的应用是系统应用,
     * 你应该添加 <strong>android.permission.INSTALL_PACKAGES</strong>权限manifest文件中,
     * 这样你就不需要Root权限</li>
     * <li>默认PackageManager安装时添加参数 "-r".</li>
     * </ul>
     */
    private static int installSilent(Context context, String filePath) {
        return installSilent(context, filePath, " -r " + getInstallLocationParams());
    }

    /**
     * 使用Root权限静默安装
     * <ul>
     * <strong>注意:</strong>
     * <li>不要在主线程调用这个方法,会很耗费时间</li>
     * <li>如果你的应用是系统应用,
     * 你应该添加 <strong>android.permission.INSTALL_PACKAGES</strong>权限manifest文件中,
     * 这样你就不需要Root权限</li>
     * </ul>
     */
    private static int installSilent(Context context, String filePath, String pmParams) {
        if (filePath == null || filePath.length() == 0) {
            return INSTALL_FAILED_INVALID_URI;
        }

        File file = new File(filePath);
        if (file.length() <= 0 || !file.exists() || !file.isFile()) {
            return INSTALL_FAILED_INVALID_URI;
        }

        /**
         * if context is system app, don't need root permission, but should add <uses-permission
         * android:name="android.permission.INSTALL_PACKAGES" /> in mainfest
         **/
        ShellUtils.CommandResult commandResult = ShellUtils.execCommand("LD_LIBRARY_PATH=/vendor/lib:/system/lib pm install " + (pmParams == null ? "" : pmParams) + " " + filePath.replace(" ", "\\ "), !isSystemApplication(context), true);
        if (commandResult.successMsg != null
                && (commandResult.successMsg.contains("Success") || commandResult.successMsg.contains("success"))) {
            return INSTALL_SUCCEEDED;
        }

        if (commandResult.errorMsg == null) {
            return INSTALL_FAILED_OTHER;
        }
        if (commandResult.errorMsg.contains("INSTALL_FAILED_ALREADY_EXISTS")) {
            return INSTALL_FAILED_ALREADY_EXISTS;
        }
        if (commandResult.errorMsg.contains("INSTALL_FAILED_INVALID_APK")) {
            return INSTALL_FAILED_INVALID_APK;
        }
        if (commandResult.errorMsg.contains("INSTALL_FAILED_INVALID_URI")) {
            return INSTALL_FAILED_INVALID_URI;
        }
        if (commandResult.errorMsg.contains("INSTALL_FAILED_INSUFFICIENT_STORAGE")) {
            return INSTALL_FAILED_INSUFFICIENT_STORAGE;
        }
        if (commandResult.errorMsg.contains("INSTALL_FAILED_DUPLICATE_PACKAGE")) {
            return INSTALL_FAILED_DUPLICATE_PACKAGE;
        }
        if (commandResult.errorMsg.contains("INSTALL_FAILED_NO_SHARED_USER")) {
            return INSTALL_FAILED_NO_SHARED_USER;
        }
        if (commandResult.errorMsg.contains("INSTALL_FAILED_UPDATE_INCOMPATIBLE")) {
            return INSTALL_FAILED_UPDATE_INCOMPATIBLE;
        }
        if (commandResult.errorMsg.contains("INSTALL_FAILED_SHARED_USER_INCOMPATIBLE")) {
            return INSTALL_FAILED_SHARED_USER_INCOMPATIBLE;
        }
        if (commandResult.errorMsg.contains("INSTALL_FAILED_MISSING_SHARED_LIBRARY")) {
            return INSTALL_FAILED_MISSING_SHARED_LIBRARY;
        }
        if (commandResult.errorMsg.contains("INSTALL_FAILED_REPLACE_COULDNT_DELETE")) {
            return INSTALL_FAILED_REPLACE_COULDNT_DELETE;
        }
        if (commandResult.errorMsg.contains("INSTALL_FAILED_DEXOPT")) {
            return INSTALL_FAILED_DEXOPT;
        }
        if (commandResult.errorMsg.contains("INSTALL_FAILED_OLDER_SDK")) {
            return INSTALL_FAILED_OLDER_SDK;
        }
        if (commandResult.errorMsg.contains("INSTALL_FAILED_CONFLICTING_PROVIDER")) {
            return INSTALL_FAILED_CONFLICTING_PROVIDER;
        }
        if (commandResult.errorMsg.contains("INSTALL_FAILED_NEWER_SDK")) {
            return INSTALL_FAILED_NEWER_SDK;
        }
        if (commandResult.errorMsg.contains("INSTALL_FAILED_TEST_ONLY")) {
            return INSTALL_FAILED_TEST_ONLY;
        }
        if (commandResult.errorMsg.contains("INSTALL_FAILED_CPU_ABI_INCOMPATIBLE")) {
            return INSTALL_FAILED_CPU_ABI_INCOMPATIBLE;
        }
        if (commandResult.errorMsg.contains("INSTALL_FAILED_MISSING_FEATURE")) {
            return INSTALL_FAILED_MISSING_FEATURE;
        }
        if (commandResult.errorMsg.contains("INSTALL_FAILED_CONTAINER_ERROR")) {
            return INSTALL_FAILED_CONTAINER_ERROR;
        }
        if (commandResult.errorMsg.contains("INSTALL_FAILED_INVALID_INSTALL_LOCATION")) {
            return INSTALL_FAILED_INVALID_INSTALL_LOCATION;
        }
        if (commandResult.errorMsg.contains("INSTALL_FAILED_MEDIA_UNAVAILABLE")) {
            return INSTALL_FAILED_MEDIA_UNAVAILABLE;
        }
        if (commandResult.errorMsg.contains("INSTALL_FAILED_VERIFICATION_TIMEOUT")) {
            return INSTALL_FAILED_VERIFICATION_TIMEOUT;
        }
        if (commandResult.errorMsg.contains("INSTALL_FAILED_VERIFICATION_FAILURE")) {
            return INSTALL_FAILED_VERIFICATION_FAILURE;
        }
        if (commandResult.errorMsg.contains("INSTALL_FAILED_PACKAGE_CHANGED")) {
            return INSTALL_FAILED_PACKAGE_CHANGED;
        }
        if (commandResult.errorMsg.contains("INSTALL_FAILED_UID_CHANGED")) {
            return INSTALL_FAILED_UID_CHANGED;
        }
        if (commandResult.errorMsg.contains("INSTALL_PARSE_FAILED_NOT_APK")) {
            return INSTALL_PARSE_FAILED_NOT_APK;
        }
        if (commandResult.errorMsg.contains("INSTALL_PARSE_FAILED_BAD_MANIFEST")) {
            return INSTALL_PARSE_FAILED_BAD_MANIFEST;
        }
        if (commandResult.errorMsg.contains("INSTALL_PARSE_FAILED_UNEXPECTED_EXCEPTION")) {
            return INSTALL_PARSE_FAILED_UNEXPECTED_EXCEPTION;
        }
        if (commandResult.errorMsg.contains("INSTALL_PARSE_FAILED_NO_CERTIFICATES")) {
            return INSTALL_PARSE_FAILED_NO_CERTIFICATES;
        }
        if (commandResult.errorMsg.contains("INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES")) {
            return INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES;
        }
        if (commandResult.errorMsg.contains("INSTALL_PARSE_FAILED_CERTIFICATE_ENCODING")) {
            return INSTALL_PARSE_FAILED_CERTIFICATE_ENCODING;
        }
        if (commandResult.errorMsg.contains("INSTALL_PARSE_FAILED_BAD_PACKAGE_NAME")) {
            return INSTALL_PARSE_FAILED_BAD_PACKAGE_NAME;
        }
        if (commandResult.errorMsg.contains("INSTALL_PARSE_FAILED_BAD_SHARED_USER_ID")) {
            return INSTALL_PARSE_FAILED_BAD_SHARED_USER_ID;
        }
        if (commandResult.errorMsg.contains("INSTALL_PARSE_FAILED_MANIFEST_MALFORMED")) {
            return INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
        }
        if (commandResult.errorMsg.contains("INSTALL_PARSE_FAILED_MANIFEST_EMPTY")) {
            return INSTALL_PARSE_FAILED_MANIFEST_EMPTY;
        }
        if (commandResult.errorMsg.contains("INSTALL_FAILED_INTERNAL_ERROR")) {
            return INSTALL_FAILED_INTERNAL_ERROR;
        }
        return INSTALL_FAILED_OTHER;
    }

    /**
     * 卸载应用
     * <ul>
     * <li>如果是系统应用或Root权限, see {@link #uninstallSilent(Context, String)}</li>
     * <li>否则 {@link #uninstallNormal(Context, String)}</li>
     * </ul>
     */
    public static int uninstall(Context context, String packageName) {
        if (PackageUtils.isSystemApplication(context) || ShellUtils.checkRootPermission()) {
            return uninstallSilent(context, packageName);
        }
        return uninstallNormal(context, packageName) ? DELETE_SUCCEEDED : DELETE_FAILED_INVALID_PACKAGE;
    }

    /**
     * 使用系统Intent卸载应用
     *
     * @param context
     * @param packageName package name of app
     * @return whether package name is empty
     */
    private static boolean uninstallNormal(Context context, String packageName) {
        if (packageName == null || packageName.length() == 0) {
            return false;
        }

        Intent i = new Intent(Intent.ACTION_DELETE, Uri.parse("package:" + packageName));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
        return true;
    }

    /**
     * 使用Root权限静默卸载应用并清理数据
     *
     * @param context
     * @param packageName package name of app
     * @return
     * @see #uninstallSilent(Context, String, boolean)
     */
    private static int uninstallSilent(Context context, String packageName) {
        return uninstallSilent(context, packageName, true);
    }

    /**
     * 使用Root权限静默卸载
     * <ul>
     * <strong>注意:</strong>
     * <li>不要在主线程使用这个方法，很耗费时间</li>
     * <li>如果你的应用是系统应用,
     * 你应该添加 <strong>android.permission.INSTALL_PACKAGES</strong>权限manifest文件中,
     * 这样你就不需要Root权限</li>
     * </ul>
     *
     * @param context     file path of package
     * @param packageName package name of app
     * @param isKeepData  whether keep the data and cache directories around after package removal
     * @return <ul>
     * <li>{@link #DELETE_SUCCEEDED} means uninstall success</li>
     * <li>{@link #DELETE_FAILED_INTERNAL_ERROR} means internal error</li>
     * <li>{@link #DELETE_FAILED_INVALID_PACKAGE} means package name error</li>
     * <li>{@link #DELETE_FAILED_PERMISSION_DENIED} means permission denied</li>
     */
    private static int uninstallSilent(Context context, String packageName, boolean isKeepData) {
        if (packageName == null || packageName.length() == 0) {
            return DELETE_FAILED_INVALID_PACKAGE;
        }

        /**
         * if context is system app, don't need root permission, but should add <uses-permission
         * android:name="android.permission.DELETE_PACKAGES" /> in mainfest
         **/
        ShellUtils.CommandResult commandResult = ShellUtils.execCommand("LD_LIBRARY_PATH=/vendor/lib:/system/lib pm uninstall" + (isKeepData ? " -k " : " ") + packageName.replace(" ", "\\ "), !isSystemApplication(context), true);
        if (commandResult.successMsg != null
                && (commandResult.successMsg.contains("Success") || commandResult.successMsg.contains("success"))) {
            return DELETE_SUCCEEDED;
        }
        if (commandResult.errorMsg == null) {
            return DELETE_FAILED_INTERNAL_ERROR;
        }
        if (commandResult.errorMsg.contains("Permission denied")) {
            return DELETE_FAILED_PERMISSION_DENIED;
        }
        return DELETE_FAILED_INTERNAL_ERROR;
    }

    /**
     * 判断自身是否系统应用
     */
    private static boolean isSystemApplication(Context context) {
        return context != null && isSystemApplication(context, context.getPackageName());

    }

    /**
     * 判断应用是否系统应用
     */
    private static boolean isSystemApplication(Context context, String packageName) {
        return context != null && isSystemApplication(context.getPackageManager(), packageName);

    }

    /**
     * 判断应用是否系统应用
     */
    private static boolean isSystemApplication(PackageManager packageManager, String packageName) {
        if (packageManager == null || packageName == null || packageName.length() == 0) {
            return false;
        }

        try {
            ApplicationInfo app = packageManager.getApplicationInfo(packageName, 0);
            return (app != null && (app.flags & ApplicationInfo.FLAG_SYSTEM) > 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断应用是否启动
     */
    @SuppressWarnings("deprecation")
    public static boolean isTopActivity(Context context, String packageName) {
        if (context == null || TextUtils.isEmpty(packageName)) {
            return false;
        }

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
        if (tasksInfo.isEmpty()) {
            return false;
        }
        try {
            return packageName.equals(tasksInfo.get(0).topActivity.getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取系统默认安装应用位置<br/>
     * can be set by System Menu Setting->Storage->Prefered install location
     *
     * @return
     */
    private static int getInstallLocation() {
        ShellUtils.CommandResult commandResult = ShellUtils.execCommand(
                "LD_LIBRARY_PATH=/vendor/lib:/system/lib pm get-install-location", false, true);
        if (commandResult.result == 0 && commandResult.successMsg != null && commandResult.successMsg.length() > 0) {
            try {
                int location = Integer.parseInt(commandResult.successMsg.substring(0, 1));
                switch (location) {
                    case APP_INSTALL_INTERNAL:
                        return APP_INSTALL_INTERNAL;
                    case APP_INSTALL_EXTERNAL:
                        return APP_INSTALL_EXTERNAL;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return APP_INSTALL_AUTO;
    }

    /**
     * get params for pm install location
     */
    private static String getInstallLocationParams() {
        int location = getInstallLocation();
        switch (location) {
            case APP_INSTALL_INTERNAL:
                return "-f";
            case APP_INSTALL_EXTERNAL:
                return "-s";
        }
        return "";
    }

}
