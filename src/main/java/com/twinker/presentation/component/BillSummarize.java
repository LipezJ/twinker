package com.twinker.presentation.component;

import com.twinker.domain.collection.SaleEntry;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.List;

/**
 * A custom panel component for displaying a summary of a bill or sale.
 * This class creates a detailed view of all items in a sale, including
 * quantities, unit prices, and total amounts.
 *
 * <p>
 * The summary displays:
 * <ul>
 * <li>Scrollable list of sale items</li>
 * <li>Product names and quantities</li>
 * <li>Unit prices and line totals</li>
 * <li>Overall bill total</li>
 * <li>Formatted currency values</li>
 * </ul>
 * </p>
 *
 * @author Twinker Development Team
 * @see com.twinker.domain.collection.SaleEntry
 */
public class BillSummarize extends JPanel {

    /**
     * Constructs a new BillSummarize panel.
     * Creates a scrollable view of sale entries with price calculations
     * and a total amount display.
     *
     * @param salesEntries the list of sale entries to display
     * @param amount       the total amount of the bill
     */
    public BillSummarize(List<SaleEntry> salesEntries, double amount) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        NumberFormat fmt = NumberFormat.getCurrencyInstance();

        JPanel salesPanel = new JPanel();
        salesPanel.setLayout(new BoxLayout(salesPanel, BoxLayout.Y_AXIS)); // Apilar verticalmente

        for (SaleEntry saleEntry : salesEntries) {
            JPanel container = new JPanel(new BorderLayout(5, 5));
            container.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            container.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
            container.setAlignmentX(Component.LEFT_ALIGNMENT);

            JPanel productPanel = new JPanel();
            productPanel.setLayout(new GridLayout(1, 4, 10, 0));
            productPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            container.add(productPanel, BorderLayout.CENTER);

            JLabel nameLabel = new JLabel(saleEntry.getProductName());
            JLabel quantityLabel = new JLabel(String.valueOf(saleEntry.getQuantity()));
            JLabel unitPriceLabel = new JLabel(fmt.format(saleEntry.getUnitPrice()));
            JLabel totalPriceLabel = new JLabel(fmt.format(saleEntry.getUnitPrice() * saleEntry.getQuantity()));

            productPanel.add(nameLabel);
            productPanel.add(quantityLabel);
            productPanel.add(unitPriceLabel);
            productPanel.add(totalPriceLabel);

            salesPanel.add(container);
        }

        JScrollPane scroll = new JScrollPane(salesPanel);
        scroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        scroll.setPreferredSize(new Dimension(500, 300));
        scroll.getVerticalScrollBar().setUnitIncrement(14);

        add(scroll);
        add(Box.createVerticalStrut(10));

        JLabel totalLabel = new JLabel("Total: " + fmt.format(amount));
        totalLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(totalLabel);
    }
}
