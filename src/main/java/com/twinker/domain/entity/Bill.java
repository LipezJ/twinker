package com.twinker.domain.entity;

import java.util.UUID;

/**
 * Represents a sales bill in the Twinker application.
 * This class stores information about a sales transaction,
 * including client details, date, and total amount.
 *
 * <p>
 * Each bill is assigned a unique identifier upon creation
 * and tracks:
 * <ul>
 * <li>The associated client's ID</li>
 * <li>The transaction date</li>
 * <li>The total transaction amount</li>
 * </ul>
 * </p>
 *
 * @author Twinker Development Team
 * @see com.twinker.domain.entity.Client
 */
public class Bill extends Entity {
    private final String id;
    private String clientId;
    private String date;
    private double amount;

    /**
     * Constructs a new Bill with a randomly generated ID.
     */
    public Bill() {
        this.id = UUID.randomUUID().toString();
    }

    /**
     * Gets the ID of the client associated with this bill.
     *
     * @return the client's ID
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * Sets the ID of the client associated with this bill.
     *
     * @param clientId the client's ID to set
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * Gets the date of the transaction.
     *
     * @return the transaction date
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the date of the transaction.
     *
     * @param date the transaction date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Gets the total amount of the transaction.
     *
     * @return the transaction amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the total amount of the transaction.
     *
     * @param amount the transaction amount to set
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Gets the bill's unique identifier.
     *
     * @return the bill's ID
     */
    @Override
    public String getId() {
        return id;
    }
}
