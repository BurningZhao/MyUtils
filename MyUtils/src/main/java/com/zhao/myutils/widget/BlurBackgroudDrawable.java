package com.zhao.myutils.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.view.Display;
import android.view.Gravity;
import android.view.Surface;
import android.view.WindowManager;

import com.zhao.myutils.utils.Blur;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 实时动态模糊效果的实现
 * 其中getScreenshot中的SurfaceControl.screenshot方法需要权限android.permission.READ_FRAME_BUFFER,应用内使用可用view.getDrawingCache代替
 * 使用方法:
 * BlurBackgroundDrawable drawable = new BlurBackgroundDrawable(context); drawable.start(); imageView.setBackground(drawable);
 * <p>
 * see github: https://github.com/bjzhou/BlurBackgroundDrawable/blob/master/BlurBackgroundDrawable.java
 */
public class BlurBackgroudDrawable extends Drawable implements Animatable, Runnable {

    private static Bitmap mScreenshot;
    private static int SCREEN_WIDTH;
    private static int SCREEN_HEIGHT;
    private boolean mRunning = false;
    private Context mContext;
    private Rect mDstRect = new Rect();
    private Display mDisplay;

    @SuppressWarnings("deprecation")
    public BlurBackgroudDrawable(Context context) {
        final WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mContext = context;
        mDisplay = wm.getDefaultDisplay();
        SCREEN_WIDTH = mDisplay.getWidth();
        SCREEN_HEIGHT = mDisplay.getHeight();
    }

    private static Bitmap getScreenshot(Context context) throws IllegalAccessException {
        Bitmap shotBitmap = null;
        try {
            final Class<?> SurfaceControl = Class.forName("android.view.SurfaceControl");
            if (SurfaceControl != null) {
                final Method screenshot = SurfaceControl.getDeclaredMethod("screenshot", int.class,
                        int.class, int.class, int.class);
                shotBitmap = (Bitmap) screenshot.invoke(null,
                        SCREEN_WIDTH / 6,
                        SCREEN_HEIGHT / 6,
                        20000,
                        140000);
            }
        } catch (ClassNotFoundException | NoSuchMethodException
                | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return shotBitmap;
    }

    @Override
    public void start() {
        if (!isRunning()) {
            mRunning = true;
            run();
        }
    }

    @Override
    public void stop() {
        if (isRunning()) {
            mRunning = false;
            unscheduleSelf(this);
        }
    }

    @Override
    public boolean isRunning() {
        return mRunning;
    }

    @Override
    public void draw(Canvas canvas) {
        if (mScreenshot != null) {
            Gravity.apply(Gravity.FILL, mScreenshot.getWidth(), mScreenshot.getHeight(),
                    getBounds(), mDstRect);
            canvas.drawBitmap(mScreenshot, null, mDstRect, null);
        }
    }

    @Override
    public void setAlpha(int alpha) {
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
    }

    @Override
    public int getOpacity() {
        return 0;
    }

    @Override
    public void run() {
        new MyAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    class MyAsyncTask extends AsyncTask<Void, Void, Void> {
        long duration;

        @Override
        protected Void doInBackground(Void... params) {
            duration = System.currentTimeMillis();
            Bitmap sceenshot = null;
            try {
                sceenshot = Bitmap
                        .createBitmap(getScreenshot(mContext), 0,
                                mDisplay.getRotation() == Surface.ROTATION_270 ? getNavigationBarHeight(mContext) / 6
                                        : 0, SCREEN_WIDTH / 6, SCREEN_HEIGHT / 6
                                        - getNavigationBarHeight(mContext) / 6);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            sceenshot = Blur.fastblur(mContext, sceenshot, 12);
            int rotate = 0;
            switch (mDisplay.getRotation()) {
                case Surface.ROTATION_90:
                    rotate = 270;
                    break;
                case Surface.ROTATION_270:
                    rotate = 90;
                    break;
                default:
                    rotate = 0;
                    break;
                case Surface.ROTATION_0:
                    break;
                case Surface.ROTATION_180:
                    break;
            }
            if (rotate != 0) {
                Matrix m = new Matrix();
                m.postRotate(rotate);
                sceenshot = Bitmap.createBitmap(sceenshot, 0, 0, sceenshot.getWidth(),
                        sceenshot.getHeight(), m, true);
            }
            mScreenshot = sceenshot;
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            duration = System.currentTimeMillis() - duration;
            duration = 41 - duration;
            if (mRunning) {
                scheduleSelf(BlurBackgroudDrawable.this,
                        SystemClock.uptimeMillis() + duration >= 0 ? duration : 0);
                invalidateSelf();
            }
        }
    }

}
