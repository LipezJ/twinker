package com.twinker.domain.collection;

import com.twinker.domain.entity.Inventory;
import com.twinker.domain.entity.Product;

public class InventoryEntry {
    private final String id;
    private final String productId;
    private final double quantity;
    private final String name;
    private final double price;
    private final String description;

    public InventoryEntry(Product product, Inventory inventory) {
        id = inventory.getId();
        productId = inventory.getProductId();
        quantity = inventory.getQuantity();
        name = product.getName();
        price = product.getPrice();
        description = product.getDescription();
    }

    public String getId() {
        return id;
    }

    public String getProductId() {
        return productId;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}
