package com.twinker.presentation.controller;

import com.twinker.application.InventoryService;
import com.twinker.domain.collection.InventoryEntry;
import com.twinker.presentation.view.InventoryView;

import javax.swing.*;
import java.util.List;

public class InventoryController extends ProtectedController {
    private final InventoryView view;
    private final InventoryService inventoryService;

    public InventoryController(InventoryView view) {
        this.view = view;
        inventoryService = new InventoryService();
    }

    public void init() {
        onLoadInventory();
    }

    public void onLoadInventory() {
        List<InventoryEntry> inventory = inventoryService.getAllItems();
        view.showInventory(inventory);
    }

    public void onSearchInventory(String query) {
        List<InventoryEntry> inventory = inventoryService.searchInventory(query);
        view.showInventory(inventory);
    }

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

    public void onDeleteEntry(InventoryEntry entry) {
        inventoryService.deleteEntry(entry.getId(), entry.getProductId());

        JOptionPane.showMessageDialog(view, "Producto eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

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

    public void onOpenCreateForm() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(view);
        boolean allowed = openVerifyPinForm(frame);

        if (allowed) {
            view.showCreateForm();
            onLoadInventory();
        }
    }

    public void onOpenDeleteForm(InventoryEntry entry) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(view);
        boolean allowed = openVerifyPinForm(frame);

        if (!allowed) return;

        view.showConfirmDeleteDialog(entry);
        onLoadInventory();
    }

    public void onOpenEditForm(InventoryEntry entry) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(view);
        boolean allowed = openVerifyPinForm(frame);

        if (allowed) {
            view.showEditForm(entry);
            onLoadInventory();
        }
    }
}
