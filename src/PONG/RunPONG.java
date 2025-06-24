package PONG;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RunPONG 
{
	//Panel names
    private static final String PONG_MENU = "PONG MENU";
    private static final String PONG_SETTINGS = "PONG SETTINGS";
    private static final String PONG_GAME = "PONG GAME";
    private static final String PONG_GAME_2 = "PONG GAME_2";
    private static final String PONG_CREDITS = "PONG CREDITS";
    private static final String PONG_GAME_OVER = "PONG GAME OVER";
    
    static Timer delayTimer;
    static boolean gameIsRunning = false;
    static Timer trackPlayerLives;
    static Timer trackScoreBoard;
    static Timer rotateSoundTracks;
    static int selectSoundTrack = (int)(5 * Math.random());
    static boolean soundTrackIsMuted = false;

	public static void main(String[] args) 
	{
		//Creates the main frame that will hold the cardPanel that holds all the necessary panels for this game to function
		MainFrame mainFrame = new MainFrame();
		mainFrame.setTitle("PONG.PLAY");
		
		//Object to reference and play sound tracks
	  	Sounds GameSoundTrack = new Sounds();
		
	  	GameSoundTrack.setSoundTrack_File(selectSoundTrack);
	  	
		GameSoundTrack.PlaySoundTrack(selectSoundTrack); //Important - Method that begins playing a random sound track when the player boots up the game
		
		//Creates the CardLayout container
		CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout); //This panel holds all the cards/panels
        
        
        
        //Code (It's in each panel's constructor method) for setting up all the panels/cards for this PONG game
        //-------------------------------------------------------------------------------------------------------------------------------------//
        //---------------------------------------Code for the menu panel/screen/card---------------------------------------//
        MenuPanel menuPanel = new MenuPanel();
        menuPanel.displaySongName.setText("Currently playing: " + GameSoundTrack.SoundTracks.get(selectSoundTrack));
        //---------------------------------------Code for the settings panel/screen/card---------------------------------------//
        SettingsPanel settingsPanel = new SettingsPanel();
        settingsPanel.displaySongName.setText("Currently playing: " + GameSoundTrack.SoundTracks.get(selectSoundTrack));
        //---------------------------------------Code for the game panel (1 Player)/screen/card---------------------------------------//
        GamePanel gamePanel = new GamePanel();
        gamePanel.displaySongName.setText("Currently playing: " + GameSoundTrack.SoundTracks.get(selectSoundTrack));
        //---------------------------------------Code for the game panel (2 Players)/screen/card---------------------------------------//
        GamePanel2P gamePanel2 = new GamePanel2P();
        gamePanel2.displaySongName.setText("Currently playing: " + GameSoundTrack.SoundTracks.get(selectSoundTrack));
        //---------------------------------------Code for the game panel (2 Players)/screen/card---------------------------------------//
        CreditsReflectionsPanel creditsPanel = new CreditsReflectionsPanel();
        //---------------------------------------Code for the game over/play again panel/screen/card---------------------------------------//
        GameOverPanel gameOverPanel = new GameOverPanel();
        gameOverPanel.displaySongName.setText("Currently playing: " + GameSoundTrack.SoundTracks.get(selectSoundTrack));
        //-------------------------------------------------------------------------------------------------------------------------------------//
        
        
        
        //Code that adds the panels/cards to cardPanel/thing that holds all the panels
        cardPanel.add(menuPanel, PONG_MENU);
        cardPanel.add(settingsPanel, PONG_SETTINGS);
        cardPanel.add(gamePanel, PONG_GAME);
        cardPanel.add(gamePanel2, PONG_GAME_2);
        cardPanel.add(creditsPanel, PONG_CREDITS);
        cardPanel.add(gameOverPanel, PONG_GAME_OVER);
        
        //-----------------------Code for things that constantly run in the background-----------------------//
        //Sets up a timer that runs in the background to keep track of the number of lives a player has in a game. If the player's lives = 0, the timer will trigger an event that shows the game over screen and options to go back to main menu or play again
        //If play again or main menu is clicked, the number of lives the player has will be reset back to its original amount for the next game the player may want to play
        trackPlayerLives = new Timer(1, new ActionListener() { //Checks player lives every millisecond to see whether or not to end the game and display the game over panel
        	@Override
            public void actionPerformed(ActionEvent e) { 
                if(gamePanel.playerLives == 0 && gameIsRunning == true && gamePanel2.gameFor2P_Selected == false)
                {
                	gameIsRunning = false;
                	cardLayout.show(cardPanel, PONG_GAME_OVER);
                	gameOverPanel.add(gamePanel.score).setLocation(mainFrame.getWidth() - gamePanel.score.getWidth() - 30, mainFrame.getHeight() - gamePanel.score.getHeight() - 50);
                	gameOverPanel.requestFocusInWindow(); //VERY IMPORTANT - Gives the game panel the ability to capture user input
                }
            }
        });
        trackPlayerLives.setRepeats(true); //Makes sure to keep the timer going
        trackPlayerLives.start();
        
        
        //Sets up a timer that runs in the background to keep track of the score in the 2 player player game mode and if one of the two players gets to 3 points first, the game ends and the game over screen is displayed with who won
        trackScoreBoard = new Timer(1, new ActionListener() { //Checks score board every millisecond to see whether or not to end the game and display the game over panel
        	@Override
            public void actionPerformed(ActionEvent e) { 
                if((gamePanel2.scoreP1 == 3 || gamePanel2.scoreP2 == 3) && gameIsRunning == true && gamePanel2.gameFor2P_Selected)
                {
                	gameIsRunning = false;
                	
                	if(gamePanel2.scoreP1 == 3)
                		gameOverPanel.displayVictor.setText("WINNER: PLAYER 1");
                	else gameOverPanel.displayVictor.setText("WINNER: PLAYER 2");
                	
                	gameOverPanel.displayVictor.setVisible(true);
                	
                	cardLayout.show(cardPanel, PONG_GAME_OVER);
                	gameOverPanel.requestFocusInWindow(); //VERY IMPORTANT - Gives the game panel the ability to capture user input
                }
            }
        });
        trackScoreBoard.setRepeats(true); //Makes sure to keep the timer going
        trackScoreBoard.start();
        
        
        //Sets up a timer that runs in the background to rotate and play sound tracks by selecting them from a list
        //If there is no available sound track in the next element of the list or the list's last element has been reached, the sound track in the beginning of the list will be played
        rotateSoundTracks = new Timer(5000, new ActionListener() { //Checks whether or not to rotate the sound track every 5 seconds
        	@Override
            public void actionPerformed(ActionEvent e) { 
        		if(GameSoundTrack.clip.getMicrosecondPosition() == GameSoundTrack.clip.getMicrosecondLength()) //Checks if the currently playing sound track has reached its end yet and gives the go ahead to switch songs if that is the case
        			GameSoundTrack.canSwitchSongs = true;
        		
        		if(GameSoundTrack.canSwitchSongs && GameSoundTrack.checkIfSoundTrackIsRunning() == false && GameSoundTrack.SoundTrack_URLs[selectSoundTrack + 1] != null)
        		{
        			GameSoundTrack.canSwitchSongs = false;
        			GameSoundTrack.closeSoundTrack(); //Releases system resources
        			selectSoundTrack += 1;
        			GameSoundTrack.PlaySoundTrack(selectSoundTrack);
        			gamePanel.displaySongName.setText("Currently playing: " + GameSoundTrack.SoundTracks.get(selectSoundTrack));
        			gamePanel2.displaySongName.setText("Currently playing: " + GameSoundTrack.SoundTracks.get(selectSoundTrack));
        			menuPanel.displaySongName.setText("Currently playing: " + GameSoundTrack.SoundTracks.get(selectSoundTrack));
        			settingsPanel.displaySongName.setText("Currently playing: " + GameSoundTrack.SoundTracks.get(selectSoundTrack));
        			gameOverPanel.displaySongName.setText("Currently playing: " + GameSoundTrack.SoundTracks.get(selectSoundTrack));
        		}
        		
        		else if(GameSoundTrack.canSwitchSongs && GameSoundTrack.checkIfSoundTrackIsRunning() == false && GameSoundTrack.SoundTrack_URLs[selectSoundTrack + 1] == null)
        		{
        			GameSoundTrack.canSwitchSongs = false;
        			GameSoundTrack.closeSoundTrack(); //Releases system resources
        			selectSoundTrack = 0;
        			GameSoundTrack.PlaySoundTrack(selectSoundTrack);
        			gamePanel.displaySongName.setText("Currently playing: " + GameSoundTrack.SoundTracks.get(selectSoundTrack));
        			gamePanel2.displaySongName.setText("Currently playing: " + GameSoundTrack.SoundTracks.get(selectSoundTrack));
        			menuPanel.displaySongName.setText("Currently playing: " + GameSoundTrack.SoundTracks.get(selectSoundTrack));
        			settingsPanel.displaySongName.setText("Currently playing: " + GameSoundTrack.SoundTracks.get(selectSoundTrack));
        			gameOverPanel.displaySongName.setText("Currently playing: " + GameSoundTrack.SoundTracks.get(selectSoundTrack));
        		}	
            }
        });
        rotateSoundTracks.setRepeats(true); //Makes sure to keep the timer going
        rotateSoundTracks.start();
        //--------------------------------------------------------------------------------------------------//
   
        
        //Code for adding functionality to the buttons that navigate between each panel
        
        //----------Play Button (Part of the Menu Panel)----------//
        menuPanel.playButton.addActionListener(e -> {
        	gameIsRunning = true;
        	gameOverPanel.remove(gamePanel.score);
        
            if(delayTimer != null && delayTimer.isRunning()) { //Cancels any running delayTimer which helps avoid any logic errors with timers
            	delayTimer.stop(); //Cancels any running delayTimer
            	delayTimer = null;
            }
            
            if(gamePanel2.gameFor2P_Selected == false)
        	{
        		gamePanel.resetGame();
        		cardLayout.show(cardPanel, PONG_GAME);
        		gamePanel.requestFocusInWindow(); //VERY IMPORTANT - Gives the game panel the ability to capture user input
        	}
            
        	else if(gamePanel2.gameFor2P_Selected)
        	{
        		gamePanel2.resetGame();
        		cardLayout.show(cardPanel, PONG_GAME_2);
        		gamePanel2.requestFocusInWindow(); //VERY IMPORTANT - Gives the game panel the ability to capture user input
        	}
            
            if (delayTimer != null) 
            {
                delayTimer.stop();
                delayTimer = null;
            }
            
            delayTimer = new Timer(5000, new ActionListener() { //Delays the start of the game after the play button is clicked by 5 seconds to give the player time to get ready
            	@Override
                public void actionPerformed(ActionEvent e) { 
            		if(gamePanel2.gameFor2P_Selected == false)
            		{
            			gamePanel.startGame();
            		}
            		else if(gamePanel2.gameFor2P_Selected)
            		{
            			gamePanel2.startGame();
            		}
                }
            });
            delayTimer.setRepeats(false); //Makes sure the delay timer only runs once 
            delayTimer.start();
        });
        
        
        //----------Settings Button (Part of the Menu Panel)----------//
        menuPanel.settingsButton.addActionListener(e -> {
        	cardLayout.show(cardPanel, PONG_SETTINGS);
        	creditsPanel.requestFocusInWindow(); //VERY IMPORTANT - Gives the game panel the ability to capture user input
        });
        
        
        //----------Credits Button (Part of the Menu Panel)----------//
        menuPanel.creditsButton.addActionListener(e -> {
        	cardLayout.show(cardPanel, PONG_CREDITS);
        	menuPanel.requestFocusInWindow(); //VERY IMPORTANT - Gives the game panel the ability to capture user input
        });
        
        
        //----------Pause Button (Part of the Game Panel)----------//
        gamePanel.pauseButton.addActionListener(e -> {
        	gamePanel.canStartTimer = false;
        	gamePanel.timer.stop();
        	gamePanel.pauseButton.setVisible(false);
        	gamePanel.gamePausedLabel.setVisible(true);
        	gamePanel.resumeButton.setVisible(true);
        	gamePanel.settingsButton.setVisible(true);
        	gamePanel.toMainMenuButton.setVisible(true);
        });
        
        
        //----------Resume Button (Part of the Game Panel)----------//
        gamePanel.resumeButton.addActionListener(e -> {
        	gamePanel.canStartTimer = true;
        	gamePanel.timer.start();
        	gamePanel.gamePausedLabel.setVisible(false);
        	gamePanel.resumeButton.setVisible(false);
        	gamePanel.settingsButton.setVisible(false);
        	gamePanel.toMainMenuButton.setVisible(false);
        	gamePanel.pauseButton.setVisible(true);
        });
        
        
        //----------Settings Button (Part of the Game Panel)----------//
        gamePanel.settingsButton.addActionListener(e -> {
        	cardLayout.show(cardPanel, PONG_SETTINGS);
        	settingsPanel.requestFocusInWindow(); //VERY IMPORTANT - Gives the game panel the ability to capture user input
        	settingsPanel.leaveSettingsButton.setVisible(false);
        	settingsPanel.toggleGameModeButton.setVisible(false);
        	settingsPanel.backToGameButton.setVisible(true);
        });
        
        
        //----------To Main Menu Button (Part of the Game Panel)----------//
        gamePanel.toMainMenuButton.addActionListener(e -> {
        	gamePanel2.gameFor2P_Selected = false;
        	gamePanel.gamePausedLabel.setVisible(false);
        	gamePanel.resumeButton.setVisible(false);
        	gamePanel.settingsButton.setVisible(false);
        	gamePanel.toMainMenuButton.setVisible(false);
        	gamePanel.pauseButton.setVisible(true);
        	cardLayout.show(cardPanel, PONG_MENU);
        	menuPanel.requestFocusInWindow(); //VERY IMPORTANT - Gives the game panel the ability to capture user input
        	gamePanel.resetGame();
        });
        
        
      //----------Pause Button (Part of the Game Panel for 2 Players)----------//
        gamePanel2.pauseButton.addActionListener(e -> {
        	gamePanel2.canStartTimer = false;
        	gamePanel2.timer.stop();
        	gamePanel2.pauseButton.setVisible(false);
        	gamePanel2.gamePausedLabel.setVisible(true);
        	gamePanel2.resumeButton.setVisible(true);
        	gamePanel2.settingsButton.setVisible(true);
        	gamePanel2.toMainMenuButton.setVisible(true);
        });
        
        
        //----------Resume Button (Part of the Game Panel for 2 Players)----------//
        gamePanel2.resumeButton.addActionListener(e -> {
        	gamePanel2.canStartTimer = true;
        	gamePanel2.timer.start();
        	gamePanel2.gamePausedLabel.setVisible(false);
        	gamePanel2.resumeButton.setVisible(false);
        	gamePanel2.settingsButton.setVisible(false);
        	gamePanel2.toMainMenuButton.setVisible(false);
        	gamePanel2.pauseButton.setVisible(true);
        });
        
        
        //----------Settings Button (Part of the Game Panel for 2 Players)----------//
        gamePanel2.settingsButton.addActionListener(e -> {
        	cardLayout.show(cardPanel, PONG_SETTINGS);
        	settingsPanel.requestFocusInWindow(); //VERY IMPORTANT - Gives the game panel the ability to capture user input
        	settingsPanel.leaveSettingsButton.setVisible(false);
        	settingsPanel.toggleGameModeButton.setVisible(false);
        	settingsPanel.backToGameButton.setVisible(true);
        });
        
        
        //----------To Main Menu Button (Part of the Game Panel for 2 Players)----------//
        gamePanel2.toMainMenuButton.addActionListener(e -> {
        	gamePanel2.gamePausedLabel.setVisible(false);
        	gamePanel2.resumeButton.setVisible(false);
        	gamePanel2.settingsButton.setVisible(false);
        	gamePanel2.toMainMenuButton.setVisible(false);
        	gamePanel2.pauseButton.setVisible(true);
        	cardLayout.show(cardPanel, PONG_MENU);
        	menuPanel.requestFocusInWindow(); //VERY IMPORTANT - Gives the game panel the ability to capture user input
        	gamePanel2.resetGame();
        });
        
        
        //----------Back To Main Menu Button (Part of the Credits Panel)----------//
        creditsPanel.backToMainMenuButton.addActionListener(e -> {
        	cardLayout.show(cardPanel, PONG_MENU);
        	menuPanel.requestFocusInWindow(); //VERY IMPORTANT - Gives the game panel the ability to capture user input
        });
        
        
        //----------Leave Settings Button (Part of the Settings Panel)----------//
        settingsPanel.leaveSettingsButton.addActionListener(e -> {
        	cardLayout.show(cardPanel, PONG_MENU);
        	menuPanel.requestFocusInWindow(); //VERY IMPORTANT - Gives the game panel the ability to capture user input
        });
        
        
        //----------Back To Game Button (Part of the Settings Panel)----------//
        settingsPanel.backToGameButton.addActionListener(e -> {
        	if(gamePanel2.gameFor2P_Selected)
        	{
        		cardLayout.show(cardPanel, PONG_GAME_2);
            	gamePanel2.requestFocusInWindow(); //VERY IMPORTANT - Gives the game panel the ability to capture user input
        	}
        	else
        	{
        		cardLayout.show(cardPanel, PONG_GAME);
            	gamePanel.requestFocusInWindow(); //VERY IMPORTANT - Gives the game panel the ability to capture user input
        	}
        	settingsPanel.backToGameButton.setVisible(false);
        	settingsPanel.leaveSettingsButton.setVisible(true);
        	settingsPanel.toggleGameModeButton.setVisible(true);
        });
        
        
        //----------Mute Control Button (Part of the Settings Panel)----------//
        settingsPanel.muteControlButton.addActionListener(e -> {
        	if(soundTrackIsMuted)
        	{
        		GameSoundTrack.play();
        		soundTrackIsMuted = false;	
        	}
        	else
        	{
        		GameSoundTrack.stop();
        		soundTrackIsMuted = true;	
        	}
        });
        
        
        //----------Toggle Game Mode Button (Part of the Settings Panel)----------//
        settingsPanel.toggleGameModeButton.addActionListener(e -> {
        	if(gamePanel2.gameFor2P_Selected)
        	{
        		gamePanel2.gameFor2P_Selected = false;
        		settingsPanel.displayGameModeSelected.setText("Selected Game Mode: SINGLE PLAYER");
        	}
        	else
        	{
        		gamePanel2.gameFor2P_Selected = true;
        		settingsPanel.displayGameModeSelected.setText("Selected Game Mode: MULTIPLAYER");
        	}
        });
        
        
        //----------Switch Sound Track Button (Part of the Settings Panel)----------//
        settingsPanel.switchSoundTrackButton.addActionListener(e -> {
        	if(GameSoundTrack.SoundTrack_URLs[selectSoundTrack + 1] != null)
    		{
    			GameSoundTrack.closeSoundTrack(); //Releases system resources
    			selectSoundTrack += 1;
    			GameSoundTrack.PlaySoundTrack(selectSoundTrack);
    			gamePanel.displaySongName.setText("Currently playing: " + GameSoundTrack.SoundTracks.get(selectSoundTrack));
    			gamePanel2.displaySongName.setText("Currently playing: " + GameSoundTrack.SoundTracks.get(selectSoundTrack));
    			menuPanel.displaySongName.setText("Currently playing: " + GameSoundTrack.SoundTracks.get(selectSoundTrack));
    			settingsPanel.displaySongName.setText("Currently playing: " + GameSoundTrack.SoundTracks.get(selectSoundTrack));
    			gameOverPanel.displaySongName.setText("Currently playing: " + GameSoundTrack.SoundTracks.get(selectSoundTrack));
    		}
    		else
    		{
    			GameSoundTrack.closeSoundTrack(); //Releases system resources
    			selectSoundTrack = 0;
    			GameSoundTrack.PlaySoundTrack(selectSoundTrack);
    			gamePanel.displaySongName.setText("Currently playing: " + GameSoundTrack.SoundTracks.get(selectSoundTrack));
    			gamePanel2.displaySongName.setText("Currently playing: " + GameSoundTrack.SoundTracks.get(selectSoundTrack));
    			menuPanel.displaySongName.setText("Currently playing: " + GameSoundTrack.SoundTracks.get(selectSoundTrack));
    			settingsPanel.displaySongName.setText("Currently playing: " + GameSoundTrack.SoundTracks.get(selectSoundTrack));
    			gameOverPanel.displaySongName.setText("Currently playing: " + GameSoundTrack.SoundTracks.get(selectSoundTrack));
    		}
        });
        
        
        //----------Play Again Button (Part of the Game Over Panel)----------//
        gameOverPanel.playAgainButton.addActionListener(e -> {
        	gameIsRunning = true;
        	gameOverPanel.displayVictor.setVisible(false);
        	gameOverPanel.remove(gamePanel.score);
        	
        	if(delayTimer != null && delayTimer.isRunning()) //Cancels any running delayTimer which helps avoid any logic errors with timers
        	{ 
        		delayTimer.stop(); 
        		delayTimer = null;
        	}
        	
        	if(gamePanel2.gameFor2P_Selected == false)
        	{
        		gamePanel.resetGame();
        		cardLayout.show(cardPanel, PONG_GAME);
        		gamePanel.requestFocusInWindow(); //VERY IMPORTANT - Gives the game panel the ability to capture user input
        	}
        	else if(gamePanel2.gameFor2P_Selected)
        	{
        		gamePanel2.resetGame();
        		cardLayout.show(cardPanel, PONG_GAME_2);
        		gamePanel2.requestFocusInWindow(); //VERY IMPORTANT - Gives the game panel the ability to capture user input
        	}
        	
        	if (delayTimer != null) 
        	{
        	    delayTimer.stop();
        	    delayTimer = null;
        	}
        	
        	delayTimer = new Timer(5000, new ActionListener() { //Delays the start of the game after the play button is clicked by 5 seconds to give the player time to get ready
            	@Override
                public void actionPerformed(ActionEvent e) { 
            		if(gamePanel2.gameFor2P_Selected == false)
            		{
            			gamePanel.startGame();
            		}
            		else if(gamePanel2.gameFor2P_Selected)
            		{
            			gamePanel2.startGame();
            		}
                }
            });
            delayTimer.setRepeats(false); //Makes sure the delay timer only runs once 
            delayTimer.start();
        });
        
        
        //----------Main Menu Button (Part of the Game Over Panel)----------//
        gameOverPanel.mainMenuButton.addActionListener(e -> {
        	gameOverPanel.displayVictor.setVisible(false);
        	cardLayout.show(cardPanel, PONG_MENU);
        	menuPanel.requestFocusInWindow(); //VERY IMPORTANT - Gives the game panel the ability to capture user input
        });
     
        
        //This adds the cardPanel, which holds all the other panels to the main frame
        mainFrame.add(cardPanel);
        mainFrame.setVisible(true);
	}
	
}
