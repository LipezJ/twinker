package com.twinker.domain.model;

import com.twinker.domain.Entity;

import java.util.UUID;

public class Sale extends Entity {
    private String id;
    private String billId;
    private String productId;
    private int quantity;
    private double unitPrice;

    public Sale() { }

    public Sale(String productId, String billId, int quantity, double unitPrice) {
        this.id = UUID.randomUUID().toString();
        this.productId = productId;
        this.billId = billId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getProductId() {
        return productId;
    }

    public String getBillId() {
        return billId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void aggregate() {
        quantity++;
    }

    public void subtract() {
        if (quantity < 1) return;
        quantity--;
    }
}
