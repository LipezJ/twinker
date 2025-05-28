package com.twinker.presentation.controller;

import com.twinker.application.ClientService;
import com.twinker.domain.entity.Client;
import com.twinker.presentation.view.ClientsView;

import javax.swing.*;
import java.util.List;

/**
 * Controller class for managing client operations in the Twinker application.
 * This class extends ProtectedController to ensure secure access to client
 * management functions through PIN verification.
 *
 * <p>
 * The controller handles:
 * <ul>
 * <li>Client listing and search</li>
 * <li>Adding new clients</li>
 * <li>Editing client information</li>
 * <li>Deleting clients</li>
 * <li>Access control for operations</li>
 * </ul>
 * </p>
 *
 * @author Twinker Development Team
 * @see com.twinker.presentation.view.ClientsView
 * @see com.twinker.application.ClientService
 * @see com.twinker.presentation.controller.ProtectedController
 */
public class ClientsController extends ProtectedController {
    private final ClientsView view;
    private final ClientService clientService;

    /**
     * Constructs a new ClientsController.
     * Initializes the client service for customer management.
     *
     * @param view the clients view to be controlled
     */
    public ClientsController(ClientsView view) {
        this.view = view;
        this.clientService = new ClientService();
    }

    /**
     * Initializes the controller by loading client data.
     */
    public void initClients() {
        onLoadClients();
    }

    /**
     * Handles loading and displaying the client list.
     * Updates the view with all available clients.
     */
    public void onLoadClients() {
        List<Client> inventory = clientService.getAllClients();
        view.showClients(inventory);
    }

    /**
     * Handles client search operations.
     * Updates the view with filtered clients.
     *
     * @param query the search term for filtering clients
     */
    public void onSearchClients(String query) {
        List<Client> clients = clientService.searchClient(query);
        view.showClients(clients);
    }

    /**
     * Handles adding a new client.
     * Shows confirmation message after addition.
     *
     * @param modal the add client dialog
     * @param name  the client name
     * @param phone the client phone number
     * @param email the client email address
     */
    public void onAddClient(JDialog modal, String name, String phone, String email) {
        clientService.addClient(name, phone, email);

        JOptionPane.showMessageDialog(modal, "Cliente agregado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        modal.dispose();
    }

    /**
     * Handles deleting a client.
     * Shows confirmation message after deletion.
     *
     * @param client the client to delete
     */
    public void onDeleteClient(Client client) {
        clientService.deleteEntry(client.getId());

        JOptionPane.showMessageDialog(view, "Cliente eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Handles updating client information.
     * Shows confirmation message after update.
     *
     * @param modal    the edit client dialog
     * @param clientId the ID of the client
     * @param name     the updated client name
     * @param phone    the updated phone number
     * @param email    the updated email address
     */
    public void onEditClient(JDialog modal, String clientId, String name, String phone, String email) {
        clientService.editClient(clientId, name, phone, email);

        JOptionPane.showMessageDialog(modal, "Cliente actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        modal.dispose();
    }

    /**
     * Handles opening the create client form.
     * Verifies PIN before allowing access.
     */
    public void onOpenCreateForm() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(view);
        boolean allowed = openVerifyPinForm(frame);

        if (allowed) {
            view.showCreateForm();
            onLoadClients();
        }
    }

    /**
     * Handles opening the delete confirmation dialog.
     * Verifies PIN before allowing access.
     *
     * @param client the client to delete
     */
    public void onOpenDeleteForm(Client client) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(view);
        boolean allowed = openVerifyPinForm(frame);

        if (!allowed) return;

        view.showConfirmDeleteDialog(client);
        onLoadClients();
    }

    /**
     * Handles opening the edit client form.
     * Verifies PIN before allowing access.
     *
     * @param client the client to edit
     */
    public void onOpenEditForm(Client client) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(view);
        boolean allowed = openVerifyPinForm(frame);

        if (allowed) {
            view.showEditForm(client);
            onLoadClients();
        }
    }
}
