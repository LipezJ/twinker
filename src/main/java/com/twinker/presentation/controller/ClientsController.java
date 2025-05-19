package com.twinker.presentation.controller;

import com.twinker.application.ClientService;
import com.twinker.domain.entity.Client;
import com.twinker.presentation.view.ClientsView;

import javax.swing.*;
import java.util.List;

public class ClientsController extends ProtectedController {
    private final ClientsView view;
    private final ClientService clientService;

    public ClientsController(ClientsView view) {
        this.view = view;
        this.clientService = new ClientService();
    }

    public void initClients() {
        onLoadClients();
    }

    public void onLoadClients() {
        List<Client> inventory = clientService.getAllClients();
        view.showInventory(inventory);
    }

    public void onSearchClients(String query) {
        List<Client> clients = clientService.searchClient(query);
        view.showInventory(clients);
    }

    public void onAddClient(JDialog modal, String name, String phone, String email) {
        clientService.addClient(name, phone, email);

        JOptionPane.showMessageDialog(modal, "Cliente agregado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        modal.dispose();
    }

    public void onDeleteClient(Client client) {
        clientService.deleteEntry(client.getId());

        JOptionPane.showMessageDialog(view, "Cliente eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    public void onEditClient(JDialog modal, String clientId, String name, String phone, String email) {
        clientService.editClient(clientId, name, phone, email);

        JOptionPane.showMessageDialog(modal, "Cliente actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        modal.dispose();
    }

    public void onOpenCreateForm() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(view);
        boolean allowed = openVerifyPinForm(frame);

        if (allowed) {
            view.showCreateForm();
            onLoadClients();
        }
    }

    public void onOpenDeleteForm(Client client) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(view);
        boolean allowed = openVerifyPinForm(frame);

        if (!allowed) return;

        view.showConfirmDeleteDialog(client);
        onLoadClients();
    }

    public void onOpenEditForm(Client client) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(view);
        boolean allowed = openVerifyPinForm(frame);

        if (allowed) {
            view.showEditForm(client);
            onLoadClients();
        }
    }
}
