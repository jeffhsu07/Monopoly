package resources;

import java.io.Serializable;
import java.util.Vector;

// Created by Nick
// Edited by Jesse
// Edited by James
public class Player implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9079112669746633237L;
	private String name;
	private int wins;
	private int gamesPlayed; //changed from loses to gamesPlayed in accord to database 
	private int money;
	private boolean inJail;
	private Vector<Property> properties;
	private int jailCards;
	private int currentLocation;
	private int gameToken;
	private int ID; //added player ID 
	public Player(String name, int wins, int gamesPlayed, int ID) {
		this.name = name;
		this.wins = wins;
		this.gamesPlayed = gamesPlayed;
		this.ID = ID;
		money = 0;
		inJail = false;
		properties = new Vector<Property>();
		jailCards = 0;
		currentLocation = 0;
	}
	
	public String getName() {
		return name;
	}
	
	public int getWins() {
		return wins;
	}
	
	public int getGamesPlayed() {
		return gamesPlayed;
	}
	
	public int getMoney() {
		return money;
	}
	
	public boolean isInJail() {
		return inJail;
	}
	
	public void setInJail(boolean b) {
		inJail = b;
	}
	
	public Vector<Property> getProperties() {
		return properties;
	}
	
	public int getJailCards() {
		return jailCards;
	}
	
	public void setJailCards(int num) {
		jailCards = num;
	}

	public int getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(int currentLocation) {
		this.currentLocation = currentLocation;
	}

	public int getGameToken() {
		return gameToken;
	}

	public void setGameToken(int gameToken) {
		this.gameToken = gameToken;
	}
}
