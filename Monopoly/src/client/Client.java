package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import resources.LoginInfo;
import resources.Player;
import resources.Property;
import server.Server;
import utilities.Constants;
/*-----------------------------------------
 * Author: James Su
 * 
 * 
 * 
 * 
 */
public class Client extends Thread{
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Socket s = null;
	private int thisPlayerID = -1;
	private String thisPlayerName; //set to login user name or guest name when assigned 
	//private ArrayList<String> teamNames; 
	private ArrayList<Player> playerList;
	private LoginWindow loginWindow;
	private StartWindow startWindow;
	private MainWindow mainWindow;
	public Client() {
		
		try {
			//teamNames = new ArrayList<String>();
			s = new Socket(Constants.defaultHostname, Constants.defaultPort);
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());
			
			
		} catch (IOException ioe) {
			System.out.println("ioe construct: " + ioe.getMessage());
		}
	}
	public void sendMessage(String message) {
		try {
			oos.writeObject(message);
			oos.flush();
		} catch (IOException ioe) {
			System.out.println("ioe sending message: " + ioe.getMessage());
		}
	}
	
	//send login information to server, whether an existing user or new registered user
	public void sendLoginInfo(LoginInfo loginInfo){
		try {
			oos.writeObject(loginInfo);
			oos.flush();
		} catch (IOException ioe) {
			System.out.println("ioe sending message: " + ioe.getMessage());
		}
	}
	
	
	
	//stop listening to server
	public void endClient(){
		try {
			if (s != null) {
				s.close();
			}
		} catch (IOException ioe) {
			System.out.println("ioe in end: " + ioe.getMessage());
		}
	
	}
	public void run() {
		try {
			while(true) {
				Object obj = (Object ) ois.readObject();
				if(obj instanceof String){
					String message = (String)obj;
					System.out.println("Got message: " + message);
					interpretMessage(message);
					//oos.writeObject("OK");
					//oos.flush();
				}else if (obj instanceof ArrayList<?>){//checking what arraylist contains, if contain strings then its otherplayerInfo, else is playerlist
					if(((ArrayList<?>)obj).size()!=0){
						if(((ArrayList<?>)obj).get(0) instanceof String){ //after login success, server send otherplayerInfo to consctuct a startwindow
							ArrayList<String> otherPlayerInfo = (ArrayList<String>)obj; //pass to startwindow to initialize it 
							loginWindow.setVisible(false);
							startWindow = new StartWindow(thisPlayerName, otherPlayerInfo, this);
							startWindow.setVisible(true);
						}else if(((ArrayList<?>)obj).get(0) instanceof Player){
							playerList = (ArrayList<Player>)obj; //send array of players maybe??
							synchronized(playerList){
								for (Player p : playerList) {
									System.out.println("Players' names are: " + p.getName());
								}
							}
							mainWindow = new MainWindow(playerList);
							//start main game gui
						}
					}else{//this player is the first player who logged in, button in startwindow should be set to start instead of ready
						loginWindow.setVisible(false);
						startWindow = new StartWindow(thisPlayerName, null, this);
						startWindow.setVisible(true);
						
					}
				}
			}
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		} catch (IOException ioe) {
			System.out.println("ioe in run: " + ioe.getMessage());
		}
	}
	private void interpretMessage(String message){
		if(message.contains("Login success: ")){
			System.out.println(message);
			message = message.replace("Login success: ", "");
			message = message.trim();
			thisPlayerName = message; //set this player's name 
		}else if(message.contains("Login deny")){
			System.out.println(message);
		}else if(message.contains("Creating account success")){
			System.out.println(message);
		}else if(message.contains("Creating account deny")){
			System.out.println(message);
		}else if(message.contains("Guest name: ")){ //guest doesn't have a name so we give them one
			message = message.replace("Guest name: ", "");
			message = message.trim();
			thisPlayerName = message;
		}else if (message.contains("Update ID: ")){
			message = message.replace("Update ID:", "");
			message = message.trim();
			int id = Integer.parseInt(message);
			thisPlayerID = id;
		}
		else if(message.contains("Guest Login: ") && startWindow != null){
			message = message.replace("Guest Login: ", "");
			message = message.trim();
			String guestName = message;
			//TODO
			startWindow.userJoined(guestName);
			//do something after a Guest logs in 
		}else if(message.contains("User Login: ") && startWindow != null){
			message = message.replace("User Login: ", "");
			message = message.trim();
			String username = message;
			//TODO
			startWindow.userJoined(username);
			//do something after a User logs in 
		}else if(message.contains("::Picked Token::") && startWindow != null){ //ClientName::Picked Token::TokenID
			String[] command = message.split("::");
			String clientName = command[0];
			int tokenID = Integer.parseInt(command[2]);
			//TODO
			startWindow.refreshPlayer(clientName, tokenID);
			//do something after the client picked a token
			//let other players know
			//ready to start unless quit
		}else if(message.contains("Ready: ") && startWindow != null){
			if(thisPlayerID ==1){
				//TODO
				//if this client is host do sth
			}else{
				System.out.println(message);
			}
		}else if(message.contains("StartGame") && startWindow != null){
			//obtain the correct teamnames and team id at this point 
			//TODO 
			//start the game 
			
		}else if(message.contains("EndTurn: ") && mainWindow != null ){
			message = message.replace("EndTurn: ", "");
			message = message.trim();
			int clientID = Integer.parseInt(message);
			//TODO
			//after client end his turn, proceed to another player
		}else if(message.contains("::RolledForTurn::") && mainWindow != null){ //at the begining of the game decide turns
			
			String[] command = message.split("::");
			int clientID = Integer.parseInt(command[0]);
			int diceRolled = Integer.parseInt(command[2]);
		}else if(message.contains("::Rolled::") && mainWindow != null){ //ID::Rolled::number
			String[] command = message.split("::");
			int clientID = Integer.parseInt(command[0]);
			int diceRolled = Integer.parseInt(command[2]);
			//TODO
			//do somthing when player rooled dice 
		}else if(message.contains("::PurchasedProperty::") && mainWindow != null){
			String[] command = message.split("::");
			int clientID = Integer.parseInt(command[0]);
			int propertyID = Integer.parseInt(command[2]);
			//TODO
		}else if(message.contains("::PurchasedHouse::") && mainWindow != null){
			String[] command = message.split("::");
			int clientID = Integer.parseInt(command[0]);
			int propertyID = Integer.parseInt(command[2]); //house on the property
			Player currentPlayer = mainWindow.getPlayerList().get(clientID-1);
			Property property = mainWindow.getPropertiesArray()[propertyID];
			currentPlayer.subtractMoney(property.getHouseCost());
			mainWindow.updateProgressArea(currentPlayer.getName() + " built a house on " + property.getName()); 
			mainWindow.updateProgressArea("Total number of houses on " + property.getName() + ": " + property.getNumHouses());
			mainWindow.repaint();
			//TODO
		}else if(message.contains("::MortgagedProperty::") && mainWindow != null){
			String[] command = message.split("::");
			int clientID = Integer.parseInt(command[0]);
			int propertyID = Integer.parseInt(command[2]);
			mainWindow.getPropertiesArray()[propertyID].setMortgaged(true);
			mainWindow.getPlayerList().get(clientID-1).addMoney(mainWindow.getPropertiesArray()[propertyID].getMortgageValue());
			mainWindow.updateProgressArea(mainWindow.getPlayerList().get(clientID-1).getName() +
					" mortgaged " + mainWindow.getPropertiesArray()[propertyID].getName());
			//TODO
		}else if(message.contains("::ReclaimedProperty::") && mainWindow != null){
			String[] command = message.split("::");
			int clientID = Integer.parseInt(command[0]);
			int propertyID = Integer.parseInt(command[2]);
			mainWindow.getPropertiesArray()[propertyID].setMortgaged(false);
			mainWindow.getPlayerList().get(clientID-1).subtractMoney(mainWindow.getPropertiesArray()[propertyID].getMortgageValue());
			mainWindow.updateProgressArea(mainWindow.getPlayerList().get(clientID-1).getName() +
										" reclaimed " + mainWindow.getPropertiesArray()[propertyID].getName());
			//TODO
		}else if(message.contains("::MortgagedHouse::") && mainWindow != null){
			String[] command = message.split("::");
			int clientID = Integer.parseInt(command[0]);
			int propertyID = Integer.parseInt(command[2]); //house on the property
			Player currentPlayer = mainWindow.getPlayerList().get(clientID-1);
			Property property = mainWindow.getPropertiesArray()[propertyID];
			currentPlayer.addMoney(property.getSellHouseCost());
			mainWindow.updateProgressArea(currentPlayer.getName() + " sold a house on " + property.getName()); 
			mainWindow.updateProgressArea("Total number of houses on " + property.getName() + ": " + property.getNumHouses());
			mainWindow.repaint();
			//TODO
		}else if(message.contains("::DrewCard::") && mainWindow != null){
			String[] command = message.split("::");
			int clientID = Integer.parseInt(command[0]);
			int cardID = Integer.parseInt(command[2]); //house on the property
			//TODO
			
		}else if(message.contains("")){
			
		}else if(message.contains("")){
			
		}else if(message.contains("")){
			
		}else if(message.contains("")){
			
		}else if(message.contains("")){
			
		}else if(message.contains("You are connected")){
			System.out.println("You are connected");
		}else if(message.contains("Client Logout: ")){
			message = message.replace("Client Logout: ", "");
			message = message.trim();
			String username = message;
			//TODO
			if(startWindow != null){// a client in startwindow logs out 
				
			}else if(mainWindow != null){// a client in mainwindow logs out 
				
			}
			//do something if client logs out 
		}
	}
	
	
	
	
	public void setLoginWindow(LoginWindow loginWindow ){
		this.loginWindow = loginWindow;
	}
	
	public static void main(String args[]){
		
		Client client = new Client();
		client.start();
		new LoginWindow(client).setVisible(true);
	}
}
