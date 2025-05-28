package com.twinker.presentation.form;

import com.twinker.domain.collection.InventoryEntry;
import com.twinker.presentation.controller.InventoryController;

import javax.swing.*;
import java.awt.*;

/**
 * Dialog for editing existing products in the inventory.
 * This dialog provides a form pre-populated with the current product
 * information for modification.
 *
 * <p>
 * The form includes:
 * <ul>
 * <li>Pre-filled product name field</li>
 * <li>Current description</li>
 * <li>Existing stock quantity</li>
 * <li>Current unit price</li>
 * <li>Styled update button</li>
 * </ul>
 * </p>
 *
 * @author Twinker Development Team
 * @see com.twinker.presentation.form.FormDialog
 * @see com.twinker.presentation.controller.InventoryController
 * @see com.twinker.domain.collection.InventoryEntry
 */
public class InventoryEditFormDialog extends FormDialog {

    /**
     * Constructs a new InventoryEditFormDialog.
     * Creates a form pre-filled with the current product details
     * for editing.
     *
     * @param parent     the parent frame for this dialog
     * @param controller the inventory controller to handle updates
     * @param entry      the inventory entry being edited
     */
    public InventoryEditFormDialog(JFrame parent, InventoryController controller, InventoryEntry entry) {
        super(parent, "Editar Producto", new Dimension(300, 400));

        addLabel("Nombre del producto:");
        JTextField nameField = addTextField(entry.getName());

        addLabel("Descripción del producto:");
        JTextField descriptionField = addTextField(entry.getDescription());

        addLabel("Cantidad en stock:");
        JTextField quantityField = addTextField(String.valueOf(entry.getStock()));

        addLabel("Precio unitario:");
        JTextField priceField = addTextField(String.valueOf(entry.getPrice()));

        JButton updateBtn = addButton("Actualizar Producto");
        updateBtn.setBackground(new Color(52, 152, 219));
        updateBtn.setForeground(Color.WHITE);

        updateBtn.addActionListener(_ -> controller.onEditEntry(
                this, entry.getId(), nameField.getText(), priceField.getText(), descriptionField.getText(), quantityField.getText())
        );

        finalizeDialog();
    }
}
