package com.twinker.persistence.repository;

import com.twinker.data.config.DataConfig;
import com.twinker.domain.entity.Bill;

public class BillRepository extends Repository<Bill> {
    public BillRepository() {
        super(DataConfig.get("bills.csv.path"), Bill.class);
    }
}
