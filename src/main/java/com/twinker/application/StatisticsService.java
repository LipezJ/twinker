package com.twinker.application;

import com.twinker.domain.entity.Bill;
import com.twinker.domain.entity.Sale;
import com.twinker.persistence.repository.*;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StatisticsService {
    private final ClientRepository clientRepository;
    private final BillRepository billRepository;
    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;

    public StatisticsService() {
        clientRepository = new ClientRepository();
        billRepository = new BillRepository();
        productRepository = new ProductRepository();
        saleRepository = new SaleRepository();
    }

    public Map<String, Double> getWeeklySales() {
        LocalDate today = LocalDate.now();
        LocalDate lastMonday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        List<Bill> bills = billRepository.getBillsSince(lastMonday);
        Map<String, Double> weeklySales = new LinkedHashMap<>();
        for (DayOfWeek dow : DayOfWeek.values()) {
            weeklySales.put(dow.name(), 0.0);
        }

        for (Bill bill : bills) {
            LocalDateTime billDate = LocalDateTime.parse(bill.getDate());
            String key = billDate.getDayOfWeek().name();
            weeklySales.put(key, weeklySales.get(key) + bill.getAmount());
        }

        return weeklySales;
    }

    public Map<String, Double> getMonthlySales() {
        LocalDate today = LocalDate.now();
        LocalDate firstOfMonth = today.with(TemporalAdjusters.firstDayOfMonth());
        List<Bill> bills = billRepository.getBillsSince(firstOfMonth);

        Map<String, Double> monthlySales = new LinkedHashMap<>();
        YearMonth ym = YearMonth.from(today);
        int daysInMonth = ym.lengthOfMonth();

        for (int d = 1; d <= daysInMonth; d++) {
            monthlySales.put(String.valueOf(d), 0.0);
        }

        for (Bill bill : bills) {
            LocalDateTime billDate = LocalDateTime.parse(bill.getDate());
            if (billDate.getYear() == today.getYear() && billDate.getMonthValue() == today.getMonthValue()) {
                String key = String.valueOf(billDate.getDayOfMonth());
                monthlySales.put(key, monthlySales.get(key) + bill.getAmount());
            }
        }

        return monthlySales;
    }

    public Map<String, Double> getAnnualSales() {
        LocalDate today = LocalDate.now();
        LocalDate firstOfYear = today.with(TemporalAdjusters.firstDayOfYear());

        List<Bill> bills = billRepository.getBillsSince(firstOfYear);

        Map<String, Double> annualSales = new LinkedHashMap<>();
        for (Month month : Month.values()) {
            annualSales.put(month.name(), 0.0);
        }

        for (Bill bill : bills) {
            LocalDateTime billDate = LocalDateTime.parse(bill.getDate());
            if (billDate.getYear() == today.getYear()) {
                String key = billDate.getMonth().name();
                annualSales.put(key, annualSales.get(key) + bill.getAmount());
            }
        }

        return annualSales;
    }

    public Map<String, Integer> getMonthlyTopProducts() {
        LocalDate today = LocalDate.now();
        LocalDate firstOfMonth = today.with(TemporalAdjusters.firstDayOfMonth());
        List<Bill> bills = billRepository.getBillsSince(firstOfMonth);

        Map<String, Integer> topProducts = new LinkedHashMap<>();

        for (Bill bill : bills) {
            List<Sale> sales = saleRepository.getSalesByBillId(bill.getId());
            for (Sale sale : sales) {
                productRepository.getById(sale.getProductId())
                        .ifPresent(product ->
                                topProducts.merge(
                                        product.getName(),
                                        sale.getQuantity(),
                                        Integer::sum
                                )
                        );
            }
        }

        return topProducts;
    }

    public Map<String, Double> getMonthlyTopClients() {
        LocalDate today = LocalDate.now();
        LocalDate firstOfMonth = today.with(TemporalAdjusters.firstDayOfMonth());
        List<Bill> bills = billRepository.getBillsSince(firstOfMonth);

        Map<String, Double> topClients = new LinkedHashMap<>();

        for (Bill bill : bills) {
            if (bill.getId() == null) continue;
            clientRepository.getById(bill.getClientId())
                    .ifPresent(client ->
                            topClients.merge(
                                    client.getName(),
                                    bill.getAmount(),
                                    Double::sum
                            )
                    );
        }

        return topClients;
    }
}
