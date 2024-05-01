import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.*;

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
		bottom.setPreferredSize(new Dimension(100, 55));
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

		frame.add(center, BorderLayout.CENTER);
		frame.add(bottom, BorderLayout.SOUTH);
		frame.setVisible(true);
	}
}