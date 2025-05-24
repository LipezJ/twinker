package com.twinker.domain.collection;

import com.twinker.domain.model.Bill;
import com.twinker.domain.model.Product;
import com.twinker.domain.model.Sale;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class BillList {
    private final Bill bill;
    private final List<SaleEntry> sales;
    private double amount;

    public BillList(Bill bill) {
        this.amount = 0;
        this.bill = bill;
        this.sales = new ArrayList<>();
    }

    public Bill getBill() {
        return bill;
    }

    public List<SaleEntry> getSales() {
        return sales;
    }

    public List<Product> getProducts() {
        List<SaleEntry> sales = getSales();
        List<Product> products = new ArrayList<>();

        for (SaleEntry saleEntry : sales) {
            products.add(saleEntry.product());
        }

        return products;
    }

    public double getAmount() {
        return amount;
    }

    public int getQuantityInBillByProduct(Product product) {
        List<SaleEntry> sales = getSales();

        for (SaleEntry saleEntry : sales) {
            if (saleEntry.getProductId().equals(product.getId())) return saleEntry.getQuantity();
        }

        return 0;
    }

    public void addSale(Sale sale, Product product) {
        Optional<Product> productOptional = getProductById(product.getId());

        if (productOptional.isEmpty()) {
            sales.add(new SaleEntry(sale, product));
        } else {
            Optional<SaleEntry> saleEntry = getSaleByProductId(product.getId());
            if (saleEntry.isPresent()) {
                Sale saleRef = saleEntry.get().sale();
                saleRef.aggregate();
            }
        }

        amount += sale.getUnitPrice();
    }

    public Optional<SaleEntry> getSaleByProductId(String productId) {
        return sales.stream().filter(sale -> productId.equals(sale.getProductId())).findFirst();
    }

    public void removeSale(SaleEntry sale) {
        sales.removeIf(s -> {
            boolean result = s.getId().equals(sale.getId());
            if (result) amount -= s.getUnitPrice() * s.getQuantity();
            return result;
        });
    }

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

    public Optional<Product> getProductById(String productId) {
        List<Product> products = getProducts();
        return products.stream().filter(product -> productId.equals(product.getId())).findFirst();
    }

    public String getId() {
        return bill.getId();
    }
}
