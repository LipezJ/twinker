package com.twinker.presentation.component;

import com.twinker.domain.collection.SaleEntry;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.List;

public class BillSummarize extends JPanel {
    public BillSummarize(List<SaleEntry> salesEntries) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

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

            add(productPanel);
        }
    }
}
