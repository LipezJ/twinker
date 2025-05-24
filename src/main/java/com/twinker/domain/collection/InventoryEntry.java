package com.twinker.domain.collection;

import com.twinker.domain.entity.Inventory;
import com.twinker.domain.entity.Product;

public record InventoryEntry(Product product, Inventory inventory) {

    public String getId() {
        return inventory.getId();
    }

    public String getProductId() {
        return product.getId();
    }

    public int getStock() {
        return inventory.getStock();
    }

    public String getName() {
        return product.getName();
    }

    public double getPrice() {
        return product.getPrice();
    }

    public String getDescription() {
        return product.getDescription();
    }
}
