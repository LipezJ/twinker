package com.twinker.application;

import com.twinker.domain.collection.BillList;
import com.twinker.domain.collection.InventoryEntry;
import com.twinker.domain.collection.SaleEntry;
import com.twinker.domain.entity.*;
import com.twinker.persistence.repository.BillRepository;
import com.twinker.persistence.repository.InventoryRepository;
import com.twinker.persistence.repository.SaleRepository;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service class for managing billing operations in the Twinker application.
 * This class handles the creation, modification, and finalization of bills,
 * including shopping cart management and inventory updates.
 *
 * <p>
 * The service handles:
 * <ul>
 * <li>Shopping cart management</li>
 * <li>Bill creation and confirmation</li>
 * <li>Sale entry tracking</li>
 * <li>Inventory level updates</li>
 * <li>Client association with bills</li>
 * </ul>
 * </p>
 *
 * @author Twinker Development Team
 * @see com.twinker.domain.collection.BillList
 * @see com.twinker.domain.entity.Bill
 * @see com.twinker.domain.collection.SaleEntry
 */
public class BillingService {
    private BillList billList;
    private Client client;
    private final BillRepository billRepository;
    private final InventoryRepository inventoryRepository;
    private final SaleRepository saleRepository;

    /**
     * Constructs a new BillingService.
     * Initializes a new bill and required repositories.
     */
    public BillingService() {
        Bill bill = new Bill();
        this.billList = new BillList(bill);
        this.billRepository = new BillRepository();
        this.inventoryRepository = new InventoryRepository();
        this.saleRepository = new SaleRepository();
    }

    /**
     * Sets the client associated with the current bill.
     *
     * @param client the client to associate with the bill
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * Adds a product to the current bill.
     *
     * @param product the product to add
     */
    public void addProduct(Product product) {
        billList.addSale(product);
    }

    /**
     * Retrieves all sales entries in the current bill.
     *
     * @return a list of sale entries
     */
    public List<SaleEntry> getSales() {
        return billList.getSales();
    }

    /**
     * Calculates the total amount of the current bill.
     *
     * @return the total bill amount
     */
    public double getAmount() {
        return billList.getAmount();
    }

    /**
     * Gets the quantity of a specific product in the current bill.
     *
     * @param product the product to check
     * @return the quantity of the product
     */
    public int getQuantityInBillByProduct(Product product) {
        return billList.getQuantityInBillByProduct(product);
    }

    /**
     * Clears the current bill, creating a new empty one.
     */
    public void removeAll() {
        Bill bill = new Bill();
        billList = new BillList(bill);
    }

    /**
     * Removes all instances of a sale entry from the bill.
     *
     * @param sale the sale entry to remove
     */
    public void removeSale(SaleEntry sale) {
        billList.removeSale(sale);
    }

    /**
     * Removes one unit of a sale entry from the bill.
     *
     * @param sale the sale entry to modify
     */
    public void removeOneSale(SaleEntry sale) {
        billList.removeOneSale(sale);
    }

    /**
     * Confirms and finalizes the current bill.
     * Updates inventory levels, saves the bill and its sales,
     * and creates a new empty bill.
     */
    public void confirmBill() {
        Bill bill = this.billList.getBill();
        bill.setClientId(client != null ? client.getId() : null);
        bill.setDate(LocalDateTime.now().toString());
        bill.setAmount(this.billList.getAmount());
        billRepository.insert(bill);

        List<SaleEntry> saleEntries = this.billList.getSales();
        saleRepository.registerSales(saleEntries);

        for (SaleEntry saleEntry : saleEntries) {
            int quantity = saleEntry.getQuantity();
            Optional<Inventory> inventoryOptional = inventoryRepository.getByProductId(saleEntry.product().getId());

            if (inventoryOptional.isPresent()) {
                Inventory inventory = inventoryOptional.get();
                inventory.setStock(inventory.getStock() - quantity);
                inventoryRepository.update(inventory);
            }
        }

        removeAll();
    }

    /**
     * Updates the current inventory levels based on items in the bill.
     * Handles stock validation and adjusts quantities if necessary.
     *
     * @param inventory the list of inventory entries to update
     * @return a map of inventory entries and their current available stock
     */
    public Map<InventoryEntry, Integer> updateCurrentProductInventory(List<InventoryEntry> inventory) {
        Map<InventoryEntry, Integer> currentInventory = new LinkedHashMap<>();

        inventory.forEach(i -> {
            if (i.getStock() == 0) {
                billList.getSaleByProductId(i.getProductId())
                        .ifPresent(s -> billList.removeSale(s));
                currentInventory.put(i, 0);
            } else {
                int quantityInBill = billList.getQuantityInBillByProduct(i.product());
                int currentStock = i.getStock() - quantityInBill;

                if (currentStock >= 0) {
                    currentInventory.put(i, currentStock);
                } else {
                    billList.getSaleByProductId(i.getProductId())
                            .ifPresent(s -> s.sale().setQuantity(quantityInBill + currentStock));
                    currentInventory.put(i, quantityInBill + currentStock);
                }
            }
        });

        return currentInventory;
    }
}
