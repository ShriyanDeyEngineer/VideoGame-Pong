package PONG;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame
{
	private MenuPanel menuPanel;
	
	public MainFrame() //Setting up a frame for the game panels which the game will use to operate
	{
		setTitle("PONG MAIN MENU");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width);
        int height = (int) (screenSize.height);
		setSize(width, height);
		setLocationRelativeTo(null);
	}
}
