package com.twinker.domain.entity;

import java.util.UUID;

/**
 * Represents an inventory entry in the Twinker application.
 * This class manages the stock levels for products in the system.
 *
 * <p>
 * Each inventory entry is associated with a product and tracks:
 * <ul>
 * <li>The product's unique identifier</li>
 * <li>The current stock quantity</li>
 * </ul>
 * </p>
 *
 * @author Twinker Development Team
 * @see com.twinker.domain.entity.Product
 */
public class Inventory extends Entity {
    private String id;
    private String productId;
    private int stock;

    /**
     * Constructs a new empty Inventory entry.
     */
    public Inventory() { }

    /**
     * Constructs a new Inventory entry with the specified details.
     *
     * @param productId the ID of the associated product
     * @param quantity  the initial stock quantity
     */
    public Inventory(String productId, int quantity) {
        this.id = UUID.randomUUID().toString();
        this.productId = productId;
        this.stock = quantity;
    }

    /**
     * Gets the ID of the associated product.
     *
     * @return the product's ID
     */
    public String getProductId() {
        return productId;
    }

    /**
     * Gets the current stock quantity.
     *
     * @return the current stock level
     */
    public int getStock() {
        return stock;
    }

    /**
     * Sets the current stock quantity.
     *
     * @param stock the new stock level to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Gets the inventory entry's unique identifier.
     *
     * @return the inventory entry's ID
     */
    @Override
    public String getId() {
        return id;
    }
}
