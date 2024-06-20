package org.zssn.escaperoom;

import java.io.OutputStream;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.StyledDocument;

/**
 * Custom output stream to redirect the output to the JTextPane
 */
public class CustomOutputStream extends OutputStream {
    /**
     * JTextPane to redirect the output
     */
    private JTextPane textPane; 

    /**
     * Constructor for the CustomOutputStream
     * 
     * @param textPane JTextPane to redirect the output
     */
    public CustomOutputStream(JTextPane textPane) { 
        this.textPane = textPane;
    }


    /**
     * Write the output to the JTextPane
     * 
     * @param b byte to write
     */
    @Override
    public void write(int b) {
        StyledDocument doc = textPane.getStyledDocument();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    doc.insertString(doc.getLength(), String.valueOf((char) b), null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                textPane.setCaretPosition(doc.getLength());
            }
        });
    }

    /**
     * Write the output to the JTextPane
     * 
     * @param b byte array to write
     * @param off offset
     * @param len length
     */
    @Override
    public void write(byte[] b, int off, int len) {
        StyledDocument doc = textPane.getStyledDocument();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    doc.insertString(doc.getLength(), new String(b, off, len), null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                textPane.setCaretPosition(doc.getLength());
            }
        });
    }

}
