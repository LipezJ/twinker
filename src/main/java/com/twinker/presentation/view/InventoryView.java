package com.twinker.presentation.view;

import com.twinker.domain.collection.InventoryEntry;
import com.twinker.presentation.controller.InventoryController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class InventoryView extends JPanel {
    private final JPanel content;
    private JPanel itemsPanel;

    private final InventoryController inventoryController;

    public InventoryView() {
        inventoryController = new InventoryController(this);

        setLayout(new BorderLayout(10, 10));
        setBackground(UIManager.getColor("Panel.background"));

        content = new JPanel(new BorderLayout(10, 10));
        content.setBackground(UIManager.getColor("Panel.background"));
        content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(content);

        JTextField searchField = new JTextField();
        JButton searchButton = new JButton("🔍");
        searchButton.addActionListener(_ ->
                inventoryController.onSearchInventory(searchField.getText())
        );
        JPanel top = new JPanel(new BorderLayout(5, 5));
        top.setBackground(getBackground());
        top.add(searchField, BorderLayout.CENTER);
        top.add(searchButton, BorderLayout.EAST);
        content.add(top, BorderLayout.NORTH);

        itemsPanel = new JPanel();
        itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));
        itemsPanel.setBackground(getBackground());
        inventoryController.onLoadInventory();
        content.add(itemsPanel);

        JButton openFormButton = new JButton("Agregar Producto");
        openFormButton.setPreferredSize(new Dimension(180, 30));
        openFormButton.addActionListener(_ -> inventoryController.onOpenCreateForm());
        content.add(openFormButton, BorderLayout.SOUTH);
    }

    public void loadInventory(List<InventoryEntry> inventory) {
        if (itemsPanel == null) {
            itemsPanel = new JPanel();
            itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));
            itemsPanel.setBackground(getBackground());

            JScrollPane itemsScroll = new JScrollPane(itemsPanel);
            itemsScroll.setBorder(null);
            content.add(itemsScroll, BorderLayout.CENTER);
        } else {
            itemsPanel.removeAll();
        }

        for (InventoryEntry item : inventory) {
            JPanel card = new JPanel(new BorderLayout());
            card.setBackground(getBackground());
            card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            JLabel thumb = new JLabel();
            thumb.setPreferredSize(new Dimension(80, 80));
            card.add(thumb, BorderLayout.WEST);

            JPanel info = new JPanel(new GridLayout(3, 1, 5, 5));
            info.setBackground(getBackground());
            info.add(new JLabel(item.getName()));
            info.add(new JLabel("Stock: " + item.getQuantity()));
            info.add(new JLabel("Precio: $" + item.getPrice()));
            card.add(info, BorderLayout.CENTER);

            JButton edit = new JButton("✎");
            edit.setPreferredSize(new Dimension(40, 40));
            edit.addActionListener(_ -> inventoryController.onOpenEditForm(item));

            card.add(edit, BorderLayout.EAST);
            card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

            itemsPanel.add(card);
            itemsPanel.add(Box.createVerticalStrut(10));
        }

        itemsPanel.revalidate();
        itemsPanel.repaint();
    }
}
