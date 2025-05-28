package com.twinker.presentation.controller;

import com.twinker.application.HomeService;
import com.twinker.presentation.view.HomeView;

/**
 * Controller class for managing the home dashboard in the Twinker application.
 * This class coordinates the display of important business alerts and
 * notifications, particularly focusing on inventory management.
 *
 * <p>
 * The controller handles:
 * <ul>
 * <li>Low stock alerts</li>
 * <li>Dashboard initialization</li>
 * <li>Home view updates</li>
 * </ul>
 * </p>
 *
 * @author Twinker Development Team
 * @see com.twinker.presentation.view.HomeView
 * @see com.twinker.application.HomeService
 */
public class HomeController {
    private final HomeView view;
    private final HomeService homeService;

    /**
     * Constructs a new HomeController.
     * Initializes the home service for dashboard management.
     *
     * @param view the home view to be controlled
     */
    public HomeController(HomeView view) {
        this.view = view;
        homeService = new HomeService();
    }

    /**
     * Initializes the controller by displaying stock alerts.
     */
    public void init() {
        view.showAlerts(homeService.getProductsOutOffStock());
    }

    /**
     * Handles reloading of home dashboard data.
     */
    public void onLoadHome() {
        init();
    }
}
