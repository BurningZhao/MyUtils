package com.zhao.myutils.utils;

import android.util.Base64;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Description:AES加解密
 * BASE64是为了避免：javax.crypto.IllegalBlockSizeException: last block incomplete in decryption
 * @since 16/8/16
 */
public class AESUtil {
    private SecretKeySpec mKeySpec;
    private Cipher mCipher;

    private static final String CIPHER_INSTANCE = "AES/ECB/PKCS5Padding";

    public AESUtil(byte[] keyRaw) throws Exception {
        if (keyRaw == null) {
            byte[] bytesOfMessage = "".getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(bytesOfMessage);

            mKeySpec = new SecretKeySpec(bytes, "AES");
            mCipher = Cipher.getInstance(CIPHER_INSTANCE);
        } else {

            mKeySpec = new SecretKeySpec(keyRaw, "AES");
            mCipher = Cipher.getInstance(CIPHER_INSTANCE);

        }
    }

    public AESUtil(String passphrase) throws Exception {
        byte[] bytesOfMessage = passphrase.getBytes("UTF-8");
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] theDigest = md.digest(bytesOfMessage);
        mKeySpec = new SecretKeySpec(theDigest, "AES");

        mCipher = Cipher.getInstance(CIPHER_INSTANCE);
    }

    public AESUtil() throws Exception {
        byte[] bytesOfMessage = "".getBytes("UTF-8");
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] theDigest = md.digest(bytesOfMessage);
        mKeySpec = new SecretKeySpec(theDigest, "AES");

        mKeySpec = new SecretKeySpec(new byte[16], "AES");
        mCipher = Cipher.getInstance(CIPHER_INSTANCE);
    }

    /**
     * 加密:先AES再base64
     *
     * @param plaintext
     * @return
     * @throws Exception
     */
    public String encrypt(String plaintext) throws Exception {

        mCipher.init(Cipher.ENCRYPT_MODE, mKeySpec);

        byte[] cipherText = mCipher.doFinal(plaintext.getBytes("UTF-8"));

        return Base64.encodeToString(cipherText, Base64.DEFAULT);
    }

    /**
     * 解密:先base64再AES
     *
     * @param cipherText
     * @return
     * @throws Exception
     */
    public String decrypt(String cipherText) throws Exception {
        mCipher.init(Cipher.DECRYPT_MODE, mKeySpec);

        byte[] plain64text = Base64.decode(cipherText, Base64.DEFAULT);

        byte[] plaintext = mCipher.doFinal(plain64text);

        return new String(plaintext, "UTF-8");
    }

}
