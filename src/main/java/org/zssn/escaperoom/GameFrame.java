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
public class GameFrame {
    
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
    private JTextArea inventoryText;
    private ImageIcon miniMapIcon;
    private Image miniMapImage;
    private JLabel miniMap;
    /**
     * JTextField object for the input field
     */
    
    JTextField inputField;
    /**
     * JPanel object for the game information
     */
    JPanel statusPanel;
    /**
     * JLabel object for the position of the player
     */
    JLabel playerPositionLabel;
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
     * Dimension of the miniMap
     */
    private int dimension=400;
    /**
     * Constructor of the GameFrame class
     */
    public GameFrame() {
        try {
            game = new GameManager();
        } catch (Exception e) {
            writeToTerminal(e.getMessage());
        }

        // Initialize the main JFrame for the game, setting its title, close operation, icon image, and layout
        frame = new JFrame("TextAdventure");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(new ImageIcon(getClass().getResource("/text-adventure-icon.png")).getImage());
        frame.setLayout(new BorderLayout());

        // Configure the center panel
        JPanel center = new JPanel();
        center.setBackground(new Color(0, 0, 0));
        center.setLayout(new BorderLayout());

        // Create a panel to contain the main image and the minimap
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(new Color(0, 0, 0));
        imagePanel.setLayout(new BorderLayout());

        // Initialize the JLabel for displaying graphics
        JLabel graphic = new JLabel();
        graphic.setFont(new Font("Monospaced", Font.PLAIN, 20));
        graphic.setHorizontalAlignment(SwingConstants.CENTER);
        graphic.setVerticalAlignment(SwingConstants.CENTER);
        graphic.setIcon(new ImageIcon(getClass().getResource("/DefaultScreen.jpg")));
        imagePanel.add(graphic, BorderLayout.CENTER);

        
        // Resize the minimap
        miniMapIcon = new ImageIcon(getClass().getResource("/MiniMap1.png"));
        miniMapImage = miniMapIcon.getImage().getScaledInstance(dimension, dimension, Image.SCALE_SMOOTH);
        
        // Add the minimap
        miniMap = new JLabel(new ImageIcon(miniMapImage));
        miniMap.setFont(new Font("Monospaced", Font.PLAIN, 20));
        miniMap.setHorizontalAlignment(SwingConstants.CENTER);
        miniMap.setVerticalAlignment(SwingConstants.NORTH);

        // Create a panel to hold the minimap and inventory
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BorderLayout());
        sidePanel.setBackground(new Color(28, 28, 28));

        // Create the inventory panel
        JPanel inventoryPanel = new JPanel();
        inventoryPanel.setBackground(new Color(28, 28, 28));
        inventoryPanel.setLayout(new BorderLayout());

        inventoryText = new JTextArea();
        inventoryText.setEditable(false);
        inventoryText.setFont(new Font("Monospaced", Font.PLAIN, 15));
        inventoryText.setBackground(new Color(28, 28, 28));
        inventoryText.setForeground(Color.WHITE);
        JScrollPane inventoryScrollPane = new JScrollPane(inventoryText);
        inventoryPanel.add(inventoryScrollPane, BorderLayout.CENTER);
        
        // Set initial inventory text
        updateInventoryText(inventoryText);

        // Add the side panel to the main center panel
        center.add(sidePanel, BorderLayout.EAST);
        center.add(imagePanel, BorderLayout.CENTER);

        // Configure the terminal and scrolling
        terminal = new JTextPane();
        terminal.setEditable(false);
        terminal.setFont(new Font("Monospaced", Font.PLAIN, 15));
        terminal.setBackground(new Color(28, 28, 28));
        terminal.setForeground(Color.WHITE);
        writeToTerminal("Welcome to the Escape Room!\nIf you want to start a new game, please type 'New Game'.\nIf you want to load a previous game, please type 'Resume'.");
        JScrollPane terminalScrollPane = new JScrollPane(terminal);
        terminalScrollPane.setPreferredSize(new Dimension(frame.getWidth(), 225)); // Set terminal height to 225

        // Panel for terminal and input
        JPanel downPanel = new JPanel();
        downPanel.setLayout(new BorderLayout());
        downPanel.add(terminalScrollPane, BorderLayout.CENTER);

        // Create the text field for user input
        inputField = new JTextField();
        inputField.setFont(new Font("Monospaced", Font.PLAIN, 15));
        inputField.setBackground(new Color(28, 28, 28));
        inputField.setForeground(Color.WHITE);
        inputField.setCaretColor(Color.WHITE);
        inputField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String mex = inputField.getText();
                    writeToTerminal(game.nextMove(mex));
                    if (game.getGame() != null && !wallConfigured) { 
                        clearTerminal();
                        writeToTerminal("Game configured! Enter 'help' to see the list of commands.\nTo win the game you have to find all the stars in the map and fill the holes in the central room with them.\nGood luck!");
                        // Load the wall images
                        for (int i = 0; i < 36; i++) {
                            Images[i] = (Image) Room.Walls[i].returnCombinedImage();
                        }
                        wallConfigured = true;
                        // Add the minimap to the side panel
                        sidePanel.add(miniMap, BorderLayout.NORTH);
                        // Add the inventory panel below the minimap
                        sidePanel.add(inventoryPanel, BorderLayout.CENTER);
                    }
                    // Update the graphic icon, player health label, star label, and player position label if the game is active
                    if (game.getGame() != null){
                        graphic.setIcon(new ImageIcon(Images[(game.getGame().getPlayer().getCurrentRoom() - 1) * 4 + game.getGame().getPlayer().getCurrentDirection().ordinal()]));
                        updatePlayerHealthLabel();
                        updateStarLabel();
                        updatePlayerPositionLabel();
                        updateInventoryText(inventoryText);
                    }
                    else {
                        if (game.isGameWon())
                            graphic.setIcon(new ImageIcon(getClass().getResource("/YouWin.jpg")));
                        else if (game.isGameLost())
                            graphic.setIcon(new ImageIcon(getClass().getResource("/YouLose.jpg")));
                        else
                            graphic.setIcon(new ImageIcon(getClass().getResource("/DefaultScreen.jpg")));
                        playerHealthLabel.setText("");
                        filledStarsLabel.setText("");
                        playerPositionLabel.setText("");
                    } 
                    inputField.setText("");
                }
            }
        });
        downPanel.add(inputField, BorderLayout.SOUTH);

        center.add(downPanel, BorderLayout.PAGE_END);
        frame.add(center, BorderLayout.CENTER);

        // Configure the information panel
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(28, 28, 28));
        topPanel.setLayout(new BorderLayout());
        topPanel.setPreferredSize(new Dimension(0, 45));

        // Left panel for player health
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setPreferredSize(new Dimension(250, 45));
        leftPanel.setBackground(new Color(28, 28, 28));
        playerHealthLabel = new JLabel();
        playerHealthLabel.setFont(new Font("Monospaced", Font.PLAIN, 35));
        playerHealthLabel.setForeground(Color.RED);
        playerHealthLabel.setText("");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.LINE_START; // Align to left
        gbc.weightx = 1.0; // Take up all available space
        leftPanel.add(playerHealthLabel, gbc);

        // Center panel for collected stars
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(28, 28, 28));
        filledStarsLabel = new JLabel();
        filledStarsLabel.setFont(new Font("Monospaced", Font.PLAIN, 35));
        filledStarsLabel.setForeground(Color.YELLOW);
        filledStarsLabel.setText("");
        centerPanel.add(filledStarsLabel);

        // Right panel for player position
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setPreferredSize(new Dimension(250, 45));
        rightPanel.setBackground(new Color(28, 28, 28));
        playerPositionLabel = new JLabel();
        playerPositionLabel.setFont(new Font("Monospaced", Font.PLAIN, 20));
        playerPositionLabel.setForeground(Color.WHITE);
        playerPositionLabel.setText("");
        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.LINE_END; // Align to right
        gbc.weightx = 1.0; // Take up all available space
        rightPanel.add(playerPositionLabel, gbc);

        // Add the panels to the top panel
        topPanel.add(leftPanel, BorderLayout.WEST);
        topPanel.add(centerPanel, BorderLayout.CENTER);
        topPanel.add(rightPanel, BorderLayout.EAST);

        frame.add(topPanel, BorderLayout.PAGE_START);

        // Listener for window resizing
        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                int h = frame.getHeight();
                int w = frame.getWidth();
                double grapSize = w / 25;
                graphic.setFont(new Font("Monospaced", Font.PLAIN, (int) grapSize));
                center.setPreferredSize(new Dimension(w, h - 140));
            }   
        });

        // Configure key bindings for the window
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
        //PrintStream printStream = new PrintStream(new CustomOutputStream(terminal));
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
        for (int i = game.getGame().getPlayer().getHealth();i<5;i++) {
            heartSymbols.append("♡");
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
    private void updatePlayerPositionLabel() {
        playerPositionLabel.setText(game.getGame().getMap().getRoom(game.getGame().getPlayer().getCurrentRoom()).getName() + ", " + game.getGame().getPlayer().getCurrentDirection().toString());
    }

    /**
     * Update inventary label
     */
    private void updateInventoryText(JTextArea inventoryText) {
        if (game != null && game.getGame() != null) {
            inventoryText.setText(game.getGame().getPlayer().printInventory());
        } else {
            inventoryText.setText("INVENTARIO:\nNessun oggetto");
        }
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
        inventoryText.setFont(font.deriveFont(size));
        dimension +=20;
        miniMapImage = miniMapIcon.getImage().getScaledInstance(dimension, dimension, Image.SCALE_SMOOTH);
        miniMap.setIcon(new ImageIcon(miniMapImage));
        miniMap.revalidate();
        miniMap.repaint();
    }

    /**
     * Unzoom the font of the terminal and the input field
     */
    private void unzoom() {
        Font font = terminal.getFont();
        float size = font.getSize() - 1.0f;
        terminal.setFont(font.deriveFont(size));
        inputField.setFont(font.deriveFont(size));
        inventoryText.setFont(font.deriveFont(size));
        dimension -=20;
        miniMapImage = miniMapIcon.getImage().getScaledInstance(dimension, dimension, Image.SCALE_SMOOTH);
        miniMap.setIcon(new ImageIcon(miniMapImage));
        miniMap.revalidate();
        miniMap.repaint();
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
}
