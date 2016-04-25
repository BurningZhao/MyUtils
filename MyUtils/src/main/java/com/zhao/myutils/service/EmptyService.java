package com.zhao.myutils.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Description: Background Service that is used to keep our process alive long enough
 * for background threads to finish. Started and stopped directly by specific background
 * tasks when needed.
 */
public class EmptyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
