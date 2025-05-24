package com.twinker.application;

import com.twinker.domain.model.Client;
import com.twinker.persistence.repository.ClientRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientService {
    public final ClientRepository clientRepository;

    public ClientService() {
        clientRepository = new ClientRepository();
    }

    public List<Client> getAllClients() {
        return clientRepository.getAll();
    }

    public List<Client> searchClient(String query) {
        String lowerQuery = query.toLowerCase();
        List<Client> allItems = getAllClients();
        List<Client> result = new ArrayList<>();

        for (Client entry : allItems) {
            if (
                    entry.getName().toLowerCase().contains(lowerQuery) ||
                    entry.getEmail().toLowerCase().contains(lowerQuery) ||
                    entry.getPhone().toLowerCase().contains(lowerQuery)
            ) {
                result.add(entry);
            }
        }

        return result;
    }

    public void addClient(String name, String phone, String email) {
        Client client = new Client(name, phone, email);
        clientRepository.insert(client);
    }

    public void editClient(String clientId, String name, String phone, String email) {
        Optional<Client> clientOptional = clientRepository.searchById(clientId);
        if (clientOptional.isEmpty()) return;

        Client client = clientOptional.get();

        client.setName(name);
        client.setPhone(phone);
        client.setEmail(email);

        clientRepository.update(client);
    }

    public void deleteEntry(String clientId) {
        Optional<Client> clientOptional = clientRepository.searchById(clientId);
        if (clientOptional.isEmpty()) return;

        clientRepository.deleteById(clientId);
    }
}
