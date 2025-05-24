package com.twinker.application;

import com.twinker.domain.collection.InventoryEntry;
import com.twinker.domain.entity.Inventory;
import com.twinker.domain.entity.Product;
import com.twinker.persistence.repository.InventoryRepository;
import com.twinker.persistence.repository.ProductRepository;

import java.util.*;

public class InventoryService {
    public final InventoryRepository inventoryRepository;
    public final ProductRepository productRepository;

    public InventoryService() {
        inventoryRepository = new InventoryRepository();
        productRepository = new ProductRepository();
    }

    public List<InventoryEntry> getAllItems() {
        List<InventoryEntry> productInventory = new ArrayList<>();
        List<Inventory> inventory = inventoryRepository.getAll();
        for (Inventory inventoryItem : inventory) {
            Optional<Product> product = productRepository.getById(inventoryItem.getProductId());
            product.ifPresent(value -> productInventory.add(new InventoryEntry(value, inventoryItem)));
        }
        return productInventory;
    }

    public List<Product> getProducts() {
        return productRepository.getAll();
    }

    public List<InventoryEntry> searchInventory(String query) {
        String lowerQuery = query.toLowerCase();
        List<InventoryEntry> allItems = getAllItems();
        List<InventoryEntry> result = new ArrayList<>();

        for (InventoryEntry entry : allItems) {
            if (entry.getName().toLowerCase().contains(lowerQuery) ||
                    entry.getDescription().toLowerCase().contains(lowerQuery)) {
                result.add(entry);
            }
        }

        return result;
    }

    public void addEntry(String name, double price, String description, int quantity) {
        Product product = new Product(name, price, description);
        productRepository.insert(product);

        Inventory inventory = new Inventory(product.getId(), quantity);
        inventoryRepository.insert(inventory);
    }

    public void editEntry(String inventoryId, String name, double price, String description, int quantity) {
        Optional<Inventory> inventoryOptional = inventoryRepository.searchById(inventoryId);
        if (inventoryOptional.isEmpty()) return;

        Inventory inventory = inventoryOptional.get();

        Optional<Product> productOptional = productRepository.searchById(inventory.getProductId());
        if (productOptional.isEmpty()) return;

        Product product = productOptional.get();

        inventory.setStock(quantity);
        product.setName(name);
        product.setPrice(price);
        product.setDescription(description);

        inventoryRepository.update(inventory);
        productRepository.update(product);
    }

    public void deleteEntry(String inventoryId, String productId) {
        Optional<Inventory> inventoryOptional = inventoryRepository.searchById(inventoryId);
        if (inventoryOptional.isEmpty()) return;

        Inventory inventory = inventoryOptional.get();

        if (!productId.equals(inventory.getProductId())) return;

        inventoryRepository.deleteById(inventoryId);
        productRepository.deleteById(productId);
    }
}
