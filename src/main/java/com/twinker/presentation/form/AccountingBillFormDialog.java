package com.twinker.presentation.form;

import com.twinker.domain.collection.BillEntry;
import com.twinker.presentation.component.BillSummarize;

import javax.swing.*;
import java.awt.*;

public class AccountingBillFormDialog extends FormDialog {
    public AccountingBillFormDialog(JFrame parent, BillEntry billEntry) {
        super(parent, "Detalle de la factura", new Dimension(350, 500));

        addLabel("Factura No. " + billEntry.getId());
        addLabel("Productos:");
        JPanel listPanel = new BillSummarize(billEntry.saleEntries());
        JScrollPane scroll = new JScrollPane(listPanel);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setMinimumSize(new Dimension(300, 400));
        add(scroll, gbc);
        gbc.gridy++;

        finalizeDialog();
    }
}
