import javax.swing.SwingUtilities;

public class Main{
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GameFrame(); // Crea un oggetto GameFrame e mostra l'interfaccia grafica
            }
        });

        // Altre istruzioni della tua applicazione, se presenti
    }
}
