import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.*;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.Point;

public class GameFrame
{
	public GameFrame()
	{
		//creating frame
		JFrame frame = new JFrame("TextAdventure");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.setSize(500, 500);

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

		/*
		while(true){
            PointerInfo a = MouseInfo.getPointerInfo();
            Point b = a.getLocation();
            int x = (int) b.getX();
            int y = (int) b.getY();

            System.out.println(x);
            System.out.println(y);
        }
		*/

		//ciao test
	}
}