package com.twinker.application.security;

import com.twinker.persistence.security.PinKeyStoreStorage;

/**
 * Service class for managing administrator PIN operations in the Twinker
 * application.
 * This class provides a high-level interface for PIN management, abstracting
 * the
 * underlying storage mechanism and providing error handling.
 *
 * <p>
 * The service handles:
 * <ul>
 * <li>PIN configuration and storage</li>
 * <li>PIN verification</li>
 * <li>PIN existence checking</li>
 * <li>PIN removal</li>
 * <li>Exception handling and translation</li>
 * </ul>
 * </p>
 *
 * @author Twinker Development Team
 * @see com.twinker.persistence.security.PinKeyStoreStorage
 * @see com.twinker.application.security.PinServiceException
 */
public class PinService {

    /**
     * Configures a new administrator PIN.
     * Stores the PIN securely using the underlying storage mechanism.
     *
     * @param pinPlain the plain text PIN to configure
     * @throws PinServiceException if there is an error storing the PIN
     */
    public void configurePin(String pinPlain) {
        try {
            PinKeyStoreStorage.storePin(pinPlain);
        } catch (Exception e) {
            throw new PinServiceException("Error al almacenar el PIN", e);
        }
    }

    /**
     * Verifies a candidate PIN against the stored administrator PIN.
     *
     * @param pin the PIN to verify
     * @return true if the PIN matches, false otherwise
     * @throws PinServiceException if there is an error during verification
     */
    public boolean verifyPin(String pin) {
        try {
            return PinKeyStoreStorage.verifyPin(pin);
        } catch (Exception e) {
            throw new PinServiceException("Error al verificar el PIN", e);
        }
    }

    /**
     * Checks if an administrator PIN has been configured.
     *
     * @return true if a PIN exists, false otherwise
     * @throws PinServiceException if there is an error checking PIN existence
     */
    public boolean existsPin() {
        try {
            return PinKeyStoreStorage.existsPin();
        } catch (Exception e) {
            throw new PinServiceException("Error al verificar el PIN", e);
        }
    }

    /**
     * Removes the configured administrator PIN.
     *
     * @throws PinServiceException if there is an error clearing the PIN
     */
    public void clearPin() {
        try {
            PinKeyStoreStorage.clearPin();
        } catch (Exception e) {
            throw new PinServiceException("Error al borrar el PIN", e);
        }
    }
}
