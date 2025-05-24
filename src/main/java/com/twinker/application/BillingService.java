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

public class BillingService {
    private BillList billList;
    private Client client;
    private final BillRepository billRepository;
    private final InventoryRepository inventoryRepository;
    private final SaleRepository saleRepository;

    public BillingService() {
        Bill bill = new Bill();
        this.billList = new BillList(bill);
        this.billRepository = new BillRepository();
        this.inventoryRepository = new InventoryRepository();
        this.saleRepository = new SaleRepository();
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void addProduct(Product product) {
        Sale sale = new Sale(product.getId(), billList.getId(), 1, product.getPrice());
        billList.addSale(sale, product);
    }

    public List<SaleEntry> getSales() {
        return billList.getSales();
    }

    public double getAmount() {
        return billList.getAmount();
    }

    public int getQuantityInBillByProduct(Product product) {
        return billList.getQuantityInBillByProduct(product);
    }

    public void removeAll() {
        Bill bill = new Bill();
        billList = new BillList(bill);
    }

    public void removeSale(SaleEntry sale) {
        billList.removeSale(sale);
    }

    public void removeOneSale(SaleEntry sale) {
        billList.removeOneSale(sale);
    }

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

    public Map<InventoryEntry, Integer> getCurrentProductInventory(List<InventoryEntry> inventory) {
        Map<InventoryEntry, Integer> currentInventory = new LinkedHashMap<>();

        inventory.forEach(i -> {
            int quantityInBill = billList.getQuantityInBillByProduct(i.product());
            int currentStock = i.getStock() - quantityInBill;

            if (currentStock >= 0) {
                if (currentStock == 0) {
                    billList.getSaleByProductId(i.getProductId())
                            .ifPresent(s -> billList.removeSale(s));
                }
                currentInventory.put(i, currentStock);
            } else {
                billList.getSaleByProductId(i.getProductId())
                        .ifPresent(s -> s.sale().setQuantity(quantityInBill - currentStock));
                currentInventory.put(i, quantityInBill - currentStock);
            }
        });

        return currentInventory;
    }
}
