package resources;

import java.util.Vector;

// Created by Nick
// Edited by Jesse
public class Player {
	private String name;
	private int wins;
	private int losses;
	private int money;
	private boolean inJail;
	private Vector<Property> properties;
	private int jailCards;
	private int currentLocation;
	private int gameToken;
    private boolean bankrupt;
	public Player(String name, int wins, int losses) {
		this.name = name;
		this.wins = wins;
		this.losses = losses;
		money = 0;
		inJail = false;
		properties = new Vector<Property>();
		jailCards = 0;
		currentLocation = 0;
        bankrupt = false;
	}
	
	public String getName() {
		return name;
	}
	
	public int getWins() {
		return wins;
	}
	
	public int getLosses() {
		return losses;
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
	
	public void addProperty(Property property){
		properties.add(property);
	}
    
    public boolean isBankrupt(){
        return bankrupt;
    }
	
	public void addMoney(int amount){
		money += amount;
		if(money <= 0){
			bankrupt = true;
		}
	}
	
	public boolean subtractMoney(int amount){
		if(amount <= money){
			money -= amount;
			return true;
		}
		return false;
	}
}
