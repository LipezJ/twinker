package com.twinker.persistence.repository;

import com.twinker.data.config.DataConfig;
import com.twinker.domain.collection.SaleEntry;
import com.twinker.domain.entity.Sale;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository class for managing sale records in the Twinker application.
 * Extends the generic Repository class to provide sale-specific storage and
 * retrieval operations.
 *
 * <p>
 * This repository handles:
 * <ul>
 * <li>Basic CRUD operations for sales</li>
 * <li>Bill-based sale filtering</li>
 * <li>Batch sale registration</li>
 * <li>CSV-based persistence of sale data</li>
 * </ul>
 * </p>
 *
 * @author Twinker Development Team
 * @see com.twinker.domain.entity.Sale
 * @see com.twinker.domain.collection.SaleEntry
 * @see com.twinker.persistence.repository.Repository
 */
public class SaleRepository extends Repository<Sale> {

    /**
     * Constructs a new SaleRepository.
     * Initializes the repository with the configured sales CSV file path.
     */
    public SaleRepository() {
        super(DataConfig.get("sales.csv.path"), Sale.class);
    }

    /**
     * Retrieves all sales associated with a specific bill.
     * Filters sales based on the bill ID.
     *
     * @param billId the ID of the bill to search for
     * @return a list of sales associated with the specified bill
     */
    public List<Sale> getSalesByBillId(String billId) {
        List<Sale> sales = getAll();
        List<Sale> salesList = new ArrayList<>();

        for (Sale sale : sales) {
            if (billId.equals(sale.getBillId())) salesList.add(sale);
        }

        return salesList;
    }

    /**
     * Registers multiple sales entries in the repository.
     * Converts SaleEntry objects to Sale entities and persists them.
     *
     * @param sales the list of sale entries to register
     */
    public void registerSales(List<SaleEntry> sales) {
        for (SaleEntry saleEntry : sales) {
            Sale sale = saleEntry.sale();
            insert(sale);
        }
    }
}
