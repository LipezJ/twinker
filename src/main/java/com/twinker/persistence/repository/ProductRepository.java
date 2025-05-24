package com.twinker.persistence.repository;

import com.twinker.data.config.DataConfig;
import com.twinker.domain.entity.Product;

public class ProductRepository extends Repository<Product> {
    public ProductRepository() {
        super(DataConfig.get("products.csv.path"), Product.class);
    }
}
