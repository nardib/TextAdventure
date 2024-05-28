import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameFrame implements MouseMotionListener, MouseListener {
    Game game = new Game("Player", "m", "Enemy", "f");
    int x = 0;
    int y = 0;
    int wallcount = 0;
    Image[] Images = new Image[36];
    boolean isFullscreen = false;
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    JFrame frame;
    JTextArea terminal;
    JPanel topPanel;
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
        center.setBackground(new Color(245, 243, 200));
        center.setLayout(new BorderLayout());

        JLabel graphic = new JLabel();
        graphic.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        graphic.setHorizontalAlignment(SwingConstants.CENTER);
        graphic.setVerticalAlignment(SwingConstants.CENTER);
        center.add(graphic, BorderLayout.CENTER);

        terminal = new JTextArea();
        terminal.setEditable(true);
        terminal.setLineWrap(true);
        terminal.setWrapStyleWord(true);
        terminal.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane terminalScrollPane = new JScrollPane(terminal);
        terminalScrollPane.setPreferredSize(new Dimension(frame.getWidth(), 150));
        center.add(terminalScrollPane, BorderLayout.SOUTH);

        frame.add(center, BorderLayout.CENTER);

        topPanel = new JPanel();
        topPanel.setBackground(new Color(0, 0, 0));
        topPanel.setLayout(new GridBagLayout());
        topPanel.setPreferredSize(new Dimension(0, 45));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gameStatusLabel = new JLabel();
        gameStatusLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        gameStatusLabel.setForeground(Color.WHITE);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.EAST;
        topPanel.add(gameStatusLabel, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(0, 0, 0));
        buttonPanel.setLayout(new FlowLayout());

        JButton startButton = new JButton("Start");
        startButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameStatusLabel.setText("GIOCO INIZIATO");
            }
        });
        buttonPanel.add(startButton);

        JButton pauseButton = new JButton("Pause");
        pauseButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        pauseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameStatusLabel.setText("GIOCO IN PAUSA");
            }
        });
        buttonPanel.add(pauseButton);

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
        buttonPanel.add(resetButton);

        JButton nextWallButton = new JButton("Turn Right");
        nextWallButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        nextWallButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                graphic.setIcon(new ImageIcon(Images[wallcount]));
				gameStatusLabel.setText("");
                wallcount++;
            }
        });
        buttonPanel.add(nextWallButton);

        JButton attackButton = new JButton("Attack");
        attackButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        attackButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                terminal.setText(frame.getHeight() + " " + frame.getWidth());
            }
        });
        buttonPanel.add(attackButton);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.CENTER;
        topPanel.add(buttonPanel, gbc);

        frame.add(topPanel, BorderLayout.PAGE_START);

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
        terminal.append(message + "\n");
        terminal.setCaretPosition(terminal.getDocument().getLength());
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
