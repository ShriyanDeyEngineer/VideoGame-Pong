package PONG;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameOverPanel extends JPanel
{
	JLabel gameOver;
	JLabel displaySongName;
	JLabel displayVictor;
	JButton playAgainButton;
	JButton mainMenuButton;
	
	public GameOverPanel()
	{
		setBackground(Color.BLACK);
		
		setLayout(null);
		
		gameOver = new JLabel("GAME OVER");
        gameOver.setForeground(Color.WHITE);
        gameOver.setFont(new Font("SansSerif", Font.BOLD + Font.ITALIC, 50));
        gameOver.setSize(400, 80);
        
        playAgainButton = new JButton("PLAY AGAIN");
        playAgainButton.setBackground(Color.WHITE);
        playAgainButton.setFont(new Font("SansSerif", Font.BOLD, 30));
        playAgainButton.setSize(200, 60);
        
        mainMenuButton = new JButton("MAIN MENU");
        mainMenuButton.setBackground(Color.WHITE);
        mainMenuButton.setFont(new Font("SansSerif", Font.BOLD, 30));
        mainMenuButton.setSize(200, 60);
        
        displaySongName = new JLabel("Currently playing: ");
		displaySongName.setForeground(Color.WHITE);
		displaySongName.setFont(new Font("SansSerif", Font.PLAIN, 20));
		displaySongName.setSize(700, 50);
		
		displayVictor = new JLabel("WINNER: ");
		displayVictor.setForeground(Color.WHITE);
		displayVictor.setFont(new Font("SansSerif", Font.BOLD, 30));
		displayVictor.setSize(500, 50);
		
        add(gameOver);
        add(playAgainButton);
        add(mainMenuButton);
        add(displaySongName);
        add(displayVictor);
        displayVictor.setVisible(false);
		
		setFocusable(true);
		requestFocusInWindow(); //VERY IMPORTANT - Gives the game panel the ability to capture user input
		
		
		//This listener ensures dynamic re-centering (resets panel objects on the screen when going full screen mode or exiting it)
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				placeGameOverLabel();
				placePlayAgainButton();
				placeMainMenuButton();
				displaySongName.setLocation(20, getHeight() - displaySongName.getHeight() - 20);
				displayVictor.setLocation((getWidth() - displayVictor.getWidth()) / 2 + getWidth() / 13, -getHeight() / 100 + getHeight() / 5);
			}
		});
	}
	
	
	
	@Override
	public void addNotify()
	{
		super.addNotify();
		SwingUtilities.invokeLater(() -> placeGameOverLabel());
		SwingUtilities.invokeLater(() -> placePlayAgainButton());
		SwingUtilities.invokeLater(() -> placeMainMenuButton());
		SwingUtilities.invokeLater(() -> displaySongName.setLocation(20, getHeight() - displaySongName.getHeight() - 20));
		SwingUtilities.invokeLater(() -> displayVictor.setLocation((getWidth() - displayVictor.getWidth()) / 2 + getWidth() / 13, -getHeight() / 100 + getHeight() / 5));
	}
	
	
		
	public void placeGameOverLabel()
	{
		gameOver.setLocation((getWidth() - gameOver.getWidth()) / 2 + getWidth() / 15 - 50, -getHeight() / 100 + 30);
	}
	
	public void placePlayAgainButton()
	{
		playAgainButton.setLocation((getWidth() - playAgainButton.getWidth()) / 2, (getHeight() - playAgainButton.getHeight()) / 2 - 50);
	}
	
	public void placeMainMenuButton()
	{
		mainMenuButton.setLocation((getWidth() - mainMenuButton.getWidth()) / 2, (getHeight() - mainMenuButton.getHeight()) / 2 + 50);
	}
}
