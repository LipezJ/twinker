package com.twinker.domain.collection;

import com.twinker.domain.entity.Bill;
import com.twinker.domain.entity.Product;
import com.twinker.domain.entity.Sale;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * Manages a collection of sales entries associated with a bill.
 * This class provides functionality for managing a shopping cart or
 * bill in progress, including adding and removing items, and
 * calculating totals.
 *
 * <p>
 * The class maintains:
 * <ul>
 * <li>The bill being built</li>
 * <li>A list of sale entries (items)</li>
 * <li>The running total amount</li>
 * </ul>
 * </p>
 *
 * @author Twinker Development Team
 * @see com.twinker.domain.entity.Bill
 * @see com.twinker.domain.collection.SaleEntry
 */
public class BillList {
    private final Bill bill;
    private final List<SaleEntry> sales;
    private double amount;

    /**
     * Constructs a new BillList for the specified bill.
     *
     * @param bill the bill to associate with this list
     */
    public BillList(Bill bill) {
        this.amount = 0;
        this.bill = bill;
        this.sales = new ArrayList<>();
    }

    /**
     * Gets the associated bill.
     *
     * @return the bill
     */
    public Bill getBill() {
        return bill;
    }

    /**
     * Gets the list of sale entries.
     *
     * @return the list of sales
     */
    public List<SaleEntry> getSales() {
        return sales;
    }

    /**
     * Gets a list of all products in the bill.
     *
     * @return the list of products
     */
    public List<Product> getProducts() {
        List<SaleEntry> sales = getSales();
        List<Product> products = new ArrayList<>();

        for (SaleEntry saleEntry : sales) {
            products.add(saleEntry.product());
        }

        return products;
    }

    /**
     * Gets the total amount of the bill.
     *
     * @return the bill total
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Gets the quantity of a specific product in the bill.
     *
     * @param product the product to check
     * @return the quantity of the product
     */
    public int getQuantityInBillByProduct(Product product) {
        List<SaleEntry> sales = getSales();

        for (SaleEntry saleEntry : sales) {
            if (saleEntry.getProductId().equals(product.getId())) return saleEntry.getQuantity();
        }

        return 0;
    }

    /**
     * Adds a product to the bill.
     * If the product already exists in the bill, its quantity is increased.
     *
     * @param product the product to add
     */
    public void addSale(Product product) {
        Optional<Product> productOptional = getProductById(product.getId());

        if (productOptional.isEmpty()) {
            Sale sale = new Sale(product.getId(), bill.getId(), 1, product.getPrice());
            sales.add(new SaleEntry(sale, product));
        } else {
            Optional<SaleEntry> saleEntry = getSaleByProductId(product.getId());
            if (saleEntry.isPresent()) {
                Sale saleRef = saleEntry.get().sale();
                saleRef.aggregate();
            }
        }

        amount += product.getPrice();
    }

    /**
     * Finds a sale entry by product ID.
     *
     * @param productId the ID of the product to find
     * @return an Optional containing the sale entry if found
     */
    public Optional<SaleEntry> getSaleByProductId(String productId) {
        return sales.stream().filter(sale -> productId.equals(sale.getProductId())).findFirst();
    }

    /**
     * Removes all quantities of a sale entry from the bill.
     *
     * @param sale the sale entry to remove
     */
    public void removeSale(SaleEntry sale) {
        sales.removeIf(s -> {
            boolean result = s.getId().equals(sale.getId());
            if (result) amount -= s.getUnitPrice() * s.getQuantity();
            return result;
        });
    }

    /**
     * Removes one quantity of a sale entry from the bill.
     * If the quantity reaches zero, the entry is removed.
     *
     * @param sale the sale entry to reduce
     */
    public void removeOneSale(SaleEntry sale) {
        Iterator<SaleEntry> it = sales.iterator();
        while (it.hasNext()) {
            SaleEntry s = it.next();
            if (s.getId().equals(sale.getId())) {
                if (s.getQuantity() > 0) {
                    s.sale().subtract();
                    amount -= s.getUnitPrice();
                }
                if (s.getQuantity() == 0) it.remove();
                break;
            }
        }
    }

    /**
     * Finds a product by its ID.
     *
     * @param productId the ID of the product to find
     * @return an Optional containing the product if found
     */
    public Optional<Product> getProductById(String productId) {
        List<Product> products = getProducts();
        return products.stream().filter(product -> productId.equals(product.getId())).findFirst();
    }

    /**
     * Gets the bill's unique identifier.
     *
     * @return the bill ID
     */
    public String getId() {
        return bill.getId();
    }
}
