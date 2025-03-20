package com.github.fuyo.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESUtil {
    // 硬编码的 AES 密钥（Base64 编码，32 字节）
    private static final String BASE64_KEY = "MTIzNDU2Nzg5MDEyMzQ1Njc4OTAxMjM0NTY3ODkwMTI=";

    // 将 Base64 编码的密钥转换为 SecretKey
    private static SecretKey getSecretKey() {
        byte[] decodedKey = Base64.getDecoder().decode(BASE64_KEY);
        return new SecretKeySpec(decodedKey, "AES");
    }

    // 加密数据
    public static String encrypt(String data) throws Exception {
        SecretKey key = getSecretKey();
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // 解密数据
    public static String decrypt(String encryptedData) throws Exception {
        SecretKey key = getSecretKey();
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decryptedBytes);
    }
}
