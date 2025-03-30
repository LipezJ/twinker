package com.twinker.domain.entity;

import java.util.UUID;

public class Product extends Entity {
    public String id;
    public String name;
    public double price;
    public String description;

    public Product() { }

    public Product(String name, double price, String description) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.price = price;
        this.description = description;
    }

    @Override
    public String getId() {
        return id;
    }
}
