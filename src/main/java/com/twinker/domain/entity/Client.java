package com.twinker.domain.entity;

import java.util.UUID;

/**
 * Represents a client in the Twinker application.
 * This class stores client contact information and provides
 * methods for managing client details.
 *
 * <p>
 * Each client is assigned a unique identifier upon creation.
 * The client information includes:
 * <ul>
 * <li>Name</li>
 * <li>Phone number</li>
 * <li>Email address</li>
 * </ul>
 * </p>
 *
 * @author Twinker Development Team
 */
public class Client extends Entity {
    private final String id;
    private String name;
    private String phone;
    private String email;

    /**
     * Constructs a new Client with a randomly generated ID.
     */
    public Client() {
        this.id = UUID.randomUUID().toString();
    }

    /**
     * Constructs a new Client with the specified details.
     *
     * @param nombre the client's name
     * @param phone  the client's phone number
     * @param email  the client's email address
     */
    public Client(String nombre, String phone, String email) {
        this.id = UUID.randomUUID().toString();
        this.name = nombre;
        this.phone = phone;
        this.email = email;
    }

    /**
     * Gets the client's name.
     *
     * @return the client's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the client's name.
     *
     * @param name the new name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the client's phone number.
     *
     * @return the client's phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the client's phone number.
     *
     * @param phone the new phone number to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets the client's email address.
     *
     * @return the client's email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the client's email address.
     *
     * @param email the new email address to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the client's unique identifier.
     *
     * @return the client's ID
     */
    @Override
    public String getId() {
        return id;
    }
}
