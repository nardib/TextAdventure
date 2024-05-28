import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.PrintStream;
    
public class GameFrame implements MouseMotionListener, MouseListener {
    Game game = new Game("Player", "m", "Enemy", "f");
    int x = 0;
    int y = 0;
    int wallcount = 0;
    Image[] Images = new Image[36];
    boolean isFullscreen = false;
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    JFrame frame;
    JTextPane terminal; // Cambiato da JTextArea a JTextPane
    JPanel buttonPanel;
    JLabel gameStatusLabel;

    public GameFrame() {
        frame = new JFrame("TextAdventure");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.addMouseMotionListener(this);
        frame.addMouseListener(this);

        for (int i = 0; i < 36; i++) {
            Images[i] = (Image) Room.Walls[i].getCombinedImage();
        }

        JPanel center = new JPanel();
        center.setBackground(new Color(0, 0, 0));
        center.setLayout(new BorderLayout());

        JLabel graphic = new JLabel();
        graphic.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        graphic.setHorizontalAlignment(SwingConstants.CENTER);
        graphic.setVerticalAlignment(SwingConstants.CENTER);
        center.add(graphic, BorderLayout.CENTER);

        JTextPane terminal = new JTextPane(); // Cambiato da JTextArea a JTextPane
        terminal.setEditable(true);
        terminal.setFont(new Font("Monospaced", Font.PLAIN, 14));
        terminal.setBackground(new Color(28, 28, 28));
        terminal.setForeground(Color.WHITE);
        JScrollPane terminalScrollPane = new JScrollPane(terminal);
        terminalScrollPane.setPreferredSize(new Dimension(frame.getWidth(), 150));
        center.add(terminalScrollPane, BorderLayout.SOUTH);
        

        frame.add(center, BorderLayout.CENTER);

        buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(28, 28, 28));
        buttonPanel.setLayout(new GridBagLayout());
        buttonPanel.setPreferredSize(new Dimension(0, 45));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gameStatusLabel = new JLabel();
        gameStatusLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        gameStatusLabel.setForeground(Color.WHITE);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.EAST;
        buttonPanel.add(gameStatusLabel, gbc);

        JPanel buttonPanelCenter = new JPanel();
        buttonPanelCenter.setBackground(new Color(0, 0, 0));
        buttonPanelCenter.setLayout(new FlowLayout());

        Color buttonBackgroundColor = new Color(70, 70, 70);
        Color buttonTextColor = Color.WHITE;

        JButton startButton = new JButton("Start");
        startButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameStatusLabel.setText("GIOCO INIZIATO");
            }
        });
        startButton.setBackground(new Color(54, 54 ,54));
        startButton.setForeground(Color.WHITE);
        buttonPanelCenter.add(startButton);

        JButton pauseButton = new JButton("Pause");
        pauseButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        pauseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameStatusLabel.setText("GIOCO IN PAUSA");
            }
        });
        pauseButton.setBackground(new Color(54, 54 ,54));
        pauseButton.setForeground(Color.WHITE);
        buttonPanelCenter.add(pauseButton);

        JButton resetButton = new JButton("Reset");
        resetButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameStatusLabel.setText("");
                graphic.setText(" ");
                graphic.setIcon(null);
                wallcount = 0;
            }
        });
        resetButton.setBackground(new Color(54, 54 ,54));
        resetButton.setForeground(Color.WHITE);
        buttonPanelCenter.add(resetButton);

        JButton nextWallButton = new JButton("Turn Right");
        nextWallButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        nextWallButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                graphic.setIcon(new ImageIcon(Images[wallcount]));
                gameStatusLabel.setText("");
                wallcount++;
            }
        });
        nextWallButton.setBackground(new Color(54, 54 ,54));
        nextWallButton.setForeground(Color.WHITE);
        buttonPanelCenter.add(nextWallButton);

        JButton attackButton = new JButton("Attack");
        attackButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        attackButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                terminal.setText(frame.getHeight() + " " + frame.getWidth());
            }
        });
        attackButton.setBackground(new Color(54, 54 ,54));
        attackButton.setForeground(Color.WHITE);
        buttonPanelCenter.add(attackButton);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.CENTER;
        buttonPanel.add(buttonPanelCenter, gbc);

        frame.add(buttonPanel, BorderLayout.PAGE_START);

        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                int h = frame.getHeight();
                int w = frame.getWidth();
                double grapSize = w / 25;
                graphic.setFont(new Font("Times New Roman", Font.PLAIN, (int) grapSize));

                terminalScrollPane.setPreferredSize(new Dimension(w, 150));
                center.setPreferredSize(new Dimension(w, h - 150));
            }
        });

        InputMap inputMap = frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0), "toggleFullscreen");
        frame.getRootPane().getActionMap().put("toggleFullscreen", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                toggleFullscreen();
                gameStatusLabel.setText("F11");
            }
        });

        // Reindirizza System.out e System.err alla JTextArea
        PrintStream printStream = new PrintStream(new CustomOutputStream(terminal));
        System.setOut(printStream);
        System.setErr(printStream);

        frame.setVisible(true);
        toggleFullscreen();
    }

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

    private void writeToTerminal(String message) {
        StyledDocument doc = terminal.getStyledDocument(); // Ottieni il documento stile associato al JTextPane
        SimpleAttributeSet paraSet = new SimpleAttributeSet();
        StyleConstants.setLineSpacing(paraSet, -0.3f); // Imposta l'interlinea a un valore negativo per ridurre lo spazio tra le righe
    
        try {
            doc.insertString(doc.getLength(), message, null); // Inserisci il messaggio seguito da un salto di riga nel documento
            terminal.setCaretPosition(doc.getLength()); // Imposta la posizione del cursore alla fine del documento
        } catch (BadLocationException e) {
            e.printStackTrace(); // Gestione dell'eccezione BadLocationException
        }
    }
    

    public void mouseDragged(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
        PointerInfo a = MouseInfo.getPointerInfo();
        Point b = a.getLocation();
        x = (int) b.getX();
        y = (int) b.getY();
    }

    public void mouseClicked(MouseEvent e) {
        System.out.println("You have CLICKED the frame");
    }

    public void mouseEntered(MouseEvent e) {
        System.out.println("The mouse has ENTERED the frame");
    }

    public void mouseExited(MouseEvent e) {
        System.out.println("The mouse has EXITED the frame");
    }

    public void mousePressed(MouseEvent e) {
        System.out.println("You have PRESSED the mouse");
    }

    public void mouseReleased(MouseEvent e) {
        System.out.println("You have RELEASED the mouse");
    }
}
