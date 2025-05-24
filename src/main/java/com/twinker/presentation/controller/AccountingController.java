package com.twinker.presentation.controller;

import com.twinker.application.BillService;
import com.twinker.application.ClientService;
import com.twinker.application.InventoryService;
import com.twinker.domain.collection.BillEntry;
import com.twinker.domain.entity.Client;
import com.twinker.domain.entity.Product;
import com.twinker.presentation.view.AccountingView;

import javax.swing.*;
import java.util.Collection;
import java.util.List;

public class AccountingController {
    public final AccountingView view;
    public final BillService billService;
    public final ClientService clientService;
    public final InventoryService inventoryService;

    public AccountingController(AccountingView view) {
        this.view = view;
        billService = new BillService();
        clientService = new ClientService();
        inventoryService = new InventoryService();
    }

    public void initAccounting() {
        loadAccounting();
        loadFilters();
    }

    public void loadAccounting() {
        List<BillEntry> billEntries = billService.getAllBillsSorted();
        view.showSales(billEntries);
    }

    public void loadFilters() {
        List<Client> clients = clientService.getAllClients();
        List<Product> products = inventoryService.getProducts();

        view.showFilters(clients, products);
    }

    public void onLoadAccounting() {
        loadAccounting();
        loadFilters();
    }

    public String VOID_FILTER = "-";

    public void onFilterBills(JComboBox<String> clientFilter, JComboBox<String> productFilter) {
        if (clientFilter.getSelectedItem() != null && productFilter.getSelectedItem() != null) {
            String clientName = clientFilter.getSelectedItem().toString();
            String productName = productFilter.getSelectedItem().toString();

            if (clientName.equals(VOID_FILTER)) clientName = null;
            if (productName.equals(VOID_FILTER)) productName = null;

            List<BillEntry> bills = billService.filterBills(clientName, productName);
            view.showSales(bills);
        }
    }

    public void onOpenBillDialog(BillEntry billEntry) {
        view.showDetailForm(billEntry);
    }
}
