package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import resources.Player;
import resources.Property;
import utilities.AppearanceSettings;
import utilities.Constants;

//Edited by Jesse
public class ManageBuildingsWindow extends JFrame{
	private JLabel selectPropertyLabel;
	private JButton buyHouseButton;
	private JButton sellHouseButton;
	private JButton closeWindowButton;
	private JComboBox propertyDropDownComboBox;
	private JLabel buyHouseDescriptionLabel;
	private JLabel sellHouseDescriptionLabel;
	private Player player;
	private int numPropertiesInGroupOwned = 0;
	private ArrayList<Integer> groupLocation;
	private Property currentProperty;
	private MainWindow mw;
	private Client client;
	public ManageBuildingsWindow(Player player, MainWindow mw, Client client){
		super("Manage Properties Window");
		this.player = player;
		this.mw = mw;
		this.client = client;
		initializeComponents();
		createGUI();
		addListeners();
	}
	
	private void initializeComponents(){
		selectPropertyLabel = new JLabel("Select a property: ");
		buyHouseButton = new JButton("Buy House");
		sellHouseButton = new JButton("Sell House");
		closeWindowButton = new JButton("Close Window");
		propertyDropDownComboBox = new JComboBox();
		currentProperty = player.getProperties().get(0);
		buyHouseDescriptionLabel = new JLabel("<html>Clicking this button will add a house to the property<br>House cost: " + currentProperty.getHouseCost() + "</br><html>");
		sellHouseDescriptionLabel = new JLabel("<html>Clicking this button will remove a house from this property<br>Current number of houses on this property: " + 
												currentProperty.getNumHouses() + "</br><html>");
		groupLocation = new ArrayList<Integer>();
	}
	
	private void createGUI(){
		setSize(500,500);
		JPanel northPanel = new JPanel(new GridLayout(1,2));
		JPanel centerPanel = new JPanel(new GridBagLayout());
		centerPanel.setBorder(BorderFactory.createEmptyBorder(80,10,80,10));
		GridBagConstraints c = new GridBagConstraints();
		AppearanceSettings.setSize(100, 60, selectPropertyLabel);
		AppearanceSettings.setTextAlignment(selectPropertyLabel, buyHouseDescriptionLabel, sellHouseDescriptionLabel);
		
		for(int i = 0; i < player.getProperties().size(); i++){
			propertyDropDownComboBox.addItem(player.getProperties().get(i).getName());
		}
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.1;
		c.weighty = 0.1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 0;
		buyHouseButton.setPreferredSize(new Dimension(200,50));
		centerPanel.add(buyHouseButton, c);
		c.gridx = 0;
		c.gridy = 4;
		sellHouseButton.setPreferredSize(new Dimension(200,50));
		centerPanel.add(sellHouseButton, c);
		c.gridx = 4;
		c.gridy = 0;
		c.weightx = 0.3;
		centerPanel.add(buyHouseDescriptionLabel,c);
		c.gridx = 4;
		c.gridy = 4;
		centerPanel.add(sellHouseDescriptionLabel,c);
		
		northPanel.add(selectPropertyLabel);
		northPanel.add(propertyDropDownComboBox);
		
		/*centerPanel.add(buyHouseButton);
		centerPanel.add(buyHouseDescriptionLabel);
		centerPanel.add(sellHouseButton);
		centerPanel.add(sellHouseDescriptionLabel);*/
	
		add(northPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(closeWindowButton, BorderLayout.SOUTH);
		
	}
	
	private void addListeners(){
		// Had to change to do nothing because it was quitting program - Jesse
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				dispose();
			}
		});
		closeWindowButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				
				// Can't quit the program when we close one window.
				//System.exit(0);		
			}		
		});
		
		buyHouseButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(hasAllPropertiesInGroup()){
					addHouse();
					//System.out.println("Built a house on " + currentProperty.getName() + " Number of Houses: " + currentProperty.getNumHouses());
					mw.repaint();
					mw.revalidate();
				}
				else{
					buyHouseDescriptionLabel.setText("<html>Can't build a house on this property, <br>you don't have a complete set</br></html>");
				}
			}
		});
		
		sellHouseButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				sellHouse();
				mw.repaint();
				mw.revalidate();
			}
		});
		final int temp = 0;
		propertyDropDownComboBox.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				for(int i = 0; i < player.getProperties().size(); i++){ //this for loop finds which property is selected and then updates the 												//description label to reflect the mortgage value
					String propertyName = (String) propertyDropDownComboBox.getSelectedItem();
					if(propertyName.equals(player.getProperties().get(i).getName())){ //if the property name in the combo box matches the name current property
						currentProperty = player.getProperties().get(i); //updates the current property the combobox is on
						buyHouseDescriptionLabel.setText("<html>Clicking this button will add a house to" + currentProperty.getName() + "<br>House cost: " + currentProperty.getHouseCost() + "</br><html>");
						sellHouseDescriptionLabel.setText("<html>Clicking this button will remove a house from " + currentProperty.getName() + "<br>Current number of houses on this property: " + 
																currentProperty.getNumHouses() + "</br><html>");
						break; //break the for loop cuz property was found
					}
				}
				
			}
			
		});
	}
	
	public void findWhichSet(){ //finds which group the current property is in
		Integer currPropertyPosition = currentProperty.getBoardPosition();
		if(currPropertyPosition == 1 || currPropertyPosition == 3){
			groupLocation = Constants.group1Locations;

		}
		else if(currPropertyPosition == 6 || currPropertyPosition == 8 || currPropertyPosition == 9){
			groupLocation = Constants.group2Locations;

		}
		else if(currPropertyPosition == 11 || currPropertyPosition == 13 || currPropertyPosition == 14){
			groupLocation = Constants.group3Locations;

		}
		else if(currPropertyPosition == 16 || currPropertyPosition == 18 || currPropertyPosition == 19){
			groupLocation = Constants.group4Locations;

		}
		else if(currPropertyPosition == 21 || currPropertyPosition == 23 || currPropertyPosition == 24){
			groupLocation = Constants.group5Locations;

		}
		else if(currPropertyPosition == 26 || currPropertyPosition == 27 || currPropertyPosition == 29){
			groupLocation = Constants.group6Locations;

		}
		else if(currPropertyPosition == 31 || currPropertyPosition == 32 || currPropertyPosition == 34){
			groupLocation = Constants.group7Locations;

		}
		else if(currPropertyPosition == 37 || currPropertyPosition == 39){
			groupLocation = Constants.group8Locations;

		}
		//numPropertiesInGroupOwned++;
	}
	
	public boolean hasAllPropertiesInGroup(){ //checks if player has all properties in the current property group
		findWhichSet();

		for(int i = 0; i < groupLocation.size(); i++){
			for(int j = 0; j < player.getProperties().size();j++){
				if(player.getProperties().get(j).getBoardPosition() == groupLocation.get(i)){
					numPropertiesInGroupOwned++;
				}
			}
		}

		if(numPropertiesInGroupOwned != groupLocation.size()){
			numPropertiesInGroupOwned = 0;
			return false;
		}
		numPropertiesInGroupOwned = 0;
		return true;
	}
	
	public void setCanBuildHouseOnGroupProperty(){
		for(int i = 0; i < groupLocation.size(); i++){
			for(int j = 0; i < player.getProperties().size(); i++){
				if(player.getProperties().get(j).getBoardPosition() == groupLocation.get(i)){
					player.getProperties().get(j).setCanBuild();
				}
			}
		}
	}
	
	public void addHouse(){
		ArrayList<Property> group = new ArrayList<Property>();
		for(int i = 0; i < groupLocation.size(); i++){
			for(int j = 0; j < player.getProperties().size(); j++){
				if(player.getProperties().get(j).getBoardPosition() == groupLocation.get(i)){
					group.add(player.getProperties().get(j));
				}
			}
		}
		
		for(int i = 0; i < group.size(); i++){
			if(currentProperty.getNumHouses() > group.get(i).getNumHouses()){
				buyHouseDescriptionLabel.setText("<html>Cannot buy a house here, <br>houses must be distributed evenly among the property group</br></html>");
				return;
			}
		}
		if(player.getMoney() > currentProperty.getHouseCost()){
			currentProperty.addBuilding();
			player.subtractMoney(currentProperty.getHouseCost());
			if(currentProperty.getHotel()){
				buyHouseDescriptionLabel.setText("Added a hotel to " + currentProperty.getName());
				sellHouseDescriptionLabel.setText("<html>Clicking this button will remove a house from this property<br>Current number of houses on this property: 1 hotel</br><html>");
				mw.updateProgressArea(player.getName() + " built a hotel on " + currentProperty.getName());
			}
			else{
			
				buyHouseDescriptionLabel.setText("<html>Added house to " + currentProperty.getName() + "<br> total number of houses built on this property: " + currentProperty.getNumHouses() + "</br></html>");
				sellHouseDescriptionLabel.setText("<html>Clicking this button will remove a house from this property<br>Current number of houses on this property: " + 
					currentProperty.getNumHouses() + "</br><html>");
				mw.updateProgressArea(player.getName() + " built a house on " + currentProperty.getName() + ", Total number of houses on "
									+ currentProperty.getName() + ": " + currentProperty.getNumHouses());
			}
		}
		else{
			buyHouseDescriptionLabel.setText("You do not have enough money to buy a house on this property!");
		}
	}
	
	public void sellHouse(){
		if(currentProperty.getHotel()){
			currentProperty.setHouses(4);
		}
		if(currentProperty.getNumHouses() > 0){
			ArrayList<Property> group = new ArrayList<Property>();
			for(int i = 0; i < groupLocation.size(); i++){
				for(int j = 0; i < player.getProperties().size(); i++){
					if(player.getProperties().get(j).getBoardPosition() == groupLocation.get(i)){
						group.add(player.getProperties().get(j));
					}
				}
			}
			
			for(int i = 0; i < group.size(); i++){
				if(currentProperty.getNumHouses() < group.get(i).getNumHouses()){
					sellHouseDescriptionLabel.setText("<html>Cannot sell a house here <br>houses must be distributed evenly among the property group</br></html>");
					return;
				}
			}
			
			currentProperty.removeBuilding();
			System.out.println(currentProperty.getNumHouses());
			player.addMoney(currentProperty.getSellHouseCost());
			sellHouseDescriptionLabel.setText("<html>Removed house from <br>" + currentProperty.getName() + "<br>total number of houses built: " + currentProperty.getNumHouses() + "</br><html>");
			buyHouseDescriptionLabel.setText("<html>Clicking this button will <br>add a house to the property<br>House cost: " + currentProperty.getHouseCost() + "</br><html>");
			mw.updateProgressArea(player.getName() + " sold a house on " + currentProperty.getName() + ", Total number of houses on "
					+ currentProperty.getName() + ": " + currentProperty.getNumHouses());
		}
		else{
			sellHouseDescriptionLabel.setText("There are no houses on this property!");
		}
	}
}
