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

public class PlayerInformation extends JFrame {
	public static final long serialVersionUID = 1;
	private Player player;
	private JLabel nameLabel, moneyLabel, moneyValueLabel, jailCardsLabel, jailCardsValueLabel, propertiesLabel;
	private JTextArea propertiesArea;
	private JScrollPane scrollPane;
	private JButton closeButton;
	
	public PlayerInformation(Player p) {
		player = p;
		initializeComponents();
		createGUI();
		addEvents();
	}
	
	private void initializeComponents() {
		nameLabel = new JLabel(player.getName());
		nameLabel.setFont(new Font(nameLabel.getFont().getFontName(), Font.BOLD, 24));
		moneyLabel = new JLabel("Money");
		moneyLabel.setFont(new Font(moneyLabel.getFont().getFontName(), Font.BOLD, moneyLabel.getFont().getSize()));
		moneyValueLabel = new JLabel("$"+player.getMoney());
		jailCardsLabel = new JLabel("Get Out of Jail Free Cards");
		jailCardsLabel.setFont(new Font(jailCardsLabel.getFont().getFontName(), Font.BOLD, jailCardsLabel.getFont().getSize()));
		jailCardsValueLabel = new JLabel(Integer.toString(player.getJailCards()));
		propertiesLabel = new JLabel("Properties");
		propertiesLabel.setFont(new Font(propertiesLabel.getFont().getFontName(), Font.BOLD, propertiesLabel.getFont().getSize()));
		propertiesArea = new JTextArea();
		setPropertiesPane();
		propertiesArea.setPreferredSize(new Dimension(400,300));
		scrollPane = new JScrollPane(propertiesArea);
		closeButton = new JButton("Close");
	}
	
	private void createGUI() {
		setSize(600, 600);
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
	
	private void setPropertiesPane() {
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
