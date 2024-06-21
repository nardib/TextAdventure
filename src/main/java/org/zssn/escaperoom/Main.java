package org.zssn.escaperoom;

import javax.swing.SwingUtilities;
/**
 * The Main class is the entry point of the application.
 */
public class Main {
    /**
     * Main method of the application.
     * 
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
