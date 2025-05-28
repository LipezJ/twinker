package com.twinker.presentation.form;

import com.twinker.presentation.controller.ProtectedController;

import javax.swing.*;
import java.awt.*;

/**
 * Dialog for administrator PIN verification in the Twinker application.
 * This dialog provides a secure way to verify administrator identity
 * before allowing access to protected operations.
 *
 * <p>
 * The dialog features:
 * <ul>
 * <li>PIN input field</li>
 * <li>Verification button</li>
 * <li>Integration with protected controller</li>
 * <li>Styled button appearance</li>
 * </ul>
 * </p>
 *
 * @author Twinker Development Team
 * @see com.twinker.presentation.form.FormDialog
 * @see com.twinker.presentation.controller.ProtectedController
 */
public class SecurityPinFormDialog extends FormDialog {

    /**
     * Constructs a new SecurityPinFormDialog.
     * Creates a dialog for entering and verifying the administrator PIN.
     *
     * @param parent     the parent frame for this dialog
     * @param controller the protected controller to handle verification
     */
    public SecurityPinFormDialog(JFrame parent, ProtectedController controller) {
        super(parent, "Verificacion de identidad", new Dimension(300, 200));

        addLabel("Introduzca el pin de administrador:");
        JTextField pinLabel = addTextField("");

        JButton updateBtn = addButton("Actualizar Producto");
        updateBtn.setBackground(new Color(52, 152, 219));
        updateBtn.setForeground(Color.WHITE);

        updateBtn.addActionListener(e -> controller.onVerifyPin(this, pinLabel.getText()));

        finalizeDialog();
    }
}
