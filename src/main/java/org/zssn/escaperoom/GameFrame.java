package org.zssn.escaperoom;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.PrintStream;

/**
 * GameFrame class. This class represents the frame of the game. It contains the main window of the game, the terminal, the input field.
 */
public class GameFrame /*implements MouseMotionListener, MouseListener*/ {
    
    /**
     * Game object
     */
    private GameManager game;

    /**
     * Array of images of the walls
     */
    private Image[] Images = new Image[36];
    /**
     * Boolean for the fullscreen state
     */
    private boolean isFullscreen = false;
    /**
     * GraphicsDevice object
     */
    private GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    /**
     * JFrame object for the main window
     */
    private JFrame frame;
    /**
     * JTextPane object for the terminal
     */
    private JTextPane terminal;
    /**
     * JTextField object for the input field
     */
    JTextField inputField;
    /**
     * JPanel object for the game informations
     */
    JPanel statusPanel;
    /**
     * JLabel object for the position of the player
     */
    JLabel playerStatusLabel;
    /**
     * JLabel object for the player health
     */
    private JLabel playerHealthLabel;

    /**
     * JLabel object for the filled stars
     */
    private JLabel filledStarsLabel;

    /**
     * Boolean for the wall configuration
     */
    private boolean wallConfigured = false;

    /**
     * Constructor of the GameFrame class
     */
    public GameFrame() {
        try {
            game = new GameManager(/*this*/);
        } catch (Exception e) {
            writeToTerminal(e.getMessage());
        }

        frame = new JFrame("TextAdventure");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(new ImageIcon(getClass().getResource("/text-adventure-icon.png")).getImage());
        frame.setLayout(new BorderLayout());
        /*frame.addMouseMotionListener(this);
        frame.addMouseListener(this);*/

        // Configura il pannello centrale
        JPanel center = new JPanel();
        center.setBackground(new Color(0, 0, 0));
        center.setLayout(new BorderLayout());

        // Crea un pannello per contenere l'immagine principale e la minimappa
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(new Color(0, 0, 0));
        imagePanel.setLayout(new BorderLayout());

        JLabel graphic = new JLabel();
        graphic.setFont(new Font("Monospaced", Font.PLAIN, 20));
        graphic.setHorizontalAlignment(SwingConstants.CENTER);
        graphic.setVerticalAlignment(SwingConstants.CENTER);
        graphic.setIcon(new ImageIcon(getClass().getResource("/DefaultScreen.png")));
        imagePanel.add(graphic, BorderLayout.CENTER);

        
        // Ridimensiona la minimappa
        ImageIcon miniMapIcon = new ImageIcon(getClass().getResource("/MiniMapNero.png"));
        Image miniMapImage = miniMapIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH); // Imposta le dimensioni desiderate (es. 200x200)
        // Aggiungi la minimappa
        JLabel miniMap = new JLabel(new ImageIcon(miniMapImage));
        miniMap.setFont(new Font("Monospaced", Font.PLAIN, 20));
        miniMap.setHorizontalAlignment(SwingConstants.CENTER);
        miniMap.setVerticalAlignment(SwingConstants.NORTH);
        center.add(imagePanel, BorderLayout.CENTER);

        // Configura il terminale e lo scorrimento
        terminal = new JTextPane();
        terminal.setEditable(false);
        terminal.setFont(new Font("Monospaced", Font.PLAIN, 14));
        terminal.setBackground(new Color(28, 28, 28));
        terminal.setForeground(Color.WHITE);
        writeToTerminal("Welcome to the Escape Room!\nIf you want to start a new game, please type 'New Game'.\nIf you want to load a previous game, please type 'Resume'.");
        JScrollPane terminalScrollPane = new JScrollPane(terminal);
        terminalScrollPane.setPreferredSize(new Dimension(frame.getWidth(), 225)); // Altezza del terminale a 225
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
                    if (game.getGame() != null && !wallConfigured) {
                        // Carica le immagini dei muri
                        clearTerminal();
                        writeToTerminal("Game configured! Enter 'help' to see the list of commands.\nTo win the game you have to find all the stars in the map and fill the holes in the central room with them.\nGood luck!");
                        for (int i = 0; i < 36; i++) {
                            Images[i] = (Image) Room.Walls[i].returnCombinedImage();
                        }
                        imagePanel.add(miniMap, BorderLayout.EAST); // Aggiungi la minimappa a destra

                        wallConfigured = true;
                    }
                    if (game.getGame() != null){
                        graphic.setIcon(new ImageIcon(Images[(game.getGame().getPlayer().getCurrentRoom() - 1) * 4 + game.getGame().getPlayer().getCurrentDirection().ordinal()]));
                        updatePlayerHealthLabel();
                        updateStarLabel();
                        updatePlayerStatusLabel();
                    }
                    else {
                        if (game.isGameWon())
                            graphic.setIcon(new ImageIcon(getClass().getResource("/YouWin.png")));
                        else if (game.isGameLost())
                            graphic.setIcon(new ImageIcon(getClass().getResource("/YouLose.png")));
                        else
                            graphic.setIcon(new ImageIcon(getClass().getResource("/DefaultScreen.png")));
                        playerHealthLabel.setText("");
                        filledStarsLabel.setText("");
                        playerStatusLabel.setText("");
                    } 
                    inputField.setText("");
                }
            }
        });
        downPanel.add(inputField, BorderLayout.SOUTH);

        center.add(downPanel, BorderLayout.PAGE_END);
        frame.add(center, BorderLayout.CENTER);

        // Configura il pannello delle informazioni
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(28, 28, 28));
        topPanel.setLayout(new BorderLayout());
        topPanel.setPreferredSize(new Dimension(0, 45));

        // Pannello sinistro per la salute del giocatore
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setPreferredSize(new Dimension(250, 45));
        leftPanel.setBackground(new Color(28, 28, 28));
        playerHealthLabel = new JLabel();
        playerHealthLabel.setFont(new Font("Monospaced", Font.PLAIN, 35));
        playerHealthLabel.setForeground(Color.RED);
        playerHealthLabel.setText("");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.LINE_START; // align to left
        gbc.weightx = 1.0; // take up all available space
        leftPanel.add(playerHealthLabel, gbc);

        // Pannello centrale per le stelle raccolte
        JPanel centerPanel = new JPanel(new GridBagLayout());
        //centerPanel.setPreferredSize(new Dimension(frame.getWidth()/3, 45));
        centerPanel.setBackground(new Color(28, 28, 28));
        filledStarsLabel = new JLabel();
        filledStarsLabel.setFont(new Font("Monospaced", Font.PLAIN, 35));
        filledStarsLabel.setForeground(Color.YELLOW);
        filledStarsLabel.setText("");
        centerPanel.add(filledStarsLabel);

       // Pannello destro per la posizione del giocatore
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setPreferredSize(new Dimension(250, 45));
        rightPanel.setBackground(new Color(28, 28, 28));
        playerStatusLabel = new JLabel();
        playerStatusLabel.setFont(new Font("Monospaced", Font.PLAIN, 20));
        playerStatusLabel.setForeground(Color.WHITE);
        playerStatusLabel.setText("");
        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.LINE_END; // align to right
        gbc.weightx = 1.0; // take up all available space
        rightPanel.add(playerStatusLabel, gbc);

        // Aggiungi i pannelli al pannello superiore
        topPanel.add(leftPanel, BorderLayout.WEST);
        topPanel.add(centerPanel, BorderLayout.CENTER);
        topPanel.add(rightPanel, BorderLayout.EAST);

        frame.add(topPanel, BorderLayout.PAGE_START);

        // Listener per il ridimensionamento della finestra
        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                int h = frame.getHeight();
                int w = frame.getWidth();
                double grapSize = w / 25;
                graphic.setFont(new Font("Monospaced", Font.PLAIN, (int) grapSize));
                //terminalScrollPane.setPreferredSize(new Dimension(w, 225));
                center.setPreferredSize(new Dimension(w, h - 140));
            }   
        });

        // Configura i key bindings per la finestra
        InputMap inputMap = frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0), "toggleFullscreen");
        frame.getRootPane().getActionMap().put("toggleFullscreen", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                toggleFullscreen();
            }
        });

        InputMap inputMapZoom = frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMapZoom.put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "zoom");
        frame.getRootPane().getActionMap().put("zoom", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                zoom();
            }
        });

        InputMap inputMapUnzoom = frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMapUnzoom.put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "unzoom");
        frame.getRootPane().getActionMap().put("unzoom", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                unzoom();
            }
        });

        // Reindirizza System.out e System.err alla JTextPane
        PrintStream printStream = new PrintStream(new CustomOutputStream(terminal));
        //System.setOut(printStream);
        //System.setErr(printStream);

        frame.setVisible(true);
        toggleFullscreen();
    }

    /**
     * Update the player health label
     */
    private void updatePlayerHealthLabel() {
        StringBuilder heartSymbols = new StringBuilder();
        for (int i = 0; i < game.getGame().getPlayer().getHealth(); i++) {
            heartSymbols.append("♥");
        }
        playerHealthLabel.setText(heartSymbols.toString());
    }

    /**
     * Update the star label
     */
    private void updateStarLabel() {
        StringBuilder starSymbols = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            if (game.getGame().getStarHole(i))
                starSymbols.append("★");
            else
                starSymbols.append("☆");
        }
        filledStarsLabel.setText(starSymbols.toString());
    }

    /**
     * Update the player status label
     */
    private void updatePlayerStatusLabel() {
        playerStatusLabel.setText(game.getGame().getMap().getRoom(game.getGame().getPlayer().getCurrentRoom()).getName() + ", " + game.getGame().getPlayer().getCurrentDirection().toString());
    }

    /**
     * Toggle the fullscreen mode
     */
    private void toggleFullscreen() {
        if (isFullscreen) {
            gd.setFullScreenWindow(null);
            frame.dispose();
            frame.setUndecorated(false);
            frame.setSize(1920, 1080);
            frame.setResizable(true);
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

        /**
     * Zoom the font of the terminal and the input field
     */
    private void zoom() {
        Font font = terminal.getFont();
        float size = font.getSize() + 1.0f;
        terminal.setFont(font.deriveFont(size));
        inputField.setFont(font.deriveFont(size));

    }

    /**
     * Unzoom the font of the terminal and the input field
     */
    private void unzoom() {
        Font font = terminal.getFont();
        float size = font.getSize() - 1.0f;
        terminal.setFont(font.deriveFont(size));
        inputField.setFont(font.deriveFont(size));
    }

    /**
     * Write a message to the terminal
     * 
     * @param message the message to write
     */
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

    /**
     * Clear the terminal
     */
    public void clearTerminal() {
        StyledDocument doc = terminal.getStyledDocument();
        try {
            doc.remove(0, doc.getLength());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    /*
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
    */
}