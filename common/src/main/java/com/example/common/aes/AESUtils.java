package com.example.common.aes;

import android.util.Base64;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * function：AES-256-CBC 对称加密封装类,应用于接口加密
 * author by admin
 * create on 2018/5/3.
 */

public class AESUtils {

    /**
     * AES/CBC/PKCS5Padding    128位解密
     */
    public static String aes128CbcDecrypt(String password, String encodeContent, String iv) throws Exception {
        Key seckey = new SecretKeySpec(password.getBytes(), "AES");
        Cipher cipher;
        cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, seckey, new IvParameterSpec(iv.getBytes()));
        byte[] resultByte = cipher.doFinal(Base64.decode(encodeContent, Base64.DEFAULT));
        return new String(resultByte);
    }

}
