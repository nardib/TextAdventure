package org.zssn.escaperoom;

import javax.swing.SwingUtilities;

/**
 * Class Main - Create an instance of GameFrame and show the graphical interface
 */
public class Main{
    /**
     * Main method of the application
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GameFrame();
            }
        });
    }
}
