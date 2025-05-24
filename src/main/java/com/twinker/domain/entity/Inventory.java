package com.twinker.domain.entity;

import java.util.UUID;

public class Inventory extends Entity {
    private String id;
    private String productId;
    private int quantity;

    public Inventory() { }

    public Inventory(String productId, int quantity) {
        this.id = UUID.randomUUID().toString();
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String getId() {
        return id;
    }
}
