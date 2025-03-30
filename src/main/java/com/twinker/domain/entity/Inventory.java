package com.twinker.domain.entity;

public class Inventory extends Entity {
    public String id;
    public String productId;
    public double quantity;

    @Override
    public String getId() {
        return id;
    }
}
