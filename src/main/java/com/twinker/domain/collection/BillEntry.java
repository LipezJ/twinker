package com.twinker.domain.collection;

import com.twinker.domain.entity.Bill;
import com.twinker.domain.entity.Client;

import java.util.List;
import java.util.Objects;

/**
 * A record that combines bill, sale entries, and client information.
 * This record provides a comprehensive view of a sales transaction,
 * including all items sold and customer details.
 *
 * <p>
 * The record combines:
 * <ul>
 * <li>Bill information (date, total amount)</li>
 * <li>List of items sold (products, quantities, prices)</li>
 * <li>Client information (name, contact details)</li>
 * </ul>
 * </p>
 *
 * @param bill        the bill details
 * @param saleEntries the list of items sold
 * @param client      the customer information
 *
 * @author Twinker Development Team
 * @see com.twinker.domain.entity.Bill
 * @see com.twinker.domain.entity.Client
 * @see com.twinker.domain.collection.SaleEntry
 */
public record BillEntry(Bill bill, List<SaleEntry> saleEntries, Client client) {

    /**
     * Gets the bill's unique identifier.
     *
     * @return the bill ID
     */
    public String getId() {
        return bill.getId();
    }

    /**
     * Gets the date of the transaction.
     *
     * @return the transaction date
     */
    public String getDate() {
        return bill.getDate();
    }

    /**
     * Gets the name of the client.
     *
     * @return the client's name
     */
    public String getClientName() {
        return client.getName();
    }

    /**
     * Gets the total amount of the bill.
     *
     * @return the bill total
     */
    public double getAmount() {
        return bill.getAmount();
    }

    /**
     * Compares this bill entry with another object for equality.
     * Bill entries are considered equal if they have the same bill ID.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BillEntry other)) return false;
        return Objects.equals(this.bill.getId(), other.bill.getId());
    }

    /**
     * Generates a hash code for this bill entry.
     * The hash code is based on the bill's ID.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(bill.getId());
    }
}
