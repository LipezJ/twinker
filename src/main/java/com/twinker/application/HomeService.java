package com.twinker.application;

import com.twinker.domain.model.Inventory;
import com.twinker.persistence.repository.InventoryRepository;
import com.twinker.persistence.repository.ProductRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeService {
    private final int OUT_OF_STOCK_MARGIN = 5;

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    public HomeService() {
        inventoryRepository = new InventoryRepository();
        productRepository = new ProductRepository();
    }

    public Map<String, Integer> getProductsOutOffStock() {
        List<Inventory> inventoryList = inventoryRepository.getAll();
        Map<String, Integer> products = new HashMap<>();

        for (Inventory inventory : inventoryList) {
            if (inventory.getStock() <= OUT_OF_STOCK_MARGIN) {
                productRepository.getById(inventory.getProductId())
                        .ifPresent(product -> products.put(product.getName(), inventory.getStock()));
            }
        }

        return products;
    }
}
