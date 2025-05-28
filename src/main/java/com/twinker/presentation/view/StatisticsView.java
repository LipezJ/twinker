package com.twinker.presentation.view;

import com.twinker.presentation.controller.StatisticsController;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Map;

/**
 * The statistics and analytics view of the Twinker application.
 * This class provides visual representations of business metrics including
 * sales trends, top-selling products, and top customers.
 *
 * <p>
 * The view consists of three main sections:
 * <ul>
 * <li>A sales chart with weekly/monthly/yearly views</li>
 * <li>A top products panel showing best-selling items</li>
 * <li>A top clients chart showing highest-value customers</li>
 * </ul>
 * </p>
 *
 * <p>
 * The view uses JFreeChart for creating professional-looking charts
 * and graphs to visualize the business data.
 * </p>
 *
 * @author Twinker Development Team
 * @see com.twinker.presentation.controller.StatisticsController
 * @see org.jfree.chart.JFreeChart
 */
public class StatisticsView extends JPanel {
    private final JPanel chartPanel;
    private final JPanel topProductsPanel;
    private final JPanel topClientChart;
    private final StatisticsController statisticsController;

    /**
     * Constructs a new StatisticsView.
     * Initializes the UI components and sets up the statistics controller.
     * The view will automatically load statistics when it becomes visible.
     */
    public StatisticsView() {
        statisticsController = new StatisticsController(this);

        setLayout(new BorderLayout(10, 10));
        setBackground(UIManager.getColor("Panel.background"));
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JPanel topSection = new JPanel(new BorderLayout(10,10));
        topSection.setBackground(getBackground());

        JPanel leftPanel = new JPanel(new BorderLayout(5,5));
        leftPanel.setBorder(BorderFactory.createTitledBorder("Ventas"));
        leftPanel.setBackground(getBackground());
        JComboBox<String> chartSelect = new JComboBox<>(new String[]{ "Semanal", "Mensual", "Anual" });
        chartSelect.addActionListener(e ->
                statisticsController.onSelectChart(chartSelect.getSelectedIndex())
        );
        leftPanel.add(chartSelect, BorderLayout.NORTH);

        chartPanel = new JPanel(new BorderLayout());
        leftPanel.add(chartPanel, BorderLayout.CENTER);

        topSection.add(leftPanel, BorderLayout.CENTER);

        topProductsPanel = new JPanel();
        topProductsPanel.setLayout(new BoxLayout(topProductsPanel, BoxLayout.Y_AXIS));
        topProductsPanel.setBorder(BorderFactory.createTitledBorder("Top de productos vendidos"));
        topProductsPanel.setPreferredSize(new Dimension(200, 0));
        topSection.add(topProductsPanel, BorderLayout.EAST);

        add(topSection, BorderLayout.CENTER);

        topClientChart = new JPanel(new BorderLayout());
        topClientChart.setBorder(BorderFactory.createTitledBorder("Top de clientes"));
        topClientChart.setPreferredSize(new Dimension(0, 200));
        add(topClientChart, BorderLayout.SOUTH);

        statisticsController.initStatistics();
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) { statisticsController.onLoadStatistics(); }
        });
    }

    /**
     * Updates the main sales chart with new data.
     * Creates a bar chart showing sales amounts over time.
     *
     * @param data a map of time periods to sales amounts
     */
    public void showChart(Map<String, Double> data) {
        chartPanel.removeAll();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        data.forEach((k,v) -> dataset.addValue(v, "Ventas", k));
        JFreeChart chart = ChartFactory.createBarChart("Ventas", "", "Monto", dataset);
        ChartPanel cp = new ChartPanel(chart);
        chartPanel.add(cp, BorderLayout.CENTER);
        revalidate(); repaint();
    }

    /**
     * Updates the top products panel with current best-sellers.
     * Displays a list of products and their sales quantities.
     *
     * @param topProducts a map of product names to quantities sold
     */
    public void showTopProducts(Map<String, Integer> topProducts) {
        topProductsPanel.removeAll();
        topProducts.forEach((name,qty) -> {
            JPanel row = new JPanel(new BorderLayout());
            row.add(new JLabel(name), BorderLayout.CENTER);
            row.add(new JLabel(qty.toString()), BorderLayout.EAST);
            topProductsPanel.add(row);
        });
        revalidate(); repaint();
    }

    /**
     * Updates the top clients chart with current high-value customers.
     * Creates a bar chart showing total sales amount per client.
     *
     * @param topClients a map of client names to their total purchase amounts
     */
    public void showTopClientChart(Map<String, Double> topClients) {
        topClientChart.removeAll();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        topClients.forEach((name,amt) -> dataset.addValue(amt, "Venta", name));
        JFreeChart chart = ChartFactory.createBarChart("", "", "Monto", dataset);
        topClientChart.add(new ChartPanel(chart), BorderLayout.CENTER);
        revalidate(); repaint();
    }
}
