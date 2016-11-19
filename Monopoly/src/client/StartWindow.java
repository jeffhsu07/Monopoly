//	Author: Matthew van Niekerk
package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
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
	private ArrayList<PlayerInfoLayout> playerInfo;
	private ArrayList<Image> chosenIcons;
	private ArrayList<JButton> tokenButtons;
	private JPanel playerGrid;
	private JPanel tokenGrid;
	private Socket s;
	private String playerName;
	private int playerToken;
	private Client client;
	private ArrayList<Image> tokenImages;

	public StartWindow(String playerName, ArrayList<String> otherPlayerInfo, Client client) {
		this.playerName = playerName;
		this.client = client;
		playerToken = -1;
		initializeVariables();
		createGUI();
		addActionListeners();
	}
	
	private void initializeVariables() {
		selectPrompt = new JLabel("Select your game token.", JLabel.CENTER);
		//	Change to variable
		yourName = new JLabel("Name: " + playerName, JLabel.CENTER);
		playersLabel = new JLabel("Players:", JLabel.CENTER);
		tokensLabel = new JLabel("Tokens:", JLabel.CENTER);
		readyButton = new JButton("Ready");
		
		playerInfo = new ArrayList<PlayerInfoLayout>(8);
		playerInfo.add(0, new PlayerInfoLayout(playerName, playerToken));
		for (int i = 1; i < 8; i++) {
			playerInfo.add(i, new PlayerInfoLayout("No Player", -1));
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
			playerGrid.add(playerInfo.get(i));
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

	private JPanel createSouthPanel() {
		JPanel southPanel = new JPanel();
		
		southPanel.add(readyButton);
		
		return southPanel;
	}
	
	private void refreshPlayers() {
		for (int i = 0; i < 8; i++) {
			
		}
	}
	
	private void addActionListeners() {
		for (int i = 0; i < 8; i++) {
			setUpTokenWindow(i);
		}
	}
	
	private void setUpTokenWindow(int i) {
		tokenButtons.get(i).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playerToken = i;
				refreshPlayers();
				client.sendMessage(playerName + "::Picked Token::" + playerToken);
			}
		});
	}
	
	private class PlayerInfoLayout extends JPanel {
		private static final long serialVersionUID = -6341876191272116746L;
		
		String playerName;
		int playerToken;
		
		public PlayerInfoLayout(String playerName, int playerToken) {
			this.setLayout(new BorderLayout());
			this.setPreferredSize(new Dimension(200, 100));
			this.playerName = playerName;
			this.playerToken = playerToken;
			// Initialize our Image
			/*try {
					playerToken = ImageIO.read(new File("images/tokens/token" + p.getGameToken() + ".png"));
			} catch (IOException ioe) {
				System.out.println("Error Loading Player Image: " + ioe.getMessage());
			}*/
		}
		
		public String getName() {
			return playerName;
		}
		
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			int width = this.getWidth();
			int height = this.getHeight();
			int fontHeight = g.getFont().getSize();
			
			g.drawRect(2, 2, getWidth()-4, getHeight()-4);
			if (playerToken != -1) {
				g.drawImage(tokenImages.get(playerToken), 5, 5, width/2-10, height-10, null);
			}
			
			g.drawString(playerName, width/2, height/4+fontHeight/2);
		}
	}
}
