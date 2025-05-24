package com.twinker.presentation.view;

import com.twinker.domain.collection.BillEntry;
import com.twinker.domain.model.Client;
import com.twinker.domain.model.Product;
import com.twinker.presentation.controller.AccountingController;
import com.twinker.presentation.form.AccountingBillFormDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

public class AccountingView extends JPanel {
    private final JPanel content;
    private JPanel salesPanel;
    private final JPanel filtersPanel;

    private final AccountingController accountingController;

    public AccountingView() {
        accountingController = new AccountingController(this);

        setLayout(new BorderLayout(10, 10));
        setBackground(UIManager.getColor("Panel.background"));

        content = new JPanel(new BorderLayout(10, 10));
        content.setBackground(UIManager.getColor("Panel.background"));
        content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(content);

        filtersPanel = new JPanel(new BorderLayout(5, 5));
        filtersPanel.setLayout(new BoxLayout(filtersPanel, BoxLayout.X_AXIS));
        filtersPanel.setBackground(getBackground());
        content.add(filtersPanel, BorderLayout.NORTH);

        salesPanel = new JPanel();
        salesPanel.setLayout(new BoxLayout(salesPanel, BoxLayout.Y_AXIS));
        salesPanel.setBackground(getBackground());
        JScrollPane salesScrollPanel = new JScrollPane(salesPanel);
        salesScrollPanel.setBorder(null);
        salesScrollPanel.getVerticalScrollBar().setUnitIncrement(14);
        content.add(salesScrollPanel);

        accountingController.initAccounting();
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) { accountingController.onLoadAccounting(); }
        });
    }

    public void showSales(List<BillEntry> billEntries) {
        if (salesPanel == null) {
            salesPanel = new JPanel();
            salesPanel.setLayout(new BoxLayout(salesPanel, BoxLayout.Y_AXIS));
            salesPanel.setBackground(getBackground());

            JScrollPane itemsScroll = new JScrollPane(salesPanel);
            itemsScroll.setBorder(null);
            content.add(itemsScroll, BorderLayout.CENTER);
        } else {
            salesPanel.removeAll();
        }

        for (BillEntry billEntry : billEntries) {
            JPanel card = new JPanel(new BorderLayout());
            card.setBackground(getBackground());
            card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

            JLabel thumb = new JLabel();
            thumb.setPreferredSize(new Dimension(80, 80));
            card.add(thumb, BorderLayout.WEST);

            JPanel info = new JPanel(new GridLayout(3, 1, 5, 5));
            info.setBackground(getBackground());
            info.add(new JLabel("Factura No. " + billEntry.getId()));
            info.add(new JLabel("Cliente: " + billEntry.getClientName()));
            info.add(new JLabel("Fecha: " + billEntry.getDate()));
            card.add(info, BorderLayout.CENTER);

            JButton viewDetail = new JButton("🧾");
            viewDetail.setPreferredSize(new Dimension(40, 40));
            viewDetail.addActionListener(_ -> accountingController.onOpenBillDialog(billEntry));
            card.add(viewDetail, BorderLayout.EAST);

            salesPanel.add(card);
            salesPanel.add(Box.createVerticalStrut(10));
        }

        salesPanel.revalidate();
        salesPanel.repaint();
    }

    public void showFilters(List<Client> clients, List<Product> products) {
        filtersPanel.removeAll();

        JComboBox<String> clientFilter = new JComboBox<>();
        clientFilter.addItem(accountingController.VOID_FILTER);
        for (Client client : clients) {
            clientFilter.addItem(client.getName());
        }

        JComboBox<String> productFilter = new JComboBox<>();
        productFilter.addItem(accountingController.VOID_FILTER);
        for (Product product : products) {
            productFilter.addItem(product.getName());
        }

        JButton searchButton = new JButton("🔍");
        searchButton.addActionListener(_ ->
                accountingController.onFilterBills(clientFilter, productFilter)
        );

        filtersPanel.add(clientFilter);
        filtersPanel.add(productFilter);
        filtersPanel.add(searchButton);

        filtersPanel.revalidate();
        filtersPanel.repaint();
    }

    public void showDetailForm(BillEntry billEntry) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        AccountingBillFormDialog dialog = new AccountingBillFormDialog(frame, billEntry);
        dialog.setVisible(true);
    }
}
