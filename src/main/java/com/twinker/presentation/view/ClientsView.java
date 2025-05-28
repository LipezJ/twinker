package com.twinker.presentation.view;

import com.twinker.domain.entity.Client;
import com.twinker.presentation.controller.ClientsController;
import com.twinker.presentation.form.ClientEditFormDialog;
import com.twinker.presentation.form.ClientFormDialog;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * The client management view of the Twinker application.
 * This class provides an interface for managing customer information,
 * including adding, editing, and removing clients from the system.
 *
 * <p>
 * The view consists of:
 * <ul>
 * <li>A search bar for filtering clients</li>
 * <li>A scrollable list of client cards showing contact information</li>
 * <li>Controls for adding new clients and managing existing ones</li>
 * </ul>
 * </p>
 *
 * @author Twinker Development Team
 * @see com.twinker.presentation.controller.ClientsController
 * @see com.twinker.domain.entity.Client
 */
public class ClientsView extends JPanel {
    private final JPanel content;
    private JPanel clientsPanel;

    private final ClientsController clientsController;

    /**
     * Constructs a new ClientsView.
     * Initializes the UI components and sets up the clients controller.
     * The view will automatically load client data when initialized.
     */
    public ClientsView() {
        clientsController = new ClientsController(this);

        setLayout(new BorderLayout(10, 10));
        setBackground(UIManager.getColor("Panel.background"));

        content = new JPanel(new BorderLayout(10, 10));
        content.setBackground(UIManager.getColor("Panel.background"));
        content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(content);

        JTextField searchField = new JTextField();
        JButton searchButton = new JButton("🔍");
        searchButton.addActionListener(e ->
                clientsController.onSearchClients(searchField.getText())
        );
        JPanel top = new JPanel(new BorderLayout(5, 5));
        top.setBackground(getBackground());
        top.add(searchField, BorderLayout.CENTER);
        top.add(searchButton, BorderLayout.EAST);
        content.add(top, BorderLayout.NORTH);

        clientsPanel = new JPanel();
        clientsPanel.setLayout(new BoxLayout(clientsPanel, BoxLayout.Y_AXIS));
        clientsPanel.setBackground(getBackground());
        JScrollPane clientsScrollPanel = new JScrollPane(clientsPanel);
        clientsScrollPanel.setBorder(null);
        clientsScrollPanel.getVerticalScrollBar().setUnitIncrement(14);
        content.add(clientsScrollPanel);

        JButton openFormButton = new JButton("Agregar Cliente");
        openFormButton.setPreferredSize(new Dimension(180, 30));
        openFormButton.addActionListener(e -> clientsController.onOpenCreateForm());
        content.add(openFormButton, BorderLayout.SOUTH);

        clientsController.initClients();
    }

    /**
     * Updates the clients panel with the current list of clients.
     * Creates a card for each client showing their contact information
     * and management controls.
     *
     * @param clients the list of clients to display
     */
    public void showClients(List<Client> clients) {
        if (clientsPanel == null) {
            clientsPanel = new JPanel();
            clientsPanel.setLayout(new BoxLayout(clientsPanel, BoxLayout.Y_AXIS));
            clientsPanel.setBackground(getBackground());

            JScrollPane itemsScroll = new JScrollPane(clientsPanel);
            itemsScroll.setBorder(null);
            content.add(itemsScroll, BorderLayout.CENTER);
        } else {
            clientsPanel.removeAll();
        }

        for (Client client : clients) {
            JPanel card = new JPanel(new BorderLayout());
            card.setBackground(getBackground());
            card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            JLabel thumb = new JLabel();
            thumb.setPreferredSize(new Dimension(80, 80));
            card.add(thumb, BorderLayout.WEST);

            JPanel info = new JPanel(new GridLayout(3, 1, 5, 5));
            info.setBackground(getBackground());
            info.add(new JLabel(client.getName()));
            info.add(new JLabel("Phone: " + client.getPhone()));
            info.add(new JLabel("Email: " + client.getEmail()));
            card.add(info, BorderLayout.CENTER);

            JPanel actionsPanel = new JPanel(new GridLayout(1, 2, 5, 5));
            actionsPanel.setBackground(getBackground());
            actionsPanel.setMaximumSize(new Dimension(50, 100));

            JButton delete = new JButton("🗑️");
            delete.setPreferredSize(new Dimension(40, 40));
            delete.addActionListener(e -> clientsController.onOpenDeleteForm(client));

            JButton edit = new JButton("✎");
            edit.setPreferredSize(new Dimension(40, 40));
            edit.addActionListener(e -> clientsController.onOpenEditForm(client));

            actionsPanel.add(delete, BorderLayout.WEST);
            actionsPanel.add(edit, BorderLayout.EAST);
            card.add(actionsPanel, BorderLayout.EAST);
            card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

            clientsPanel.add(card);
            clientsPanel.add(Box.createVerticalStrut(10));
        }

        clientsPanel.revalidate();
        clientsPanel.repaint();
    }

    /**
     * Displays the dialog for creating a new client.
     * Opens a modal form that allows input of new client details.
     */
    public void showCreateForm() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        ClientFormDialog dialog = new ClientFormDialog(frame, clientsController);
        dialog.setVisible(true);
    }

    /**
     * Displays the dialog for editing an existing client.
     * Opens a modal form pre-populated with the client's current information.
     *
     * @param client the client to edit
     */
    public void showEditForm(Client client) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        ClientEditFormDialog dialog = new ClientEditFormDialog(frame, clientsController, client);
        dialog.setVisible(true);
    }

    /**
     * Shows a confirmation dialog for deleting a client.
     * If confirmed, triggers the deletion process through the controller.
     *
     * @param client the client to delete
     */
    public void showConfirmDeleteDialog(Client client) {
        int option = JOptionPane.showConfirmDialog(
                this,
                String.format("¿Desea eliminar el cliente %s?", client.getName()),
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (option != JOptionPane.YES_OPTION) return;
        clientsController.onDeleteClient(client);
    }
}
