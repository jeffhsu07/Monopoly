//	Author: Matthew van Niekerk
package client;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import resources.Player;

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
	private Player player;
	private ArrayList<Image> tokenImages;

	public StartWindow(Player player) {
		this.player = player;
		initializeVariables();
		createGUI();
		addActionListeners();
	}
	
	private void initializeVariables() {
		selectPrompt = new JLabel("Select your game token.", JLabel.CENTER);
		//	Change to variable
		yourName = new JLabel("Name: " + player.getName(), JLabel.CENTER);
		playersLabel = new JLabel("Players:", JLabel.CENTER);
		tokensLabel = new JLabel("Tokens:", JLabel.CENTER);
		readyButton = new JButton("Ready");
		
		playerNames = new ArrayList<JLabel>(8);
		playerNames.add(0, new JLabel(player.getName(), JLabel.CENTER));
		for (int i = 1; i < 8; i++) {
			playerNames.add(i, new JLabel("No Player", JLabel.CENTER));
		}
		
		chosenIcons = new ArrayList<Image>();
		tokenImages = new ArrayList<Image>(8);
		tokenButtons = new ArrayList<JButton>(8);
		
		for (int i = 0; i < 8; i++) {
			JButton temp = new JButton();
			try {
				Image img = ImageIO.read(new File("images/tokens/token" + i + ".png"));
				tokenImages.add(img);
				temp.setIcon(new ImageIcon(tokenImages.get(i)));
			} catch (IOException e) {
				e.printStackTrace();
			}
			temp.setSize(100, 100);
			tokenButtons.add(temp);
		}
		
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
		tokenPanel.setLayout(new FlowLayout());
		
		playerGrid.setLayout(new GridLayout(4, 2));
		tokenGrid.setLayout(new GridLayout(2, 4));
		
		for (int i = 0; i < 8; i++) {
			playerGrid.add(playerNames.get(i));
		}
		
		for (int i = 0; i < 8; i++) {
			tokenGrid.add(tokenButtons.get(i));
		}
		
		playerPanel.add(playersLabel, BorderLayout.NORTH);
		playerPanel.add(playerGrid, BorderLayout.CENTER);
		
		tokenPanel.add(tokensLabel);
		tokenPanel.add(tokenGrid);
		
		centerPanel.add(playerPanel, BorderLayout.NORTH);
		centerPanel.add(tokenPanel, BorderLayout.CENTER);
		
		return centerPanel;
	}
	
	private void refreshPlayers() {
		for (int i = 0; i < 8; i++) {
			playerGrid.remove(playerNames.get(i));
		}
		for (int i = 0; i < 8; i++) {
			playerGrid.add(playerNames.get(i));
		}
	}

	private JPanel createSouthPanel() {
		JPanel southPanel = new JPanel();
		
		southPanel.add(readyButton);
		
		return southPanel;
	}
	
	private void addActionListeners() {
		
	}
}
