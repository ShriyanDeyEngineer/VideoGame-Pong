package PONG;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

public class GamePanel extends JPanel implements ActionListener
{
	private Ball PongBall;
	private Paddle PongPaddle;
	Timer timer;
	
	private boolean rightPressed = false;
	private boolean leftPressed = false;
	
	boolean canStartTimer = true;
	
	int playerLives;
	private int hitScore;
	JLabel score;
	JLabel displaySongName;
	private int resetPlayerLives;
	private int resetPongBallSpeed_x;
	private int resetPongBallSpeed_y;
	private int resetPongPaddleSpeed;
	private boolean controlIncreaseGameSpeed;
	
	JLabel gamePausedLabel;
	JButton pauseButton;
	JButton resumeButton;
	JButton settingsButton;
	JButton toMainMenuButton;
	
	//Sound effects to be referenced
	Sounds BallHitsPaddleSFX = new Sounds();
	Sounds BallHitsWallSFX = new Sounds();
	Sounds BallGoesOutSFX = new Sounds();
	
	private ArrayList<Color> streakColors;
	private int rainbowColor = 0;
	
	public GamePanel() 
	{
		setBackground(Color.BLACK);
		
		setLayout(null);
		
		score = new JLabel("Score: 0");
        score.setForeground(Color.WHITE);
        score.setFont(new Font("SansSerif", Font.PLAIN, 50));
        score.setSize(350, 50);
        add(score);
        
        displaySongName = new JLabel("Currently playing: ");
		displaySongName.setForeground(Color.WHITE);
		displaySongName.setFont(new Font("SansSerif", Font.PLAIN, 20));
		displaySongName.setSize(700, 50);
		add(displaySongName);
		
		gamePausedLabel = new JLabel("GAME PAUSED");
		gamePausedLabel.setForeground(Color.WHITE);
		gamePausedLabel.setFont(new Font("SansSerif", Font.BOLD + Font.ITALIC, 70));
		gamePausedLabel.setSize(550, 80);
		add(gamePausedLabel);
		gamePausedLabel.setVisible(false);
		
		resumeButton = new JButton("RESUME");
		resumeButton.setBackground(Color.WHITE);
		resumeButton.setFont(new Font("SansSerif", Font.BOLD, 20));
		resumeButton.setSize(300, 40);
		add(resumeButton);
		resumeButton.setVisible(false);
		
		settingsButton = new JButton("SETTINGS");
		settingsButton.setBackground(Color.WHITE);
		settingsButton.setFont(new Font("SansSerif", Font.BOLD, 20));
		settingsButton.setSize(300, 40);
		add(settingsButton);
		settingsButton.setVisible(false);
		
		toMainMenuButton = new JButton("EXIT TO MENU");
		toMainMenuButton.setBackground(Color.WHITE);
		toMainMenuButton.setFont(new Font("SansSerif", Font.BOLD, 20));
		toMainMenuButton.setSize(300, 40);
		add(toMainMenuButton);
		toMainMenuButton.setVisible(false);
		
		pauseButton = new JButton("PAUSE");
		pauseButton.setBackground(Color.WHITE);
		pauseButton.setFont(new Font("SansSerif", Font.BOLD, 20));
		pauseButton.setSize(300, 40);
		add(pauseButton);
	
		setFocusable(true);
		requestFocusInWindow(); //VERY IMPORTANT - Gives the game panel the ability to capture user input
		
		playerLives = 3;
		hitScore = 0;
		resetPlayerLives = 3;
		resetPongBallSpeed_x = 2;
		resetPongBallSpeed_y = -2;
		resetPongPaddleSpeed = 7;
		controlIncreaseGameSpeed = false;
		
		streakColors = new ArrayList<>(Arrays.asList(Color.LIGHT_GRAY, Color.GRAY, Color.CYAN, Color.BLUE, Color.GREEN, Color.PINK, Color.MAGENTA, Color.YELLOW, Color.ORANGE, Color.RED));
		
		int direction = Math.random() < 0.5 ? -1 : 1; //Randomizes the ball velocity direction (uses shorthand notation for an if/else statement)
		PongBall = new Ball(10, direction * resetPongBallSpeed_x, resetPongBallSpeed_y); //Adds a ball to the game
		PongPaddle = new Paddle(180, 15, resetPongPaddleSpeed); //Adds a paddle to the game
		
		
		//The chunk of code encapsulated by the comment lines is vital to implementing smooth control of the paddle by utilizing
		//Press and release of key binds (flags) to remove the delay movement delay after the player holds down a key 
		//------------------------------------------------------------------------------------------------
		getInputMap().put(KeyStroke.getKeyStroke("pressed RIGHT"), "rightPressed"); //Basically sets up a key bind/trigger and gives it a name
		getActionMap().put("rightPressed", new AbstractAction() { //Basically gives the key bind a function/what to do when it is used by using the name given in a getInputMap method
			public void actionPerformed(ActionEvent e) {
				rightPressed = true;
			}
		});
		getInputMap().put(KeyStroke.getKeyStroke("released RIGHT"), "rightReleased"); //This line and the next 5 lines get the paddle to stop moving after the key is released
		getActionMap().put("rightReleased", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				rightPressed = false;
			}
		});
		
		getInputMap().put(KeyStroke.getKeyStroke("pressed LEFT"), "leftPressed");
		getActionMap().put("leftPressed", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				leftPressed = true;
			}
		});
		getInputMap().put(KeyStroke.getKeyStroke("released LEFT"), "leftReleased");
		getActionMap().put("leftReleased", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				leftPressed = false;
			}
		});
		//------------------------------------------------------------------------------------------------
		
		timer = new Timer(5, this); //Every 5 milliseconds, calls the actionPerformed method
		
		//Resets the ball, paddle, and other panel object positions accordingly when the screen size changes (entering/exiting full screen mode)
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				resetBall();
				resetPaddle();
				score.setLocation(getWidth() - score.getWidth() - 30, getHeight() - score.getHeight() - 20);
				displaySongName.setLocation(20, getHeight() - displaySongName.getHeight() - 20);
				placeGamePausedLabel();
				placePauseButton();
				placeResumeButton();
				placeSettingsButton();
				placeToMainMenuButton();
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) //Very important. This method handles the functionalities of the game/paddles/ball
	{
		if(playerLives > 0 && canStartTimer)
		{
			PongBall.move();
			
			if(rightPressed == true && PongPaddle.xPos <= getWidth() - PongPaddle.width)
				PongPaddle.xPos += PongPaddle.moveSpeed;
			if(leftPressed == true && PongPaddle.xPos >= 0)
				PongPaddle.xPos -= PongPaddle.moveSpeed;
			
			repaint(); //refreshes/repaints/creates an updated screen with the new ball and paddle positions; basically calls the paint method again
		}
	}
	
	@Override
	public void paintComponent(Graphics g) //Draws the game objects onto the game panel and is used with repaint() to redraw them again and again
	{
		super.paintComponent(g);
		PongBall.draw((Graphics2D) g);
		PongPaddle.draw((Graphics2D) g);
	}
	
	@Override
	public void addNotify()
	{
		super.addNotify();
		
		//resets the Ball to the center of the screen ONLY after the screen is done setting up so the program then has the screen dimensions to use as a reference for setting the ball
		SwingUtilities.invokeLater(() -> resetBall()); //Without the lambda syntax, this code line would look like this:
		/*
		SwingUtilities.invokeLater(new Runnable() {
		    @Override
		    public void run() {
		        resetBall();
		    }
		});
		*/
		SwingUtilities.invokeLater(() -> resetPaddle());
		SwingUtilities.invokeLater(() -> placeGamePausedLabel());
		SwingUtilities.invokeLater(() -> placePauseButton());
		SwingUtilities.invokeLater(() -> placeResumeButton());
		SwingUtilities.invokeLater(() -> placeSettingsButton());
		SwingUtilities.invokeLater(() -> placeToMainMenuButton());
	}
	
	public void resetBall() //Just centers the ball at the start of the game in the middle of the screen
	{
		PongBall.xPos = (getWidth() - 2 * PongBall.radius) / 2;
		PongBall.yPos = (getHeight() - 2 * PongBall.radius) / 2;
		
		int direction = Math.random() < 0.5 ? -1 : 1; //Randomizes the ball velocity direction (uses shorthand notation for an if/else statement)
		PongBall.xVelocity *= direction;
	}
	
	public void resetPaddle() //Just centers the paddle at the top/bottom of the screen at the start of the game
	{
		PongPaddle.xPos = (getWidth() - PongPaddle.width) / 2;
		PongPaddle.yPos = getHeight() - PongPaddle.height - 100;
	}
	
	public void resetGame() 
	{
		resetBall();
		resetPaddle();
		playerLives = resetPlayerLives;
		PongBall.xVelocity = resetPongBallSpeed_x;
		PongBall.yVelocity = resetPongBallSpeed_y;
		PongPaddle.moveSpeed = resetPongPaddleSpeed;
		hitScore = 0;
		score.setText("Score: " + String.format("%05d", hitScore));
		score.setLocation(getWidth() - score.getWidth() - 30, getHeight() - score.getHeight() - 20);
		add(score);
		PongBall.ballColor = Color.WHITE;
		controlIncreaseGameSpeed = false;
		rightPressed = false;  //Fixes the logic error where the game sometimes starts with the paddle moving without the user's input (right arrow key)
		leftPressed = false;   //Fixes the logic error where the game sometimes starts with the paddle moving without the user's input (left arrow key)
		timer.stop();
	}
	
	public void placeGamePausedLabel()
	{
		gamePausedLabel.setLocation((getWidth() - gamePausedLabel.getWidth()) / 2 + getWidth() / 50, (getHeight() - gamePausedLabel.getHeight()) / 2 - getHeight() / 3 - 50);
	}
	
	public void placePauseButton()
	{
		pauseButton.setLocation((getWidth() - pauseButton.getWidth()) / 2, getHeight() - pauseButton.getHeight() - 20);
	}
	
	public void placeResumeButton()
	{
		resumeButton.setLocation((getWidth() - resumeButton.getWidth()) / 2, (getHeight() - resumeButton.getHeight()) / 2 - 80);
	}
	
	public void placeSettingsButton()
	{
		settingsButton.setLocation((getWidth() - settingsButton.getWidth()) / 2, (getHeight() - settingsButton.getHeight()) / 2 - 130);
	}
	
	public void placeToMainMenuButton()
	{
		toMainMenuButton.setLocation((getWidth() - toMainMenuButton.getWidth()) / 2, (getHeight() - toMainMenuButton.getHeight()) / 2 - 180);
	}
	
	public void startGame()
	{
		timer.start();
	}
	
	public void increaseGameSpeed() //Increases the pace of the game depending on the player's score
	{
		if(PongBall.xVelocity > 0)
			PongBall.xVelocity += 1;
		else PongBall.xVelocity -= 1;
		
		if(PongBall.yVelocity > 0)
			PongBall.yVelocity += 1;
		else PongBall.yVelocity -= 1;
		
		PongPaddle.moveSpeed += 1;
	}
	
	public void changeStreakColor(int numHits) //Notifies the player of their score streak by changing the ball color
	{
		switch(numHits)
		{
			case 10:
				PongBall.ballColor = streakColors.get(0);
				break;
			case 20:
				PongBall.ballColor = streakColors.get(1);
				break;
			case 30:
				PongBall.ballColor = streakColors.get(2);
				break;
			case 40:
				PongBall.ballColor = streakColors.get(3);
				break;
			case 50:
				PongBall.ballColor = streakColors.get(4);
				break;
			case 60:
				PongBall.ballColor = streakColors.get(5);
				break;
			case 70:
				PongBall.ballColor = streakColors.get(6);
				break;
			case 80:
				PongBall.ballColor = streakColors.get(7);
				break;
			case 90:
				PongBall.ballColor = streakColors.get(8);
				break;
			case 100:
				PongBall.ballColor = streakColors.get(9);
				break;
			default:
				PongBall.ballColor = PongBall.ballColor;
		}
	}
	
	public void PlaySFX(int SFX_ID)
	{
		if(SFX_ID == 0)
		{
			BallHitsPaddleSFX.setSFX_File(SFX_ID);
			BallHitsPaddleSFX.play();
		}
		
		else if(SFX_ID == 1)
		{
			BallHitsWallSFX.setSFX_File(SFX_ID);
			BallHitsWallSFX.play();
		}
		
		else if(SFX_ID == 2)
		{
			BallGoesOutSFX.setSFX_File(SFX_ID);
			BallGoesOutSFX.play();
		}
	}
	
	
	
	public class Ball //Class for the ball which handles the attributes of the ball but not for its functionality
	{
		private int radius;
		private int xPos;
		private int yPos;
		private int xVelocity;
		private int yVelocity;
		Color ballColor;
		
		public Ball(int r, int xV, int yV) 
		{
			radius = r;
			xVelocity = xV;
			yVelocity = yV;
			ballColor = Color.WHITE;
		}
		
		public void draw(Graphics2D ball)
		{
			ball.setColor(ballColor);
			ball.fillOval(xPos, yPos, 2 * radius, 2 * radius);
		}
		
		public void move()
		{
			if(xPos >= getWidth() - 2 * radius || xPos < 0)
			{
				PlaySFX(1);
				xVelocity *= -1;
			}
			xPos += xVelocity;
			
			if(yPos >= getHeight() - 2 * radius)
			{
				PlaySFX(2);
				timer.stop();
				playerLives -= 1;
				yVelocity *= -1;
				Timer resetTimer = new Timer(3000, new ActionListener() { //Delays the game every time the player loses the ball to reset the ball by 3 seconds
	            	@Override
	                public void actionPerformed(ActionEvent e) { 
	                    timer.start();
	                }
	            });
				resetTimer.setRepeats(false); //Makes sure the delay timer only runs once 
				resetBall();
				resetPaddle();
				resetTimer.start();
			}
			else if(yPos < 0)
			{
				PlaySFX(1);
				yVelocity *= -1;
			}
			else if(yVelocity > 0 && xPos + 2 * radius >= PongPaddle.xPos && xPos <= PongPaddle.xPos + PongPaddle.width && yPos + 2 * radius >= PongPaddle.yPos && yPos <= PongPaddle.yPos + PongPaddle.height) //checks the necessary conditions for the ball to rebound of the paddle
			{
				PlaySFX(0);
				yVelocity *= -1;
				hitScore += 1;
				score.setText("Score: " + String.format("%05d", hitScore));
				
				if(hitScore != 0 && hitScore % 10 == 0 && hitScore <= 100)
					controlIncreaseGameSpeed = true;
			}
			yPos += yVelocity;
			
			//Check the player's score to see whether or not to switch or do not switch the ball color depending on the player's current score and change the game speed
			if(hitScore != 0 && hitScore % 10 == 0 && hitScore <= 100 && controlIncreaseGameSpeed)
			{
				changeStreakColor(hitScore);
				
				if(hitScore != 0 && hitScore != 20 && hitScore % 20 == 0 && hitScore <= 100 && controlIncreaseGameSpeed)
				{
					increaseGameSpeed();
					controlIncreaseGameSpeed = false;
				}
				else controlIncreaseGameSpeed = false;
			}
			
			//If the player is going crazy and their hit score is off the charts, turn the ball crazy also and make it rainbow
			if(hitScore >= 110)
			{
				if(rainbowColor == 9)
				{
					rainbowColor = 0;
					PongBall.ballColor = streakColors.get(rainbowColor);
				}
				else 
				{
					PongBall.ballColor = streakColors.get(rainbowColor);
					rainbowColor += 1;
				}
			}
		}
	}
	
	
	
	public class Paddle //Class for the paddle that the player controls. Handles the attributes of a paddle but not for its functionality
	{
		private int xPos;
		private int yPos;
		private int width;
		private int height;
		private int moveSpeed;
		
		public Paddle(int w, int h, int s)
		{
			width = w;
			height = h;
			moveSpeed = s;
		}
		
		public void draw(Graphics2D paddle)
		{
			paddle.setColor(Color.WHITE);
			paddle.fillRect(xPos, yPos, width, height);
		}
	}
}
