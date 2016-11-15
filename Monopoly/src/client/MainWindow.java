package client;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;

import resources.Player;

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
	
	public MainWindow(ArrayList<Player> players) {
		super("Monopoly");
		this.players = players;
		initializeComponents();
		createGUI();
		addListeners();
	}

	private void initializeComponents() {
		// Initialize our player tracking to default values.
		currentPlayer = 0;
		ownedPlayer = 0;
		
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
		// TODO Auto-generated method stub
		
	}

	private void addListeners() {
		// TODO Auto-generated method stub
		
	}
}
