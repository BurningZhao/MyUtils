package com.zhao.myutils.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.RawContacts;
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
        if (context == null) {
            return true;
        }
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
        } catch (Exception e) {
            e.printStackTrace();
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
            String[] projection = {CommonDataKinds.Phone.DISPLAY_NAME};
            Cursor cursor = context.getContentResolver().query(
                    CommonDataKinds.Phone.CONTENT_URI,
                    projection,
                    CommonDataKinds.Phone.NUMBER + "='" + number + "'",
                    null,
                    null);
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
            String[] projection = {CommonDataKinds.Phone.DISPLAY_NAME};
            Cursor cursor = context.getContentResolver().query(
                    CommonDataKinds.Phone.CONTENT_URI,
                    projection,
                    CommonDataKinds.Phone.DISPLAY_NAME + "='" + name + "'",
                    null,
                    null);
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
        String selection = CommonDataKinds.Phone.DISPLAY_NAME + " LIKE " + "'%" + name + "%'";
        Cursor cursor = context.getContentResolver().query(
                CommonDataKinds.Phone.CONTENT_URI,
                new String[]{CommonDataKinds.Phone.NUMBER},
                selection,
                null,
                null);
        if (null != cursor) {
            if (cursor.moveToNext()) {
                number = cursor.getString(0);
            }
            closeCursor(cursor);
        }
        return number;
    }

    /**
     * 根据联系人名字获取电话号码，如果有多个，获取第一个
     */
    public static String getContactNumberByName(Context context, String name) {
        String number = "";
        String[] projection = new String[]{Contacts._ID, Contacts.HAS_PHONE_NUMBER};
        String selection = Contacts.DISPLAY_NAME + " LIKE " + "'%" + name + "%'";
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(Contacts.CONTENT_URI,
                projection, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToNext()) {
                String id = cursor.getString(0);
                int hasPhoneNum = cursor.getInt(1);

                if (hasPhoneNum > 0) {
                    Cursor numCursor = resolver.query(
                            CommonDataKinds.Phone.CONTENT_URI,
                            new String[]{CommonDataKinds.Phone.NUMBER},
                            CommonDataKinds.Phone.CONTACT_ID + "=" + id,
                            null,
                            null);
                    if (numCursor != null && numCursor.moveToNext()) {
                        number = numCursor.getString(0);
                        closeCursor(numCursor);
                    }
                }
            }
            closeCursor(cursor);
        }
        return number;
    }

    /**
     * 根据联系人名字获取联系人的所有号码
     */
    public static ArrayList<String> getAllNumberByName(Context context, String name) {
        ArrayList<String> numbers = new ArrayList<>();
        String selection = CommonDataKinds.Phone.DISPLAY_NAME + " LIKE " + "'%" + name + "%'";
        Cursor cursor = context.getContentResolver().query(
                CommonDataKinds.Phone.CONTENT_URI,
                new String[]{CommonDataKinds.Phone.NUMBER},
                selection,
                null,
                null);
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
        String[] projection = {ContactsContract.PhoneLookup.DISPLAY_NAME};
        String selection = "replace(replace(" + CommonDataKinds.Phone.NUMBER
                + ",'-',''), ' ', '')" + " = '" + number + "'";
        Cursor cursor = context.getContentResolver().query(
                CommonDataKinds.Phone.CONTENT_URI,
                projection,
                selection,
                null,
                null);
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
     */
    public static Bitmap getContactIcon(Context context, String number) {
        Bitmap icon = null;
        long contactId = getContactIdByNumber(context, number);
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
        String selection = CommonDataKinds.Phone.NUMBER + " = " + number;
        Cursor cursor = context.getContentResolver().query(
                CommonDataKinds.Phone.CONTENT_URI,
                projection,
                selection,
                null,
                null);
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
    public static String getNumberByContactId(Context context, int contactId) {
        Cursor phones = context.getContentResolver().query(
                CommonDataKinds.Phone.CONTENT_URI,
                new String[]{CommonDataKinds.Phone.NUMBER},
                CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                null,
                null);
        String phoneNumber = "";
        if (null != phones && phones.moveToNext()) {
            phoneNumber = phones.getString(0);
            closeCursor(phones);
        }
        return phoneNumber;
    }
}
