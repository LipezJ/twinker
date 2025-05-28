package com.twinker.application;

import com.twinker.domain.entity.Client;
import com.twinker.persistence.repository.ClientRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing client records in the Twinker application.
 * This class provides functionality for client management, including
 * searching, adding, updating, and removing client records.
 *
 * <p>
 * The service handles:
 * <ul>
 * <li>Client record management</li>
 * <li>Client search functionality</li>
 * <li>Client information updates</li>
 * <li>Client record deletion</li>
 * </ul>
 * </p>
 *
 * @author Twinker Development Team
 * @see com.twinker.domain.entity.Client
 * @see com.twinker.persistence.repository.ClientRepository
 */
public class ClientService {
    public final ClientRepository clientRepository;

    /**
     * Constructs a new ClientService.
     * Initializes the client repository for data access.
     */
    public ClientService() {
        clientRepository = new ClientRepository();
    }

    /**
     * Retrieves all clients in the system.
     *
     * @return a list of all client records
     */
    public List<Client> getAllClients() {
        return clientRepository.getAll();
    }

    /**
     * Searches for clients based on a query string.
     * Matches against client name, email, and phone number.
     * The search is case-insensitive.
     *
     * @param query the search term to match against client records
     * @return a list of clients matching the search criteria
     */
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

    /**
     * Adds a new client to the system.
     *
     * @param name  the client's name
     * @param phone the client's phone number
     * @param email the client's email address
     */
    public void addClient(String name, String phone, String email) {
        Client client = new Client(name, phone, email);
        clientRepository.insert(client);
    }

    /**
     * Updates an existing client's information.
     *
     * @param clientId the ID of the client to update
     * @param name     the updated name
     * @param phone    the updated phone number
     * @param email    the updated email address
     */
    public void editClient(String clientId, String name, String phone, String email) {
        Optional<Client> clientOptional = clientRepository.searchById(clientId);
        if (clientOptional.isEmpty()) return;

        Client client = clientOptional.get();

        client.setName(name);
        client.setPhone(phone);
        client.setEmail(email);

        clientRepository.update(client);
    }

    /**
     * Deletes a client from the system.
     *
     * @param clientId the ID of the client to delete
     */
    public void deleteEntry(String clientId) {
        Optional<Client> clientOptional = clientRepository.searchById(clientId);
        if (clientOptional.isEmpty()) return;

        clientRepository.deleteById(clientId);
    }
}
