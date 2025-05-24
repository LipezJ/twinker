package com.twinker.application.security;

import com.twinker.persistence.security.PinKeyStoreStorage;

public class PinService {

    public void configurePin(String pinPlain) {
        try {
            PinKeyStoreStorage.storePin(pinPlain);
        } catch (Exception e) {
            throw new PinServiceException("Error al almacenar el PIN", e);
        }
    }

    public boolean verifyPin(String pin) {
        try {
            return PinKeyStoreStorage.verifyPin(pin);
        } catch (Exception e) {
            throw new PinServiceException("Error al verificar el PIN", e);
        }
    }

    public boolean existsPin() {
        try {
            return PinKeyStoreStorage.existsPin();
        } catch (Exception e) {
            throw new PinServiceException("Error al verificar el PIN", e);
        }
    }

    public void clearPin() {
        try {
            PinKeyStoreStorage.clearPin();
        } catch (Exception e) {
            throw new PinServiceException("Error al borrar el PIN", e);
        }
    }
}
