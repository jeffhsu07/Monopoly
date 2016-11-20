package client;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import resources.Player;
import resources.PropertiesSetUp;
import resources.Property;
import utilities.Constants;

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
	
	// Menu options
	JMenuItem menuPlayerStats;
	
	private boolean determineOrder;
	private int firstPlayer;
	private int highRoll;
	
	public MainWindow(ArrayList<Player> players) {
		super("Monopoly");
		PropertiesSetUp p = new PropertiesSetUp();
		properties = p.getProperties();
		this.players = players;
		initializeComponents();
		createGUI();
		addListeners();
		progressArea.addProgress(players.get(currentPlayer).getName() + ", roll to see who goes first.\n");
	}
	
	public Property[] getPropertiesArray(){
		return properties;
	}
	private void initializeComponents() {
		// Initialize our player tracking to default values.
		currentPlayer = 0;
		ownedPlayer = 0;
		determineOrder = true;
		firstPlayer = 0;
		highRoll = 0;
		
		// Initialize our various buttons.
		rollButton = new JButton("Roll Dice");
		manageBuildingsButton = new JButton("Manage Buildings");
		managePropertiesButton = new JButton("Manage Properties");
		endTurnButton = new JButton("End Turn");
		endTurnButton.setEnabled(false);
		exitButton = new JButton("Exit");
		
		// Initialize our custom Panels
		progressArea = new ProgressArea();
		playerInformationGrid = new PlayerInformationGrid(players);
		gameBoard = new GameBoard(players, properties);
		
		// Initialize menu items.
		menuPlayerStats = new JMenuItem("View Player Statistics");
	}

	private void createGUI() {
		this.setSize(1280,720);
		this.setResizable(true);
		this.setMinimumSize(getSize());
		
		// Set up the menu bar
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Menu");
		menuBar.add(menu);
		menu.add(menuPlayerStats);
		setJMenuBar(menuBar);
		
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
		c.weightx = 1;
		c.gridheight = 5;
		controlPanel.add(progressArea, c);
		
		// Add control panel to the main board
		this.add(controlPanel, BorderLayout.CENTER);
	}

	private void addListeners() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		menuPlayerStats.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new PlayerStatisticsWindow(players.get(ownedPlayer)).setVisible(true);;
			}
		});
		
		this.addWindowStateListener(new WindowStateListener() {
			public void windowStateChanged(WindowEvent arg0) {
				gameBoard.repaint();
				repaint();
			}
		});
		
		// Have the roll button move the current player.
		rollButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rollButton.setEnabled(false);
				
				// Get a random dice roll
				Random rand = new Random();
				int roll1 = rand.nextInt(6)+1;
				int roll2 = rand.nextInt(6)+1;
				
				// Move the player
				Player p = players.get(currentPlayer);
				progressArea.addProgress(p.getName() + " rolled a " + roll1 +
						" and a " + roll2 + ".\n");
				if (determineOrder) {
					if (roll1+roll2 > highRoll) {
						firstPlayer = currentPlayer;
						highRoll = roll1+roll2;
					}
					if (currentPlayer == players.size()-1) {
						currentPlayer = firstPlayer;
						progressArea.addProgress("\n"+players.get(currentPlayer).getName() + " goes first.\n");
						determineOrder = false;
					} else {
						currentPlayer = (currentPlayer + 1) % players.size();
						progressArea.addProgress("\n"+players.get(currentPlayer).getName() + ", roll to see who goes first.\n");
					}
					rollButton.setEnabled(true);
					return;
				}
				if (roll1 == roll2) {
					p.setDoubles(p.getDoubles()+1);
					if (p.getDoubles() == 3) {
						p.setDoubles(0);
						p.setCurrentLocation(Constants.jailLocation);
						p.setInJail(true);
						progressArea.addProgress("    was sent to jail for rolling doubles too many times.\n\n");
						gameBoard.repaint();
						playerInformationGrid.repaint();
						return;
					}
				}
				int newLocation = (p.getCurrentLocation()+roll1+roll2) % 40;
				if(p.getCurrentLocation()+roll1+roll2 >= 40)
				{
					p.addMoney(Constants.goMoney);
					progressArea.addProgress("    passed Go and collected $"+Constants.goMoney+".\n");
				}
				p.setCurrentLocation(newLocation);
				
				progressArea.addProgress("    landed on "+properties[newLocation].getName()+".\n");
				
				// Repaint the game board and update the progress area
				gameBoard.repaint();
				playerInformationGrid.repaint();
				
				//put game logic here, I would recommend testing to see if properties[newLocation].getPrice() != 0 to determine if its an actual
				//property you can buy, then a super long if else statement for example you could use
				//"if(properties[newLocation].getName().equals("Chance")){}" in the case of testing to see if the player landed on chance space
				//-bho
				
				int debt = 0;
				Player creditor = null;
				if (properties[newLocation].getPrice() != 0) {
					if (properties[newLocation].getOwner() == null) {
						if (p.getMoney() >= properties[newLocation].getPrice()) {
							int n = JOptionPane.showConfirmDialog(
								    getContentPane(),
								    "Would you like to buy "+properties[newLocation].getName()+
								    " for $"+properties[newLocation].getPrice()+"?",
								    "Buy Property?",
								    JOptionPane.YES_NO_OPTION);
							if (n == 0) {
								p.addMoney(-properties[newLocation].getPrice());
								p.addProperty(properties[newLocation]);
								properties[newLocation].setOwner(p);
								progressArea.addProgress("    bought "+properties[newLocation].getName()
										+" for $"+properties[newLocation].getPrice()+".\n");
							}
						}
					} else {
						if (properties[newLocation].isMortgaged()) {
							//Nothing
						} else {
							int rent = 0;
							if (properties[newLocation].getGroup().equals("Stations")) {
								rent = properties[newLocation].getRent();
								for (Property property : properties[newLocation].getOwner().getProperties()) {
									if (property.getGroup().equals("Stations")) {
										rent *= 2;
									}
								}
							} else if (properties[newLocation].getGroup().equals("Utilities")) {
								int count = 0;
								for (Property property : properties[newLocation].getOwner().getProperties()) {
									if (property.getGroup().equals("Utilities")) {
										count++;
									}
								}
								if (count == 1) {
									rent = 4 * (roll1+roll2);
								} else {
									rent = 10 * (roll1+roll2);
								}
							} else {
								rent = properties[newLocation].getRent();
							}
							debt = rent;
							if (p.getMoney() < debt) {
								debt -= p.getMoney();
							} else {
								debt = 0;
							}
							creditor = properties[newLocation].getOwner();
							p.addMoney(-rent);
							properties[newLocation].getOwner().addMoney(rent);
							progressArea.addProgress("    paid $"+rent+" in rent.\n");
						}
					}
				} else {
					if (properties[newLocation].getName().equals("Chance")) {
						//TODO show card
					} else if (properties[newLocation].getName().equals("Go To Jail")) {
						p.setCurrentLocation(Constants.jailLocation);
						p.setInJail(true);
					} else if (properties[newLocation].getName().equals("Community Chest")) {
						//TODO show card
					} else if (properties[newLocation].getName().equals("Income Tax")) {
						Object[] options = {"Pay $"+Constants.incomeTax,
	                    "Pay 10% of Total Worth"};
						int n = JOptionPane.showOptionDialog(getContentPane(),
								"Would you like to estimate your tax at $"+Constants.incomeTax+" or pay 10% of your total worth (including cash, properties, and buildings)?",
								"Income Tax",
								JOptionPane.DEFAULT_OPTION,
								JOptionPane.QUESTION_MESSAGE,
								null,     //do not use a custom Icon
								options,  //the titles of buttons
								options[0]);
						if (n == 0) {
							debt = Constants.incomeTax;
							if (p.getMoney() < debt) {
								debt -= p.getMoney();
							} else {
								debt = 0;
							}
							p.addMoney(-Constants.incomeTax);
							progressArea.addProgress("    paid $"+Constants.incomeTax+" in tax.\n");
						} else {
							int totalWorth = p.getMoney();
							for (Property property : p.getProperties()) {
								totalWorth += property.getPrice();
								if (property.getHotel()) {
									totalWorth += 5 * property.getHouseCost();
								} else {
									totalWorth += property.getNumHouses() * property.getHouseCost();
								}
							}
							int tax = totalWorth/10;
							debt = tax;
							if (p.getMoney() < debt) {
								debt -= p.getMoney();
							} else {
								debt = 0;
							}
							p.addMoney(-tax);
							progressArea.addProgress("    paid $"+tax+" in tax.\n");
						}
					} else if (properties[newLocation].getName().equals("Go")) {
						//Nothing. Go money handled above.
					} else if (properties[newLocation].getName().equals("Just Visiting")) {
						//Nothing
					} else if (properties[newLocation].getName().equals("Free Parking")) {
						//Nothing
					} else if (properties[newLocation].getName().equals("Luxury Tax")) {
						debt = Constants.luxuryTax;
						if (p.getMoney() < debt) {
							debt -= p.getMoney();
						} else {
							debt = 0;
						}
						p.addMoney(-Constants.luxuryTax);
						progressArea.addProgress("    paid $"+Constants.luxuryTax+" in tax.\n");
					}
				}
				
				//Determine if player went bankrupt
				if (debt > 0) {
					//TODO let player sell buildings and mortgage properties
					//determine if player cannot repay their debt
					progressArea.addProgress("    is bankrupt.\n");
					//TODO handle bankrupt player
				}
				
				//Find next player. If doubles was rolled and player is not in jail, player goes again.
				currentPlayer = (roll1 == roll2 && !p.isInJail()) ? 
						currentPlayer : (currentPlayer+1) % players.size();
				
				// Repaint the game board and update the progress area
				gameBoard.repaint();
				playerInformationGrid.repaint();
				
				progressArea.addProgress("\n");
				
				if (players.get(currentPlayer).getDoubles() > 0) {
					progressArea.addProgress(players.get(currentPlayer).getName() +" gets to roll again.\n");
					rollButton.setEnabled(true);
				} else {
					p.setDoubles(0);
					endTurnButton.setEnabled(true);
				}
			}
		});
		
		// Have the End Turn button increment the current player.
		endTurnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//currentPlayer = (currentPlayer + 1) % players.size();
				progressArea.addProgress(players.get(currentPlayer).getName() +"'s turn to go.\n");
				rollButton.setEnabled(true);
				endTurnButton.setEnabled(false);
			}
		});
		
		// Opens the Manage properties window when clicked
		managePropertiesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ManagePropertiesWindow(players.get(ownedPlayer), MainWindow.this,null).setVisible(true);
			}
		});
		
		// Opens the Manage buildings window when clicked
		manageBuildingsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ManageBuildingsWindow(players.get(ownedPlayer), MainWindow.this,null).setVisible(true);
			}
		});
		
		// Quits the game when exit is pushed
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}
	
	public void updateProgressArea(String update){
		progressArea.addProgress(update + ".\n");
	}
}
