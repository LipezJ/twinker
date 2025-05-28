package com.twinker.presentation.controller;

import com.twinker.application.BillService;
import com.twinker.application.ClientService;
import com.twinker.application.InventoryService;
import com.twinker.domain.collection.BillEntry;
import com.twinker.domain.entity.Client;
import com.twinker.domain.entity.Product;
import com.twinker.presentation.view.AccountingView;

import javax.swing.*;
import java.util.List;

/**
 * Controller class for managing accounting operations in the Twinker
 * application.
 * This class coordinates the display and filtering of sales records and bills,
 * integrating with multiple services for comprehensive financial tracking.
 *
 * <p>
 * The controller handles:
 * <ul>
 * <li>Bill listing and filtering</li>
 * <li>Client and product filter management</li>
 * <li>Bill detail display</li>
 * <li>Sales record tracking</li>
 * </ul>
 * </p>
 *
 * @author Twinker Development Team
 * @see com.twinker.presentation.view.AccountingView
 * @see com.twinker.application.BillService
 * @see com.twinker.application.ClientService
 * @see com.twinker.application.InventoryService
 */
public class AccountingController {
    public final AccountingView view;
    public final BillService billService;
    public final ClientService clientService;
    public final InventoryService inventoryService;

    /**
     * Constructs a new AccountingController.
     * Initializes the required services for accounting operations.
     *
     * @param view the accounting view to be controlled
     */
    public AccountingController(AccountingView view) {
        this.view = view;
        billService = new BillService();
        clientService = new ClientService();
        inventoryService = new InventoryService();
    }

    /**
     * Initializes the controller by loading accounting data and filters.
     */
    public void initAccounting() {
        loadAccounting();
        loadFilters();
    }

    /**
     * Loads and displays all bills sorted by date.
     */
    public void loadAccounting() {
        List<BillEntry> billEntries = billService.getAllBillsSorted();
        view.showSales(billEntries);
    }

    /**
     * Loads and displays client and product filters.
     */
    public void loadFilters() {
        List<Client> clients = clientService.getAllClients();
        List<Product> products = inventoryService.getProducts();

        view.showFilters(clients, products);
    }

    /**
     * Handles reloading of accounting data and filters.
     */
    public void onLoadAccounting() {
        loadAccounting();
        loadFilters();
    }

    /** Constant representing no filter selection */
    public String VOID_FILTER = "-";

    /**
     * Handles filtering bills based on selected client and product.
     * Updates the view with filtered results.
     *
     * @param clientFilter  the combo box containing client filter
     * @param productFilter the combo box containing product filter
     */
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

    /**
     * Handles opening the bill detail dialog.
     * Shows detailed information for a specific bill.
     *
     * @param billEntry the bill entry to display in detail
     */
    public void onOpenBillDialog(BillEntry billEntry) {
        view.showDetailForm(billEntry);
    }
}
