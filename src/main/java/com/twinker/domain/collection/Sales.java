package com.twinker.domain.collection;

import com.twinker.domain.entity.Bill;
import com.twinker.domain.entity.Sale;

import java.util.List;
import java.util.Optional;

public class Sales {
    private final Bill bill;
    private final List<Sale> sales;

    public Sales(Bill bill, List<Sale> sales) {
        this.bill = bill;
        this.sales = sales;
        if (!verifySales()) {
            throw new IllegalArgumentException("The sales must belong to the same bill");
        }
    }

    private boolean verifySales() {
        String billId = bill.getId();
        for (Sale sale : sales) {
            if (!billId.equals(sale.getBillId())) return false;
        }
        return true;
    }

    private boolean verifySale(Sale sale) {
        String billId = bill.getId();
        return billId.equals(sale.getBillId());
    }

    private boolean verifyIfSaleExists(Sale sale) {
        for (Sale sl : sales) {
            if (sl.getId().equals(sale.getId())) return true;
        }
        return false;
    }

    public List<Sale> getSales() {
        return sales;
    }

    public void addSale(Sale sale) {
        if (!verifySale(sale)) {
            throw new IllegalArgumentException("The sale must belong to the same bill");
        } else if (verifyIfSaleExists(sale)) {
            throw new IllegalArgumentException("The sale already exists in the bill");
        }

        sales.add(sale);
    }

    public Optional<Sale> getSaleById(String id) {
        return sales.stream().filter(sale -> id.equals(sale.getId())).findFirst();
    }

    public void removeSaleById(String id) {
        sales.removeIf(sale -> id.equals(sale.getId()));
    }
}
