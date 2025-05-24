package com.twinker.presentation.form;

import com.twinker.domain.model.Client;
import com.twinker.presentation.controller.SalesController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SalesClientFormDialog extends FormDialog {

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
        selectButton.addActionListener(_ -> controller.onClientSelected(this, clientList));
        gbc.gridy++;

        JButton continueButton = addButton("Continuar");
        continueButton.addActionListener(_ -> controller.onContinueWithoutClient(this));
        gbc.gridy++;

        finalizeDialog();
    }

}
