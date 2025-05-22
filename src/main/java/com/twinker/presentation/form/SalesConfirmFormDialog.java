package com.twinker.presentation.form;

import com.twinker.domain.collection.SaleEntry;
import com.twinker.presentation.component.BillSummarize;
import com.twinker.presentation.controller.SalesController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SalesConfirmFormDialog extends FormDialog {
    public SalesConfirmFormDialog(JFrame parent, SalesController controller, List<SaleEntry> saleEntries) {
        super(parent, "Confirmar venta", new Dimension(300, 500));

        addLabel("Factura:");
        JPanel listPanel = new BillSummarize(saleEntries);
        JScrollPane scroll = new JScrollPane(listPanel);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        add(scroll, gbc);
        gbc.gridy++;

        JButton confirmBtn = addButton("Confirmar");
        confirmBtn.addActionListener(_ -> controller.onConfirmBill(this));

        finalizeDialog();
    }
}
