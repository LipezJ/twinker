package com.twinker.presentation.form;

import com.twinker.domain.collection.SaleEntry;
import com.twinker.presentation.controller.SalesController;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.List;

public class SalesConfirmFormDialog extends FormDialog {
    public SalesConfirmFormDialog(JFrame parent, SalesController controller, List<SaleEntry> salesEntries) {
        super(parent, "Confirmar venta", new Dimension(300, 500));

        addLabel("Factura:");

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        for (SaleEntry saleEntry : salesEntries) {
            JPanel productPanel = new JPanel();

            productPanel.setLayout(new GridLayout(1, 4, 10, 0));
            productPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            NumberFormat fmt = NumberFormat.getCurrencyInstance();

            JLabel nameLabel = new JLabel(saleEntry.getProductName());
            JLabel quantityLabel = new JLabel(String.valueOf(saleEntry.getQuantity()));
            JLabel unitPriceLabel = new JLabel(fmt.format(saleEntry.getUnitPrice()));
            JLabel totalPriceLabel = new JLabel(fmt.format(saleEntry.getUnitPrice() * saleEntry.getQuantity()));

            nameLabel.setVerticalAlignment(SwingConstants.CENTER);
            quantityLabel.setVerticalAlignment(SwingConstants.CENTER);
            unitPriceLabel.setVerticalAlignment(SwingConstants.CENTER);
            totalPriceLabel.setVerticalAlignment(SwingConstants.CENTER);

            productPanel.add(nameLabel);
            productPanel.add(quantityLabel);
            productPanel.add(unitPriceLabel);
            productPanel.add(totalPriceLabel);

            listPanel.add(productPanel);
        }

        JScrollPane scroll = new JScrollPane(listPanel);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        add(scroll, gbc);
        gbc.gridy++;

        JButton confirmBtn = addButton("Confirmar");
        confirmBtn.addActionListener(_ -> controller.onConfirmBill(this));
        gbc.gridy++;

        finalizeDialog();
    }
}
