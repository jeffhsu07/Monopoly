package client;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import utilities.AppearanceSettings;

public class ManagePropertiesWindow extends JFrame{
	private JLabel selectPropertyLabel;
	private JLabel mortgageStateLabel;
	private JButton mortgageButton;
	private JButton closeWindowButton;
	private JComboBox propertyDropDownComboBox;
	//private JLabel mortgageValueLabel;
	private JLabel descriptionLabel;
	
	public ManagePropertiesWindow(){
		super("Manage Properties Window");
		initializeComponents();
		createGUI();
		addListeners();
	}
	
	private void initializeComponents(){
		selectPropertyLabel = new JLabel("Select a property: ");
		mortgageStateLabel = new JLabel("this property is mortgaged bitch");
		mortgageButton = new JButton("Mortgage Property");
		closeWindowButton = new JButton("Close Window");
		propertyDropDownComboBox = new JComboBox();
		descriptionLabel = new JLabel("<html>Mortgage Value: <br>You won't be able to do shit with this property if mortgage it dumbass</html>");
	}
	
	private void createGUI(){
		setSize(500,500);
		JPanel northPanel = new JPanel(new GridLayout(1,2));
		JPanel centerPanel = new JPanel(new BorderLayout());
		JPanel centerOfCenterPanel = new JPanel(new GridLayout(1,2));
		
		AppearanceSettings.setSize(100, 60, selectPropertyLabel);
		AppearanceSettings.setTextAlignment(selectPropertyLabel, mortgageStateLabel);
		//AppearanceSettings.setSize(200, 80, mortgageButton);
		
		northPanel.add(selectPropertyLabel);
		northPanel.add(propertyDropDownComboBox);
		
		centerPanel.add(mortgageStateLabel, BorderLayout.NORTH);
		centerOfCenterPanel.add(mortgageButton);
		centerOfCenterPanel.add(descriptionLabel);
		centerPanel.add(centerOfCenterPanel, BorderLayout.CENTER);
		centerPanel.add(closeWindowButton, BorderLayout.SOUTH);
	
		//AppearanceSettings.addGlue(centerPanel, BoxLayout.PAGE_AXIS, true, centerOfCenterPanel);
		add(northPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		
	}
	
	private void addListeners(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		closeWindowButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				System.exit(0);		
			}		
		});
	}
	
	private void updateDescriptionLabel(int value){ //updates the description label to match the mortgage value of the property 
		descriptionLabel.setText("<html>Mortgage Value: " + value + "<br>You won't be able to do shit with this property if mortgage it dumbass</html>");
	}
	
	private void updateMortgageStateLabel(boolean isMortgaged){ //updates the mortgage state label.
		if(isMortgaged){ //if the property has been mortgaged then set mortgageLabel to this shit
			mortgageStateLabel.setText("This property is mortgaged");
		}else{// if its not mortgaged then set text to this shit
			mortgageStateLabel.setText("This property is not mortgaged");
		}
	}
}
