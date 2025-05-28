package com.twinker.persistence.repository;

import com.twinker.data.config.DataConfig;
import com.twinker.domain.entity.Bill;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository class for managing bill records in the Twinker application.
 * Extends the generic Repository class to provide bill-specific storage and
 * retrieval operations.
 *
 * <p>
 * This repository handles:
 * <ul>
 * <li>Basic CRUD operations for bills</li>
 * <li>Date-based bill filtering</li>
 * <li>CSV-based persistence of bill records</li>
 * </ul>
 * </p>
 *
 * @author Twinker Development Team
 * @see com.twinker.domain.entity.Bill
 * @see com.twinker.persistence.repository.Repository
 */
public class BillRepository extends Repository<Bill> {

    /**
     * Constructs a new BillRepository.
     * Initializes the repository with the configured bills CSV file path.
     */
    public BillRepository() {
        super(DataConfig.get("bills.csv.path"), Bill.class);
    }

    /**
     * Retrieves all bills created on or after a specified date.
     * Filters bills based on their creation timestamp.
     *
     * @param date the starting date for filtering bills
     * @return a list of bills created on or after the specified date
     */
    public List<Bill> getBillsSince(LocalDate date) {
        LocalDateTime since = date.atStartOfDay();

        return getAll().stream().filter(b -> {
            LocalDateTime billDateTime = LocalDateTime.parse(b.getDate());
            return !billDateTime.isBefore(since);
        }).toList();
    }
}
