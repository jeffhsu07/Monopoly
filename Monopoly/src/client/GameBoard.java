package client;

import java.awt.Dimension;
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
		
		// Default to a square
		this.setPreferredSize(new Dimension(900,900));
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// Paint the Game Board
		paintBoard(g);
		
		// Paint the houses and hotels
		
		// Paint the Players onto the Board
	}
	
	private void paintBoard(Graphics g) {
		// Get width and height of cells based on current dimensions
		int gridWidth = this.getWidth()/11;
		int width = this.getWidth();
		int gridHeight = this.getHeight()/11;
		int height = this.getHeight();
		
		// Draw our main horizontal lines.
		g.drawLine(0, 0, width, 0);
		g.drawLine(0, height-1, width, height-1);
		g.drawLine(0, gridHeight, width, gridHeight);
		g.drawLine(0, getHeight()-gridHeight, width, height-gridHeight);
		
		// Draw our main vertical lines
		g.drawLine(0, 0, 0, height);
		g.drawLine(width-1, 0, width-1, height);
		g.drawLine(gridWidth, 0, gridWidth, height);
		g.drawLine(width-gridWidth, 0, width-gridWidth, height);
		
		// Draw our horizontal lines
		for (int i = 1; i < 9; i++) {
			int currentHeight = gridHeight*(1+i);
			g.drawLine(0, currentHeight, gridWidth, currentHeight);
			g.drawLine(width-gridWidth, currentHeight, width, currentHeight);
		}
		
		// Draw our vertical lines
		for (int i = 1; i < 9; i++) {
			int currentX = gridWidth*(1+i);
			g.drawLine(currentX, 0, currentX, gridHeight);
			g.drawLine(currentX, height-gridHeight, currentX, height);
		}
	}
}
