package com.twinker.presentation.view;

import com.twinker.presentation.controller.HomeController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Map;

/**
 * The home view of the Twinker application.
 * This class represents the welcome screen that users see when they first open
 * the application.
 * It displays a welcome message and shows alerts for products that are running
 * low on stock.
 *
 * <p>
 * The view consists of:
 * <ul>
 * <li>A welcome title</li>
 * <li>A main content area</li>
 * <li>An alerts panel in the bottom-right corner showing low stock
 * warnings</li>
 * </ul>
 * </p>
 *
 * @author Twinker Development Team
 * @see com.twinker.presentation.controller.HomeController
 */
public class HomeView extends JPanel {
    private final JPanel alertsPanel;

    /**
     * Constructs a new HomeView.
     * Initializes the UI components and sets up the home controller.
     * The view will automatically update alerts when it becomes visible.
     */
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

    /**
     * Creates a wrapper panel for the alerts that positions them
     * in the bottom-right corner of the screen.
     *
     * @return a JPanel containing the alerts panel
     */
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

    /**
     * Updates the alerts panel with current product stock information.
     * Creates alert boxes for products that are running low on stock.
     *
     * @param productsOffStock a map of product names to their current stock levels
     */
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

    /**
     * Creates an individual alert panel for a product with low stock.
     *
     * @param productName the name of the product
     * @param stock       the current stock level
     * @return a JPanel containing the formatted alert
     */
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
