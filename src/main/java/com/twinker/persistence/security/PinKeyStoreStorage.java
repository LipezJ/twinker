package com.twinker.persistence.security;

import com.twinker.application.security.PinServiceException;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.MessageDigest;

public class PinKeyStoreStorage {

    private static final String KEYSTORE_TYPE = "JCEKS";
    private static final char[] STORE_PASSWORD = "changeit".toCharArray();
    private static final String KEY_ALIAS = "pinEntry";
    private static final File KEYSTORE_FILE = new File(System.getProperty("user.home"), ".pinstore.jks");

    public static void storePin(String pinPlain) throws Exception {
        KeyStore ks = KeyStore.getInstance(KEYSTORE_TYPE);

        if (KEYSTORE_FILE.exists()) {
            try (FileInputStream fis = new FileInputStream(KEYSTORE_FILE)) {
                ks.load(fis, STORE_PASSWORD);
            }
        } else {
            ks.load(null, STORE_PASSWORD);
        }

        SecretKey secret = new SecretKeySpec(pinPlain.getBytes(StandardCharsets.UTF_8), "AES");
        KeyStore.SecretKeyEntry entry = new KeyStore.SecretKeyEntry(secret);
        KeyStore.ProtectionParameter protParam =
                new KeyStore.PasswordProtection(STORE_PASSWORD);

        ks.setEntry(KEY_ALIAS, entry, protParam);

        try (FileOutputStream fos = new FileOutputStream(KEYSTORE_FILE)) {
            ks.store(fos, STORE_PASSWORD);
        }
    }

    public static boolean verifyPin(String candidatePin) throws Exception {
        if (!KEYSTORE_FILE.exists()) {
            return false;
        }

        KeyStore ks = KeyStore.getInstance(KEYSTORE_TYPE);
        try (FileInputStream fis = new FileInputStream(KEYSTORE_FILE)) {
            ks.load(fis, STORE_PASSWORD);
        }

        KeyStore.ProtectionParameter protectionParam = new KeyStore.PasswordProtection(STORE_PASSWORD);
        KeyStore.SecretKeyEntry entry = (KeyStore.SecretKeyEntry)
                ks.getEntry(KEY_ALIAS, protectionParam);

        if (entry == null) {
            return false;
        }

        SecretKey storedSecret = entry.getSecretKey();
        byte[] storedBytes = storedSecret.getEncoded();
        byte[] candidateBytes = candidatePin.getBytes(StandardCharsets.UTF_8);

        return MessageDigest.isEqual(storedBytes, candidateBytes);
    }

    public static boolean existsPin() {
        return KEYSTORE_FILE.exists();
    }

    public static void clearPin() throws Exception {
        if (!KEYSTORE_FILE.exists()) {
            return;
        }
        KeyStore ks = KeyStore.getInstance(KEYSTORE_TYPE);
        try (FileInputStream fis = new FileInputStream(KEYSTORE_FILE)) {
            ks.load(fis, STORE_PASSWORD);
        }
        ks.deleteEntry(KEY_ALIAS);
        try (FileOutputStream fos = new FileOutputStream(KEYSTORE_FILE)) {
            ks.store(fos, STORE_PASSWORD);
        }
    }
}
