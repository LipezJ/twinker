package com.twinker.presentation.controller;

import com.twinker.application.StatisticsService;
import com.twinker.presentation.view.StatisticsView;

import java.util.Map;

/**
 * Controller class for managing statistics and analytics in the Twinker
 * application.
 * This class coordinates the display of various sales metrics and charts,
 * providing insights into business performance.
 *
 * <p>
 * The controller handles:
 * <ul>
 * <li>Sales trend visualization</li>
 * <li>Top products analysis</li>
 * <li>Top clients tracking</li>
 * <li>Time-based sales reports</li>
 * </ul>
 * </p>
 *
 * @author Twinker Development Team
 * @see com.twinker.presentation.view.StatisticsView
 * @see com.twinker.application.StatisticsService
 */
public class StatisticsController {
    private final StatisticsView view;
    private final StatisticsService statisticsService;

    /**
     * Constructs a new StatisticsController.
     * Initializes the statistics service for data analysis.
     *
     * @param view the statistics view to be controlled
     */
    public StatisticsController(StatisticsView view) {
        this.view = view;
        statisticsService = new StatisticsService();
    }

    /**
     * Initializes the controller by loading all statistics components.
     * Sets up initial chart selection and displays top performers.
     */
    public void initStatistics() {
        onSelectChart(0);
        view.showTopProducts(statisticsService.getMonthlyTopProducts());
        view.showTopClientChart(statisticsService.getMonthlyTopClients());
    }

    /**
     * Handles reloading of statistics data.
     */
    public void onLoadStatistics() {
        initStatistics();
    }

    /**
     * Handles chart selection and display.
     * Updates the view with the selected time period's sales data.
     *
     * @param optionSelected the time period option (0: weekly, 1: monthly, 2:
     *                       annual)
     */
    public void onSelectChart(int optionSelected) {
        Map<String, Double> data;

        switch (optionSelected) {
            case 1 -> data = statisticsService.getMonthlySales();
            case 2 -> data = statisticsService.getAnnualSales();
            default -> data = statisticsService.getWeeklySales();
        }

        view.showChart(data);
    }
}
