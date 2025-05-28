package com.twinker.persistence.repository;

import com.twinker.data.config.DataConfig;
import com.twinker.domain.entity.Product;

/**
 * Repository class for managing product records in the Twinker application.
 * Extends the generic Repository class to provide product-specific storage and
 * retrieval operations.
 *
 * <p>
 * This repository handles:
 * <ul>
 * <li>Basic CRUD operations for products</li>
 * <li>Product catalog management</li>
 * <li>CSV-based persistence of product data</li>
 * </ul>
 * </p>
 *
 * @author Twinker Development Team
 * @see com.twinker.domain.entity.Product
 * @see com.twinker.persistence.repository.Repository
 */
public class ProductRepository extends Repository<Product> {

    /**
     * Constructs a new ProductRepository.
     * Initializes the repository with the configured products CSV file path.
     */
    public ProductRepository() {
        super(DataConfig.get("products.csv.path"), Product.class);
    }
}
