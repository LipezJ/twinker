package com.twinker.presentation.component;

import com.twinker.domain.collection.SaleEntry;
import com.twinker.presentation.controller.SalesController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SalesCartPanel extends JPanel {
    private final JPanel itemsContainer;
    private final SalesController controller;

    public SalesCartPanel(SalesController controller) {
        this.controller = controller;
        setLayout(new BorderLayout());
        setBackground(UIManager.getColor("Panel.background"));
        setPreferredSize(new Dimension(300, 0));

        itemsContainer = new JPanel();
        itemsContainer.setLayout(new BoxLayout(itemsContainer, BoxLayout.Y_AXIS));
        itemsContainer.setBackground(getBackground());
        itemsContainer.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JScrollPane scroll = new JScrollPane(itemsContainer);
        add(scroll, BorderLayout.CENTER);

        showCartItems(new ArrayList<>());
        showButtons(controller);
    }

    private void showButtons(SalesController controller) {
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JButton cancelButton = new JButton("Cancelar");
        cancelButton.setBackground(Color.RED);
        cancelButton.setForeground(Color.WHITE);
        cancelButton.addActionListener(_ -> controller.onCancelSale());
        JButton confirmButton = new JButton("Confirmar venta");
        confirmButton.setBackground(new Color(46,204,113));
        confirmButton.setForeground(Color.WHITE);
        confirmButton.addActionListener(_ -> controller.onConfirmSale());
        buttonsPanel.add(cancelButton);
        buttonsPanel.add(confirmButton);

        add(buttonsPanel, BorderLayout.SOUTH);
    }

    public void showCartItems(List<SaleEntry> items) {
        itemsContainer.removeAll();
        if (items.isEmpty()) {
            itemsContainer.add(new JLabel("El carrito está vacío"));
        } else {
            for (SaleEntry item : items) {
                itemsContainer.add(new SalesCartItem(item, controller));
                itemsContainer.add(Box.createVerticalStrut(5));
            }
        }
        itemsContainer.revalidate();
        itemsContainer.repaint();
    }
}
