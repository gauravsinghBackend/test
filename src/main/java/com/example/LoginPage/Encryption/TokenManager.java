package com.example.LoginPage.Encryption;
//import java.security.Key;
//import java.security.NoSuchAlgorithmException;
//import java.util.Base64;
//import javax.crypto.Cipher;
//import javax.crypto.KeyGenerator;
//import javax.crypto.SecretKey;
//
//public class TokenManager {
//
//    private static final String ALGORITHM = "AES";
//    private static final int KEY_SIZE = 256;
//
//    public static String generateToken(long userId, long expirationTimeInMinutes) throws Exception {
//        // Generate a secret key
//        SecretKey secretKey = generateSecretKey();
//
//        // Create a cipher for encryption
//        Cipher cipher = Cipher.getInstance(ALGORITHM);
//        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
//
//        // Construct the token payload
//        String tokenPayload = userId + ":" + expirationTimeInMinutes;
//
//        // Encrypt the payload
//        byte[] encryptedBytes = cipher.doFinal(tokenPayload.getBytes());
//
//        // Encode the encrypted bytes to Base64 for transmission
//        return Base64.getEncoder().encodeToString(encryptedBytes);
//    }
//
//    public static TokenData decryptToken(String token) throws Exception {
//        // Decode Base64-encoded token
//        byte[] encryptedBytes = Base64.getDecoder().decode(token);
//
//        // Generate a secret key
//        SecretKey secretKey = generateSecretKey();
//
//        // Create a cipher for decryption
//        Cipher cipher = Cipher.getInstance(ALGORITHM);
//        cipher.init(Cipher.DECRYPT_MODE, secretKey);
//
//        // Decrypt the token payload
//        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
//
//        // Convert decrypted bytes to string
//        String decryptedPayload = new String(decryptedBytes);
//
//        // Split the payload into user ID and expiration time
//        String[] parts = decryptedPayload.split(":");
//        long userId = Long.parseLong(parts[0]);
//        long expirationTime = Long.parseLong(parts[1]);
//
//        return new TokenData(userId, expirationTime);
//    }
//
//    private static SecretKey generateSecretKey() throws NoSuchAlgorithmException {
//        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
//        keyGenerator.init(KEY_SIZE);
//        return keyGenerator.generateKey();
//    }
//
//    public static class TokenData {
//        private final long userId;
//        private final long expirationTime;
//
//        public TokenData(long userId, long expirationTime) {
//            this.userId = userId;
//            this.expirationTime = expirationTime;
//        }
//
//        public long getUserId() {
//            return userId;
//        }
//
//        public long getExpirationTime() {
//            return expirationTime;
//        }
//    }
//}
//
import com.example.LoginPage.Encryption.TokenData;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Base64;
@Service
public class TokenManager {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int KEY_SIZE = 256;
    private static final int GCM_IV_LENGTH = 12; // GCM requires a 12-byte IV
    private static final int GCM_TAG_LENGTH = 128; // 128-bit authentication tag

    private SecretKey secretKey;

    public TokenManager() throws Exception {
        secretKey = generateSecretKey();
    }

    public String generateToken(long userId) throws Exception {
        byte[] iv = generateIV(); // Generate a new IV for each encryption
        String tokenPayload = String.valueOf(userId);

        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, gcmParameterSpec);

        byte[] encryptedBytes = cipher.doFinal(tokenPayload.getBytes(StandardCharsets.UTF_8));

        // Combine IV and encrypted data for transmission
        byte[] combined = new byte[iv.length + encryptedBytes.length];
        System.arraycopy(iv, 0, combined, 0, iv.length);
        System.arraycopy(encryptedBytes, 0, combined, iv.length, encryptedBytes.length);

        return Base64.getEncoder().encodeToString(combined);
    }

    public TokenData decryptToken(String token) throws Exception {
        byte[] combined = Base64.getDecoder().decode(token);

        // Extract IV
        byte[] iv = new byte[GCM_IV_LENGTH];
        System.arraycopy(combined, 0, iv, 0, GCM_IV_LENGTH);

        // Extract encrypted data
        byte[] encryptedBytes = new byte[combined.length - GCM_IV_LENGTH];
        System.arraycopy(combined, GCM_IV_LENGTH, encryptedBytes, 0, encryptedBytes.length);

        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmParameterSpec);

        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        String decryptedPayload = new String(decryptedBytes, StandardCharsets.UTF_8);

        // Extract user ID
        long userId = Long.parseLong(decryptedPayload);

        return new TokenData(userId);
    }

    private byte[] generateIV() {
        byte[] iv = new byte[GCM_IV_LENGTH];
        new SecureRandom().nextBytes(iv);
        return iv;
    }

    private SecretKey generateSecretKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        keyGenerator.init(KEY_SIZE);
        return keyGenerator.generateKey();
    }
}


