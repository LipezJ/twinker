package com.twinker.domain.entity;

import java.util.UUID;

public class Bill extends Entity {
    public String id;
    public String clientId;
    public String date;
    public double amount;

    public Bill() {
        this.id = UUID.randomUUID().toString();
    }

    @Override
    public String getId() {
        return id;
    }
}
