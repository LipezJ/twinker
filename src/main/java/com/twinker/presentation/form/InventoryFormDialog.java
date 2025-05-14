package com.twinker.presentation.form;

import com.twinker.presentation.controller.InventoryController;

import javax.swing.*;
import java.awt.*;

public class InventoryFormDialog extends FormDialog {

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
        createButton.addActionListener(_ -> {
            controller.onAddEntry(
                    this,
                    nameField.getText(),
                    priceField.getText(),
                    descriptionField.getText(),
                    quantityField.getText()
            );
        });

        finalizeDialog();
    }
}
