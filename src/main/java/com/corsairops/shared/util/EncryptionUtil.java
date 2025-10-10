package com.corsairops.shared.util;

import com.corsairops.shared.exception.EncryptionException;
import org.springframework.http.HttpStatus;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

public class EncryptionUtil {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int GCM_IV_LENGTH = 12;
    private static final int GCM_TAG_LENGTH = 128;

    private final String encryptionKey;

    public EncryptionUtil(String encryptionKey) {
        this.encryptionKey = encryptionKey;
    }

    public String encryptString(String data) throws EncryptionException {
        try {
            GCMParameterSpec iv = generateIv();
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, getKey(), iv);
            byte[] cipherText = cipher.doFinal(data.getBytes());

            // Combine IV and encrypted data
            ByteBuffer byteBuffer = combineIvAndCipherText(iv.getIV(), cipherText);
            return Base64.getEncoder().encodeToString(byteBuffer.array());
        } catch (Exception e) {
            throw new EncryptionException("Encryption failed", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    private ByteBuffer combineIvAndCipherText(byte[] iv, byte[] cipherText) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(iv.length + cipherText.length);
        byteBuffer.put(iv);
        byteBuffer.put(cipherText);
        return byteBuffer;
    }

    public String decryptString(String encryptedData) throws EncryptionException {
        try {
            byte[] decodedData = Base64.getDecoder().decode(encryptedData);

            // Extract IV and cipher text
            ByteBuffer byteBuffer = ByteBuffer.wrap(decodedData);
            byte[] ivBytes = new byte[GCM_IV_LENGTH];
            byteBuffer.get(ivBytes);
            byte[] cipherText = new byte[byteBuffer.remaining()];
            byteBuffer.get(cipherText);

            // Decrypt
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            GCMParameterSpec iv = new GCMParameterSpec(GCM_TAG_LENGTH, ivBytes);
            cipher.init(Cipher.DECRYPT_MODE, getKey(), iv);
            byte[] plainText = cipher.doFinal(cipherText);
            return new String(plainText, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new EncryptionException("Decryption failed", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    private SecretKey getKey() {
        byte[] keyBytes = encryptionKey.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(keyBytes, ALGORITHM);
    }

    public static GCMParameterSpec generateIv() {
        byte[] iv = new byte[GCM_IV_LENGTH];
        new SecureRandom().nextBytes(iv);
        return new GCMParameterSpec(GCM_TAG_LENGTH, iv);
    }

}