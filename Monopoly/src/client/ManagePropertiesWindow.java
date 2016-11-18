package client;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import resources.Player;
import utilities.AppearanceSettings;

// Edited by Jesse
public class ManagePropertiesWindow extends JFrame{
	private JLabel selectPropertyLabel;
	private JLabel mortgageStateLabel;
	private JButton mortgageButton;
	private JButton closeWindowButton;
	private JComboBox propertyDropDownComboBox;
	private  Player player;
	private int mortgageValue;
	//private JLabel mortgageValueLabel;
	private JLabel descriptionLabel;
	public ManagePropertiesWindow(Player player){
		super("Manage Properties Window");
		this.player = player;
		initializeComponents();
		createGUI();
		addListeners();
	}
	
	private void initializeComponents(){
		selectPropertyLabel = new JLabel("Select a property: ");
		mortgageStateLabel = new JLabel("this property is mortgaged");
		mortgageButton = new JButton("Mortgage Property");
		closeWindowButton = new JButton("Close Window");
		propertyDropDownComboBox = new JComboBox();
		int mortgageValue =  player.getProperties().get(0).getMortgageValue();
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
		closeWindowButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				
				// Can't quit the program when we close one window
				//System.exit(0);		
			}		
		});
		
		propertyDropDownComboBox.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				for(int i = 0; i < player.getProperties().size(); i++){
					String propertyName = (String) propertyDropDownComboBox.getSelectedItem();
					if(propertyName.equals(player.getProperties().get(i).getName())){
						updateDescriptionLabel(player.getProperties().get(i).getMortgageValue());
						if(player.getProperties().get(i).isMortgaged()){
							updateMortgageStateLabel(true);
							mortgageButton.setEnabled(false);
						}
						else{
							updateMortgageStateLabel(false);
							mortgageButton.setEnabled(true);
						}
						break;
					}
				}
				
			}
			
		});
	}
	
	private void updateDescriptionLabel(int value){ //updates the description label to match the mortgage value of the property 
		descriptionLabel.setText("<html>Mortgage Value: " + value + "<br>You won't be able to do shit with this property if mortgage it dumbass</html>");
	}
	
	private void updateMortgageStateLabel(boolean isMortgaged){ //updates the mortgage state label.
		if(isMortgaged){ //if the property has been mortgaged then set mortgageLabel to this shit
			mortgageStateLabel.setText("This property has already been mortgaged");
		}else{// if its not mortgaged then set text to this shit
			mortgageStateLabel.setText("This property has not been mortgaged");
		}
	}
}
