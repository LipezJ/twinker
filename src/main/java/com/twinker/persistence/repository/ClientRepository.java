package com.twinker.persistence.repository;

import com.twinker.data.config.DataConfig;
import com.twinker.domain.entity.Client;

/**
 * Repository class for managing client records in the Twinker application.
 * Extends the generic Repository class to provide client-specific storage and
 * retrieval operations.
 *
 * <p>
 * This repository handles:
 * <ul>
 * <li>Basic CRUD operations for clients</li>
 * <li>Client data persistence in CSV format</li>
 * <li>Client record management</li>
 * </ul>
 * </p>
 *
 * @author Twinker Development Team
 * @see com.twinker.domain.entity.Client
 * @see com.twinker.persistence.repository.Repository
 */
public class ClientRepository extends Repository<Client> {

    /**
     * Constructs a new ClientRepository.
     * Initializes the repository with the configured clients CSV file path.
     */
    public ClientRepository() {
        super(DataConfig.get("clients.csv.path"), Client.class);
    }
}
