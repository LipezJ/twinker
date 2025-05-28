package com.twinker.presentation.form;

import com.twinker.domain.collection.BillEntry;
import com.twinker.presentation.component.BillSummarize;

import javax.swing.*;
import java.awt.*;

/**
 * Dialog for displaying detailed bill information in the accounting view.
 * This dialog shows a comprehensive summary of a bill including header
 * information and itemized product details.
 *
 * <p>
 * The dialog displays:
 * <ul>
 * <li>Bill number and ID</li>
 * <li>Date and time of sale</li>
 * <li>Customer information</li>
 * <li>Detailed product list</li>
 * <li>Total amount and calculations</li>
 * </ul>
 * </p>
 *
 * @author Twinker Development Team
 * @see com.twinker.presentation.form.FormDialog
 * @see com.twinker.domain.collection.BillEntry
 * @see com.twinker.presentation.component.BillSummarize
 */
public class AccountingBillFormDialog extends FormDialog {

    /**
     * Constructs a new AccountingBillFormDialog.
     * Creates a detailed view of a bill's contents and information.
     *
     * @param parent    the parent frame for this dialog
     * @param billEntry the bill entry containing all sale details
     */
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
