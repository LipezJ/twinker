package com.twinker.domain.entity;

import java.util.UUID;

/**
 * Represents an individual sale line item in the Twinker application.
 * This class stores information about a specific product sale within
 * a bill, including quantity and pricing details.
 *
 * <p>
 * Each sale entry tracks:
 * <ul>
 * <li>The associated bill's ID</li>
 * <li>The product being sold</li>
 * <li>The quantity purchased</li>
 * <li>The unit price at time of sale</li>
 * </ul>
 * </p>
 *
 * @author Twinker Development Team
 * @see com.twinker.domain.entity.Bill
 * @see com.twinker.domain.entity.Product
 */
public class Sale extends Entity {
    private String id;
    private String billId;
    private String productId;
    private int quantity;
    private double unitPrice;

    /**
     * Constructs a new empty Sale entry.
     */
    public Sale() { }

    /**
     * Constructs a new Sale entry with the specified details.
     *
     * @param productId the ID of the product being sold
     * @param billId    the ID of the associated bill
     * @param quantity  the quantity being purchased
     * @param unitPrice the price per unit
     */
    public Sale(String productId, String billId, int quantity, double unitPrice) {
        this.id = UUID.randomUUID().toString();
        this.productId = productId;
        this.billId = billId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    /**
     * Gets the sale entry's unique identifier.
     *
     * @return the sale's ID
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * Gets the ID of the product being sold.
     *
     * @return the product's ID
     */
    public String getProductId() {
        return productId;
    }

    /**
     * Gets the ID of the associated bill.
     *
     * @return the bill's ID
     */
    public String getBillId() {
        return billId;
    }

    /**
     * Gets the quantity being purchased.
     *
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Gets the price per unit.
     *
     * @return the unit price
     */
    public double getUnitPrice() {
        return unitPrice;
    }

    /**
     * Sets the quantity being purchased.
     *
     * @param quantity the new quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Increases the quantity by one unit.
     * Used for incrementally adding items to the sale.
     */
    public void aggregate() {
        quantity++;
    }

    /**
     * Decreases the quantity by one unit.
     * Used for removing items from the sale.
     * Will not reduce quantity below zero.
     */
    public void subtract() {
        if (quantity < 1) return;
        quantity--;
    }
}
