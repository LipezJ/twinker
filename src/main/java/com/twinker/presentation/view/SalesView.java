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
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;
import java.util.Map;

/**
 * The sales management view of the Twinker application.
 * This class provides an interface for managing product sales, including
 * product search, shopping cart management, and sale completion.
 *
 * <p>
 * The view consists of:
 * <ul>
 * <li>A search bar for filtering products</li>
 * <li>A grid of product cards showing available inventory</li>
 * <li>A shopping cart panel showing selected items</li>
 * </ul>
 * </p>
 *
 * @author Twinker Development Team
 * @see com.twinker.presentation.controller.SalesController
 * @see com.twinker.presentation.component.SalesCartPanel
 * @see com.twinker.presentation.component.SalesProductCard
 */
public class SalesView extends JPanel {
    private final JTextField searchField;
    private final JPanel productsPanel;
    private final SalesCartPanel cartPanel;
    private final SalesController controller;

    /**
     * Constructs a new SalesView.
     * Initializes the UI components and sets up the sales controller.
     * The view will automatically load sales data when it becomes visible.
     */
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

        productsPanel = new JPanel(new GridLayout(10, 2, 10, 10));
        productsPanel.setBackground(getBackground());
        JScrollPane scroll = new JScrollPane(productsPanel);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(14);
        center.add(scroll);

        cartPanel = new SalesCartPanel(controller);
        center.add(cartPanel, BorderLayout.EAST);

        content.add(center, BorderLayout.CENTER);

        controller.init();
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) { controller.onLoadSales(); }
        });
    }

    /**
     * Displays the client selection dialog.
     * This dialog allows selecting an existing client or creating a new one for the
     * sale.
     *
     * @param clients the list of existing clients to display
     */
    public void showClientForm(List<Client> clients) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);

        SalesClientFormDialog dialog = new SalesClientFormDialog(frame, controller, clients);
        dialog.setVisible(true);
    }

    /**
     * Displays the sale confirmation dialog.
     * This dialog shows the final sale details and allows confirming the
     * transaction.
     *
     * @param salesEntries the list of items in the sale
     * @param amount       the total amount of the sale
     */
    public void showConfirmForm(List<SaleEntry> salesEntries, double amount) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);

        SalesConfirmFormDialog dialog = new SalesConfirmFormDialog(frame, controller, salesEntries, amount);
        dialog.setVisible(true);
    }

    /**
     * Updates the products grid with the current inventory.
     *
     * @param inventory the list of inventory entries to display
     */
    public void showProducts(List<InventoryEntry> inventory) {
        productsPanel.removeAll();
        for (InventoryEntry entry : inventory) {
            SalesProductCard productCard = new SalesProductCard(entry, controller, entry.getStock());
            productCard.setMaximumSize(new Dimension(0, 200));
            productsPanel.add(productCard);
        }
        productsPanel.revalidate();
        productsPanel.repaint();
    }

    /**
     * Updates the products grid with current stock levels.
     * This method is used when the inventory needs to show real-time stock levels.
     *
     * @param currentInventory a map of inventory entries to their current stock
     *                         levels
     */
    public void showProductsCurrentStock(Map<InventoryEntry, Integer> currentInventory) {
        productsPanel.removeAll();
        for (InventoryEntry entry : currentInventory.keySet()) {
            SalesProductCard productCard = new SalesProductCard(entry, controller, currentInventory.get(entry));
            productCard.setMaximumSize(new Dimension(0, 200));
            productsPanel.add(productCard);
        }
        productsPanel.revalidate();
        productsPanel.repaint();
    }

    /**
     * Updates the shopping cart panel with the current items.
     *
     * @param items the list of sale entries in the cart
     */
    public void showCart(List<SaleEntry> items) {
        cartPanel.showCartItems(items);
    }
}
