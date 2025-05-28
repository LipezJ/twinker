package com.twinker.presentation.form;

import com.twinker.domain.entity.Client;
import com.twinker.presentation.controller.ClientsController;

import javax.swing.*;
import java.awt.*;

/**
 * Dialog for editing existing client information in the Twinker application.
 * This dialog provides a form pre-populated with the current client's
 * information for modification.
 *
 * <p>
 * The form includes:
 * <ul>
 * <li>Pre-filled client name field</li>
 * <li>Current phone number</li>
 * <li>Existing email address</li>
 * <li>Styled update button</li>
 * </ul>
 * </p>
 *
 * @author Twinker Development Team
 * @see com.twinker.presentation.form.FormDialog
 * @see com.twinker.presentation.controller.ClientsController
 * @see com.twinker.domain.entity.Client
 */
public class ClientEditFormDialog extends FormDialog {

    /**
     * Constructs a new ClientEditFormDialog.
     * Creates a form pre-filled with the current client details
     * for editing.
     *
     * @param parent     the parent frame for this dialog
     * @param controller the clients controller to handle updates
     * @param client     the client being edited
     */
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

        updateBtn.addActionListener(
                _ ->
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
