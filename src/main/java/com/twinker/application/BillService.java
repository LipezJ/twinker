package com.twinker.application;

import com.twinker.domain.collection.BillEntry;
import com.twinker.domain.collection.SaleEntry;
import com.twinker.domain.entity.Bill;
import com.twinker.domain.entity.Client;
import com.twinker.domain.entity.Product;
import com.twinker.domain.entity.Sale;
import com.twinker.persistence.repository.BillRepository;
import com.twinker.persistence.repository.ClientRepository;
import com.twinker.persistence.repository.ProductRepository;
import com.twinker.persistence.repository.SaleRepository;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Service class for managing bill records in the Twinker application.
 * This class provides functionality for retrieving and filtering bills,
 * including their associated sales and client information.
 *
 * <p>
 * The service handles:
 * <ul>
 * <li>Bill retrieval and sorting</li>
 * <li>Bill filtering by client and product</li>
 * <li>Sale entry aggregation</li>
 * <li>Client and product association</li>
 * </ul>
 * </p>
 *
 * @author Twinker Development Team
 * @see com.twinker.domain.collection.BillEntry
 * @see com.twinker.domain.entity.Bill
 * @see com.twinker.domain.entity.Sale
 */
public class BillService {
    private final BillRepository billRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;

    /**
     * Constructs a new BillService.
     * Initializes the required repositories for bill management.
     */
    public BillService() {
        billRepository = new BillRepository();
        clientRepository = new ClientRepository();
        productRepository = new ProductRepository();
        saleRepository = new SaleRepository();
    }

    /**
     * Retrieves all bills sorted by date in descending order.
     * Includes associated sales, products, and client information.
     *
     * @return a list of bill entries with complete information
     */
    public List<BillEntry> getAllBillsSorted() {
        List<Bill> bills = billRepository.getAll();
        List<BillEntry> billEntryList = new ArrayList<>();
        Client nullClient = new Client();
        Product nullProduct = new Product();

        for (Bill bill : bills) {
            List<Sale> sales = saleRepository.getSalesByBillId(bill.getId());
            List<SaleEntry> saleEntries = new ArrayList<>();

            for (Sale sale : sales) {
                Optional<Product> product = productRepository.getById(sale.getProductId());

                SaleEntry saleEntry = product.map(value -> new SaleEntry(sale, value))
                        .orElseGet(() -> new SaleEntry(sale, nullProduct));

                saleEntries.add(saleEntry);
            }

            Optional<Client> client = clientRepository.getById(bill.getClientId());

            BillEntry billEntry = client.map(value -> new BillEntry(bill, saleEntries, value))
                    .orElseGet(() -> new BillEntry(bill, saleEntries, nullClient));

            billEntryList.add(billEntry);
        }

        return billEntryList.stream().sorted((b1, b2) -> {
            LocalDateTime d1 = LocalDateTime.parse(b1.getDate());
            LocalDateTime d2 = LocalDateTime.parse(b2.getDate());
            return d2.compareTo(d1);
        }).toList();
    }

    /**
     * Filters bills based on client name and product name.
     * Either filter can be null to skip that criteria.
     *
     * @param clientName  the name of the client to filter by, or null
     * @param productName the name of the product to filter by, or null
     * @return a filtered list of bill entries
     */
    public List<BillEntry> filterBills(String clientName, String productName) {
        return getAllBillsSorted().stream()
                .filter(billEntry -> clientName == null
                        || clientName.equals(billEntry.getClientName()))
                .filter(billEntry -> productName == null
                        || billEntry.saleEntries().stream()
                        .anyMatch(s -> productName.equals(s.getProductName())))
                .toList();
    }
}
