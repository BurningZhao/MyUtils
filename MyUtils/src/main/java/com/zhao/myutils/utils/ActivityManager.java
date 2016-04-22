package com.zhao.myutils.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.Stack;


/**
 * activity 管理类
 */
public class ActivityManager {

    private static Stack<Activity> activityStack;
    private static ActivityManager instance;

    private ActivityManager() {
        activityStack = new Stack<>();
    }

    /**
     * 单一实例
     */
    public static ActivityManager getAppManager() {
        if (instance == null) {
            instance = new ActivityManager();
        }
        return instance;
    }

    /**
     * 栈中是否为空
     */
    public boolean isEmpty() {
        return activityStack.isEmpty();
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        if (activityStack.isEmpty()) {
            return null;
        }
        return activityStack.lastElement();
    }

    /**
     * 获取前一个activity，便于返回
     */
    public Activity lastActivity() {
        if (activityStack.size() < 2) {
            return null;
        }
        return activityStack.get(activityStack.size() - 1);
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        if (activityStack.empty()) {
            return;
        }
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 从栈中移除指定的Activity
     */
    public void removeActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            if (activityStack.remove(activity)) {
                activity.finish();
            }
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        ArrayList<Activity> activityList = new ArrayList<>(
                activityStack);
        for (int i = 0, size = activityList.size(); i < size; i++) {
            if (null != activityList.get(i)) {
                activityList.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 结束所有Activity保留主界面
     */
    public void finishAllActivityExcludeMain() {
        int stackSize = activityStack.size();
        if (stackSize >= 1) {
            ArrayList<Activity> activityList = new ArrayList<>(
                    activityStack.subList(1, stackSize));
            for (int i = 0, size = activityList.size(); i < size; i++) {
                Activity activity = activityList.get(i);
                if (activity != null) {
                    activity.finish();
                    activityStack.remove(activity);
                }
            }
        }
    }

    /**
     * 退出应用程序
     */
    public void AppExit() {
        try {
            finishAllActivity();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 退出应用程序并停止服务
     */
    public void AppExit(Context context, Intent intent) {
        try {
            context.stopService(intent);
            finishAllActivity();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}