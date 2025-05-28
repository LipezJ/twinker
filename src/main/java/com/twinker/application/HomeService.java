package com.twinker.application;

import com.twinker.domain.entity.Inventory;
import com.twinker.persistence.repository.InventoryRepository;
import com.twinker.persistence.repository.ProductRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class for managing the home dashboard functionality.
 * This class provides methods for monitoring inventory levels and
 * identifying products that need restocking.
 *
 * <p>
 * The service monitors:
 * <ul>
 * <li>Products with low stock levels</li>
 * <li>Items below the out-of-stock margin</li>
 * </ul>
 * </p>
 *
 * @author Twinker Development Team
 * @see com.twinker.domain.entity.Inventory
 * @see com.twinker.persistence.repository.InventoryRepository
 * @see com.twinker.persistence.repository.ProductRepository
 */
public class HomeService {
    private final int OUT_OF_STOCK_MARGIN = 5;

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    /**
     * Constructs a new HomeService.
     * Initializes the required repositories for inventory and product management.
     */
    public HomeService() {
        inventoryRepository = new InventoryRepository();
        productRepository = new ProductRepository();
    }

    /**
     * Retrieves a list of products that are running low on stock.
     * Products are considered low on stock when their quantity is at or below
     * the out-of-stock margin (currently set to 5 units).
     *
     * @return a map containing product names and their current stock levels
     *         for items that need restocking
     */
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
