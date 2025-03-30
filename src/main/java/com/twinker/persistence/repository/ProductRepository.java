package com.twinker.persistence.repository;

import com.twinker.data.config.DataConfig;
import com.twinker.domain.entity.Product;

import java.util.List;
import java.util.Optional;

public class ProductRepository extends Repository<Product> {
    public ProductRepository() {
        super(DataConfig.get("products.csv.path"), Product.class);
    }

    public Optional<Product> getById(String productId) {
        List<Product> products = getAll();

        return products.stream()
                .filter(product -> productId.equals(product.getId()))
                .findFirst();
    }
}
