/**
 * Main entry point for the Twinker application.
 * This class initializes the Swing GUI application using the Event Dispatch Thread (EDT)
 * to ensure thread safety in Swing components.
 *
 * <p>The application uses FlatLaf look and feel for modern UI appearance and
 * launches the main window ({@link com.twinker.presentation.view.MainView})
 * which serves as the primary container for all application views.</p>
 *
 * @author Twinker Development Team
 * @see com.twinker.presentation.view.MainView
 * @see javax.swing.SwingUtilities#invokeLater(Runnable)
 */
package com.twinker;

import com.twinker.presentation.view.MainView;

import javax.swing.*;

public class Main {
    /**
     * The main method that serves as the entry point for the application.
     * It schedules the creation of the main window on the Event Dispatch Thread
     * using {@link SwingUtilities#invokeLater(Runnable)}.
     *
     * @param args command line arguments (not used in this application)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainView::new);
    }
}