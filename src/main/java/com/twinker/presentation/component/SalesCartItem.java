package com.twinker.presentation.component;

import com.twinker.domain.collection.SaleEntry;
import com.twinker.presentation.controller.SalesController;

import javax.swing.*;
import java.awt.*;

public class SalesCartItem extends JPanel {

    public SalesCartItem(SaleEntry saleEntry, SalesController controller) {
        setLayout(new BorderLayout(5,5));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        setBackground(getBackground());
        setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JPanel info = new JPanel(new GridLayout(2,1));
        info.setBackground(getBackground());
        info.add(new JLabel(saleEntry.getProductName()));
        info.add(new JLabel("Cantidad: " + saleEntry.getQuantity() + "  Precio/u: $" + saleEntry.getUnitPrice()));
        add(info, BorderLayout.CENTER);

        JButton remove = new JButton("❌");
        remove.setPreferredSize(new Dimension(30,30));
        remove.addActionListener(e -> controller.onRemoveFromCart(saleEntry));
        add(remove, BorderLayout.EAST);
    }

}
