package com.twinker.persistence.repository;

import com.twinker.data.config.DataConfig;
import com.twinker.domain.collection.SaleEntry;
import com.twinker.domain.entity.Sale;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SaleRepository extends Repository<Sale> {
    public SaleRepository() {
        super(DataConfig.get("sales.csv.path"), Sale.class);
    }

    public List<Sale> getSalesByBillId(String billId) {
        List<Sale> sales = getAll();
        List<Sale> salesList = new ArrayList<>();

        for (Sale sale :  sales) {
            if (billId.equals(sale.getBillId())) salesList.add(sale);
        }

        return  salesList;
    }

    public void registerSales(List<SaleEntry> sales) {
        for (SaleEntry saleEntry : sales) {
            Sale sale = saleEntry.sale();
            insert(sale);
        }
    }
}
