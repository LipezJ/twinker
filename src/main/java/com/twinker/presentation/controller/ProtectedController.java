package com.twinker.presentation.controller;

import com.twinker.application.security.PinService;
import com.twinker.presentation.form.SecurityPinFormDialog;

import javax.swing.*;

/**
 * Abstract base class for controllers requiring administrator access control.
 * This class provides common functionality for PIN verification and
 * access management in protected operations.
 *
 * <p>
 * The controller provides:
 * <ul>
 * <li>PIN verification dialog</li>
 * <li>Access control state management</li>
 * <li>Security feedback messages</li>
 * <li>Common security operations</li>
 * </ul>
 * </p>
 *
 * @author Twinker Development Team
 * @see com.twinker.application.security.PinService
 * @see com.twinker.presentation.form.SecurityPinFormDialog
 */
public abstract class ProtectedController {

    private boolean adminAllowed;

    /**
     * Handles PIN verification.
     * Validates the entered PIN and updates access state.
     *
     * @param modal the PIN verification dialog
     * @param pin   the PIN to verify
     */
    public void onVerifyPin(JDialog modal, String pin) {
        PinService pinService = new PinService();

        adminAllowed = pinService.verifyPin(pin);
        modal.dispose();
    }

    /**
     * Opens the PIN verification form and processes the result.
     * Shows appropriate messages based on verification outcome.
     *
     * @param frame the parent frame for the verification dialog
     * @return true if PIN verification was successful
     */
    public boolean openVerifyPinForm(JFrame frame) {
        SecurityPinFormDialog securityDialog = new SecurityPinFormDialog(frame, this);
        securityDialog.setVisible(true);

        boolean allowed = adminAllowed;
        adminAllowed = !adminAllowed;

        if (!allowed) {
            JOptionPane.showMessageDialog(frame, "El pin introducido no es correcto.", "Verificacionn Fallida", JOptionPane.WARNING_MESSAGE);
        }

        return allowed;
    }

}
