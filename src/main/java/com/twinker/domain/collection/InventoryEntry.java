package com.twinker.domain.collection;

import com.twinker.domain.entity.Inventory;
import com.twinker.domain.entity.Product;

/**
 * A record that combines product and inventory information.
 * This record provides a convenient way to access both product details
 * and stock information in a single object.
 *
 * <p>
 * The record combines:
 * <ul>
 * <li>Product information (name, price, description)</li>
 * <li>Inventory information (stock levels)</li>
 * </ul>
 * </p>
 *
 * @param product   the product details
 * @param inventory the inventory information
 *
 * @author Twinker Development Team
 * @see com.twinker.domain.entity.Product
 * @see com.twinker.domain.entity.Inventory
 */
public record InventoryEntry(Product product, Inventory inventory) {

    /**
     * Gets the inventory entry's unique identifier.
     *
     * @return the inventory ID
     */
    public String getId() {
        return inventory.getId();
    }

    /**
     * Gets the associated product's unique identifier.
     *
     * @return the product ID
     */
    public String getProductId() {
        return product.getId();
    }

    /**
     * Gets the current stock level.
     *
     * @return the stock quantity
     */
    public int getStock() {
        return inventory.getStock();
    }

    /**
     * Gets the product name.
     *
     * @return the product name
     */
    public String getName() {
        return product.getName();
    }

    /**
     * Gets the product price.
     *
     * @return the product price
     */
    public double getPrice() {
        return product.getPrice();
    }

    /**
     * Gets the product description.
     *
     * @return the product description
     */
    public String getDescription() {
        return product.getDescription();
    }
}
