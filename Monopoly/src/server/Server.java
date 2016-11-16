package server;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import resources.JDBCDriver;
import utilities.Constants;
/*-----------------------------------------
 * Author: James Su
 * 
 * 
 * 
 * 
 */
public class Server extends Thread{
	private ServerSocket ss = null;
	private boolean stop;  //when set to true stop running thread, stop accepting more players   
	private ArrayList<ServerThread> serverThreads; 
	private ArrayList<ServerThread> actualPlayerTheads; //keep track of player who actually logs in order
	private JDBCDriver jDBCDriver;

	private ArrayList<String> teamNames; 
	private int numberOfGuests = 1; //used to assign guest name 
	public Server(){
		try{
			ss = new ServerSocket(Constants.defaultPort);
			serverThreads = new ArrayList<ServerThread>();
			actualPlayerTheads = new ArrayList<ServerThread>();
			stop = false;
			jDBCDriver = new JDBCDriver();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	//send message to all other clients except the one who is doing the action
	public void sendMessageToAllOtherClients(String message, ServerThread serverThread) {
		synchronized(actualPlayerTheads){
			for (ServerThread st : actualPlayerTheads) {
				if(st !=serverThread ){
					st.sendMessage(message);				
				}
			}
		}
		
	}
	
	public void run(){
		try{
			while (!stop && actualPlayerTheads.size() != 8 ) {
				System.out.println("waiting for connection...");
				Socket s = ss.accept();
				System.out.println("connection from " + s.getInetAddress());
				ServerThread st = new ServerThread(s, this);
				st.sendMessage("You are connected");
				serverThreads.add(st);
				
				//serverThreadID ++;
			}
		}catch(BindException be) {
			System.out.println("be: " + be.getMessage());
		}catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		} 
	}
	//check login info match database
	public boolean verifyUser(String username, String password){
		jDBCDriver.connect();
		
		String mUsername = username;
		String mPassword = password;
		
		if(!jDBCDriver.doesExist(mUsername)){
			System.out.println("This username does not exist.");
			jDBCDriver.stop();
			return false;
		}
		else{
			
			//if the user gave the wrong password
			if (!mPassword.equals(jDBCDriver.getPassword(mUsername))){
				System.out.println("Wrong password.");
				jDBCDriver.stop();
				return false;
			}
			//login successful
			else{
				jDBCDriver.stop();
				return true;
			}
		}	
		
	}
	//create account if username valid 
	public boolean createUser(String username, String password){
		jDBCDriver.connect();
		String mUsername = username;
		String mPassword = password;
		//check if username has been created
		if(jDBCDriver.doesExist(mUsername)){
			System.out.println("This username already exist.");
			jDBCDriver.stop();
			return false;
		}
		//username has not been created, create account and login
		else{
			jDBCDriver.add(mUsername, mPassword);
			jDBCDriver.stop();
			return true;
		}
	}
	
	//start a new game 
	public void refreshServer(){
		serverThreads.clear();
		stop = false;
		run();
	}
	
	//end server thread 
	public void endServer(){
		if (ss != null) {
			try {
				ss.close();
			} catch (IOException ioe) {
				System.out.println("ioe closing ss: " + ioe.getMessage());
			}
		}
	}
	//give an id to the login user or guest, ID is based on the capacity of arraylist
	public void setID(ServerThread serverThread){
		if(actualPlayerTheads.size() <= 8){
			int serverThreadID = actualPlayerTheads.size() ;
			serverThread.setServerThreadID(serverThreadID);
		}else{
			System.out.println("exceed server capacity");
		}
		
	}
	
	//if a player logs out during the game, update all players' serverThreadID 
	
	public void removeFromPlayerThread(ServerThread st){
		if(st != null){
			synchronized(teamNames){
				teamNames.remove(st.getClientName());
			}
			synchronized(actualPlayerTheads){
				actualPlayerTheads.remove(st);
			}
		}else {
			return;
		}
		int newThreadID = 1;
		synchronized(actualPlayerTheads){
			for (ServerThread playerThead:actualPlayerTheads ){
				playerThead.setServerThreadID(newThreadID);
				newThreadID++;	
			}
		}
		
		
	}
	
	//add playerthread when player logs in or play as guest
	public void addToPalyerThread(ServerThread st){
		if(st != null){
			synchronized(actualPlayerTheads){
				actualPlayerTheads.add(st);
				teamNames.add(st.getClientName());
			}
		}
		
	}
	
	public void addGuestName(ServerThread st){
		String newGuestName = "Guest" + Integer.toString(numberOfGuests);
		teamNames.add(newGuestName);
		numberOfGuests++;
		st.setName(newGuestName);
	}
	
	
	
	public static void main(String args[]){
		new Server().start();
		
	}

}
