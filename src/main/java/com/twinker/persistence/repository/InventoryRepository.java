package com.twinker.persistence.repository;

import com.twinker.data.config.DataConfig;
import com.twinker.domain.model.Inventory;

import java.util.Optional;

public class InventoryRepository extends Repository<Inventory> {
    public InventoryRepository() {
        super(DataConfig.get("inventory.csv.path"), Inventory.class);
    }

    public Optional<Inventory> getByProductId(String productId) {
        return getAll().stream()
                .filter(obj -> productId.equals(obj.getProductId()))
                .findFirst();
    }
}
