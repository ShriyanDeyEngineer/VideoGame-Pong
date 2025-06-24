package PONG;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SettingsPanel extends JPanel
{
	JLabel settingsLabel;
	JLabel displaySongName;
	JLabel displayGameModeSelected;
	JButton leaveSettingsButton;
	JButton muteControlButton;
	JButton switchSoundTrackButton;
	JButton backToGameButton;
	JButton toggleGameModeButton;
	
	public SettingsPanel()
	{
		setBackground(Color.BLACK);
		
		setLayout(null);
		
		settingsLabel = new JLabel("SETTINGS");
        settingsLabel.setForeground(Color.WHITE);
        settingsLabel.setFont(new Font("SansSerif", Font.BOLD + Font.ITALIC, 70));
        settingsLabel.setSize(400, 80);
        
        leaveSettingsButton = new JButton("EXIT");
        leaveSettingsButton.setBackground(Color.WHITE);
        leaveSettingsButton.setFont(new Font("SansSerif", Font.BOLD, 30));
        leaveSettingsButton.setSize(450, 60);
        
        displaySongName = new JLabel("Currently playing: ");
		displaySongName.setForeground(Color.WHITE);
		displaySongName.setFont(new Font("SansSerif", Font.PLAIN, 20));
		displaySongName.setSize(700, 50);
		
		displayGameModeSelected = new JLabel("Selected Game Mode: SINGLE PLAYER");
		displayGameModeSelected.setForeground(Color.WHITE);
		displayGameModeSelected.setFont(new Font("SansSerif", Font.PLAIN, 20));
		displayGameModeSelected.setSize(400, 50);
		
		muteControlButton = new JButton("MUTE/UNMUTE MUSIC");
		muteControlButton.setBackground(Color.WHITE);
		muteControlButton.setFont(new Font("SansSerif", Font.BOLD, 30));
		muteControlButton.setSize(450, 60);
		
		switchSoundTrackButton = new JButton("SWITCH SOUND TRACK");
		switchSoundTrackButton.setBackground(Color.WHITE);
		switchSoundTrackButton.setFont(new Font("SansSerif", Font.BOLD, 30));
		switchSoundTrackButton.setSize(450, 60);
		
		backToGameButton = new JButton("BACK TO GAME");
		backToGameButton.setBackground(Color.WHITE);
		backToGameButton.setFont(new Font("SansSerif", Font.BOLD, 30));
		backToGameButton.setSize(450, 60);
		
		toggleGameModeButton = new JButton("TOGGLE 1PLAYER/2PLAYERS");
		toggleGameModeButton.setBackground(Color.WHITE);
		toggleGameModeButton.setFont(new Font("SansSerif", Font.BOLD, 25));
		toggleGameModeButton.setSize(450, 60);
		
        add(settingsLabel);
        add(leaveSettingsButton);
        add(displaySongName);
        add(muteControlButton);
        add(switchSoundTrackButton);
        add(backToGameButton);
        add(toggleGameModeButton);
        add(displayGameModeSelected);
        backToGameButton.setVisible(false);
		
		setFocusable(true);
		requestFocusInWindow(); //VERY IMPORTANT - Gives the game panel the ability to capture user input
		
		
		//This listener ensures dynamic re-centering (resets panel objects on the screen when going full screen mode or exiting it)
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				placeSettingsLabel();
				placeLeaveSettingsButton();
				displaySongName.setLocation(20, getHeight() - displaySongName.getHeight() - 20);
				placeMuteMusicButton();
				placeSwitchSoundTrackButton();
				placeBackToGameButton();
				placeToggleGameModeButton();
				placeDisplayGameModeSelectedLabel();
			}
		});
	}
	
	
	
	@Override
	public void addNotify()
	{
		super.addNotify();
		SwingUtilities.invokeLater(() -> placeSettingsLabel());
		SwingUtilities.invokeLater(() -> placeLeaveSettingsButton());
		SwingUtilities.invokeLater(() -> displaySongName.setLocation(20, getHeight() - displaySongName.getHeight() - 20));
		SwingUtilities.invokeLater(() -> placeMuteMusicButton());
		SwingUtilities.invokeLater(() -> placeSwitchSoundTrackButton());
		SwingUtilities.invokeLater(() -> placeBackToGameButton());
		SwingUtilities.invokeLater(() -> placeToggleGameModeButton());
		SwingUtilities.invokeLater(() ->placeDisplayGameModeSelectedLabel());
	}
	
	
		
	public void placeSettingsLabel()
	{
		settingsLabel.setLocation((getWidth() - settingsLabel.getWidth()) / 2 + getWidth() / 50, (getHeight() - settingsLabel.getHeight()) / 2 - getHeight() / 3 - 50);
	}
	
	public void placeLeaveSettingsButton()
	{
		leaveSettingsButton.setLocation((getWidth() - leaveSettingsButton.getWidth()) / 2, (getHeight() - leaveSettingsButton.getHeight()) / 2 + 130);
	}
	
	public void placeMuteMusicButton()
	{
		muteControlButton.setLocation((getWidth() - muteControlButton.getWidth()) / 2, (getHeight() - muteControlButton.getHeight()) / 2 - 70);
	}
	
	public void placeSwitchSoundTrackButton()
	{
		switchSoundTrackButton.setLocation((getWidth() - switchSoundTrackButton.getWidth()) / 2, (getHeight() - switchSoundTrackButton.getHeight()) / 2 + 30);
	}
	
	public void placeBackToGameButton()
	{
		backToGameButton.setLocation((getWidth() - leaveSettingsButton.getWidth()) / 2, (getHeight() - leaveSettingsButton.getHeight()) / 2 + 130);
	}
	
	public void placeToggleGameModeButton()
	{
		toggleGameModeButton.setLocation((getWidth() - toggleGameModeButton.getWidth()) / 2, (getHeight() - toggleGameModeButton.getHeight()) / 2 - 170);
	}
	
	public void placeDisplayGameModeSelectedLabel()
	{
		displayGameModeSelected.setLocation((getWidth() - displayGameModeSelected.getWidth()), getHeight() - displayGameModeSelected.getHeight() - 20);
	}
}
