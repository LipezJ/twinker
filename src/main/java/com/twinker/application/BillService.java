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

public class BillService {
    private final BillRepository billRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;

     public BillService() {
         billRepository = new BillRepository();
         clientRepository = new ClientRepository();
         productRepository = new ProductRepository();
         saleRepository = new SaleRepository();
     }

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
