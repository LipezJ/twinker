package com.twinker.presentation.controller;

import com.twinker.application.security.PinService;
import com.twinker.presentation.view.MainView;

import javax.swing.*;

/**
 * Controller class for managing main application setup in the Twinker
 * application.
 * This class handles initial configuration, particularly focusing on security
 * setup through administrator PIN configuration.
 *
 * <p>
 * The controller handles:
 * <ul>
 * <li>Initial PIN setup</li>
 * <li>PIN verification</li>
 * <li>Application startup flow</li>
 * <li>Security configuration</li>
 * </ul>
 * </p>
 *
 * @author Twinker Development Team
 * @see com.twinker.presentation.view.MainView
 * @see com.twinker.application.security.PinService
 */
public class MainController {
    private final MainView view;
    private final PinService pinService;

    /**
     * Constructs a new MainController.
     * Initializes the PIN service for security management.
     *
     * @param view the main view to be controlled
     */
    public MainController(MainView view) {
        this.view = view;
        pinService = new PinService();
    }

    /**
     * Initializes the controller by ensuring PIN setup.
     * Shows setup form until a valid PIN is configured.
     */
    public void init() {
        while (!pinService.existsPin()) {
            view.showSetUpForm();
            if (!pinService.existsPin()) {
                JOptionPane.showMessageDialog(
                        view,
                        "Por favor registre su PIN de administrador",
                        "No se registro un PIN",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    /**
     * Handles saving the administrator PIN.
     * Configures the PIN and closes the setup dialog.
     *
     * @param modal the PIN setup dialog
     * @param pin   the PIN to be configured
     */
    public void onSavePin(JDialog modal, String pin) {
        pinService.configurePin(pin);
        modal.dispose();
    }
}
