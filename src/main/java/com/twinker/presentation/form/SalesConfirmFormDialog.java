package com.twinker.presentation.form;

import com.twinker.domain.collection.SaleEntry;
import com.twinker.presentation.component.BillSummarize;
import com.twinker.presentation.controller.SalesController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Dialog for confirming a sale in the Twinker application.
 * This dialog displays a final summary of the sale before completion,
 * allowing review and confirmation of the transaction.
 *
 * <p>
 * The dialog shows:
 * <ul>
 * <li>Complete list of sale items</li>
 * <li>Quantities and prices</li>
 * <li>Total sale amount</li>
 * <li>Confirmation button</li>
 * </ul>
 * </p>
 *
 * @author Twinker Development Team
 * @see com.twinker.presentation.form.FormDialog
 * @see com.twinker.presentation.controller.SalesController
 * @see com.twinker.domain.collection.SaleEntry
 * @see com.twinker.presentation.component.BillSummarize
 */
public class SalesConfirmFormDialog extends FormDialog {

    /**
     * Constructs a new SalesConfirmFormDialog.
     * Creates a confirmation dialog showing the sale summary
     * before finalizing the transaction.
     *
     * @param parent      the parent frame for this dialog
     * @param controller  the sales controller to handle confirmation
     * @param saleEntries the list of items in the sale
     * @param amount      the total amount of the sale
     */
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
