package com.twinker.presentation.form;

import com.twinker.presentation.controller.ClientsController;

import javax.swing.*;
import java.awt.*;

/**
 * Dialog for adding new clients to the Twinker application.
 * This dialog provides a form for entering client contact information
 * and personal details.
 *
 * <p>
 * The form includes fields for:
 * <ul>
 * <li>Client name</li>
 * <li>Phone number</li>
 * <li>Email address</li>
 * <li>Styled add button</li>
 * </ul>
 * </p>
 *
 * @author Twinker Development Team
 * @see com.twinker.presentation.form.FormDialog
 * @see com.twinker.presentation.controller.ClientsController
 */
public class ClientFormDialog extends FormDialog {

    /**
     * Constructs a new ClientFormDialog.
     * Creates a form for entering new client information.
     *
     * @param parent     the parent frame for this dialog
     * @param controller the clients controller to handle client addition
     */
    public ClientFormDialog(JFrame parent, ClientsController controller) {
        super(parent, "Agregar cliente", new Dimension(300, 350));

        addLabel("Nombre del cliente:");
        JTextField nameField = addTextField("");

        addLabel("Telefono:");
        JTextField phoneField = addTextField("");

        addLabel("Email:");
        JTextField emailField = addTextField("");

        JButton createButton = addButton("Agregar Client");
        createButton.setBackground(new Color(46, 204, 113));
        createButton.setForeground(Color.WHITE);
        createButton.addActionListener(
                _ ->
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
