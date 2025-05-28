package com.twinker.presentation.form;

import com.twinker.domain.entity.Client;
import com.twinker.presentation.controller.SalesController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Dialog for selecting a client during the sales process.
 * This dialog displays a list of available clients and allows either
 * selecting a specific client or continuing without client selection.
 *
 * <p>
 * The dialog features:
 * <ul>
 * <li>Scrollable list of clients with custom rendering</li>
 * <li>Single selection mode for client choice</li>
 * <li>Option to proceed with or without client selection</li>
 * <li>Integration with sales controller for handling selections</li>
 * </ul>
 * </p>
 *
 * @author Twinker Development Team
 * @see com.twinker.presentation.form.FormDialog
 * @see com.twinker.presentation.controller.SalesController
 * @see com.twinker.domain.entity.Client
 */
public class SalesClientFormDialog extends FormDialog {

    /**
     * Constructs a new SalesClientFormDialog.
     * Sets up the client selection list and control buttons.
     *
     * @param parent     the parent frame for this dialog
     * @param controller the sales controller to handle selections
     * @param clients    the list of available clients to display
     */
    public SalesClientFormDialog(JFrame parent, SalesController controller, List<Client> clients) {
        super(parent, "Seleccionar Cliente", new Dimension(300, 350));

        JList<Client> clientList = new JList<>(clients.toArray(new Client[0]));
        clientList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        clientList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list,
                    Object value,
                    int index,
                    boolean isSelected,
                    boolean cellHasFocus
            ) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Client) {
                    setText(((Client) value).getName());
                }
                return this;
            }
        });

        JScrollPane scrollPane = new JScrollPane(clientList);
        add(scrollPane, gbc);
        gbc.gridy++;

        JButton selectButton = addButton("Seleccionar");
        selectButton.addActionListener(e -> controller.onClientSelected(this, clientList));
        gbc.gridy++;

        JButton continueButton = addButton("Continuar");
        continueButton.addActionListener(e -> controller.onContinueWithoutClient(this));
        gbc.gridy++;

        finalizeDialog();
    }

}
