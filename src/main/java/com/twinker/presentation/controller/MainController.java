package com.twinker.presentation.controller;

import com.twinker.application.security.PinService;
import com.twinker.presentation.view.MainView;

import javax.swing.*;

public class MainController {
    private final MainView view;
    private final PinService pinService;

    public MainController(MainView view) {
        this.view = view;
        pinService = new PinService();
    }

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

    public void onSavePin(JDialog modal, String pin) {
        pinService.configurePin(pin);
        modal.dispose();
    }
}
