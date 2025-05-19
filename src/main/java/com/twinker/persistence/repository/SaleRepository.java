package com.twinker.persistence.repository;

import com.twinker.data.config.DataConfig;
import com.twinker.domain.collection.SaleEntry;
import com.twinker.domain.entity.Sale;

import java.util.List;

public class SaleRepository extends Repository<Sale> {
    public SaleRepository() {
        super(DataConfig.get("sales.csv.path"), Sale.class);
    }

    public void registerSales(List<SaleEntry> sales) {
        for (SaleEntry saleEntry : sales) {
            Sale sale = saleEntry.sale();
            insert(sale);
        }
    }
}
