/**
 * Merged Nick and Jeffrey's versions of PlayerInformationWindow.java
 */

package client;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import resources.Player;
import resources.Property;
import utilities.AppearanceSettings;
import utilities.AppearanceConstants;

public class PlayerInformation extends JFrame {
	public static final long serialVersionUID = 1;
	private Player player;
	private JLabel nameLabel, moneyLabel, moneyValueLabel, jailCardsLabel, jailCardsValueLabel, 
		propertiesLabel;
	private JTextArea propertiesArea;
	private JScrollPane scrollPane;
	private JButton closeButton;
	
	public PlayerInformation(Player p) {
		super("Player Information");
		player = p;
		initializeComponents();
		createGUI();
		addEvents();
	}
	
	private void initializeComponents() {
		nameLabel = new JLabel(player.getName());
		moneyLabel = new JLabel("Money");
		moneyValueLabel = new JLabel("$"+player.getMoney());
		jailCardsLabel = new JLabel("Get Out of Jail Free Cards");
		jailCardsValueLabel = new JLabel(Integer.toString(player.getJailCards()));
		propertiesLabel = new JLabel("Properties");
		propertiesArea = new JTextArea();
		scrollPane = new JScrollPane(propertiesArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		closeButton = new JButton("Close");
	}
	
	private void createGUI() {
		//Visual Settings
		Font boldNormal = new Font(nameLabel.getFont().getFontName(), Font.BOLD, 
				nameLabel.getFont().getSize());
		Font boldLarge = new Font(nameLabel.getFont().getFontName(), Font.BOLD, 24);
		AppearanceSettings.setFont(boldNormal, moneyLabel, jailCardsLabel, propertiesLabel);
		AppearanceSettings.setFont(boldLarge, nameLabel);
		AppearanceSettings.setSize(400, 300, propertiesArea);
		AppearanceSettings.setTextComponents(propertiesArea);
		setPropertiesArea();
		
		setSize(500, 500);
		setLocation(0, 0);
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.5;
		c.weighty = 0.5;
		add(nameLabel, c);
		
		c.gridy = 1;
		JPanel jp1 = new JPanel(new GridLayout(2, 2));
		jp1.add(moneyLabel);
		jp1.add(jailCardsLabel);
		jp1.add(moneyValueLabel);
		jp1.add(jailCardsValueLabel);
		add(jp1, c);
		
		c.gridy = 2;
		add(propertiesLabel, c);
		
		c.gridy = 3;
		add(scrollPane, c);
		
		c.gridy = 4;
		add(closeButton, c);
	}
	
	private void addEvents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				dispose();
			}
		});
	}
	
	public void update(Player player) {
		this.player = player;
		nameLabel.setText(player.getName());
		moneyLabel.setText("$"+player.getMoney());
		jailCardsLabel.setText(Integer.toString(player.getJailCards()));
		setPropertiesPane();
	}
	
	private void setPropertiesArea() {
		propertiesArea.setText("");
		for (Property property : player.getProperties()) {
			propertiesArea.append(property.getName());
			if (property.isMortgaged()) {
				propertiesArea.append(" (Mortgaged)");
			} else {
				if (property.hasHotel()) {
					propertiesArea.append(" (Hotel)");
				} else {
					int numHouses = property.getNumHouses();
					if (numHouses == 1) {
						propertiesArea.append(" (1 House)");
					} else if (numHouses > 1) {
						propertiesArea.append(" ("+numHouses+" Houses)");
					}
				}
			}
			propertiesArea.append("\n");
		}
	}
}


//Jeffrey's version
/*package client;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import utilities.AppearanceConstants;
import utilities.AppearanceSettings;
public class PlayerInformationWindow extends JFrame {
	private JLabel usernameLabel;
	private JLabel moneyLabel;
	private JLabel jailLabel;
	private JLabel propertiesOwnedLabel;
	private JTextArea allPropertiesOwnedArea;
	private JButton closeWindowButton;

	public PlayerInformationWindow(){
		super("Player Information");
		initializeComponents();
		createGUI();
		addListeners();
	}
	
	private void initializeComponents(){
		usernameLabel = new JLabel("username");
		moneyLabel = new JLabel("<html><b>Money:</b><br>$100 </html>");
		jailLabel = new JLabel("<html><b>number of get out of jail free cards:</b><br> 1 </html>");
		propertiesOwnedLabel = new JLabel("<html><b>Properties Owned: </b></html>");
		allPropertiesOwnedArea = new JTextArea();
		closeWindowButton = new JButton("Close");
	}
	
	private void createGUI(){
		setSize(500,500);
		JPanel northPanel = new JPanel(new GridLayout(2,1));
		JPanel moneyAndJailPanel = new JPanel(new GridLayout(1,2));
		JPanel centerPanel = new JPanel();
		allPropertiesOwnedArea.setEditable(false);
		JScrollPane propertiesScrollPane = new JScrollPane(allPropertiesOwnedArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		AppearanceSettings.setTextAlignment(usernameLabel);
		AppearanceSettings.setSize(400, 400, propertiesScrollPane);
		usernameLabel.setFont(AppearanceConstants.fontLarge);
		moneyAndJailPanel.add(moneyLabel);
		moneyAndJailPanel.add(jailLabel);
		northPanel.add(usernameLabel);
		northPanel.add(moneyAndJailPanel);
		add(northPanel, BorderLayout.NORTH);
		
		centerPanel.add(propertiesOwnedLabel, BorderLayout.NORTH);
		centerPanel.add(propertiesScrollPane, BorderLayout.CENTER);
		//centerPanel.add(closeWindowButton, BorderLayout.SOUTH);
		add(centerPanel,BorderLayout.CENTER);
		add(closeWindowButton, BorderLayout.SOUTH);
		
	}
	
	private void addListeners(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		closeWindowButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				dispose();
				System.exit(0);
			}
		});
	}
}*/
