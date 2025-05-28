package com.twinker.presentation.form;

import com.twinker.presentation.controller.MainController;

import javax.swing.*;
import java.awt.*;

/**
 * Dialog for initial application setup in the Twinker application.
 * This dialog prompts for the administrator PIN during first-time setup
 * or when resetting security settings.
 *
 * <p>
 * The dialog features:
 * <ul>
 * <li>PIN input field</li>
 * <li>Save button for PIN confirmation</li>
 * <li>Integration with main controller</li>
 * </ul>
 * </p>
 *
 * @author Twinker Development Team
 * @see com.twinker.presentation.form.FormDialog
 * @see com.twinker.presentation.controller.MainController
 */
public class MainSetupAppFormDialog extends FormDialog {

    /**
     * Constructs a new MainSetupAppFormDialog.
     * Creates a dialog for setting up the administrator PIN.
     *
     * @param parent     the parent frame for this dialog
     * @param controller the main controller to handle PIN setup
     */
    public MainSetupAppFormDialog(JFrame parent, MainController controller) {
        super(parent, "Agregar al Inventario", new Dimension(300, 200));

        addLabel("Por favor introduzca su pin para empezar:");
        JTextField pinField = addTextField("");
        JButton saveButton = addButton("Guardar");
        saveButton.addActionListener(e -> controller.onSavePin(this, pinField.getText()));

        finalizeDialog();
    }
}
