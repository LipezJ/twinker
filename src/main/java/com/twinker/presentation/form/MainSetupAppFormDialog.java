package com.twinker.presentation.form;

import com.twinker.presentation.controller.MainController;

import javax.swing.*;
import java.awt.*;

public class MainSetupAppFormDialog extends FormDialog {
    public MainSetupAppFormDialog(JFrame parent, MainController controller) {
        super(parent, "Agregar al Inventario", new Dimension(300, 200));

        addLabel("Por favor introduzca su pin para empezar:");
        JTextField pinField = addTextField("");
        JButton saveButton = addButton("Guardar");
        saveButton.addActionListener(_ -> controller.onSavePin(this, pinField.getText()));

        finalizeDialog();
    }
}
