package com.zhao.myutils.utils;

import android.content.Context;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.zhao.myutils.R;
import com.zhao.myutils.manager.AppManagerUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Description: 处理 unchecked Exception 导致程序的crash
 *
 * @author zhaoqingbo
 * @since 2016/5/26
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = CrashHandler.class.getSimpleName();

    private static CrashHandler instance = new CrashHandler();
    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    /**
     * 存储异常和参数信息
     */
    private Map<String, String> mParamsMap = new HashMap<>();

    /**
     * 格式化时间
     */
    private SimpleDateFormat mDateFormat;

    private static final String VERSION_NAME = "versionName";
    private static final String VERSION_CODE = "versionCode";

    // log file store dir
    private static final String LOG_DIR = "Log";
    private static final String LOG_NAME = "crash";

    public static CrashHandler getInstance() {
        if (instance == null) {
            synchronized (CrashHandler.class) {
                if (instance == null) {
                    instance = new CrashHandler();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        mContext = context;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        mDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.getDefault());
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        // 自定义错误处理
        boolean isTreat = handleException(ex);
        System.out.println(isTreat);
        if (!isTreat && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            //自己处理
            try {//延迟3秒杀进程
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Log.e(TAG, "error : ", e);
            }
            //退出程序
            AppManagerUtil.quit(mContext);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     * 开发者可以根据自己的情况来自定义异常处理逻辑
     *
     * @param ex Throwable
     * @return true:如果处理了该异常信息;否则返回false
     */
    private boolean handleException(final Throwable ex) {
        if (ex == null) {
            return false;
        }

        // 需要context为activity对象,才会弹出dialog
        //        new Thread() {
        //            @Override
        //            public void run() {
        //                Looper.prepare();
        //
        //                System.out.println("Thread");
        //                new AlertDialog.Builder(mContext).setTitle(R.string.promise).setCancelable(false)
        //                        .setMessage(R.string.app_exception).setNeutralButton(R.string.know,
        //                        new DialogInterface.OnClickListener() {
        //                    @Override
        //                    public void onClick(DialogInterface dialog, int which) {
        //                        AppManagerUtil.quit(mContext);
        //                    }
        //                }).create().show();
        //
        //                Looper.loop();
        //            }
        //        }.start();
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                //在此处处理出现异常的情况
                Toast.makeText(mContext, R.string.app_exception, Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();
        // 处理异常信息,例如保存到日志
        collectDeviceInfo(mContext);
        saveCrashInfoFile(ex);
        return true;
    }

    /**
     * 收集设备参数信息
     *
     * @param ctx context
     */
    public void collectDeviceInfo(Context ctx) {
        mParamsMap.put(VERSION_NAME, ManifestUtils.getVersionName(ctx));
        mParamsMap.put(VERSION_CODE, String.valueOf(ManifestUtils.getVersionCode(ctx)));
        //获取所有系统信息
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                mParamsMap.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
                LogUtil.e(TAG, "An error occurred when collect crash info", e);
            }
        }
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex Throwable
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private String saveCrashInfoFile(Throwable ex) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : mParamsMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key).append("=").append(value).append("\n");
        }

        // exception info
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();

        sb.append(result);
        try {
            // long timestamp = System.currentTimeMillis();
            String time = mDateFormat.format(new Date());
            String fileName = LOG_NAME + time + ".log";
            if (SDCardUtils.isSDCardEnable()) {
                String path = SDCardUtils.getSDCardPath() + LOG_DIR + File.separator;
                FileUtil.makeDirs(path);

                FileOutputStream fos = new FileOutputStream(path + fileName);
                fos.write(sb.toString().getBytes());
                LogUtil.i(TAG, "saveCrashInfoFile: " + sb.toString());
                fos.close();
            }
            return fileName;
        } catch (Exception e) {
            LogUtil.e(TAG, "An error occurred while writing file...", e);
        }
        return null;
    }
}
