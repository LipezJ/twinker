package com.twinker.presentation.component;

import com.twinker.domain.collection.InventoryEntry;
import com.twinker.presentation.controller.SalesController;

import javax.swing.*;
import java.awt.*;

public class SalesProductCard extends JPanel {
    private final JPanel info;

    public SalesProductCard(InventoryEntry entry, SalesController controller, int currentStock) {
        setLayout(new BorderLayout());
        setBackground(getBackground());
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        JLabel thumb = new JLabel();
        thumb.setPreferredSize(new Dimension(80,80));
        add(thumb, BorderLayout.WEST);

        info = new JPanel(new GridLayout(3,1,5,5));
        info.setBackground(getBackground());
        updateInfo(entry.getName(), currentStock, entry.getPrice());
        add(info, BorderLayout.CENTER);

        JButton addBtn = new JButton("+");
        addBtn.setPreferredSize(new Dimension(40,40));
        addBtn.addActionListener(_ -> controller.onAddToCart(entry));
        add(addBtn, BorderLayout.EAST);
    }

    public void updateInfo(String name, int stock, double price) {
        info.removeAll();

        info.add(new JLabel(name));
        info.add(new JLabel("Stock: " + stock));
        info.add(new JLabel("Precio: $" + price));

        info.revalidate();
        info.repaint();
    }

}
