package client;

import java.awt.BorderLayout;
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

// Edited by Jesse
public class ManagePropertiesWindow extends JFrame{
	private JLabel selectPropertyLabel;
	private JLabel mortgageStateLabel;
	private JButton mortgageButton;
	private JButton closeWindowButton;
	private JComboBox propertyDropDownComboBox;
	private  Player player;
	private int mortgageValue;
	private Property currentProperty; 
	//private JLabel mortgageValueLabel;
	private JLabel descriptionLabel;
	private boolean isMortgaged;
	private MainWindow mw;
	private ArrayList<Integer> groupLocation;
	private int numPropertiesInGroupOwned = 0;
	private Client client;
	public ManagePropertiesWindow(Player player, MainWindow mw, Client client){
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
		mortgageStateLabel = new JLabel();
		mortgageButton = new JButton();
		closeWindowButton = new JButton("Close Window");
		propertyDropDownComboBox = new JComboBox();
		mortgageValue =  player.getProperties().get(0).getMortgageValue();
		currentProperty = player.getProperties().get(0);
		isMortgaged = currentProperty.isMortgaged();
		groupLocation = new ArrayList<Integer>();
		if(isMortgaged){
			mortgageButton.setText("Reclaim Property");
			mortgageStateLabel.setText("this property has been mortgaged");
		}
		else{
			mortgageButton.setText("Mortgage Property");
			mortgageStateLabel.setText("This property has not been mortgaged yet");
		}
		descriptionLabel = new JLabel("<html>Mortgage Value: " + mortgageValue + "<br>You won't be able to utilize this property if mortgage it</html>");
		
	}
	
	private void createGUI(){
		setSize(500,500);
		JPanel northPanel = new JPanel(new GridLayout(1,2));
		JPanel centerPanel = new JPanel(new BorderLayout());
		JPanel centerOfCenterPanel = new JPanel(new GridBagLayout());
		centerOfCenterPanel.setBorder(BorderFactory.createEmptyBorder(120,10,120,10));
		GridBagConstraints c = new GridBagConstraints();
		AppearanceSettings.setSize(100, 60, selectPropertyLabel);
		AppearanceSettings.setTextAlignment(selectPropertyLabel, mortgageStateLabel, descriptionLabel);
		//AppearanceSettings.setSize(200, 80, mortgageButton);
		
		for(int i = 0; i < player.getProperties().size(); i++){
			propertyDropDownComboBox.addItem(player.getProperties().get(i).getName());
		}
		northPanel.add(selectPropertyLabel);
		northPanel.add(propertyDropDownComboBox);
		
		centerPanel.add(mortgageStateLabel, BorderLayout.NORTH);
		
		c.fill = GridBagConstraints.BOTH;
		c.weightx = .1;
		c.weighty = 1;
		c.gridheight = 2;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 0;
		centerOfCenterPanel.add(mortgageButton, c);
		c.gridx = 5;
		c.gridy = 0;
		c.weightx = .5;
		c.weighty = 1;
		centerOfCenterPanel.add(descriptionLabel,c);
		centerPanel.add(centerOfCenterPanel, BorderLayout.CENTER);
		centerPanel.add(closeWindowButton, BorderLayout.SOUTH);
	
		//AppearanceSettings.addGlue(centerPanel, BoxLayout.PAGE_AXIS, true, centerOfCenterPanel);
		add(northPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		
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
				
				// Can't quit the program when we close one window
				//System.exit(0);		
			}		
		});
		mortgageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				mortgageProperty();
			}
		});
		
		propertyDropDownComboBox.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				for(int i = 0; i < player.getProperties().size(); i++){ //this for loop finds which property is selected and then updates the 
																		//description label to reflect the mortgage value
					String propertyName = (String) propertyDropDownComboBox.getSelectedItem();
					if(propertyName.equals(player.getProperties().get(i).getName())){ //if the property name in the combo box matches the name current property
						updateDescriptionLabel(player.getProperties().get(i).getMortgageValue()); //update the mortgage value displayed
						currentProperty = player.getProperties().get(i); //updates the current property the combobox is on
						isMortgaged = player.getProperties().get(i).isMortgaged();
						if(player.getProperties().get(i).isMortgaged()){
							updateMortgageStateLabel();
						}
						else{
							updateMortgageStateLabel();
						}
						break; //break the for loop cuz property was found
					}
				}
				
			}
			
		});
	}
	
	private void updateDescriptionLabel(int value){ //updates the description label to match the mortgage value of the property 
		mortgageValue = value;
		descriptionLabel.setText("<html>Mortgage Value: " + mortgageValue + "<br>You won't be able to do shit with this property if mortgage it dumbass</html>");
	}
	
	private void updateMortgageStateLabel(){ //updates the mortgage state label.
		if(isMortgaged){ //if the property has been mortgaged then set mortgageLabel to this shit
			mortgageStateLabel.setText("This property has already been mortgaged");
			mortgageButton.setText("Reclaim Property"); //disables mortgage button so that can't be clicked if mortgaged
		}else{// if its not mortgaged then set text to this shit
			mortgageStateLabel.setText("This property has not been mortgaged");
			mortgageButton.setText("Mortgage Property");
		}
	}
	
	private void mortgageProperty(){
		findWhichSet();
		if(!isMortgaged && !groupHasHouses()){
			player.addMoney(mortgageValue);
			currentProperty.setMortgaged(true);
			isMortgaged = currentProperty.isMortgaged();
			updateMortgageStateLabel();
			mw.updateProgressArea(player.getName() + " mortgaged " + currentProperty.getName());
		}
		else if(groupHasHouses()){
			mortgageStateLabel.setText("<html>Can't mortgage property if properties in the same group has <br>buildings on them</br></html>");
		}
		else{
			if(player.subtractMoney(mortgageValue)){
				currentProperty.setMortgaged(false);
				isMortgaged = currentProperty.isMortgaged();
				updateMortgageStateLabel();
				mw.updateProgressArea(player.getName() + " reclaimed " + currentProperty.getName());
			}
			else{
				mortgageStateLabel.setText("Can't reclaim this property because you don't have enough money");
			}
		}
		
		//updateMortgageStateLabel();
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
		for(int i = 0; i < groupLocation.size(); i++){
			System.out.println(groupLocation.get(i));
		}
	}
	
	public boolean groupHasHouses(){
		for(int i = 0; i < player.getProperties().size(); i++){
			for(int j = 0; j < groupLocation.size(); j++){
				if(player.getProperties().get(i).getBoardPosition() == groupLocation.get(j)){
					if(player.getProperties().get(i).getNumHouses() > 0){
						System.out.println("Is this running?");
						return true;
					}
				}
			}
		}
		
		return false;
	}
}
