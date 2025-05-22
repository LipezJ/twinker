package com.twinker.domain.collection;

import com.twinker.domain.entity.Bill;
import com.twinker.domain.entity.Client;

import java.util.List;
import java.util.Objects;

public record BillEntry(Bill bill, List<SaleEntry> saleEntries, Client client) {
    public String getId() {
        return bill.getId();
    }

    public String getDate() {
        return bill.getDate();
    }

    public String getClientName() {
        return client.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BillEntry other)) return false;
        return Objects.equals(this.bill.getId(), other.bill.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(bill.getId());
    }
}
