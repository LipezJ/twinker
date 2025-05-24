package com.twinker.presentation.controller;

import com.twinker.application.StatisticsService;
import com.twinker.presentation.view.StatisticsView;

import java.util.HashMap;
import java.util.Map;

public class StatisticsController {
    private final StatisticsView view;
    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsView view) {
        this.view = view;
        statisticsService = new StatisticsService();
    }

    public void initStatistics() {
        onSelectChart(0);
        view.showTopProducts(statisticsService.getMonthlyTopProducts());
        view.showTopClientChart(statisticsService.getMonthlyTopClients());
    }

    public void onLoadStatistics() {
        initStatistics();
    }

    public void onSelectChart(int optionSelected) {
        Map<String, Double> data = new HashMap<>();

        switch (optionSelected) {
            case 1 -> data = statisticsService.getMonthlySales();
            case 2 -> data = statisticsService.getAnnualSales();
            default -> data = statisticsService.getWeeklySales();
        }

        view.showChart(data);
    }
}
