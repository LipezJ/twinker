package com.twinker.domain.collection;

import com.twinker.domain.entity.Product;
import com.twinker.domain.entity.Sale;

/**
 * A record that combines sale and product information.
 * This record provides a convenient way to access both sale transaction
 * details and product information in a single object.
 *
 * <p>
 * The record combines:
 * <ul>
 * <li>Sale information (quantity, unit price)</li>
 * <li>Product information (name, ID)</li>
 * </ul>
 * </p>
 *
 * @param sale    the sale transaction details
 * @param product the product being sold
 *
 * @author Twinker Development Team
 * @see com.twinker.domain.entity.Sale
 * @see com.twinker.domain.entity.Product
 */
public record SaleEntry(Sale sale, Product product) {

    /**
     * Gets the sale entry's unique identifier.
     *
     * @return the sale ID
     */
    public String getId() {
        return sale.getId();
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
     * Gets the quantity being sold.
     *
     * @return the sale quantity
     */
    public int getQuantity() {
        return sale.getQuantity();
    }

    /**
     * Gets the name of the product being sold.
     *
     * @return the product name
     */
    public String getProductName() {
        return product.getName();
    }

    /**
     * Gets the unit price of the sale.
     *
     * @return the price per unit
     */
    public double getUnitPrice() {
        return sale.getUnitPrice();
    }
}
