package com.twinker.presentation.form;

import com.twinker.domain.collection.SaleEntry;
import com.twinker.presentation.component.BillSummarize;
import com.twinker.presentation.controller.SalesController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SalesConfirmFormDialog extends FormDialog {
    public SalesConfirmFormDialog(JFrame parent, SalesController controller, List<SaleEntry> saleEntries, double amount) {
        super(parent, "Confirmar venta", new Dimension(400, 500));

        addLabel("Factura:");
        JPanel listPanel = new BillSummarize(saleEntries, amount);
        listPanel.setBorder(BorderFactory.createEmptyBorder());
        listPanel.setMinimumSize(new Dimension(300, 400));
        add(listPanel, gbc);
        gbc.gridy++;

        JButton confirmBtn = addButton("Confirmar");
        confirmBtn.addActionListener(_ -> controller.onConfirmBill(this));

        finalizeDialog();
    }
}
