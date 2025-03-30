package com.twinker.domain.service;

import com.twinker.domain.collection.Sales;
import com.twinker.domain.entity.Bill;
import com.twinker.domain.entity.Client;
import com.twinker.domain.entity.Product;
import com.twinker.domain.entity.Sale;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BillService {
    private final Bill bill;
    private final Sales sales;
    private Client client;

    public BillService() {
        this.bill = new Bill();
        this.sales = new Sales(bill, new ArrayList<>());
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Sale addProduct(Product product) {
        Sale sale = new Sale(product.getId(), bill.getId(), 0, product.price);
        sales.addSale(sale);
        return sale;
    }

    public List<Sale> getSales() {
        return this.sales.getSales();
    }

    public Optional<Sale> getSaleById(String saleId) {
        return this.sales.getSaleById(saleId);
    }

    public Optional<Sale> getSaleByProductId(String productId) {
        List<Sale> sales = this.sales.getSales();
        return sales.stream()
                .filter(sale -> productId.equals(sale.getProductId()))
                .findFirst();
    }

    public void removeSaleById(String saleId) {
        this.sales.removeSaleById(saleId);
    }

    public boolean isValid() {
        if (client == null) {
            throw new IllegalArgumentException("Client must be defined");
        } else if (sales.getSales().isEmpty()) {
            throw new IllegalArgumentException("Bill must have products");
        }

        return true;
    }
}
