package com.twinker.persistence.repository;

import com.twinker.data.config.DataConfig;
import com.twinker.domain.entity.Inventory;

import java.util.Optional;

/**
 * Repository class for managing inventory records in the Twinker application.
 * Extends the generic Repository class to provide inventory-specific storage
 * and retrieval operations.
 *
 * <p>
 * This repository handles:
 * <ul>
 * <li>Basic CRUD operations for inventory entries</li>
 * <li>Product-based inventory lookups</li>
 * <li>Stock level tracking</li>
 * <li>CSV-based persistence of inventory data</li>
 * </ul>
 * </p>
 *
 * @author Twinker Development Team
 * @see com.twinker.domain.entity.Inventory
 * @see com.twinker.persistence.repository.Repository
 */
public class InventoryRepository extends Repository<Inventory> {

    /**
     * Constructs a new InventoryRepository.
     * Initializes the repository with the configured inventory CSV file path.
     */
    public InventoryRepository() {
        super(DataConfig.get("inventory.csv.path"), Inventory.class);
    }

    /**
     * Retrieves an inventory entry by its associated product ID.
     * Searches through all inventory entries to find a match.
     *
     * @param productId the ID of the product to search for
     * @return an Optional containing the inventory entry if found
     */
    public Optional<Inventory> getByProductId(String productId) {
        return getAll().stream()
                .filter(obj -> productId.equals(obj.getProductId()))
                .findFirst();
    }
}
