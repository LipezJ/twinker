package com.twinker.presentation.form;

import com.twinker.domain.collection.BillEntry;
import com.twinker.presentation.component.BillSummarize;

import javax.swing.*;
import java.awt.*;

public class AccountingBillFormDialog extends FormDialog {
    public AccountingBillFormDialog(JFrame parent, BillEntry billEntry) {
        super(parent, "Detalle de la factura", new Dimension(400, 550));

        addLabel("Factura No. " + billEntry.getId());
        addLabel("Fecha y Hora: " + billEntry.getDate());
        addLabel("Cliente: " + billEntry.client().getName());
        addLabel("Productos:");
        JPanel listPanel = new BillSummarize(billEntry.saleEntries(), billEntry.getAmount());
        listPanel.setBorder(BorderFactory.createEmptyBorder());
        listPanel.setMinimumSize(new Dimension(300, 400));
        add(listPanel, gbc);
        gbc.gridy++;

        finalizeDialog();
    }
}
