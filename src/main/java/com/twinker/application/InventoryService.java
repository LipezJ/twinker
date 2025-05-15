package com.twinker.application;

import com.twinker.domain.collection.InventoryEntry;
import com.twinker.domain.entity.Inventory;
import com.twinker.domain.entity.Product;
import com.twinker.persistence.repository.InventoryRepository;
import com.twinker.persistence.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class InventoryService {
    private static final Logger logger = Logger.getLogger(InventoryService.class.getName());

    public InventoryRepository inventoryRepository;
    public ProductRepository productRepository;

    public InventoryService() {
        inventoryRepository = new InventoryRepository();
        productRepository = new ProductRepository();
    }

    public List<InventoryEntry> getAllItems() {
        List<InventoryEntry> productInventory = new ArrayList<>();
        List<Inventory> inventory = inventoryRepository.getAll();
        for (Inventory inventoryItem : inventory) {
            Optional<Product> product = productRepository.getById(inventoryItem.getProductId());
            if (product.isPresent()) {
                productInventory.add(new InventoryEntry(product.get(), inventoryItem));
            } else {
                logger.warning("There is not a Product with ID " + inventoryItem.getId());
            }
        }
        return productInventory;
    }

    public Inventory getInventoryByProduct(Product product) {
        Optional<Inventory> inventory = inventoryRepository.getByProductId(product.getId());

        if (inventory.isEmpty()) {
            logger.warning("There is not a Product with ID " + product.getId());
            return null;
        }

        return inventory.get();
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

    public void addEntry(String name, double price, String description, double quantity) {
        Product product = new Product(name, price, description);
        productRepository.insert(product);

        Inventory inventory = new Inventory(product.getId(), quantity);
        inventoryRepository.insert(inventory);
    }

    public void editEntry(String inventoryId, String name, double price, String description, double quantity) {
        Optional<Inventory> inventoryOptional = inventoryRepository.searchById(inventoryId);
        if (inventoryOptional.isEmpty()) return;

        Inventory inventory = inventoryOptional.get();

        Optional<Product> productOptional = productRepository.searchById(inventory.getProductId());
        if (productOptional.isEmpty()) return;

        Product product = productOptional.get();

        inventory.setQuantity(quantity);
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
