package client;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import resources.Player;

//Made by Jesse
public class GameBoard extends JPanel {

	private static final long serialVersionUID = 6731326368140967067L;
	
	ArrayList<Player> players;
	
	public GameBoard(ArrayList<Player> players) {
		this.players = players;
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// Paint the Game Board
		
		// Paint the houses and hotels
		
		// Paint the Players onto the Board
	}
}
