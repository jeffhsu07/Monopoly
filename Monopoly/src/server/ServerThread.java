package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import resources.LoginInfo;
/*-----------------------------------------
 * Author: James Su
 * 
 * 
 * 
 * 
 */
public class ServerThread extends Thread  {
	
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private int ID = -1; //after player login as user or guest, he is given an id from 1 to 8
	private Server server;
	private Socket s;
	private String clientName;
	public ServerThread(Socket s, Server server) {
		try {
			this.server = server;
			this.s = s;
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());
			sendMessage(s.getInetAddress() + ", you are connected");
			this.start();  //start this thread after client is connected 
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
	}
	
	//send message to the client that is connected to this server thread
	public void sendMessage(String message) {
		try {
			oos.writeObject(message);
			oos.flush();
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
	}
	
	public void run(){
		try {
			while(true) {
				Object obj = (Object ) ois.readObject();
				if(obj instanceof String){
					String message = (String)obj;
					interpretMessage(message);
				}else if(obj instanceof LoginInfo){//user login or new account login
					LoginInfo loginInfo = (LoginInfo)obj;
					verifyOrCreateUser(loginInfo);
				}
		
			}
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe in run: " + cnfe.getMessage());
		} catch (IOException ioe) {
			System.out.println("ioe in run: " + ioe.getMessage());
		}
		
	}
	
	private void interpretMessage(String message){
		if(message.contains("Guest Login: ")){	//guest login
			server.addToPalyerThread(this);
			server.setID(this);
			server.addGuestName(this); //set guest name from server
			server.sendMessageToAllOtherClients(message+clientName, this); //send this guest client's name to all other clients
			
			//send player information they need to get to the start window 
		}else if(message.contains("Client Logout: ")){// a player log out during starting game gui or main gui
			server.sendMessageToAllOtherClients(message+ clientName, this); // tell all other client that this client logs out with its ID number
			server.removeFromPlayerThread(this);
			
		}else if(message.contains("StartGame")){
			//the host(first player joined) clicked start game
			//TODO
			//send new ID to all players in actual player list 
			
		}else{
			server.sendMessageToAllOtherClients(message, this); //if it is other message,  send to other players
		}
	}
	
	
	//verify the user or create an account for the user 
	private void verifyOrCreateUser(LoginInfo loginInfo){
		if(loginInfo.isLogin()){ // if client clicked login, verify account
			boolean match = server.verifyUser(loginInfo.getUsername(), loginInfo.getPassword());
			if(match){
				clientName = loginInfo.getUsername();
				server.sendMessageToAllOtherClients("User Login: "+ clientName, this );	
				server.addToPalyerThread(this);
				server.setID(this); //server generate an id for this user
			}else{
				sendMessage("Login deny, retype your username and password");
			}
		}else{ //if the client clicked create account, create an account
			boolean succeed = server.createUser(loginInfo.getUsername(), loginInfo.getPassword());
			if(succeed){
				clientName = loginInfo.getUsername();
				server.sendMessageToAllOtherClients("User Login: "+ clientName, this );	
				server.addToPalyerThread(this);
				server.setID(this);
			}else{
				sendMessage("Creating account deny, try a different name");
			}
		}
	}
	

	public String getClientName(){
		return clientName;
	}
	public void setClientName(String name){
		clientName = name;
	}
	public void setServerThreadID(int id){
		ID = id;
	}
	public int getServerThreadID(){
		return ID;
	}
}
