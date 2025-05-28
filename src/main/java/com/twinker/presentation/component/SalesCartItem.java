package com.twinker.presentation.component;

import com.twinker.domain.collection.SaleEntry;
import com.twinker.presentation.controller.SalesController;

import javax.swing.*;
import java.awt.*;

/**
 * A custom panel component for displaying individual items in the shopping
 * cart.
 * This class creates a compact view of a sale entry with controls for
 * modifying the quantity or removing the item.
 *
 * <p>
 * The item display includes:
 * <ul>
 * <li>Product name and details</li>
 * <li>Quantity and unit price information</li>
 * <li>Controls for decreasing quantity</li>
 * <li>Controls for removing the item entirely</li>
 * </ul>
 * </p>
 *
 * @author Twinker Development Team
 * @see com.twinker.domain.collection.SaleEntry
 * @see com.twinker.presentation.controller.SalesController
 */
public class SalesCartItem extends JPanel {

    /**
     * Constructs a new SalesCartItem.
     * Creates a panel showing sale entry details and quantity controls.
     *
     * @param saleEntry  the sale entry containing item details
     * @param controller the sales controller to handle item modifications
     */
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

        JPanel actionPanel = new JPanel();

        JButton removeOne = new JButton("➖");
        removeOne.setPreferredSize(new Dimension(30,30));
        removeOne.addActionListener(_ -> controller.onRemoveOneFromCart(saleEntry));
        actionPanel.add(removeOne);

        JButton remove = new JButton("❌");
        remove.setPreferredSize(new Dimension(30,30));
        remove.addActionListener(_ -> controller.onRemoveFromCart(saleEntry));
        actionPanel.add(remove);

        add(actionPanel, BorderLayout.EAST);
    }

}
