package org.zssn.escaperoom;

import java.io.OutputStream;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.StyledDocument;

public class CustomOutputStream extends OutputStream {
    private JTextPane textPane; 

    public CustomOutputStream(JTextPane textPane) { 
        this.textPane = textPane;
    }

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
