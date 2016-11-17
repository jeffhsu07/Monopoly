//	Author: Matthew van Niekerk
package client;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StartWindow extends JFrame {
	
	private JLabel selectPrompt;
	private JLabel yourName;
	private JLabel playersLabel;
	private JLabel tokensLabel;
	private JButton readyButton;
	private ArrayList<JLabel> playerNames;
	private ArrayList<Image> chosenIcons;
	private ArrayList<JButton> tokenButtons;
	private JPanel playerGrid;
	private JPanel tokenGrid;
	private Socket s;

	public StartWindow() {
		initializeVariables();
		createGUI();
		addActionListeners();
	}
	
	private void initializeVariables() {
		selectPrompt = new JLabel("Select your game token.", JLabel.CENTER);
		//	Change to variable
		yourName = new JLabel("Name: USERNAME", JLabel.CENTER);
		playersLabel = new JLabel("Players:");
		tokensLabel = new JLabel("Tokens:");
		readyButton = new JButton("Ready");
		playerNames = new ArrayList<JLabel>();
		chosenIcons = new ArrayList<Image>();
		tokenButtons = new ArrayList<JButton>();
		playerGrid = new JPanel();
		tokenGrid = new JPanel();
		s = new Socket();
	}
	
	private void createGUI() {
		JPanel mainPanel = new JPanel();
		JPanel northPanel = createNorthPanel();
		JPanel centerPanel = createCenterPanel();
		JPanel southPanel = createSouthPanel();
		
		mainPanel.setLayout(new BorderLayout());
		
		mainPanel.add(northPanel, BorderLayout.NORTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(southPanel, BorderLayout.SOUTH);
		
		add(mainPanel);
		setSize(600, 800);
		setLocationRelativeTo(null);
	}
	
	private JPanel createNorthPanel() {
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new BorderLayout());
		northPanel.add(selectPrompt, BorderLayout.NORTH);
		northPanel.add(yourName, BorderLayout.CENTER);
		return northPanel;
	}
	
	private JPanel createCenterPanel() {
		JPanel centerPanel = new JPanel();
		JPanel playerPanel = new JPanel();
		JPanel tokenPanel = new JPanel();
		
		centerPanel.setLayout(new BorderLayout());
		playerPanel.setLayout(new BorderLayout());
		tokenPanel.setLayout(new BorderLayout());
		
		playerGrid.setLayout(new GridLayout(4, 2));
		tokenGrid.setLayout(new GridLayout(2, 4));
		
		playerPanel.add(playersLabel, BorderLayout.NORTH);
		playerPanel.add(playerGrid, BorderLayout.CENTER);
		
		tokenPanel.add(tokensLabel, BorderLayout.NORTH);
		tokenPanel.add(tokenGrid, BorderLayout.CENTER);
		
		centerPanel.add(playerPanel, BorderLayout.NORTH);
		centerPanel.add(tokenPanel, BorderLayout.CENTER);
		
		return centerPanel;
	}
	
	private JPanel createSouthPanel() {
		JPanel southPanel = new JPanel();
		
		southPanel.add(readyButton);
		
		return southPanel;
	}
	
	private void addActionListeners() {
		
	}
}
