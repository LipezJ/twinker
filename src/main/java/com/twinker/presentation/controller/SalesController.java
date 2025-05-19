package com.twinker.presentation.controller;

import com.twinker.application.BillingService;
import com.twinker.application.ClientService;
import com.twinker.application.InventoryService;
import com.twinker.domain.collection.InventoryEntry;
import com.twinker.domain.collection.SaleEntry;
import com.twinker.domain.entity.Client;
import com.twinker.domain.entity.Product;
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
        onLoad();
    }

    public void onLoad() {
        List<InventoryEntry> products = inventoryService.getAllItems();
        view.showProducts(products);
    }

    public void onSearchProducts(String query) {
        List<InventoryEntry> inventory = inventoryService.searchInventory(query);
        view.showProducts(inventory);
    }

    public void onClientSelected(JDialog modal, JList<Client> clientJList) {
        Client clientSelected = clientJList.getSelectedValue();
        if (clientSelected != null) {
            billingService.setClient(clientSelected);
            List<SaleEntry> saleEntries = billingService.getSales();

            modal.dispose();
            view.showConfirmForm(saleEntries);
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
        view.showConfirmForm(saleEntries);
    }

    public void onAddToCart(Product product) {
        billingService.addProduct(product);
        view.showCart(billingService.getSales());
    }

    public void onRemoveFromCart(SaleEntry sale) {
        billingService.removeSale(sale);
        view.showCart(billingService.getSales());
    }

    public void onCancelSale() {
        billingService.removeAll();
        view.showCart(billingService.getSales());
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

}
