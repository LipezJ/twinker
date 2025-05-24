package com.twinker.presentation.component;

import com.twinker.domain.collection.InventoryEntry;
import com.twinker.presentation.controller.SalesController;

import javax.swing.*;
import java.awt.*;

public class SalesProductCard extends JPanel {

    public SalesProductCard(InventoryEntry entry, SalesController controller) {
        setLayout(new BorderLayout());
        setBackground(getBackground());
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        JLabel thumb = new JLabel();
        thumb.setPreferredSize(new Dimension(80,80));
        add(thumb, BorderLayout.WEST);

        JPanel info = new JPanel(new GridLayout(3,1,5,5));
        info.setBackground(getBackground());
        info.add(new JLabel(entry.getName()));
        info.add(new JLabel("Stock: " + entry.getStock()));
        info.add(new JLabel("Precio: $" + entry.getPrice()));
        add(info, BorderLayout.CENTER);

        JButton addBtn = new JButton("+");
        addBtn.setPreferredSize(new Dimension(40,40));
        addBtn.addActionListener(_ -> controller.onAddToCart(entry.product()));
        add(addBtn, BorderLayout.EAST);
    }

}
