package PONG;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CreditsReflectionsPanel extends JPanel
{
	private JLabel creditsLabel;
	private JTextArea creditsReflectionsText;
	private JScrollPane scrollBar;
	JButton backToMainMenuButton;
	
	public CreditsReflectionsPanel()
	{
		setBackground(Color.BLACK);
			
		setLayout(null);
		
		creditsLabel = new JLabel("CREDITS AND REFLECTIONS");
		creditsLabel.setForeground(Color.WHITE);
		creditsLabel.setFont(new Font("SansSerif", Font.BOLD + Font.ITALIC, 70));
		creditsLabel.setSize(1200, 80);
			
		backToMainMenuButton = new JButton("BACK");
		backToMainMenuButton.setBackground(Color.WHITE);
		backToMainMenuButton.setFont(new Font("SansSerif", Font.BOLD, 30));
		backToMainMenuButton.setSize(200, 60);
		
		
		//Creates the credits and reflections text
		creditsReflectionsText = new JTextArea();
		creditsReflectionsText.setEditable(false);
		creditsReflectionsText.setLineWrap(true);
		creditsReflectionsText.setWrapStyleWord(true);
		creditsReflectionsText.setFont(new Font("SansSerif", Font.PLAIN, 20));
		creditsReflectionsText.setForeground(Color.WHITE);
		creditsReflectionsText.setBackground(Color.BLACK);
		creditsReflectionsText.setText(
            "Project Creation Date: June 7, 2025\n" +
            "Project Completion Date: June 23, 2025\n\n\n" +

            "CREDITS:\n" +
            "Most of everything in this project was created by me, Shriyan Dey.\n\n" +
            "Debugging and learning about JSwing, especially with creating the paddle controls was supported by ChatGPT.\n" +
            "YouTube videos were also used to help with things like setting up the foundation (The JFrame, JPanels that go on the frame, and the card layout to handle switching between those panels, and implementing sound)\n" +
            "All the code libraries/imports used in this program can be found on the APIs provided in this website: https://www.oracle.com/java/\n\n" +

            "These are the sound tracks I made (Ascendance was made by me in GarageBand while the rest were AI generated by using Riffusion AI):\n" +
            "- Shriyan Dey – Ascendance\n" +
            "- Shriyan Dey (Riffusion AI) – Finale\n" +
            "- Shriyan Dey (Riffusion AI) – Vivid Anthem\n" +
            "- Shriyan Dey (Riffusion AI) – Retro Recall\n" +
            "- Shriyan Dey (Riffusion AI) – Pulse\n\n" +

            "WHAT I LEARNED:\n" +
            "- I learned that it is better to take the time to learn about the kind of code/libraries you will be using before you start coding your program because there may be ways to better organize your code (for example, the card layout) than what you first think to do even though both ways work.\n" +
            "- I learned that the JFrame serves as the place to hold the JPanels. The JPanel is where you make the action happen, add game objects with their functionalities, buttons, etc. The JFrame is like the hand that holds the cards, the canvas stand, while the JPanels are the canvas where the paint/game objects/cards go.\n" +
            "- I learned that again, a great way for implementing a way for the user to transition between different screens/panels which is the card layout method which is a class that serves as a layout manager for a container.\n" +
            "- I learned about a way to animate movement for things like the ball by setting up a timer that calls on a method that repaints/recreates the panel (after updating the ball position) every certain number of milliseconds to create the illusion of the ball moving.\n" +
            "- I learned how to use boolean flags to my advantage to trigger certain events while avoiding triggering certain events that are undesired at certain times.\n" +
            "- I learned that timers are a neat way to handle multiple events happening at the same time.\n" +
            "- I learned that when placing things on your panels, especially buttons, you need to account for the device you are using and its screen size to properly place things by using methods like getWidth() and getHeight instead of just raw numbers in order to account for varying screen sizes, especially when the user enters/exits full screen mode.\n\n" +

            "WHAT I COULD HAVE DONE BETTER:\n" +
            "- I could have planned out what exactly I wanted and how I would satisfy those wants better. I began coding right away and that is usually not a good idea. I could have saved a lot of time if I did that. This led to issues down the line like having to spend time figuring out an idea on how the user should switch between panels/screens when they wanted to with making multiple screens pop up on their device.\n" +
            "- There is repeat code that could have been condensed into a method or class to keep the code lines shorter, more neat, and organized. Again, this is probably due to a lack of planning ahead. For example, the display song label that the user sees on every panel was coded in on each panel. Instead, I could have made a class to handle this and probably just paste one line of code for each panel for the display song label.\n" +
            "- I could have taken the time to learn how to account for other kinds of devices not similar to mine such as tablets where the main source of control in video games is the player's fingers on the screen instead of a keyboard and mouse.\n" +
            "- I could simply have added more to the game. Perhaps features like options in settings for changing the color theme instead of just having black and white, or adding more than one game ball for the player to hit in a game to add more challenge, or adding more game modes/settings for the player to control the difficulty of the game or play against an AI.\n" +
            "- I could have done more with the animations and music. For example, changing the music when the game ends or having a specific sound track for each screen/panel. Another example, adding a motion trail behind the ball to emphasize its speed or a splash/bounce visual when it collides with something.\n\n" +

            "QUESTIONS I STILL HAVE:\n" +
            "- I am still confused about lambda notation and why it works. What I know is that it can be used in contexts where an interface with only one abstract method is being used.\n" +
            "This was commonly the case when I was adding functionality to things like buttons and the game objects because you call on a method that you must rewrite to match what you want the button or ball or paddle to do when the player interacts with it."
        );
		        
		
		add(creditsLabel);
		add(backToMainMenuButton);
		add(creditsReflectionsText);
		
		//Adds a scroll bar to ensure the player can see all the text on the credits and reflections panel
		scrollBar = new JScrollPane(creditsReflectionsText);
		scrollBar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollBar.setBounds(getWidth() / 12, 150, getWidth() * 5 / 6, getHeight() - 300);
		add(scrollBar);
			
		setFocusable(true);
		requestFocusInWindow(); //VERY IMPORTANT - Gives the game panel the ability to capture user input
			
		//This listener ensures dynamic re-centering (resets/readjusts panel components when going full screen mode or exiting it)
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				placeCreditsLabel();
				placeBackToMainMenuButton();
				setCreditsReflectionsText();
				setScrollBar();
			}
		});
	}
		
		
		
	@Override
	public void addNotify()
	{
		super.addNotify();
		SwingUtilities.invokeLater(() -> placeCreditsLabel());
		SwingUtilities.invokeLater(() -> placeBackToMainMenuButton());
		SwingUtilities.invokeLater(() -> setCreditsReflectionsText());
		SwingUtilities.invokeLater(() -> setScrollBar());
	}
	
	
	
	public void placeCreditsLabel()
	{
		creditsLabel.setLocation((getWidth() - creditsLabel.getWidth()) / 2 + getWidth() / 15, -getHeight() / 100 + 30);
	}
	
	public void placeBackToMainMenuButton()
	{
		backToMainMenuButton.setLocation(getWidth() - backToMainMenuButton.getWidth() - getWidth() / 50, getHeight() - backToMainMenuButton.getHeight() - getHeight() / 50);
	}
	
	public void setCreditsReflectionsText()
	{
		creditsReflectionsText.setSize(getWidth(), getHeight());
		creditsReflectionsText.setLocation(creditsLabel.getY(), creditsLabel.getHeight() + creditsLabel.getY());
	}
	
	public void setScrollBar()
	{
		scrollBar.setBounds(getWidth() / 12, 150, getWidth() * 5 / 6, getHeight() - 300);
	}
}
