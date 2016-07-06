package com.zhao.myutils.utils;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

/**
 * Description:飞行模式的好处在于：
 * 1.可以让你拥有一个安静的休息时间
 * 2.减少对身体的辐射
 * 3.可以节省电量
 * 4.不会关闭闹钟
 *
 * @author zhaoqingbo
 * @since 2016/7/6
 */
public class AirplaneModeUtil {

    /**
     * 设置手机飞行模式
     * @param context
     * @param enabling true:设置为飞行模式 false:取消飞行模式
     */
    public static void setAirplaneModeOn(Context context, boolean enabling) {
        Settings.System.putInt(context.getContentResolver(),
                Settings.System.AIRPLANE_MODE_ON, enabling ? 1 : 0);
        Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        intent.putExtra("state", enabling);
        context.sendBroadcast(intent);
    }
    /**
     * 判断手机是否是飞行模式
     * @param context
     * @return
     */
    public static boolean getAirplaneMode(Context context){
        int isAirplaneMode = Settings.System.getInt(context.getContentResolver(),
                Settings.System.AIRPLANE_MODE_ON, 0) ;
        return isAirplaneMode == 1;
    }
}
