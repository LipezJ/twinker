package com.twinker.domain.entity;

import java.util.UUID;

public class Inventory extends Entity {
    private String id;
    private String productId;
    private double quantity;

    public Inventory() { }

    public Inventory(String productId, double quantity) {
        this.id = UUID.randomUUID().toString();
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    @Override
    public String getId() {
        return id;
    }
}
