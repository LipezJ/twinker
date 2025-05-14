package com.twinker.application;

import com.twinker.domain.entity.Product;
import com.twinker.persistence.repository.ProductRepository;

import java.util.List;

public class ProductsService {
    public ProductRepository repository;

    public ProductsService() {
        repository = new ProductRepository();
    }

    public List<Product> getAllProducts() {
        return repository.getAll();
    }
}
