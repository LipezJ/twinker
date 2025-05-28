package com.twinker.domain.entity;

import java.util.UUID;

/**
 * Represents a product in the Twinker application.
 * This class stores product information and provides
 * methods for managing product details.
 *
 * <p>
 * Each product is assigned a unique identifier upon creation.
 * The product information includes:
 * <ul>
 * <li>Name</li>
 * <li>Price</li>
 * <li>Description</li>
 * </ul>
 * </p>
 *
 * @author Twinker Development Team
 */
public class Product extends Entity {
    private final String id;
    private String name;
    private double price;
    private String description;

    /**
     * Constructs a new Product with a randomly generated ID.
     */
    public Product() {
        this.id = UUID.randomUUID().toString();
    }

    /**
     * Constructs a new Product with the specified details.
     *
     * @param name        the product name
     * @param price       the product price
     * @param description the product description
     */
    public Product(String name, double price, String description) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.price = price;
        this.description = description;
    }

    /**
     * Gets the product name.
     *
     * @return the product name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the product name.
     *
     * @param name the new name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the product price.
     *
     * @return the product price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the product price.
     *
     * @param price the new price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the product description.
     *
     * @return the product description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the product description.
     *
     * @param description the new description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the product's unique identifier.
     *
     * @return the product ID
     */
    @Override
    public String getId() {
        return id;
    }
}
