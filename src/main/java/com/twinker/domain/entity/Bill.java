package com.twinker.domain.entity;

import java.util.UUID;

public class Bill extends Entity {
    private final String id;
    private String clientId;
    private String date;
    private double amount;

    public Bill() {
        this.id = UUID.randomUUID().toString();
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String getId() {
        return id;
    }
}
