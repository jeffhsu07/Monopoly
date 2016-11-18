package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import resources.LoginInfo;
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
	private ArrayList<String> teamNames; 
	public Client() {
		
		try {
			teamNames = new ArrayList<String>();
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
				}
			}
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		} catch (IOException ioe) {
			System.out.println("ioe in run: " + ioe.getMessage());
		}
	}
	private void interpretMessage(String message){
		//TODO
		if(message.contains("Login success")){
			System.out.println(message);
		}else if(message.contains("Login deny")){
			System.out.println(message);
		}else if(message.contains("Creating account success")){
			System.out.println(message);
		}else if(message.contains("Creating account deny")){
			System.out.println(message);
		}else if (message.contains("Update ID: ")){
			message = message.replace("Update ID:", "");
			message = message.trim();
			int id = Integer.parseInt(message);
			thisPlayerID = id;
		}
		else if(message.contains("Guest Login: ")){
			message = message.replace("Guest Login: ", "");
			message = message.trim();
			String guestName = message;
			//TODO
			//do something after a Guest logs in 
		}else if(message.contains("User Login: ")){
			message = message.replace("User Login: ", "");
			message = message.trim();
			String username = message;
			//TODO
			//do something after a User logs in 
		}else if(message.contains("Client Logout: ")){
			message = message.replace("Client Logout: ", "");
			message = message.trim();
			String username = message;
			//TODO
			//do something if client logs out 
		}else if(message.contains("::Picked Token::")){ //ClientName::Picked Token::TokenID
			String[] command = message.split("::");
			String clientName = command[0];
			int tokenID = Integer.parseInt(command[2]);
			//TODO
			
			//do something after the client picked a token
			//let other players know
			//ready to start unless quit
		}else if(message.contains("Ready: ")){
			if(thisPlayerID ==1){
				//TODO
				//if this client is host do sth
			}else{
				System.out.println(message);
			}
		}else if(message.contains("StartGame")){
			//obtain the correct teamnames and team id at this point 
			//TODO 
			//start the game 
			
		}else if(message.contains("EndTurn: ")){
			message = message.replace("EndTurn: ", "");
			message = message.trim();
			int clientID = Integer.parseInt(message);
			//TODO
			//after client end his turn, proceed to another player
		}else if(message.contains("::RolledForTurn::")){ //at the begining of the game decide turns
			
			String[] command = message.split("::");
			int clientID = Integer.parseInt(command[0]);
			int diceRolled = Integer.parseInt(command[2]);
		}else if(message.contains("::Rolled::")){ //ID::Rolled::number
			String[] command = message.split("::");
			int clientID = Integer.parseInt(command[0]);
			int diceRolled = Integer.parseInt(command[2]);
			//TODO
			//do somthing when player rooled dice 
		}else if(message.contains("::PurchasedProperty::")){
			String[] command = message.split("::");
			int clientID = Integer.parseInt(command[0]);
			int propertyID = Integer.parseInt(command[2]);
			//TODO
		}else if(message.contains("::PurchasedHouse::")){
			String[] command = message.split("::");
			int clientID = Integer.parseInt(command[0]);
			int propertyID = Integer.parseInt(command[2]); //house on the property
			//TODO
		}else if(message.contains("::MortgagedProperty::")){
			String[] command = message.split("::");
			int clientID = Integer.parseInt(command[0]);
			int propertyID = Integer.parseInt(command[2]);
			//TODO
		}else if(message.contains("::MortgagedHouse::")){
			String[] command = message.split("::");
			int clientID = Integer.parseInt(command[0]);
			int propertyID = Integer.parseInt(command[2]); //house on the property
			//TODO
		}else if(message.contains("::DrewCard::")){
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
		}
	}
	
	public static void main(String args[]){
		
		Client client = new Client();
		client.start();
		new LoginWindow(client).setVisible(true);
	}
}
