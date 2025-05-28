package com.twinker.presentation.form;

import com.twinker.presentation.controller.InventoryController;

import javax.swing.*;
import java.awt.*;

/**
 * Dialog for adding new products to the inventory in the Twinker application.
 * This dialog provides a form for entering all necessary product information
 * including name, description, quantity, and price.
 *
 * <p>
 * The form includes fields for:
 * <ul>
 * <li>Product name</li>
 * <li>Product description</li>
 * <li>Initial stock quantity</li>
 * <li>Unit price</li>
 * <li>Styled add button</li>
 * </ul>
 * </p>
 *
 * @author Twinker Development Team
 * @see com.twinker.presentation.form.FormDialog
 * @see com.twinker.presentation.controller.InventoryController
 */
public class InventoryFormDialog extends FormDialog {

    /**
     * Constructs a new InventoryFormDialog.
     * Creates a form for entering new product details and adding them
     * to the inventory.
     *
     * @param parent     the parent frame for this dialog
     * @param controller the inventory controller to handle product addition
     */
    public InventoryFormDialog(JFrame parent, InventoryController controller) {
        super(parent, "Agregar al Inventario", new Dimension(300, 400));

        addLabel("Nombre del producto:");
        JTextField nameField = addTextField("");

        addLabel("Descripción del producto:");
        JTextField descriptionField = addTextField("");

        addLabel("Cantidad en stock:");
        JTextField quantityField = addTextField("");

        addLabel("Precio unitario:");
        JTextField priceField = addTextField("");

        JButton createButton = addButton("Agregar al Inventario");
        createButton.setBackground(new Color(46, 204, 113));
        createButton.setForeground(Color.WHITE);
        createButton.addActionListener(e -> controller.onAddEntry(
                this,
                nameField.getText(),
                priceField.getText(),
                descriptionField.getText(),
                quantityField.getText()
        ));

        finalizeDialog();
    }
}
