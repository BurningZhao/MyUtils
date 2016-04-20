package com.zhao.myutils.utils;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.RawContacts;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Description:联系人数据库操作工具类
 */
public class ContactsUtil {

    /**
     * 判断数据库中是否存在联系人
     */
    public static boolean isContactsExistInDb(Context context) {
        boolean bEmpty = true;
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(
                    RawContacts.CONTENT_URI,
                    new String[]{RawContacts.CONTACT_ID},
                    Contacts.DISPLAY_NAME + " NOTNULL AND " + RawContacts.DELETED + "=0",
                    null,
                    null);
            if ((null != cursor) && (cursor.getCount() > 0)) {
                bEmpty = false;
            }
        } finally {
            closeCursor(cursor);
        }
        return bEmpty;
    }

    public static void closeCursor(Cursor cursor) {
        if (cursor != null) {
            cursor.close();
        }
    }

    /**
     * 根据号码判断是否有联系人
     */
    public static boolean judgeHasContactsByNumber(Context context, String number) {
        boolean exist = false;
        if (!TextUtils.isEmpty(number)) {
            String selection = CommonDataKinds.Phone.NUMBER + "='" + number + "'";
            Cursor cursor = getPhoneCursor(context, selection, null, null);
            if (null != cursor) {
                if (cursor.moveToNext() && (cursor.getCount() > 0)) {
                    exist = true;
                }
                closeCursor(cursor);
            }
        }
        return exist;
    }

    /**
     * 根据名字判断是否有联系人
     */
    public static boolean judgeHasContactsByName(Context context, String name) {
        boolean exist = false;
        if (!TextUtils.isEmpty(name)) {
            String selection = CommonDataKinds.Phone.DISPLAY_NAME + "='" + name + "'";
            Cursor cursor = getPhoneCursor(context, selection, null, null);
            if (null != cursor) {
                if (cursor.moveToNext() && (cursor.getCount() > 0)) {
                    exist = true;
                }
                closeCursor(cursor);
            }
        }
        return exist;
    }

    /**
     * 根据联系人姓名获取电话号码，如果有多个，获取第一个
     */
    public static String getNumberByName(Context context, String name) {
        String number = null;
        ArrayList<String> numbers = getAllNumbersByName(context, name);
        if (numbers.size() > 0) {
            number = numbers.get(0);
        }
        return number;
    }

    /**
     * 根据联系人名字获取联系人的所有号码
     */
    public static ArrayList<String> getAllNumbersByName(Context context, String name) {
        ArrayList<String> numbers = new ArrayList<>();
        String[] projection = {CommonDataKinds.Phone.NUMBER};
        String selection = CommonDataKinds.Phone.DISPLAY_NAME + " ='" + name + "'";
        Cursor cursor = getPhoneCursor(context, selection, projection, null);
        if (null != cursor) {
            while (cursor.moveToNext()) {
                numbers.add(cursor.getString(0));
            }
            closeCursor(cursor);
        }
        return numbers;
    }

    /**
     * 根据联系人Id获取联系人的所有号码
     */
    public static ArrayList<String> getAllNumbersByContactId(Context context, long contactId) {
        ArrayList<String> numbers = new ArrayList<>();
        String[] projection = {CommonDataKinds.Phone.NUMBER};
        String selection = CommonDataKinds.Phone.CONTACT_ID + " =" + contactId;
        Cursor cursor = getPhoneCursor(context, selection, projection, null);
        if (null != cursor) {
            while (cursor.moveToNext()) {
                numbers.add(cursor.getString(0));
            }
            closeCursor(cursor);
        }
        return numbers;
    }

    /**
     * 根据电话号码取得联系人姓名
     */
    public static String getContactNameByNumber(Context context, String number) {
        String name = null;
        String[] projection = {CommonDataKinds.Phone.DISPLAY_NAME};
        String selection = CommonDataKinds.Phone.NUMBER + " = '" + number + "'";
        Cursor cursor = getPhoneCursor(context, selection, projection, null);
        if (null != cursor) {
            if (cursor.moveToNext()) {
                name = cursor.getString(0);
            }
            closeCursor(cursor);
        }
        return name;
    }

    /**
     * 根据电话号码获取联系人头像
     *
     * @param number 电话号码
     * @return bitmap
     */
    public static Bitmap getContactIconByNumber(Context context, String number) {
        long contactId = getContactIdByNumber(context, number);
        return getContactIconByContactId(context, contactId);
    }

    /**
     * 根据电话号码获取联系人头像
     *
     * @param contactId 联系人Id
     * @return bitmap
     */
    public static Bitmap getContactIconByContactId(Context context, long contactId) {
        Bitmap icon = null;
        if (contactId > 0) {
            // 获得contact_id的Uri
            Uri uri = ContentUris.withAppendedId(Contacts.CONTENT_URI, contactId);
            // 打开头像图片的InputStream
            InputStream input = Contacts.openContactPhotoInputStream(
                    context.getContentResolver(), uri);
            icon = BitmapFactory.decodeStream(input);
        }
        return icon;
    }

    /**
     * 根据电话号码查询联系人contactID
     */
    public static long getContactIdByNumber(Context context, String number) {
        long contactId = -1;
        String[] projection = {CommonDataKinds.Phone.CONTACT_ID};
        String selection = CommonDataKinds.Phone.NUMBER + " = '" + number + "'";
        Cursor cursor = getPhoneCursor(context, selection, projection, null);
        if (null != cursor) {
            try {
                if (cursor.moveToNext()) {
                    contactId = cursor.getLong(0);
                }
            } finally {
                closeCursor(cursor);
            }
        }
        return contactId;
    }

    /**
     * 根据联系人ID得到联系人号码
     */
    public static String getNumberByContactId(Context context, long contactId) {
        String phoneNumber = "";
        String selection = CommonDataKinds.Phone.CONTACT_ID + " = " + contactId;
        String[] projection = new String[]{CommonDataKinds.Phone.NUMBER};
        Cursor cursor = getPhoneCursor(context, selection, projection, null);
        if (null != cursor && cursor.moveToNext()) {
            phoneNumber = cursor.getString(0);
            closeCursor(cursor);
        }
        return phoneNumber;
    }

    /**
     * 获得查询view_phone表Cursor
     */
    public static Cursor getPhoneCursor(Context context, String selection,
                                        String[] projection, String sortOrder) {
        return context.getContentResolver().query(CommonDataKinds.Phone.CONTENT_URI,
                projection, selection, null, sortOrder
        );
    }

    /**
     * 插入一条通话记录
     *
     * @param context  context
     * @param number   通话号码
     * @param duration 通话时长
     * @param type     通话类型
     * @param date     打电话时间
     */
    public static void insertCallLog(Context context, String number, String name, long duration,
                                     int type, long date) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_CALL_LOG)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        ContentValues values = new ContentValues();
        values.clear();
        values.put(CallLog.Calls.NUMBER, number);
        values.put(CallLog.Calls.CACHED_NAME, name);
        values.put(CallLog.Calls.DATE, date);
        values.put(CallLog.Calls.DURATION, duration);
        values.put(CallLog.Calls.TYPE, type);
        values.put(CallLog.Calls.NEW, 0);//0已看1未看
        context.getContentResolver().insert(CallLog.Calls.CONTENT_URI, values);
    }

    /**
     * 删除contacts2.db中的数据
     */
    public static void clearContactsDb(Context context) {
        clearAllContactsWithData(context);
        clearAllCallLog(context);
    }

    /**
     * 物理删除所有联系人信息
     * ContactsContract.CALLER_IS_SYNCADAPTER为true，表示将所有与联系人有关联的数据库都删除了
     */
    public static void clearAllContactsWithData(Context context) {
        ContentResolver resolver = context.getContentResolver();
        Uri uri = RawContacts.CONTENT_URI.buildUpon()
                .appendQueryParameter(ContactsContract.CALLER_IS_SYNCADAPTER, "true").build();
        resolver.delete(uri, null, null);
    }

    /**
     * 删除所有通话记录
     */
    public static void clearAllCallLog(Context context) {
        //删除所有通话记录
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_CALL_LOG)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        context.getContentResolver().delete(CallLog.Calls.CONTENT_URI, null, null);
    }
}
