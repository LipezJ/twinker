package com.twinker.presentation.component;

import com.twinker.domain.collection.InventoryEntry;
import com.twinker.presentation.controller.SalesController;

import javax.swing.*;
import java.awt.*;

/**
 * A custom panel component for displaying product information in the sales
 * view.
 * This class creates a card-style display for each product, showing its details
 * and providing controls for adding it to the cart.
 *
 * <p>
 * The card displays:
 * <ul>
 * <li>Product thumbnail image</li>
 * <li>Product name and details</li>
 * <li>Current stock level</li>
 * <li>Price information</li>
 * <li>Add to cart button</li>
 * </ul>
 * </p>
 *
 * @author Twinker Development Team
 * @see com.twinker.domain.collection.InventoryEntry
 * @see com.twinker.presentation.controller.SalesController
 */
public class SalesProductCard extends JPanel {
    private final JPanel info;

    /**
     * Constructs a new SalesProductCard.
     * Creates a card layout displaying product information and an add button.
     *
     * @param entry        the inventory entry containing product details
     * @param controller   the sales controller to handle add to cart actions
     * @param currentStock the current available stock for the product
     */
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

    /**
     * Updates the product information displayed on the card.
     * Refreshes the name, stock level, and price display.
     *
     * @param name  the product name
     * @param stock the current stock level
     * @param price the product price
     */
    public void updateInfo(String name, int stock, double price) {
        info.removeAll();

        info.add(new JLabel(name));
        info.add(new JLabel("Stock: " + stock));
        info.add(new JLabel("Precio: $" + price));

        info.revalidate();
        info.repaint();
    }
}
