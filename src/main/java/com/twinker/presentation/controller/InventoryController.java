package com.twinker.presentation.controller;

import com.twinker.application.InventoryService;
import com.twinker.domain.collection.InventoryEntry;
import com.twinker.presentation.view.InventoryView;

import javax.swing.*;
import java.util.List;

/**
 * Controller class for managing inventory operations in the Twinker
 * application.
 * This class extends ProtectedController to ensure secure access to inventory
 * management functions through PIN verification.
 *
 * <p>
 * The controller handles:
 * <ul>
 * <li>Product listing and search</li>
 * <li>Adding new products</li>
 * <li>Editing existing products</li>
 * <li>Deleting products</li>
 * <li>Access control for operations</li>
 * </ul>
 * </p>
 *
 * @author Twinker Development Team
 * @see com.twinker.presentation.view.InventoryView
 * @see com.twinker.application.InventoryService
 * @see com.twinker.presentation.controller.ProtectedController
 */
public class InventoryController extends ProtectedController {
    private final InventoryView view;
    private final InventoryService inventoryService;

    /**
     * Constructs a new InventoryController.
     * Initializes the inventory service for product management.
     *
     * @param view the inventory view to be controlled
     */
    public InventoryController(InventoryView view) {
        this.view = view;
        inventoryService = new InventoryService();
    }

    /**
     * Initializes the controller by loading inventory data.
     */
    public void init() {
        onLoadInventory();
    }

    /**
     * Handles loading and displaying the inventory.
     * Updates the view with all available products.
     */
    public void onLoadInventory() {
        List<InventoryEntry> inventory = inventoryService.getAllItems();
        view.showInventory(inventory);
    }

    /**
     * Handles inventory search operations.
     * Updates the view with filtered products.
     *
     * @param query the search term for filtering products
     */
    public void onSearchInventory(String query) {
        List<InventoryEntry> inventory = inventoryService.searchInventory(query);
        view.showInventory(inventory);
    }

    /**
     * Handles adding a new product to the inventory.
     * Validates input data and shows appropriate messages.
     *
     * @param modal       the add product dialog
     * @param name        the product name
     * @param price       the product price as string
     * @param description the product description
     * @param quantity    the initial stock quantity as string
     */
    public void onAddEntry(JDialog modal, String name, String price, String description, String quantity) {
        double parsedPrice;
        int parsedQuantity;
        try {
            parsedPrice = Double.parseDouble(price);
            parsedQuantity = Integer.parseInt(quantity);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(modal, "Error al agregar el producto. Verifica los datos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        inventoryService.addEntry(name, parsedPrice, description, parsedQuantity);

        JOptionPane.showMessageDialog(modal, "Producto agregado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        modal.dispose();
    }

    /**
     * Handles deleting a product from the inventory.
     * Shows confirmation message after deletion.
     *
     * @param entry the inventory entry to delete
     */
    public void onDeleteEntry(InventoryEntry entry) {
        inventoryService.deleteEntry(entry.getId(), entry.getProductId());

        JOptionPane.showMessageDialog(view, "Producto eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Handles updating an existing product.
     * Validates input data and shows appropriate messages.
     *
     * @param modal       the edit product dialog
     * @param inventoryId the ID of the inventory entry
     * @param name        the updated product name
     * @param price       the updated price as string
     * @param description the updated description
     * @param quantity    the updated quantity as string
     */
    public void onEditEntry(JDialog modal, String inventoryId, String name, String price, String description, String quantity) {
        double parsedPrice;
        int parsedQuantity;
        try {
            parsedPrice = Double.parseDouble(price);
            parsedQuantity = Integer.parseInt(quantity);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(modal, "Error al actualizar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        inventoryService.editEntry(inventoryId, name, parsedPrice, description, parsedQuantity);

        JOptionPane.showMessageDialog(modal, "Producto actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        modal.dispose();
    }

    /**
     * Handles opening the create product form.
     * Verifies PIN before allowing access.
     */
    public void onOpenCreateForm() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(view);
        boolean allowed = openVerifyPinForm(frame);

        if (allowed) {
            view.showCreateForm();
            onLoadInventory();
        }
    }

    /**
     * Handles opening the delete confirmation dialog.
     * Verifies PIN before allowing access.
     *
     * @param entry the inventory entry to delete
     */
    public void onOpenDeleteForm(InventoryEntry entry) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(view);
        boolean allowed = openVerifyPinForm(frame);

        if (!allowed) return;

        view.showConfirmDeleteDialog(entry);
        onLoadInventory();
    }

    /**
     * Handles opening the edit product form.
     * Verifies PIN before allowing access.
     *
     * @param entry the inventory entry to edit
     */
    public void onOpenEditForm(InventoryEntry entry) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(view);
        boolean allowed = openVerifyPinForm(frame);

        if (allowed) {
            view.showEditForm(entry);
            onLoadInventory();
        }
    }
}
