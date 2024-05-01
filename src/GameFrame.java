import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.BorderLayout;
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
		JLabel graphic = new JLabel();
		center.add(graphic);

		//creating a panel and two buttons for the bottom 
		JPanel bottom = new JPanel();
		JButton startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				graphic.setText("GIOCO INIZIATO");
			}
		});

		JButton stopButton = new JButton("Stop");
		stopButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				graphic.setText("GIOCO FINITO");
			}
		});
		bottom.add(startButton);
		bottom.add(stopButton);

		frame.add(center, BorderLayout.CENTER);
		frame.add(bottom, BorderLayout.SOUTH);
		frame.setVisible(true);
	}
}