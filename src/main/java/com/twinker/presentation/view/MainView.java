package com.twinker.presentation.view;

import com.formdev.flatlaf.FlatDarkLaf;
import com.twinker.presentation.component.CircleButton;
import com.twinker.presentation.controller.MainController;
import com.twinker.presentation.form.MainSetupAppFormDialog;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;

public class MainView extends JFrame {
    private static final Logger logger = Logger.getLogger(MainView.class.getName());

    private CardLayout cardLayout;
    private JPanel mainPanel;

    private final MainController mainController;

    public MainView() {
        super("Twinker");
        mainController = new MainController(this);

        initUI();
        mainController.init();
    }

    private void initUI() {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception e) {
            logger.severe("FlatDarkLaf cannot be initialized");
        }

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(UIManager.getColor("Panel.background"));
        content.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        setContentPane(content);

        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(45, 45, 45));
        sidebar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        sidebar.setPreferredSize(new Dimension(100, 0));

        String[] names = {"Home", "Ventas", "Clientes", "Inventarios", "Estadísticas", "Contabilidad"};
        ButtonGroup group = new ButtonGroup();

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(UIManager.getColor("Panel.background"));

        for (String name : names) {
            CircleButton btn = new CircleButton(80);
            btn.setText(name.substring(0,1));
            btn.setToolTipText(name);
            btn.setForeground(Color.WHITE);
            btn.setFont(btn.getFont().deriveFont(Font.BOLD, 18f));
            btn.addActionListener(_ -> showCard(name));
            sidebar.add(btn);
            sidebar.add(Box.createVerticalStrut(12));
            group.add(btn);

            switch (name) {
                case "Home" -> mainPanel.add(new HomeView(), name);
                case "Ventas" -> mainPanel.add(new SalesView(), name);
                case "Clientes" -> mainPanel.add(new ClientsView(), name);
                case "Inventarios" -> mainPanel.add(new InventoryView(), name);
                case "Contabilidad" -> mainPanel.add(new AccountingView(), name);
                case "Estadísticas" -> mainPanel.add(new StatisticsView(), name);
                default -> mainPanel.add(showPlaceholder(name), name);
            }
        }

        ((JToggleButton) sidebar.getComponent(0)).setSelected(true);
        showCard(names[0]);

        content.add(sidebar, BorderLayout.WEST);
        content.add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void showCard(String name) {
        cardLayout.show(mainPanel, name);
    }

    private JPanel showPlaceholder(String text) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(UIManager.getColor("Panel.background"));
        JLabel l = new JLabel(text, SwingConstants.CENTER);
        l.setFont(l.getFont().deriveFont(28f));
        l.setForeground(UIManager.getColor("Label.foreground"));
        p.add(l, BorderLayout.CENTER);
        return p;
    }

    public void showSetUpForm() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);

        MainSetupAppFormDialog dialog = new MainSetupAppFormDialog(frame, mainController);
        dialog.setVisible(true);
    }
}
