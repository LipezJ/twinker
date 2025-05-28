package com.twinker.presentation.form;

import javax.swing.*;
import java.awt.*;

/**
 * Abstract base class for all form dialogs in the Twinker application.
 * Provides a consistent layout and common functionality for modal dialogs
 * used throughout the application.
 *
 * <p>
 * Features include:
 * <ul>
 * <li>Consistent grid bag layout for form elements</li>
 * <li>Helper methods for adding common form components</li>
 * <li>Modal dialog behavior</li>
 * <li>Standard sizing and positioning</li>
 * </ul>
 * </p>
 *
 * @author Twinker Development Team
 */
public abstract class FormDialog extends JDialog {

    protected final GridBagConstraints gbc;

    /**
     * Constructs a new FormDialog with the specified parameters.
     * Initializes the dialog with a grid bag layout and standard constraints.
     *
     * @param parent the parent window for this dialog
     * @param title  the title for the dialog window
     * @param size   the preferred size for the dialog
     */
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

    /**
     * Adds a label to the form.
     * The label is positioned at the current grid position.
     *
     * @param text the text to display in the label
     */
    protected void addLabel(String text) {
        add(new JLabel(text), gbc);
        gbc.gridy++;
    }

    /**
     * Adds a text field to the form.
     * The field is positioned at the current grid position.
     *
     * @param initialValue the initial text for the field
     * @return the created text field component
     */
    protected JTextField addTextField(String initialValue) {
        JTextField field = new JTextField(initialValue);
        add(field, gbc);
        gbc.gridy++;
        return field;
    }

    /**
     * Adds a button to the form.
     * The button is positioned at the current grid position
     * and uses a different fill constraint.
     *
     * @param text the text to display on the button
     * @return the created button component
     */
    protected JButton addButton(String text) {
        JButton btn = new JButton(text);

        gbc.fill = GridBagConstraints.NONE;
        add(btn, gbc);
        gbc.gridy++;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        return btn;
    }

    /**
     * Finalizes the dialog setup.
     * Packs the components and centers the dialog relative to its owner.
     */
    protected void finalizeDialog() {
        pack();
        setLocationRelativeTo(getOwner());
    }
}
