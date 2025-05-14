package com.twinker.presentation.form;

import com.twinker.presentation.controller.ProtectedController;

import javax.swing.*;
import java.awt.*;

public class SecurityPinFormDialog extends FormDialog {
    public SecurityPinFormDialog(JFrame parent, ProtectedController controller) {
        super(parent, "Verificacion de identidad", new Dimension(300, 200));

        addLabel("Introduzca el pin de administrador:");
        JTextField pinLabel = addTextField("");

        JButton updateBtn = addButton("Actualizar Producto");
        updateBtn.setBackground(new Color(52, 152, 219));
        updateBtn.setForeground(Color.WHITE);

        updateBtn.addActionListener(_ -> controller.onVerifyPin(this, pinLabel.getText()));

        finalizeDialog();
    }
}
