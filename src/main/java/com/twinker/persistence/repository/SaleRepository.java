package com.twinker.persistence.repository;

import com.twinker.data.config.DataConfig;
import com.twinker.domain.entity.Sale;

import java.util.List;

public class SaleRepository extends Repository<Sale> {
    public SaleRepository() {
        super(DataConfig.get("sales.csv.path"), Sale.class);
    }

    public void registerSales(List<Sale> sales) {
        for (Sale sale : sales) {
            insert(sale);
        }
    }
}
