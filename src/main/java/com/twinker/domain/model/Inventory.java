package com.twinker.domain.model;

import com.twinker.domain.Entity;

import java.util.UUID;

public class Inventory extends Entity {
    private String id;
    private String productId;
    private int stock;

    public Inventory() { }

    public Inventory(String productId, int quantity) {
        this.id = UUID.randomUUID().toString();
        this.productId = productId;
        this.stock = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String getId() {
        return id;
    }
}
