package com.twinker.application;

import com.twinker.domain.collection.BillList;
import com.twinker.domain.collection.SaleEntry;
import com.twinker.domain.entity.*;
import com.twinker.persistence.repository.BillRepository;
import com.twinker.persistence.repository.InventoryRepository;
import com.twinker.persistence.repository.SaleRepository;

import java.time.LocalDateTime;
import java.util.List;
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

    public void removeAll() {
        Bill bill = new Bill();
        this.billList = new BillList(bill);
    }

    public void removeSale(SaleEntry sale) {
        this.billList.removeSale(sale);
    }

    public void confirmBill() {
        Bill bill = this.billList.getBill();
        bill.setClientId(client != null ? client.getId() : null);
        bill.setDate(LocalDateTime.now().toString());
        billRepository.insert(bill);

        List<SaleEntry> saleEntries = this.billList.getSales();
        saleRepository.registerSales(saleEntries);

        for (SaleEntry saleEntry : saleEntries) {
            double quantity = saleEntry.getQuantity();
            Optional<Inventory> inventoryOptional = inventoryRepository.getByProductId(saleEntry.product().getId());

            if (inventoryOptional.isPresent()) {
                Inventory inventory = inventoryOptional.get();

                inventory.setQuantity(inventory.getQuantity() - quantity);
                inventoryRepository.update(inventory);
            }
        }

        removeAll();
    }
}
