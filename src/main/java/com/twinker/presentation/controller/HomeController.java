package com.twinker.presentation.controller;

import com.twinker.application.HomeService;
import com.twinker.presentation.view.HomeView;

public class HomeController {
    private final HomeView view;
    private final HomeService homeService;

    public HomeController(HomeView view) {
        this.view = view;
        homeService = new HomeService();
    }

    public void init() {
        view.showAlerts(homeService.getProductsOutOffStock());
    }

    public void onLoadHome() {
        init();
    }
}
