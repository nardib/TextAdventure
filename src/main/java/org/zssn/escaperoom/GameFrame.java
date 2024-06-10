package org.zssn.escaperoom;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.PrintStream;

public class GameFrame implements MouseMotionListener, MouseListener {
    // Variabili di istanza
    Game game = new Game("Player", "m", "Enemy", "f"); // Oggetto del gioco
    int x = 0; // Coordinata X del mouse
    int y = 0; // Coordinata Y del mouse
    int wallcount = 0; // Contatore per il cambio di muro
    Image[] Images = new Image[36]; // Array di immagini dei muri
    boolean isFullscreen = false; // Stato della finestra (fullscreen o no)
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice(); // Dispositivo grafico
    JFrame frame; // Finestra principale
    JTextPane terminal; // Terminale di output
    JTextField inputField; // Campo di testo per l'input dell'utente
    JPanel buttonPanel; // Pannello dei pulsanti
    JLabel gameStatusLabel; // Etichetta per lo stato del gioco
    private static JLabel playerHelthLabel; //Etichetta per la vita del giocatore 

    // Costruttore della classe
    public GameFrame() {
        frame = new JFrame("TextAdventure");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(new ImageIcon(getClass().getResource("/text-adventure-icon.png")).getImage());
        frame.setLayout(new BorderLayout());
        frame.addMouseMotionListener(this);
        frame.addMouseListener(this);

        // Carica le immagini dei muri
        for (int i = 0; i < 36; i++) {
            Images[i] = (Image) Room.Walls[i].getCombinedImage();
        }

        // Configura il pannello centrale
        JPanel center = new JPanel();
        center.setBackground(new Color(0, 0, 0));
        center.setLayout(new BorderLayout());

        JLabel graphic = new JLabel();
        graphic.setFont(new Font("Monospaced", Font.PLAIN, 20));
        graphic.setHorizontalAlignment(SwingConstants.CENTER);
        graphic.setVerticalAlignment(SwingConstants.CENTER);
        graphic.setIcon(new ImageIcon(Images[(game.getPlayer().getCurrentRoom() - 1) * 4 + game.getPlayer().getCurrentDirection().ordinal()]));
        center.add(graphic, BorderLayout.CENTER);

        // Configura il terminale e lo scorrimento
        terminal = new JTextPane();
        terminal.setEditable(false);
        terminal.setFont(new Font("Monospaced", Font.PLAIN, 14));
        terminal.setBackground(new Color(28, 28, 28));
        terminal.setForeground(Color.WHITE);
        JScrollPane terminalScrollPane = new JScrollPane(terminal);
        terminalScrollPane.setPreferredSize(new Dimension(frame.getWidth(), 140)); // Altezza del terminale a 140

        // Pannello per terminal e input
        JPanel downPanel = new JPanel();
        downPanel.setLayout(new BorderLayout());
        downPanel.add(terminalScrollPane, BorderLayout.CENTER);

        // Crea il campo di testo per l'input dell'utente
        inputField = new JTextField();
        inputField.setFont(new Font("Monospaced", Font.PLAIN, 14));
        inputField.setBackground(new Color(28, 28, 28));
        inputField.setForeground(Color.WHITE);
        inputField.setCaretColor(Color.WHITE);
        inputField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String mex = inputField.getText();
                    writeToTerminal(game.nextMove(mex));
                    graphic.setIcon(new ImageIcon(Images[(game.getPlayer().getCurrentRoom() - 1) * 4 + game.getPlayer().getCurrentDirection().ordinal()]));
                    updatePlayerHealthLabel();
                    inputField.setText("");
                }
            }
        });
        downPanel.add(inputField, BorderLayout.SOUTH);

        center.add(downPanel, BorderLayout.PAGE_END);
        frame.add(center, BorderLayout.CENTER);

        //Configura Label vita giocatore
        playerHelthLabel = new JLabel();
        playerHelthLabel.setFont(new Font("Monospaced", Font.PLAIN, 20));
        playerHelthLabel.setForeground(Color.WHITE);
        playerHelthLabel.setText("SALUTE: ♥♥♥♥♥");
        center.add(playerHelthLabel, BorderLayout.PAGE_START);

        // Configura il pannello dei pulsanti
        buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(28, 28, 28));
        buttonPanel.setLayout(new GridBagLayout());
        buttonPanel.setPreferredSize(new Dimension(0, 45));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gameStatusLabel = new JLabel();
        gameStatusLabel.setFont(new Font("Monospaced", Font.PLAIN, 20));
        gameStatusLabel.setForeground(Color.WHITE);

        gbc.gridx = 2; // Colonna 2
        gbc.gridy = 0; // Riga 0
        gbc.weightx = 1.0; // Espansione orizzontale
        gbc.anchor = GridBagConstraints.EAST; // Ancoraggio a destra
        buttonPanel.add(gameStatusLabel, gbc);

        JPanel buttonPanelCenter = new JPanel();
        buttonPanelCenter.setBackground(new Color(0, 0, 0));
        buttonPanelCenter.setLayout(new FlowLayout());

        Color buttonBackgroundColor = new Color(70, 70, 70);
        Color buttonTextColor = Color.WHITE;

        // Aggiungi pulsanti
        JButton startButton = new JButton("Start");
        startButton.setFont(new Font("Monospaced", Font.PLAIN, 20));
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameStatusLabel.setText("GIOCO INIZIATO");
            }
        });
        startButton.setBackground(buttonBackgroundColor);
        startButton.setForeground(buttonTextColor);
        buttonPanelCenter.add(startButton);

        JButton pauseButton = new JButton("Pause");
        pauseButton.setFont(new Font("Monospaced", Font.PLAIN, 20));
        pauseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameStatusLabel.setText("GIOCO IN PAUSA");
            }
        });
        pauseButton.setBackground(buttonBackgroundColor);
        pauseButton.setForeground(buttonTextColor);
        buttonPanelCenter.add(pauseButton);

        JButton resetButton = new JButton("Reset");
        resetButton.setFont(new Font("Monospaced", Font.PLAIN, 20));
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameStatusLabel.setText("");
                graphic.setText(" ");
                playerHelthLabel.setText("SALUTE: ♥♥♥♥♥");
                graphic.setIcon(null);
                wallcount = 0;
                clearTerminal();
            }
        });
        resetButton.setBackground(buttonBackgroundColor);
        resetButton.setForeground(buttonTextColor);
        buttonPanelCenter.add(resetButton);

        JButton nextWallButton = new JButton("Turn Right");
        nextWallButton.setFont(new Font("Monospaced", Font.PLAIN, 20));
        nextWallButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                graphic.setIcon(new ImageIcon(Images[wallcount]));
                gameStatusLabel.setText("");
                wallcount++;
            }
        });
        nextWallButton.setBackground(buttonBackgroundColor);
        nextWallButton.setForeground(buttonTextColor);
        buttonPanelCenter.add(nextWallButton);

        JButton attackButton = new JButton("Attack");
        attackButton.setFont(new Font("Monospaced", Font.BOLD, 20));
        writeToTerminal("Prova per il funzionamento del terminale, scrivi qualcosa");
        attackButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String mex = inputField.getText();
                writeToTerminal(game.nextMove(mex));
                inputField.setText(""); // Pulisce il campo di testo dopo l'invio
            }
        });
        attackButton.setBackground(buttonBackgroundColor);
        attackButton.setForeground(buttonTextColor);
        buttonPanelCenter.add(attackButton);

        gbc.gridx = 1; // Colonna 1
        gbc.gridy = 0; // Riga 0
        gbc.weightx = 0.0; // Nessuna espansione orizzontale
        gbc.anchor = GridBagConstraints.CENTER; // Ancoraggio al centro
        buttonPanel.add(buttonPanelCenter, gbc);

        frame.add(buttonPanel, BorderLayout.PAGE_START);

        // Listener per il ridimensionamento della finestra
        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                int h = frame.getHeight();
                int w = frame.getWidth();
                double grapSize = w / 25;
                graphic.setFont(new Font("Monospaced", Font.PLAIN, (int) grapSize));
                terminalScrollPane.setPreferredSize(new Dimension(w, 140)); // Mantieni l'altezza del terminale a 140
                center.setPreferredSize(new Dimension(w, h - 140));
            }
        });

        // Configura i key bindings per la finestra
        InputMap inputMap = frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0), "toggleFullscreen");
        frame.getRootPane().getActionMap().put("toggleFullscreen", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                toggleFullscreen();
                gameStatusLabel.setText("F11");
            }
        });

        // Reindirizza System.out e System.err alla JTextPane
        PrintStream printStream = new PrintStream(new CustomOutputStream(terminal));
        System.setOut(printStream);
        System.setErr(printStream);

        frame.setVisible(true);
        toggleFullscreen();
    }

    /**
     * Update the player health label
     */
    public void updatePlayerHealthLabel() {
        StringBuilder heartSymbols = new StringBuilder();
        for (int i = 0; i < game.getPlayer().getHealth(); i++) {
            heartSymbols.append("♥");
        }
        playerHelthLabel.setText("SALUTE: " + heartSymbols.toString());
    }

    // Attiva/disattiva la modalità fullscreen
    private void toggleFullscreen() {
        if (isFullscreen) {
            gd.setFullScreenWindow(null);
            frame.dispose();
            frame.setUndecorated(false);
            frame.setSize(1920, 1080);
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } else {
            frame.dispose();
            frame.setUndecorated(true);
            frame.setResizable(true);
            gd.setFullScreenWindow(frame);
        }
        isFullscreen = !isFullscreen;
    }

    // Scrive un messaggio nel terminale
    private void writeToTerminal(String message) {
        StyledDocument doc = terminal.getStyledDocument();
        SimpleAttributeSet paraSet = new SimpleAttributeSet();
        StyleConstants.setLineSpacing(paraSet, -0.2f);

        try {
            doc.insertString(doc.getLength(), message + "\n", paraSet);
            doc.setParagraphAttributes(doc.getLength() - message.length() - 1, message.length() + 1, paraSet, false);
            terminal.setCaretPosition(doc.getLength());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    // Pulisce il terminale
    private void clearTerminal() {
        StyledDocument doc = terminal.getStyledDocument();
        try {
            doc.remove(0, doc.getLength());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    // Listener del mouse
    public void mouseDragged(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {
        PointerInfo a = MouseInfo.getPointerInfo();
        Point b = a.getLocation();
        x = (int) b.getX();
        y = (int) b.getY();
    }
    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
}
