package com.zhao.myutils.bean;

import android.os.Handler;
import android.os.HandlerThread;

/**
 * Description:线程实体类
 *
 * @since 16/8/15
 */
public class ThreadBean {

    private String name;
    private HandlerThread thread;
    private Runnable runnable;
    private Handler handler;

    public ThreadBean(String name, HandlerThread thread, Runnable runnable, Handler handler) {
        this.name = name;
        this.thread = thread;
        this.runnable = runnable;
        this.handler = handler;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HandlerThread getThread() {
        return thread;
    }

    public void setThread(HandlerThread thread) {
        this.thread = thread;
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}
