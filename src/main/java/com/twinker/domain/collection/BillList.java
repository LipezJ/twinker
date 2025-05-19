package com.twinker.domain.collection;

import com.twinker.domain.entity.Bill;
import com.twinker.domain.entity.Product;
import com.twinker.domain.entity.Sale;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BillList {
    private final Bill bill;
    private final List<SaleEntry> sales;

    public BillList(Bill bill) {
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
    }

    public Optional<SaleEntry> getSaleByProductId(String productId) {
        return sales.stream().filter(sale -> productId.equals(sale.getProductId())).findFirst();
    }

    public void removeSale(SaleEntry sale) {
        sales.removeIf(s -> s.getId().equals(sale.getId()));
    }

    public Optional<Product> getProductById(String productId) {
        List<Product> products = getProducts();
        return products.stream().filter(product -> productId.equals(product.getId())).findFirst();
    }

    public String getId() {
        return bill.getId();
    }
}
