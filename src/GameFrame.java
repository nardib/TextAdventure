import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameFrame implements MouseMotionListener, MouseListener
{
	//we should define a specific visibility for these two variables
	int x=0;
	int y=0;
	public GameFrame()
	{
		//creating frame
		JFrame frame = new JFrame("TextAdventure");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.setLocationRelativeTo(null);
		frame.setSize(500, 500);
		frame.addMouseMotionListener(this);
		frame.addMouseListener(this);

		//creating panel and label for the game graphic
		JPanel center = new JPanel();
		center.setBackground(new Color(245, 243, 200));
		JLabel graphic = new JLabel();
		graphic.setFont(new Font("Times New Roman", Font.PLAIN, 32));
		center.add(graphic);

		//creating a panel and two buttons for the bottom 
		JPanel bottom = new JPanel();
		bottom.setBackground(new Color(105, 147, 180));
		bottom.setPreferredSize(new Dimension(55, 55));
		JButton startButton = new JButton("Start");
		startButton.setFont(new Font("Times New Roman", Font.PLAIN, 32));
		startButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				graphic.setText("GIOCO INIZIATO");
			}
		});
		JButton pauseButton = new JButton("Pause");
		pauseButton.setFont(new Font("Times New Roman", Font.PLAIN, 32));
		pauseButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				graphic.setText("GIOCO IN PAUSA");
			}
		});
		JButton resetButton = new JButton("Reset");
		resetButton.setFont(new Font("Times New Roman", Font.PLAIN, 32));
		resetButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				graphic.setText("");
			}
		});
		bottom.add(startButton);
		bottom.add(pauseButton);
		bottom.add(resetButton);

		//left panel with it's own buttons
		JPanel left = new JPanel();
		left.setPreferredSize(new Dimension(100, 100));
		JButton attackButton = new JButton("Attack");
		attackButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
		attackButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				graphic.setText(frame.getHeight() + " " + frame.getWidth());
			}
		});
		left.add(attackButton);

		frame.add(center, BorderLayout.CENTER);
		frame.add(bottom, BorderLayout.SOUTH);
		frame.add(left, BorderLayout.WEST);

		frame.addComponentListener(new ComponentAdapter() 
		{
            public void componentResized(ComponentEvent componentEvent) 
			{
                int h = frame.getHeight();
				int w = frame.getWidth();
				double grapSize = w/25;
				graphic.setFont(new Font("Times New Roman", Font.PLAIN, (int)grapSize));

				double butSize = w/25;
				startButton.setFont(new Font("Times New Roman", Font.PLAIN, (int)butSize));
				pauseButton.setFont(new Font("Times New Roman", Font.PLAIN, (int)butSize));
				resetButton.setFont(new Font("Times New Roman", Font.PLAIN, (int)butSize));
				attackButton.setFont(new Font("Times New Roman", Font.PLAIN, (int)butSize));

				double leftPaneSize = w/5;
				double bottomPaneSize = h/5;
				left.setPreferredSize(new Dimension((int)leftPaneSize, (int)leftPaneSize));
				bottom.setPreferredSize(new Dimension((int)bottomPaneSize, (int)bottomPaneSize));
            }
        });

        frame.setVisible(true);
	}
	public void mouseDragged(MouseEvent e) 
	{
		//System.out.println("You have DRAGGED the mouse");
	}
	 
	public void mouseMoved(MouseEvent e) 
	{
		//System.out.println("You have MOVED the mouse");
		PointerInfo a = MouseInfo.getPointerInfo();
    	Point b = a.getLocation();
    	x = (int) b.getX();
    	y = (int) b.getY();
	}

	public void mouseClicked(MouseEvent e)
	{
		System.out.println("You have CLICKED the frame");
	}

	public void mouseEntered(MouseEvent e)
	{
		System.out.println("The mouse has ENTERED the frame");
	}

	public void mouseExited(MouseEvent e)
	{
		System.out.println("The mouse has EXITED the frame");
	}

	public void mousePressed(MouseEvent e)
	{
		System.out.println("You have PRESSED the mouse");
	}

	public void mouseReleased(MouseEvent e)
	{
		System.out.println("You have RELEASED the mouse");
	}
}