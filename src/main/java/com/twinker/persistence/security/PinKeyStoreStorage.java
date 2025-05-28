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

/**
 * Security class for managing administrator PIN storage in the Twinker
 * application.
 * Uses Java KeyStore (JCEKS) for secure credential storage and verification.
 *
 * <p>
 * This class provides:
 * <ul>
 * <li>Secure PIN storage using KeyStore</li>
 * <li>PIN verification with secure comparison</li>
 * <li>PIN existence checking</li>
 * <li>PIN removal capabilities</li>
 * <li>File-based KeyStore persistence</li>
 * </ul>
 * </p>
 *
 * @author Twinker Development Team
 * @see javax.crypto.SecretKey
 * @see java.security.KeyStore
 */
public class PinKeyStoreStorage {

    private static final String KEYSTORE_TYPE = "JCEKS";
    private static final char[] STORE_PASSWORD = "changeit".toCharArray();
    private static final String KEY_ALIAS = "pinEntry";
    private static final File KEYSTORE_FILE = new File(System.getProperty("user.home"), ".pinstore.jks");

    /**
     * Stores a PIN securely in the KeyStore.
     * Creates a new KeyStore if none exists, or loads the existing one.
     *
     * @param pinPlain the PIN to store
     * @throws Exception if there is an error storing the PIN
     */
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

    /**
     * Verifies a candidate PIN against the stored PIN.
     * Uses secure comparison to prevent timing attacks.
     *
     * @param candidatePin the PIN to verify
     * @return true if the PIN matches, false otherwise
     * @throws Exception if there is an error during verification
     */
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

    /**
     * Checks if a PIN has been configured.
     *
     * @return true if a PIN exists in the KeyStore, false otherwise
     */
    public static boolean existsPin() {
        return KEYSTORE_FILE.exists();
    }

    /**
     * Removes the stored PIN from the KeyStore.
     * If no PIN exists, this method does nothing.
     *
     * @throws Exception if there is an error clearing the PIN
     */
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
