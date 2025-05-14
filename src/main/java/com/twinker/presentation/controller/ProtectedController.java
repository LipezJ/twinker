package com.twinker.presentation.controller;

import com.twinker.application.security.PinService;
import com.twinker.presentation.form.SecurityPinFormDialog;

import javax.swing.*;

public abstract class ProtectedController {

    private boolean adminAllowed;

    public void onVerifyPin(JDialog modal, String pin) {
        PinService pinService = new PinService();

        adminAllowed = pinService.verifyPin(pin);
        modal.dispose();
    }

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
