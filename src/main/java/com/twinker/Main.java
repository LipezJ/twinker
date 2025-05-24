package com.twinker;

import com.twinker.presentation.view.MainView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainView::new);
    }
}