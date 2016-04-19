package com.zhao.myutils.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
    private static final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f'};
    private static final String MD5 = "MD5";

    public static String getMD5(String val) {
        MessageDigest md5;
        byte[] m = new byte[0];
        try {
            md5 = MessageDigest.getInstance(MD5);
            md5.update(val.getBytes());
            m = md5.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return getString(m);
    }

    private static String getString(byte[] b) {
        String ret = "";
        for (byte aB : b) {
            String hex = Integer.toHexString(aB & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            ret += hex.toUpperCase();
        }
        return ret;
    }

    public static String getMD5(byte[] val) {
        MessageDigest md5;
        byte[] m = new byte[0];
        try {
            md5 = MessageDigest.getInstance(MD5);
            md5.update(val);
            m = md5.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return getString(m);
    }

    /**
     * MD5值计算
     * MD5的算法在RFC1321 中定义:
     * 在RFC 1321中，给出了Test suite用来检验你的实现是否正确：
     * MD5 ("") = d41d8cd98f00b204e9800998ecf8427e
     * MD5 ("a") = 0cc175b9c0f1b6a831c399e269772661
     * MD5 ("abc") = 900150983cd24fb0d6963f7d28e17f72
     * MD5 ("message digest") = f96b697d7cb7938d525a2f31aaf161d0
     * MD5 ("abcdefghijklmnopqrstuvwxyz") = c3fcd3d76192e4007dfb496cca67e13b
     *
     * @param res 源字符串
     * @return md5值
     */
    public static String encode(String res) {
        try {
            byte[] strTemp = res.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance(MD5);
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (byte bytes : md) {
                str[k++] = hexDigits[bytes >>> 4 & 0xf];
                str[k++] = hexDigits[bytes & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }
}