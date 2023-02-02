package com.itechpsp.sdk;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class Encryptor {

    private final static String CIPHER_TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private String key;

    public Encryptor(String key) {
        this.key = key;
    }

    public String EncryptPaymentLink(String toEncryptUrlPart) throws Exception {
        Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
        SecureRandom secureRandom = new SecureRandom();
        byte[] iv = new byte[cipher.getBlockSize()];
        secureRandom.nextBytes(iv);
        IvParameterSpec ivParams = new IvParameterSpec(iv);
        byte[] paddedKey = new byte[32];
        byte[] byteKey = key.getBytes("UTF-8");
        System.arraycopy(byteKey, 0, paddedKey, 0, byteKey.length);
        SecretKeySpec key = new SecretKeySpec(paddedKey, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, key, ivParams);
        String encrypted = Base64.getEncoder().
                encodeToString(cipher.doFinal(toEncryptUrlPart.getBytes("UTF-8")));
        return Base64.getEncoder().encodeToString((encrypted + "::" + Base64.getEncoder()
                .encodeToString(iv)).getBytes());
    }
}