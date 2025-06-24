package PONG;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuPanel extends JPanel
{
	JLabel gameTitle;
	JLabel displaySongName;
	JButton playButton;
	JButton settingsButton;
	JButton creditsButton;
	
	public MenuPanel()
	{	
		setBackground(Color.BLACK);
		
		setLayout(null);
		
		gameTitle = new JLabel("PONG");
		gameTitle.setForeground(Color.WHITE);
		gameTitle.setFont(new Font("SansSerif", Font.BOLD + Font.ITALIC, 70));
		gameTitle.setSize(400, 80);
		gameTitle.setLocation((getWidth() - gameTitle.getWidth()) / 2 + getWidth() / 15, -getHeight() / 100 + 30);
        
        playButton = new JButton("PLAY");
        playButton.setBackground(Color.WHITE);
        playButton.setFont(new Font("SansSerif", Font.BOLD, 30));
        playButton.setSize(200, 60);
        playButton.setLocation((getWidth() - playButton.getWidth()) / 2, (getHeight() - playButton.getHeight()) / 2 - 50);
        
        settingsButton = new JButton("SETTINGS");
        settingsButton.setBackground(Color.WHITE);
        settingsButton.setFont(new Font("SansSerif", Font.BOLD, 30));
        settingsButton.setSize(200, 60);
        settingsButton.setLocation((getWidth() - settingsButton.getWidth()) / 2, (getHeight() - settingsButton.getHeight()) / 2 + 50);
        
        displaySongName = new JLabel("Currently playing: ");
		displaySongName.setForeground(Color.WHITE);
		displaySongName.setFont(new Font("SansSerif", Font.PLAIN, 20));
		displaySongName.setSize(700, 50);
		
		creditsButton = new JButton("CREDITS");
		creditsButton.setBackground(Color.WHITE);
		creditsButton.setFont(new Font("SansSerif", Font.BOLD, 30));
		creditsButton.setSize(200, 60);
        
        add(gameTitle);
        add(playButton);
        add(settingsButton);
        add(displaySongName);
        add(creditsButton);
		
		setFocusable(true);
		requestFocusInWindow(); //VERY IMPORTANT - Gives the game panel the ability to capture user input
		
		//This listener ensures dynamic re-centering (resets the buttons and title positions on the screen when going full screen mode or exiting it)
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				placeGameTitle();
				placePlayButton();
				placeSettingsButton();
				placeCreditsButton();
				displaySongName.setLocation(20, getHeight() - displaySongName.getHeight() - 20);
			}
		});
	}
	
	
	
	@Override
	public void addNotify()
	{
		super.addNotify();
		SwingUtilities.invokeLater(() -> placeGameTitle());
		SwingUtilities.invokeLater(() -> placePlayButton());
		SwingUtilities.invokeLater(() -> placeSettingsButton());
		SwingUtilities.invokeLater(() -> placeCreditsButton());
		SwingUtilities.invokeLater(() -> displaySongName.setLocation(20, getHeight() - displaySongName.getHeight() - 20));
	}
	
	
		
	public void placeGameTitle()
	{
		gameTitle.setLocation((getWidth() - gameTitle.getWidth()) / 2 + getWidth() / 15, (getHeight() - gameTitle.getHeight()) / 2 - getHeight() / 3 - 50);
	}
	
	public void placePlayButton()
	{
		playButton.setLocation((getWidth() - playButton.getWidth()) / 2, (getHeight() - playButton.getHeight()) / 2 - 90);
	}
	
	public void placeSettingsButton()
	{
		settingsButton.setLocation((getWidth() - settingsButton.getWidth()) / 2, (getHeight() - settingsButton.getHeight()) / 2 + 10);
	}
	
	public void placeSongNameDisplay()
	{
		displaySongName.setLocation(20, getHeight() - displaySongName.getHeight() - 20);
	}
	
	public void placeCreditsButton()
	{
		creditsButton.setLocation((getWidth() - creditsButton.getWidth()) / 2, (getHeight() - settingsButton.getHeight()) / 2 + 110);
	}
}
