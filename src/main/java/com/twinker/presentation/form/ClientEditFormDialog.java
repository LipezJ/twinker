package com.twinker.presentation.form;

import com.twinker.domain.entity.Client;
import com.twinker.presentation.controller.ClientsController;

import javax.swing.*;
import java.awt.*;

public class ClientEditFormDialog extends FormDialog {

    public ClientEditFormDialog(JFrame parent, ClientsController controller, Client client) {
        super(parent, "Editar cliente", new Dimension(300, 350));

        addLabel("Nombre del cliente:");
        JTextField nameField = addTextField(client.getName());

        addLabel("Telefono:");
        JTextField phoneFiled = addTextField(client.getPhone());

        addLabel("Email:");
        JTextField emailField = addTextField(String.valueOf(client.getEmail()));

        JButton updateBtn = addButton("Actualizar Client");
        updateBtn.setBackground(new Color(52, 152, 219));
        updateBtn.setForeground(Color.WHITE);

        updateBtn.addActionListener(_ ->
                controller.onEditClient(
                this,
                        client.getId(),
                        nameField.getText(),
                        phoneFiled.getText(),
                        emailField.getText()
                )
        );

        finalizeDialog();
    }
}
