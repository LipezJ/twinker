package com.twinker.presentation.component;

import com.twinker.domain.collection.SaleEntry;
import com.twinker.presentation.controller.SalesController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A custom panel component for displaying and managing the shopping cart
 * in the Twinker application's sales interface.
 *
 * <p>
 * The panel features:
 * <ul>
 * <li>A scrollable list of cart items</li>
 * <li>Cancel and confirm sale buttons</li>
 * <li>Dynamic updates of cart contents</li>
 * <li>Integration with the sales controller for cart operations</li>
 * </ul>
 * </p>
 *
 * @author Twinker Development Team
 * @see com.twinker.presentation.component.SalesCartItem
 * @see com.twinker.presentation.controller.SalesController
 * @see com.twinker.domain.collection.SaleEntry
 */
public class SalesCartPanel extends JPanel {
    private final JPanel itemsContainer;
    private final SalesController controller;

    /**
     * Constructs a new SalesCartPanel.
     * Initializes the cart display with an empty list and sets up
     * the control buttons.
     *
     * @param controller the sales controller to handle cart operations
     */
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
        scroll.getVerticalScrollBar().setUnitIncrement(14);
        add(scroll, BorderLayout.CENTER);

        showCartItems(new ArrayList<>());
        showButtons(controller);
    }

    /**
     * Sets up the cancel and confirm buttons for the cart.
     * The cancel button clears the cart, while the confirm button
     * initiates the checkout process.
     *
     * @param controller the sales controller to handle button actions
     */
    private void showButtons(SalesController controller) {
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JButton cancelButton = new JButton("Cancelar");
        cancelButton.setBackground(Color.RED);
        cancelButton.setForeground(Color.WHITE);
        cancelButton.addActionListener(e -> controller.onCancelSale());
        JButton confirmButton = new JButton("Confirmar venta");
        confirmButton.setBackground(new Color(46,204,113));
        confirmButton.setForeground(Color.WHITE);
        confirmButton.addActionListener(e -> controller.onConfirmSale());
        buttonsPanel.add(cancelButton);
        buttonsPanel.add(confirmButton);

        add(buttonsPanel, BorderLayout.SOUTH);
    }

    /**
     * Updates the cart display with the current list of items.
     * If the cart is empty, displays a message prompting to add products.
     *
     * @param items the list of sale entries to display in the cart
     */
    public void showCartItems(List<SaleEntry> items) {
        itemsContainer.removeAll();
        if (items.isEmpty()) {
            itemsContainer.add(new JLabel("Agregue un producto"));
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
