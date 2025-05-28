package com.twinker.application;

import com.twinker.domain.collection.InventoryEntry;
import com.twinker.domain.entity.Inventory;
import com.twinker.domain.entity.Product;
import com.twinker.persistence.repository.InventoryRepository;
import com.twinker.persistence.repository.ProductRepository;

import java.util.*;

/**
 * Service class for managing inventory and product operations.
 * This class provides comprehensive functionality for managing the product
 * catalog
 * and tracking inventory levels in the Twinker application.
 *
 * <p>
 * The service handles:
 * <ul>
 * <li>Product and inventory CRUD operations</li>
 * <li>Inventory search and filtering</li>
 * <li>Stock level management</li>
 * <li>Product catalog maintenance</li>
 * </ul>
 * </p>
 *
 * @author Twinker Development Team
 * @see com.twinker.domain.collection.InventoryEntry
 * @see com.twinker.domain.entity.Inventory
 * @see com.twinker.domain.entity.Product
 */
public class InventoryService {
    public final InventoryRepository inventoryRepository;
    public final ProductRepository productRepository;

    /**
     * Constructs a new InventoryService.
     * Initializes the required repositories for inventory and product management.
     */
    public InventoryService() {
        inventoryRepository = new InventoryRepository();
        productRepository = new ProductRepository();
    }

    /**
     * Retrieves all inventory items with their associated product information.
     *
     * @return a list of inventory entries combining product and stock information
     */
    public List<InventoryEntry> getAllItems() {
        List<InventoryEntry> productInventory = new ArrayList<>();
        List<Inventory> inventory = inventoryRepository.getAll();
        for (Inventory inventoryItem : inventory) {
            Optional<Product> product = productRepository.getById(inventoryItem.getProductId());
            product.ifPresent(value -> productInventory.add(new InventoryEntry(value, inventoryItem)));
        }
        return productInventory;
    }

    /**
     * Retrieves all products in the catalog.
     *
     * @return a list of all products
     */
    public List<Product> getProducts() {
        return productRepository.getAll();
    }

    /**
     * Searches inventory items based on a query string.
     * The search is case-insensitive and matches against product names
     * and descriptions.
     *
     * @param query the search term to match against products
     * @return a list of matching inventory entries
     */
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

    /**
     * Adds a new product to the catalog with initial inventory.
     *
     * @param name        the name of the product
     * @param price       the price of the product
     * @param description the product description
     * @param quantity    the initial stock quantity
     */
    public void addEntry(String name, double price, String description, int quantity) {
        Product product = new Product(name, price, description);
        productRepository.insert(product);

        Inventory inventory = new Inventory(product.getId(), quantity);
        inventoryRepository.insert(inventory);
    }

    /**
     * Updates an existing inventory entry and its associated product.
     *
     * @param inventoryId the ID of the inventory entry to update
     * @param name        the updated product name
     * @param price       the updated price
     * @param description the updated description
     * @param quantity    the updated stock quantity
     */
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

    /**
     * Deletes an inventory entry and its associated product.
     * The operation only proceeds if the inventory and product IDs match.
     *
     * @param inventoryId the ID of the inventory entry to delete
     * @param productId   the ID of the associated product
     */
    public void deleteEntry(String inventoryId, String productId) {
        Optional<Inventory> inventoryOptional = inventoryRepository.searchById(inventoryId);
        if (inventoryOptional.isEmpty()) return;

        Inventory inventory = inventoryOptional.get();

        if (!productId.equals(inventory.getProductId())) return;

        inventoryRepository.deleteById(inventoryId);
        productRepository.deleteById(productId);
    }
}
