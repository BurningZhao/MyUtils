package com.zhao.myutils.manager;

import android.os.Handler;
import android.os.HandlerThread;

import com.zhao.myutils.bean.ThreadBean;
import com.zhao.myutils.utils.LogUtil;
import com.zhao.myutils.utils.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 线程管理类
 * ThreadManager.getInstance().runThread(...);
 * 在使用ThreadManager的Context被销毁前ThreadManager.getInstance().destroyThread(...);
 * 在应用退出前ThreadManager.getInstance().finish();
 */
public class ThreadManager {
    private static final String TAG = "ThreadManager";
    private static ThreadManager threadManager;

    private Map<String, ThreadBean> threadMap;

    private ThreadManager() {
        threadMap = new HashMap<>();
    }

    public static ThreadManager getInstance() {
        if (threadManager == null) {
            threadManager = new ThreadManager();
        }

        return threadManager;
    }

    /**
     * 运行线程
     *
     * @param name
     * @param runnable
     * @return Handler
     */
    public Handler runThread(String name, Runnable runnable) {
        if (!StringUtil.isBlank(name, true) || runnable == null) {
            LogUtil.e(TAG, " name is null  || runnable == null >> return");
            return null;
        }
        name = name.trim();
        LogUtil.d(TAG, "\n runThread  name = " + name);

        Handler handler = getHandler(name);
        if (handler != null) {
            LogUtil.w(TAG, "handler != null >>  destroyThread(name);");
            destroyThread(name);
        }

        HandlerThread thread = new HandlerThread(name);
        thread.start();//创建一个HandlerThread并启动它
        handler = new Handler(thread.getLooper());//使用HandlerThread的looper对象创建Handler
        handler.post(runnable);//将线程post到Handler中

        threadMap.put(name, new ThreadBean(name, thread, runnable, handler));

        LogUtil.d(TAG, "runThread  added name = " + name + "; threadMap.size() = " + threadMap.size() + "\n");
        return handler;
    }

    /**
     * 获取线程Handler
     *
     * @param name
     * @return Handler
     */
    private Handler getHandler(String name) {
        ThreadBean tb = getThread(name);
        return tb == null ? null : tb.getHandler();
    }

    /**
     * 获取线程
     *
     * @param name
     * @return ThreadBean
     */
    public ThreadBean getThread(String name) {
        return name == null ? null : threadMap.get(name);
    }

    /**
     * 销毁线程
     *
     * @param nameList
     */
    public void destroyThread(List<String> nameList) {
        if (nameList != null) {
            for (String name : nameList) {
                destroyThread(name);
            }
        }
    }

    /**
     * 销毁线程
     *
     * @param name
     */
    public void destroyThread(String name) {
        destroyThread(getThread(name));
    }

    /**
     * 销毁线程
     *
     * @param tb
     */
    private void destroyThread(ThreadBean tb) {
        if (tb == null) {
            LogUtil.e(TAG, "destroyThread  tb == null >> return;");
            return;
        }

        destroyThread(tb.getHandler(), tb.getRunnable());
        if (tb.getName() != null) { // StringUtil.isNotEmpty(tb.getName(), true)) {
            threadMap.remove(tb.getName());
        }
    }

    /**
     * 销毁线程
     *
     * @param handler
     * @param runnable
     */
    public void destroyThread(Handler handler, Runnable runnable) {
        if (handler == null || runnable == null) {
            LogUtil.e(TAG, "destroyThread  handler == null || runnable == null >> return;");
            return;
        }

        try {
            handler.removeCallbacks(runnable);
        } catch (Exception e) {
            LogUtil.e(TAG, "onDestroy try { handler.removeCallbacks(runnable);...  >> catch  : " + e.getMessage());
        }
    }

    /**
     * 结束ThreadManager所有进程
     */
    public void finish() {
        threadManager = null;
        if (threadMap == null) {
            LogUtil.d(TAG, "finish  threadMap == null || threadMap.keySet() == null >> threadMap = null; >> return;");
            threadMap = null;
            return;
        }
        List<String> nameList = new ArrayList<>(threadMap.keySet());//直接用Set在系统杀掉应用时崩溃
        for (String name : nameList) {
            destroyThread(name);
        }
        threadMap = null;
        LogUtil.d(TAG, "\n finish  finished \n");
    }

}
