package com.twinker.presentation.view;

import com.twinker.presentation.controller.HomeController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Map;

public class HomeView extends JPanel {
    private final JPanel alertsPanel;

    public HomeView() {
        HomeController homeController = new HomeController(this);

        setLayout(new OverlayLayout(this));
        setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        setBackground(UIManager.getColor("Panel.background"));

        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setOpaque(false);

        JLabel title = new JLabel("Bienvenido", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 48));
        title.setForeground(Color.WHITE);
        mainContent.add(title, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        mainContent.add(centerPanel, BorderLayout.CENTER);

        add(mainContent);

        alertsPanel = new JPanel();
        alertsPanel.setLayout(new BoxLayout(alertsPanel, BoxLayout.Y_AXIS));
        alertsPanel.setOpaque(false);
        alertsPanel.setAlignmentX(1.0f);
        alertsPanel.setAlignmentY(1.0f);
        add(wrapAlertsPanel());

        homeController.init();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                homeController.onLoadHome();
            }
        });
    }

    private JPanel wrapAlertsPanel() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        JPanel bottomRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 20));
        bottomRight.setOpaque(false);
        bottomRight.add(alertsPanel);

        wrapper.add(bottomRight, BorderLayout.SOUTH);
        return wrapper;
    }

    public void showAlerts(Map<String, Integer> productsOffStock) {
        alertsPanel.removeAll();

        if (!productsOffStock.isEmpty()) {
            alertsPanel.add(Box.createVerticalStrut(10));

            productsOffStock.forEach((productName, stock) -> {
                JPanel alert = createAlertPanel(productName, stock);
                alertsPanel.add(alert);
                alertsPanel.add(Box.createVerticalStrut(10));
            });

            alertsPanel.revalidate();
            alertsPanel.repaint();
        }
    }

    private JPanel createAlertPanel(String productName, Integer stock) {
        JPanel alert = new JPanel();
        alert.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        alert.setPreferredSize(new Dimension(300, 50));
        alert.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
        alert.setOpaque(true);

        JLabel icon = new JLabel("⚠️");
        JLabel text = new JLabel(String.format("Solo quedan %d unidades de %s", stock, productName));
        text.setForeground(Color.WHITE);

        alert.add(icon);
        alert.add(text);
        return alert;
    }
}
