package client;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import resources.Player;

public class WinnerAnnouncementWindow extends JFrame {
	public static final long serialVersionUID = 1;
	private Player player;
	private JLabel winnerLabel, nameLabel, statisticsLabel, numPropertiesLabel, ownedLabel, buildingsBoughtLabel,
		housesLabel, hotelsLabel, totalAmountLabel, moneyLabel, recordLabel;
	private JButton closeButton;
	
	public WinnerAnnouncementWindow(Player p) {
		super("Winner!");
		player = p;
		initializeComponents();
		createGUI();
		addEvents();
	}
	
	private void initializeComponents() {
		winnerLabel = new JLabel("The Winner Is");
		nameLabel = new JLabel(player.getName());
		statisticsLabel = new JLabel("In Game Statistics:");
		numPropertiesLabel = new JLabel("Number of Properties");
		ownedLabel = new JLabel("Owned: " + player.getProperties().size());
		buildingsBoughtLabel = new JLabel("Buildings Bought:");
		housesLabel = new JLabel("Houses: 14");
		hotelsLabel = new JLabel("Hotels: 2");
		totalAmountLabel = new JLabel("Total Amount of Money");
		moneyLabel = new JLabel("$" + player.getMoney());
		recordLabel = new JLabel(player.getName() + " now has " +
				player.getWins() + " W " +
				(player.getGamesPlayed()-player.getWins()) + " L");
		closeButton = new JButton("Close");
	}
	
	private void createGUI() {

		setSize(500, 500);
		setLocation(0, 0);
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.1;
		c.weighty = 0.1;
		add(winnerLabel, c);
		
		c.gridy = 1;
		add(nameLabel, c);
		
		c.gridy = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		
		// Paint the statistics window
		{
			JPanel statisticsPanel = new JPanel();
			statisticsPanel.setLayout(new GridBagLayout());
			statisticsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			GridBagConstraints c2 = new GridBagConstraints();
			c2.gridx = 0;
			c2.gridy = 0;
			c2.weightx = 0.1;
			c2.weighty = 0.1;
			c2.fill = GridBagConstraints.VERTICAL;
			c2.gridwidth = 4;
			statisticsPanel.add(statisticsLabel, c2);
			
			c2.gridy = 1;
			c2.gridwidth = 2;
			statisticsPanel.add(numPropertiesLabel, c2);
			
			c2.gridx = 2;
			statisticsPanel.add(buildingsBoughtLabel, c2);
			
			c2.gridx = 0;
			c2.gridy = 2;
			statisticsPanel.add(ownedLabel, c2);
			
			c2.gridx = 2;
			c2.gridwidth = 1;
			statisticsPanel.add(housesLabel, c2);
			
			c2.gridx = 3;
			statisticsPanel.add(hotelsLabel, c2);
			
			c2.gridx = 0;
			c2.gridwidth = 4;
			c2.gridy = 3;
			statisticsPanel.add(totalAmountLabel, c2);
			
			c2 .gridy = 4;
			statisticsPanel.add(moneyLabel, c2);
			
			add(statisticsPanel,c);
		}
		
		c.fill = GridBagConstraints.NONE;
		c.gridy = 3;
		add(recordLabel,c);
		
		c.gridy = 4;
		add(closeButton, c);
	}
	
	private void addEvents() {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				dispose();
			}
		});
	}
}