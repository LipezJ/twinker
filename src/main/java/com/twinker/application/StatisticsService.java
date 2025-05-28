package com.twinker.application;

import com.twinker.domain.entity.Bill;
import com.twinker.domain.entity.Sale;
import com.twinker.persistence.repository.*;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service class for managing business statistics in the Twinker application.
 * This class provides comprehensive analytics and reporting functionality,
 * including sales trends, top performers, and time-based analysis.
 *
 * <p>
 * The service provides:
 * <ul>
 * <li>Weekly sales analysis</li>
 * <li>Monthly sales tracking</li>
 * <li>Annual sales reporting</li>
 * <li>Top product analysis</li>
 * <li>Top client tracking</li>
 * </ul>
 * </p>
 *
 * @author Twinker Development Team
 * @see com.twinker.domain.entity.Bill
 * @see com.twinker.domain.entity.Sale
 */
public class StatisticsService {
    private final ClientRepository clientRepository;
    private final BillRepository billRepository;
    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;

    /**
     * Constructs a new StatisticsService.
     * Initializes the required repositories for data analysis.
     */
    public StatisticsService() {
        clientRepository = new ClientRepository();
        billRepository = new BillRepository();
        productRepository = new ProductRepository();
        saleRepository = new SaleRepository();
    }

    /**
     * Retrieves sales data aggregated by day of the week.
     * Includes data from the current week starting from Monday.
     *
     * @return a map of day names to total sales amounts
     */
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

    /**
     * Retrieves sales data aggregated by day of the month.
     * Includes data from the current month only.
     *
     * @return a map of day numbers to total sales amounts
     */
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

    /**
     * Retrieves sales data aggregated by month.
     * Includes data from the current year only.
     *
     * @return a map of month names to total sales amounts
     */
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

    /**
     * Retrieves the top-selling products for the current month.
     * Products are sorted by quantity sold in descending order.
     *
     * @return a map of product names to quantities sold
     */
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

        return topProducts.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, _) -> a,
                        LinkedHashMap::new
                ));
    }

    /**
     * Retrieves the top clients by sales amount for the current month.
     * Clients are sorted by total purchase amount.
     *
     * @return a map of client names to total purchase amounts
     */
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
