package client;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import resources.Player;

//Made by Jesse
public class PlayerInformationGrid extends JPanel {

	private static final long serialVersionUID = 5873550816023916659L;

	ArrayList<Player> players;
	ArrayList<JButton> playerButtons;
	
	public PlayerInformationGrid(ArrayList<Player> players) {
		this.players = players;
		initializeComponents();
		createGUI();
		addListeners();
	}
	
	private void initializeComponents() {
		// Create a button for each player.
		playerButtons = new ArrayList<JButton>();
		for (Player p : players) {
			JButton b = new JButton(p.getName());
			playerButtons.add(b);
		}
	}

	private void createGUI() {
		// Create a 2 by X grid to store the needed number of players.
		int numColumns = (players.size()%2 == 0) ? players.size()/2 : players.size()/2+1;
		this.setLayout(new GridLayout(2,numColumns));
		
		// Add each button to the grid.
		for (JButton b : playerButtons) {
			this.add(b);
		}
	}

	private void addListeners() {
		// No action inside progress area.
		for (int i = 0; i < players.size(); i++) {
			JButton b = playerButtons.get(i);
			Player p = players.get(i);
			b.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new PlayerInformationWindow(p).setVisible(true);;
				}
			});
		}
	}
}
