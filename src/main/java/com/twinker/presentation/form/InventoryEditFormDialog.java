package com.twinker.presentation.form;

import com.twinker.domain.collection.InventoryEntry;
import com.twinker.presentation.controller.InventoryController;

import javax.swing.*;
import java.awt.*;

public class InventoryEditFormDialog extends FormDialog {

    public InventoryEditFormDialog(JFrame parent, InventoryController controller, InventoryEntry entry) {
        super(parent, "Editar Producto", new Dimension(300, 400));

        addLabel("Nombre del producto:");
        JTextField nameField = addTextField(entry.getName());

        addLabel("Descripción del producto:");
        JTextField descriptionField = addTextField(entry.getDescription());

        addLabel("Cantidad en stock:");
        JTextField quantityField = addTextField(String.valueOf(entry.getQuantity()));

        addLabel("Precio unitario:");
        JTextField priceField = addTextField(String.valueOf(entry.getPrice()));

        JButton updateBtn = addButton("Actualizar Producto");
        updateBtn.setBackground(new Color(52, 152, 219));
        updateBtn.setForeground(Color.WHITE);

        updateBtn.addActionListener(_ -> controller.onEditEntry(
                this, entry.getId(), nameField.getText(), priceField.getText(), descriptionField.getText(), quantityField.getSelectedText())
        );

        finalizeDialog();
    }
}
