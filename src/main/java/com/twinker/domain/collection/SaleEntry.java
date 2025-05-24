package com.twinker.domain.collection;

import com.twinker.domain.model.Product;
import com.twinker.domain.model.Sale;

public record SaleEntry(Sale sale, Product product) {

    public String getId() {
        return sale.getId();
    }

    public String getProductId() {
        return product.getId();
    }

    public int getQuantity() {
        return sale.getQuantity();
    }

    public String getProductName() {
        return product.getName();
    }

    public double getUnitPrice() {
        return sale.getUnitPrice();
    }
}
