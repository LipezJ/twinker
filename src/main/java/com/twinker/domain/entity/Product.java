package com.twinker.domain.entity;

import java.util.UUID;

public class Product extends Entity {
    private String id;
    private String name;
    private double price;
    private String description;

    public Product() { }

    public Product(String name, double price, String description) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getId() {
        return id;
    }
}
