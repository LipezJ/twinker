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

/**
 * Controller class for managing sales operations in the Twinker application.
 * This class coordinates interactions between the sales view and the business
 * services for billing, inventory, and client management.
 *
 * <p>
 * The controller handles:
 * <ul>
 * <li>Product search and display</li>
 * <li>Shopping cart operations</li>
 * <li>Client selection</li>
 * <li>Bill confirmation</li>
 * <li>Stock level validation</li>
 * </ul>
 * </p>
 *
 * @author Twinker Development Team
 * @see com.twinker.presentation.view.SalesView
 * @see com.twinker.application.BillingService
 * @see com.twinker.application.ClientService
 * @see com.twinker.application.InventoryService
 */
public class SalesController {
    private final SalesView view;
    private final BillingService billingService;
    private final ClientService clientService;
    private final InventoryService inventoryService;

    /**
     * Constructs a new SalesController.
     * Initializes the required services for sales operations.
     *
     * @param view the sales view to be controlled
     */
    public SalesController(SalesView view) {
        this.view = view;
        billingService = new BillingService();
        clientService = new ClientService();
        inventoryService = new InventoryService();
    }

    /**
     * Initializes the controller by updating product display.
     */
    public void init() {
        updateProducts();
    }

    /**
     * Handles the sales view loading event.
     */
    public void onLoadSales() {
        init();
    }

    /**
     * Handles product search operations.
     * Updates the view with filtered products based on search query.
     *
     * @param query the search term for filtering products
     */
    public void onSearchProducts(String query) {
        List<InventoryEntry> inventory = inventoryService.searchInventory(query);
        view.showProductsCurrentStock(billingService.updateCurrentProductInventory(inventory));
    }

    /**
     * Handles client selection for a sale.
     * Validates selection and updates the bill with client information.
     *
     * @param modal       the client selection dialog
     * @param clientJList the list component containing clients
     */
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

    /**
     * Handles proceeding with a sale without client selection.
     *
     * @param modal the client selection dialog
     */
    public void onContinueWithoutClient(JDialog modal) {
        billingService.setClient(null);
        List<SaleEntry> saleEntries = billingService.getSales();

        modal.dispose();
        view.showConfirmForm(saleEntries, billingService.getAmount());
    }

    /**
     * Handles adding a product to the shopping cart.
     * Validates stock levels before adding.
     *
     * @param inventoryEntry the product to add to cart
     */
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

    /**
     * Handles removing an entire product entry from the cart.
     *
     * @param sale the sale entry to remove
     */
    public void onRemoveFromCart(SaleEntry sale) {
        billingService.removeSale(sale);
        updateProducts();
    }

    /**
     * Handles removing one unit of a product from the cart.
     *
     * @param sale the sale entry to modify
     */
    public void onRemoveOneFromCart(SaleEntry sale) {
        billingService.removeOneSale(sale);
        updateProducts();
    }

    /**
     * Handles canceling the entire sale.
     * Clears the shopping cart and updates display.
     */
    public void onCancelSale() {
        billingService.removeAll();
        updateProducts();
    }

    /**
     * Handles initiating the sale confirmation process.
     * Shows the client selection form.
     */
    public void onConfirmSale() {
        List<Client> clients = clientService.getAllClients();
        view.showClientForm(clients);
    }

    /**
     * Handles final confirmation of the bill.
     * Processes the sale and updates inventory.
     *
     * @param modal the confirmation dialog
     */
    public void onConfirmBill(JDialog modal) {
        billingService.confirmBill();
        List<InventoryEntry> products = inventoryService.getAllItems();

        view.showCart(billingService.getSales());
        view.showProducts(products);
        modal.dispose();
    }

    /**
     * Updates the product display and shopping cart view.
     * Called after cart operations to refresh the UI.
     */
    private void updateProducts() {
        view.showProductsCurrentStock(billingService.updateCurrentProductInventory(inventoryService.getAllItems()));
        view.showCart(billingService.getSales());
    }
}
