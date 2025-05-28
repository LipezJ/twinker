package com.twinker.presentation.view;

import com.twinker.domain.collection.InventoryEntry;
import com.twinker.presentation.controller.InventoryController;
import com.twinker.presentation.form.InventoryEditFormDialog;
import com.twinker.presentation.form.InventoryFormDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

/**
 * The inventory management view of the Twinker application.
 * This class provides an interface for managing product inventory, including
 * adding, editing, and removing products, as well as tracking stock levels.
 *
 * <p>
 * The view consists of:
 * <ul>
 * <li>A search bar for filtering inventory items</li>
 * <li>A scrollable list of product cards showing inventory details</li>
 * <li>Controls for adding new products and managing existing ones</li>
 * </ul>
 * </p>
 *
 * @author Twinker Development Team
 * @see com.twinker.presentation.controller.InventoryController
 * @see com.twinker.domain.collection.InventoryEntry
 */
public class InventoryView extends JPanel {
    private final JPanel content;
    private JPanel itemsPanel;

    private final InventoryController inventoryController;

    /**
     * Constructs a new InventoryView.
     * Initializes the UI components and sets up the inventory controller.
     * The view will automatically load inventory data when it becomes visible.
     */
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
        JScrollPane productsPanel = new JScrollPane(itemsPanel);
        productsPanel.setBorder(null);
        productsPanel.getVerticalScrollBar().setUnitIncrement(14);
        content.add(productsPanel);

        JButton openFormButton = new JButton("Agregar Producto");
        openFormButton.setPreferredSize(new Dimension(180, 30));
        openFormButton.addActionListener(_ -> inventoryController.onOpenCreateForm());
        content.add(openFormButton, BorderLayout.SOUTH);

        inventoryController.init();
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) { inventoryController.onLoadInventory(); }
        });
    }

    /**
     * Updates the inventory display with the current list of items.
     * Creates a card for each inventory entry showing product details
     * and management controls.
     *
     * @param inventory the list of inventory entries to display
     */
    public void showInventory(List<InventoryEntry> inventory) {
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
            info.add(new JLabel("Stock: " + item.getStock()));
            info.add(new JLabel("Precio: $" + item.getPrice()));
            card.add(info, BorderLayout.CENTER);

            JPanel actionsPanel = new JPanel(new GridLayout(1, 2, 5, 5));
            actionsPanel.setBackground(getBackground());
            actionsPanel.setMaximumSize(new Dimension(50, 100));

            JButton delete = new JButton("🗑️");
            delete.setPreferredSize(new Dimension(40, 40));
            delete.addActionListener(_ -> inventoryController.onOpenDeleteForm(item));

            JButton edit = new JButton("✎");
            edit.setPreferredSize(new Dimension(40, 40));
            edit.addActionListener(_ -> inventoryController.onOpenEditForm(item));

            actionsPanel.add(delete, BorderLayout.WEST);
            actionsPanel.add(edit, BorderLayout.EAST);
            card.add(actionsPanel, BorderLayout.EAST);
            card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

            itemsPanel.add(card);
            itemsPanel.add(Box.createVerticalStrut(10));
        }

        itemsPanel.revalidate();
        itemsPanel.repaint();
    }

    /**
     * Displays the dialog for creating a new inventory entry.
     * Opens a modal form that allows input of new product details.
     */
    public void showCreateForm() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        InventoryFormDialog dialog = new InventoryFormDialog(frame, inventoryController);
        dialog.setVisible(true);
    }

    /**
     * Displays the dialog for editing an existing inventory entry.
     * Opens a modal form pre-populated with the current product details.
     *
     * @param entry the inventory entry to edit
     */
    public void showEditForm(InventoryEntry entry) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        InventoryEditFormDialog dialog = new InventoryEditFormDialog(frame, inventoryController, entry);
        dialog.setVisible(true);
    }

    /**
     * Shows a confirmation dialog for deleting an inventory entry.
     * If confirmed, triggers the deletion process through the controller.
     *
     * @param entry the inventory entry to delete
     */
    public void showConfirmDeleteDialog(InventoryEntry entry) {
        int option = JOptionPane.showConfirmDialog(
                this,
                String.format("¿Desea eliminar %s elemento(s)?", entry.getName()),
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (option != JOptionPane.YES_OPTION) return;
        inventoryController.onDeleteEntry(entry);
    }
}
