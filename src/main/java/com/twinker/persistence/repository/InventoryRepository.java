package com.twinker.persistence.repository;

import com.twinker.data.config.DataConfig;
import com.twinker.domain.entity.Inventory;

public class InventoryRepository extends Repository<Inventory> {
    public InventoryRepository() {
        super(DataConfig.get("inventory.csv.path"), Inventory.class);
    }
}
