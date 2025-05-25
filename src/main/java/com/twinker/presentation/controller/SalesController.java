package com.twinker.presentation.controller;

import com.twinker.application.BillingService;
import com.twinker.application.ClientService;
import com.twinker.application.InventoryService;
import com.twinker.domain.collection.InventoryEntry;
import com.twinker.domain.collection.SaleEntry;
import com.twinker.domain.entity.Client;
import com.twinker.presentation.view.SalesView;

import javax.swing.*;
import java.util.List;

public class SalesController {
    private final SalesView view;
    private final BillingService billingService;
    private final ClientService clientService;
    private final InventoryService inventoryService;

    public SalesController(SalesView view) {
        this.view = view;
        billingService = new BillingService();
        clientService = new ClientService();
        inventoryService = new InventoryService();
    }

    public void init() {
        updateProducts();
    }

    public void onLoadSales() {
        init();
    }

    public void onSearchProducts(String query) {
        List<InventoryEntry> inventory = inventoryService.searchInventory(query);
        view.showProductsCurrentStock(billingService.updateCurrentProductInventory(inventory));
    }

    public void onClientSelected(JDialog modal, JList<Client> clientJList) {
        Client clientSelected = clientJList.getSelectedValue();
        if (clientSelected != null) {
            billingService.setClient(clientSelected);
            List<SaleEntry> saleEntries = billingService.getSales();

            modal.dispose();
            view.showConfirmForm(saleEntries, billingService.getAmount());
        } else {
            JOptionPane.showMessageDialog(
                    modal,
                    "Por favor seleccione un cliente",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    public void onContinueWithoutClient(JDialog modal) {
        billingService.setClient(null);
        List<SaleEntry> saleEntries = billingService.getSales();

        modal.dispose();
        view.showConfirmForm(saleEntries, billingService.getAmount());
    }

    public void onAddToCart(InventoryEntry inventoryEntry) {
        int productQuantityStockInventory =
                inventoryEntry.getStock() - billingService.getQuantityInBillByProduct(inventoryEntry.product());

        if (productQuantityStockInventory <= 0) {
            JOptionPane.showMessageDialog(
                    view,
                    "No hay suficiente stock",
                    "No hay suficiente stock de este producto en el inventario",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        billingService.addProduct(inventoryEntry.product());
        updateProducts();
    }

    public void onRemoveFromCart(SaleEntry sale) {
        billingService.removeSale(sale);
        updateProducts();
    }

    public void onRemoveOneFromCart(SaleEntry sale) {
        billingService.removeOneSale(sale);
        updateProducts();
    }

    public void onCancelSale() {
        billingService.removeAll();
        updateProducts();
    }

    public void onConfirmSale() {
        List<Client> clients = clientService.getAllClients();
        view.showClientForm(clients);
    }

    public void onConfirmBill(JDialog modal) {
        billingService.confirmBill();
        List<InventoryEntry> products = inventoryService.getAllItems();

        view.showCart(billingService.getSales());
        view.showProducts(products);
        modal.dispose();
    }

    private void updateProducts() {
        view.showProductsCurrentStock(billingService.updateCurrentProductInventory(inventoryService.getAllItems()));
        view.showCart(billingService.getSales());
    }

}
