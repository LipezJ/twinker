package com.twinker.persistence.repository;

import com.twinker.data.config.DataConfig;
import com.twinker.domain.entity.Client;

public class ClientRepository extends Repository<Client> {
    public ClientRepository() {
        super(DataConfig.get("clients.csv.path"), Client.class);
    }
}
