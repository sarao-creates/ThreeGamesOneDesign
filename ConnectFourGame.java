package a7;

import java.awt.BorderLayout;
import javax.swing.*;

public class ConnectFourGame {
	
	public static void main(String[] args)
	{
		JFrame main_frame = new JFrame();
		main_frame.setTitle("Connect Four");
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel top_panel = new JPanel();
		top_panel.setLayout(new BorderLayout());
		main_frame.setContentPane(top_panel);
		
		ConnectFourWidget widget = new ConnectFourWidget();
		top_panel.add(widget, BorderLayout.CENTER);
		
		main_frame.pack();
		main_frame.setVisible(true);
	}
	
}
