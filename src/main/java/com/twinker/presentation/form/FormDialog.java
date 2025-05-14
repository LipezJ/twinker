package com.twinker.presentation.form;

import javax.swing.*;
import java.awt.*;

public abstract class FormDialog extends JDialog {

    protected final GridBagConstraints gbc;

    protected FormDialog(Window parent, String title, Dimension size) {
        super(parent, title, ModalityType.APPLICATION_MODAL);
        setLayout(new GridBagLayout());
        setPreferredSize(size);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill   = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx  = 0;
        gbc.gridy  = 0;
        gbc.weightx = 1.0;
    }

    protected void addLabel(String text) {
        add(new JLabel(text), gbc);
        gbc.gridy++;
    }

    protected JTextField addTextField(String initialValue) {
        JTextField field = new JTextField(initialValue);
        add(field, gbc);
        gbc.gridy++;
        return field;
    }

    protected JButton addButton(String text) {
        JButton btn = new JButton(text);

        gbc.fill = GridBagConstraints.NONE;
        add(btn, gbc);
        gbc.gridy++;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        return btn;
    }

    protected void finalizeDialog() {
        pack();
        setLocationRelativeTo(getOwner());
    }
}
