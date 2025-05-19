package com.twinker.presentation.form;

import com.twinker.presentation.controller.ClientsController;

import javax.swing.*;
import java.awt.*;

public class ClientFormDialog extends FormDialog {

    public ClientFormDialog(JFrame parent, ClientsController controller) {
        super(parent, "Agregar al Inventario", new Dimension(300, 350));

        addLabel("Nombre del cliente:");
        JTextField nameField = addTextField("");

        addLabel("Telefono:");
        JTextField phoneField = addTextField("");

        addLabel("Email:");
        JTextField emailField = addTextField("");

        JButton createButton = addButton("Agregar Client");
        createButton.setBackground(new Color(46, 204, 113));
        createButton.setForeground(Color.WHITE);
        createButton.addActionListener(_ ->
                controller.onAddClient(
                this,
                        nameField.getText(),
                        phoneField.getText(),
                        emailField.getText()
                )
        );

        finalizeDialog();
    }
}
