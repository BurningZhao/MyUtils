package com.zhao.myutils.utils;


import com.zhao.myutils.config.Constant;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Key;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;


/**
 * http请求处理参数类，有DES加密方法，有MD5加密。还有参数排序方法
 * sortParams排序。
 * doDESEncode加密DES
 * MD5 加密MD5
 */
public class DESMD5Utils {

    private static final char[] legalChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
            .toCharArray();

    //对请求参数进行排序，加密。具体加密措施后台配合
    public static String dsigndispost(HashMap<String, String> DesParameter) {
        String str = DESMD5Utils.sortParams(DesParameter);
        String str1 = doDESEncode(str, Constant.DESKEY);
        return MD5(str1 + Constant.MD5KEY, true);
    }

    /**
     * http请求时候调用
     * 字典排序 在请求的时候需要把参数进行排序。调用方法
     */
    public static String sortParams(HashMap<String, String> params) {
        if (params == null) {
            return "";
        }
        Collection<String> keyset = params.keySet();
        List<String> list = new ArrayList<>(keyset);
        // 对key键按字典升序排序
        Collections.sort(list);
        String signCode = "";
        for (int i = 0; i < list.size(); i++) {
            if (ValidateUtils.isAllChinese(params.get(list.get(i)))) {
                signCode = signCode
                        + list.get(i)
                        + params.get(list.get(i));
            } else {
                signCode = signCode + list.get(i) + params.get(list.get(i));
            }
        }
        return signCode;
    }

    /**
     * http请求时候调用
     * 根据传来的密钥对明文进行加密  DES加密
     *
     * @param plainText 明文
     * @param key       密钥
     * @return 密文
     */
    public static String doDESEncode(String plainText, String key) {
        // 密文
        String cipherText = null;
        byte[] keyByte = key.getBytes();
        byte[] inputData = plainText.getBytes();
        try {
            inputData = encrypt(inputData, BASE64Encoder(keyByte));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            cipherText = BASE64Encoder(inputData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cipherText;
    }

    /**
     * MD5加密
     *
     * @param str
     * @param isUp true是大写
     */
    public static String MD5(String str, boolean isUp) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

        char[] charArray = str.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);

        StringBuilder hexValue = new StringBuilder();
        for (byte md5Byte : md5Bytes) {
            int val = (md5Byte) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        if (isUp) {
            return (hexValue.toString()).toUpperCase();
        } else {
            return hexValue.toString();
        }
    }

    public static byte[] encrypt(byte[] data, String key) throws Exception {
        Key k = toKey(BASE64Decoder(key));
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, k);
        return cipher.doFinal(data);
    }

    public static String BASE64Encoder(byte[] data) {
        int start = 0;
        int len = data.length;
        StringBuilder buf = new StringBuilder(data.length * 3 / 2);

        int end = len - 3;
        int i = start;
        int n = 0;
        while (i <= end) {
            int d = (((data[i]) & 0x0ff) << 16)
                    | (((data[i + 1]) & 0x0ff) << 8) | ((data[i + 2]) & 0x0ff);
            buf.append(legalChars[(d >> 18) & 63]);
            buf.append(legalChars[(d >> 12) & 63]);
            buf.append(legalChars[(d >> 6) & 63]);
            buf.append(legalChars[d & 63]);
            i += 3;
            if (n++ >= 14) {
                n = 0;
            }
        }

        if (i == start + len - 2) {
            int d = (((data[i]) & 0x0ff) << 16) | (((data[i + 1]) & 255) << 8);
            buf.append(legalChars[(d >> 18) & 63]);
            buf.append(legalChars[(d >> 12) & 63]);
            buf.append(legalChars[(d >> 6) & 63]);
            buf.append("=");
        } else if (i == start + len - 1) {
            int d = ((data[i]) & 0x0ff) << 16;
            buf.append(legalChars[(d >> 18) & 63]);
            buf.append(legalChars[(d >> 12) & 63]);
            buf.append("==");
        }

        return buf.toString();
    }

    private static Key toKey(byte[] key) throws Exception {
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(dks);

        // 当使用其他对称加密算法时，如AES、Blowfish等算法时，用下述代码替换上述三行代码
        // SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);
        return secretKey;
    }

    /**
     * Decodes the given Base64 encoded String to a new byte array. The byte
     * array holding the decoded data is returned.
     */
    public static byte[] BASE64Decoder(String s) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            _base64Decode(s, bos);
        } catch (IOException e) {
            throw new RuntimeException();
        }
        byte[] decodedBytes = bos.toByteArray();
        try {
            bos.close();
        } catch (IOException ex) {
            System.err.println("Error while decoding BASE64: " + ex.toString());
        }
        return decodedBytes;
    }

    private static void _base64Decode(String s, OutputStream os)
            throws IOException {
        int i = 0;
        int len = s.length();
        while (true) {
            while (i < len && s.charAt(i) <= ' ') {
                i++;
            }
            if (i == len) {
                break;
            }
            int tri = (_base64Decode(s.charAt(i)) << 18)
                    + (_base64Decode(s.charAt(i + 1)) << 12)
                    + (_base64Decode(s.charAt(i + 2)) << 6)
                    + (_base64Decode(s.charAt(i + 3)));
            os.write((tri >> 16) & 255);
            if (s.charAt(i + 2) == '=') {
                break;
            }
            os.write((tri >> 8) & 255);
            if (s.charAt(i + 3) == '=') {
                break;
            }
            os.write(tri & 255);
            i += 4;
        }
    }

    private static int _base64Decode(char c) {
        if (c >= 'A' && c <= 'Z') {
            return (c) - 65;
        } else if (c >= 'a' && c <= 'z') {
            return (c) - 97 + 26;
        } else if (c >= '0' && c <= '9') {
            return (c) - 48 + 26 + 26;
        } else {
            switch (c) {
                case '+':
                    return 62;
                case '/':
                    return 63;
                case '=':
                    return 0;
                default:
                    throw new RuntimeException("unexpected code: " + c);
            }
        }
    }
}
