package com.zhao.myutils.utils;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class DialogUtils {

    public static void showDialog(Context context, int resId, String message, final OnClickListener positiveListener) {
        comfirm(context, context.getResources().getString(resId), message, positiveListener, null);
    }

    public static void showDialog(Context context, String title, String message, final OnClickListener positiveListener) {
        comfirm(context, title, message, positiveListener, null);
    }

    public static void comfirm(Context context, String title, String message, final OnClickListener positiveListener,
                               final OnClickListener cancelListener) {
        try {
            Builder builder = new Builder(context);
            builder.setMessage(message);
            builder.setTitle(title);
            builder.setPositiveButton(android.R.string.ok, new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    positiveListener.onClick(dialog, which);
                }
            });

            builder.setNegativeButton(android.R.string.cancel, new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    if (cancelListener != null) {
                        cancelListener.onClick(dialog, which);
                    }
                }
            });
            builder.create().show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
