package com.twinker.domain.entity;

import java.util.UUID;

public class Client extends Entity {
    public String id;
    public String nombre;
    public String phone;
    public String email;

    public Client() { }

    public Client(String nombre, String phone, String email) {
        this.id = UUID.randomUUID().toString();
        this.nombre = nombre;
        this.phone = phone;
        this.email = email;
    }

    @Override
    public String getId() {
        return id;
    }
}
