package client;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import resources.Player;
import utilities.AppearanceSettings;

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
	public ManageBuildingsWindow(Player player){
		super("Manage Properties Window");
		this.player = player;
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
		buyHouseDescriptionLabel = new JLabel("buying a house will..");
		sellHouseDescriptionLabel = new JLabel("Selling a house will...");
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
		centerPanel.add(buyHouseButton, c);
		c.gridx = 0;
		c.gridy = 4;
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
				
			}
		});
		
		sellHouseButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			}
		});
	}
}
