package client;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import resources.Player;
import resources.PropertiesSetUp;
import resources.Property;

// Made by Jesse
public class MainWindow extends JFrame {

	private static final long serialVersionUID = 424155134277101665L;

	private ArrayList<Player> players;
	private int currentPlayer;
	private int ownedPlayer;
	
	// Control Buttons
	private JButton rollButton;
	private JButton manageBuildingsButton;
	private JButton managePropertiesButton;
	private JButton endTurnButton;
	private JButton exitButton;
	
	// Other Custom Objects For GUI
	private ProgressArea progressArea;
	private PlayerInformationGrid playerInformationGrid;
	private GameBoard gameBoard;
	private Property[] properties;
	
	public MainWindow(ArrayList<Player> players) {
		super("Monopoly");
		this.players = players;
		initializeComponents();
		createGUI();
		addListeners();
		PropertiesSetUp p = new PropertiesSetUp();
		properties = p.getProperties();

	}

	private void initializeComponents() {
		// Initialize our player tracking to default values.
		currentPlayer = 0;
		ownedPlayer = 0;
		int[] tempCosts = {100,200};
		Property temp1 = new Property("China", 100, "Chinks", tempCosts, 20, 100, 5);
		Property temp2 = new Property("penis", 100, "Chinks", tempCosts, 20, 80, 5);
		temp2.setMortgaged(true);
		players.get(currentPlayer).addProperty(temp1);
		players.get(currentPlayer).addProperty(temp2);
		// Initialize our various buttons.
		rollButton = new JButton("Roll Dice");
		manageBuildingsButton = new JButton("Manage Buildings");
		managePropertiesButton = new JButton("Manage Properties");
		endTurnButton = new JButton("End Turn");
		exitButton = new JButton("Exit");
		
		// Initialize our custom Panels
		progressArea = new ProgressArea();
		playerInformationGrid = new PlayerInformationGrid(players);
		gameBoard = new GameBoard(players);
	}

	private void createGUI() {
		this.setSize(1280,720);
		this.setResizable(false);
		
		// Use a border layout
		this.setLayout(new BorderLayout());
		this.add(gameBoard, BorderLayout.WEST);
		
		// Use GridBagLayout for remaining components
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		// Set up the teams.
		c.fill = GridBagConstraints.BOTH;
		c.weightx = .1;
		c.weighty = 1;
		c.gridheight = 2;
		c.gridwidth = 2;
		controlPanel.add(playerInformationGrid, c);
		
		// Add the column of buttons
		c.gridheight = 1;
		c.gridwidth = 1;
		c.weighty = 0.5;
		c.gridy = 2;
		c.gridx = 0;
		controlPanel.add(rollButton, c);
		c.gridy++;
		controlPanel.add(manageBuildingsButton, c);
		c.gridy++;
		controlPanel.add(managePropertiesButton, c);
		c.gridy++;
		controlPanel.add(endTurnButton, c);
		c.gridy++;
		controlPanel.add(exitButton, c);
		
		// Add the progress area
		c.gridy = 2;
		c.gridx = 1;
		c.weightx = .25;
		c.gridheight = 5;
		controlPanel.add(progressArea, c);
		
		// Add control panel to the main board
		this.add(controlPanel, BorderLayout.CENTER);
	}

	private void addListeners() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		// Have the roll button move the curren player.
		rollButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Get a random dice roll
				Random rand = new Random();
				int roll1 = rand.nextInt(6)+1;
				int roll2 = rand.nextInt(6)+1;
				
				// Move the player
				Player p = players.get(currentPlayer);
				int newLocation = (p.getCurrentLocation()+roll1+roll2) % 40;
				p.setCurrentLocation(newLocation);
				if(p.getCurrentLocation()+roll1+roll2 >= 40)
				{
					//means they passed go, increment 200
				}
				
				//put game logic here, I would recommend testing to see if properties[newLocation].getPrice() != 0 to determine if its an actual
				//property you can buy, then a super long if else statement for example you could use
				//"if(properties[newLocation].getName().equals("Chance")){}" in the case of testing to see if the player landed on chance space
				//-bho
				
				// Repaint the game board and update the progress area
				gameBoard.repaint();
				progressArea.addProgress(p.getName() + " rolled a " + roll1 +
						" and a " + roll2 + ".\n");
				
			}
		});
		
		// Have the End Turn button increment the current player.
		endTurnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentPlayer = (currentPlayer + 1) % players.size();
				progressArea.addProgress("\n"+players.get(currentPlayer).getName() +"'s turn to go.");
			}
		});
		
		// Opens the Manage properties window when clicked
		managePropertiesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ManagePropertiesWindow(players.get(ownedPlayer)).setVisible(true);
			}
		});
		
		// Opens the Manage buildings window when clicked
		manageBuildingsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ManageBuildingsWindow(players.get(ownedPlayer)).setVisible(true);
			}
		});
	}
	
	
}
