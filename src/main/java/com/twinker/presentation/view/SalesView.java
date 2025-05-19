package com.twinker.presentation.view;

import com.twinker.domain.collection.InventoryEntry;
import com.twinker.domain.collection.SaleEntry;
import com.twinker.domain.entity.Client;
import com.twinker.presentation.component.SalesCartPanel;
import com.twinker.presentation.component.SalesProductCard;
import com.twinker.presentation.controller.SalesController;
import com.twinker.presentation.form.SalesClientFormDialog;
import com.twinker.presentation.form.SalesConfirmFormDialog;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SalesView extends JPanel {
    private final JTextField searchField;
    private final JPanel productsPanel;
    private final SalesCartPanel cartPanel;
    private final SalesController controller;

    public SalesView() {
        this.controller = new SalesController(this);
        setLayout(new BorderLayout(10, 10));
        setBackground(UIManager.getColor("Panel.background"));

        JPanel content = new JPanel(new BorderLayout(10, 10));
        content.setBackground(UIManager.getColor("Panel.background"));
        content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(content);

        searchField = new JTextField();
        JButton searchButton = new JButton("🔍");
        searchButton.addActionListener(e ->
                controller.onSearchProducts(searchField.getText())
        );
        JPanel north = new JPanel(new BorderLayout(5,5));
        north.setBackground(getBackground());
        north.add(searchField, BorderLayout.CENTER);
        north.add(searchButton, BorderLayout.EAST);
        content.add(north, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout(10,10));
        center.setBackground(getBackground());

        productsPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        productsPanel.setBackground(getBackground());
        JScrollPane scroll = new JScrollPane(productsPanel);
        scroll.setBorder(null);
        center.add(scroll, BorderLayout.CENTER);

        cartPanel = new SalesCartPanel(controller);
        center.add(cartPanel, BorderLayout.EAST);

        controller.init();

        content.add(center, BorderLayout.CENTER);
    }

    public void showClientForm(List<Client> clients) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);

        SalesClientFormDialog dialog = new SalesClientFormDialog(frame, controller, clients);
        dialog.setVisible(true);
    }

    public void showConfirmForm(List<SaleEntry> salesEntries) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);

        SalesConfirmFormDialog dialog = new SalesConfirmFormDialog(frame, controller, salesEntries);
        dialog.setVisible(true);
    }

    public void showProducts(List<InventoryEntry> inventory) {
        productsPanel.removeAll();
        for (InventoryEntry entry : inventory) {
            productsPanel.add(new SalesProductCard(entry, controller));
        }
        productsPanel.revalidate();
        productsPanel.repaint();
    }

    public void showCart(List<SaleEntry> items) {
        cartPanel.showCartItems(items);
    }
}
