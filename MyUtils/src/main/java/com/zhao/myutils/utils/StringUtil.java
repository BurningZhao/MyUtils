package com.zhao.myutils.utils;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class StringUtil {

    /**
     * is null or its length is 0 or it is made by space
     */
    public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }


    /**
     * 使用utf-8转码
     */
    public static String utf8Encode(String str) {
        if (!TextUtils.isEmpty(str) && str.getBytes().length != str.length()) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("UnsupportedEncodingException occurred. ", e);
            }
        }
        return str;
    }

    /**
     * 使用utf-8转码
     * encoded in utf-8, if exception, return defultReturn
     */
    public static String utf8Encode(String str, String defultReturn) {
        if (!TextUtils.isEmpty(str) && str.getBytes().length != str.length()) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return defultReturn;
            }
        }
        return str;
    }


}
