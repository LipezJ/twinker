package com.twinker.persistence.repository;

import com.twinker.data.config.DataConfig;
import com.twinker.domain.entity.Bill;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class BillRepository extends Repository<Bill> {
    public BillRepository() {
        super(DataConfig.get("bills.csv.path"), Bill.class);
    }

    public List<Bill> getBillsSince(LocalDate date) {
        LocalDateTime since = date.atStartOfDay();

        return getAll().stream().filter(b -> {
            LocalDateTime billDateTime = LocalDateTime.parse(b.getDate());
            return !billDateTime.isBefore(since);
        }).toList();
    }
}
