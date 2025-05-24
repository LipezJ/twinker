package com.twinker.domain.model;

import com.twinker.domain.Entity;

import java.util.UUID;

public class Client extends Entity {
    private final String id;
    private String name;
    private String phone;
    private String email;

    public Client() {
        this.id = UUID.randomUUID().toString();
    }

    public Client(String nombre, String phone, String email) {
        this.id = UUID.randomUUID().toString();
        this.name = nombre;
        this.phone = phone;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getId() {
        return id;
    }
}
