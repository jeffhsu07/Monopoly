package main;

import java.util.ArrayList;

import client.Client;
import client.LoginWindow;
import client.MainWindow;
import resources.Player;;

public class Main {
	public static void main (String [] args){
		Client client = new Client();
		client.start();
		new LoginWindow(client).setVisible(true);
		
		Client client2 = new Client();
		client2.start();
		new LoginWindow(client2).setVisible(true);
		/*
		// For Testing Main Window
		ArrayList<Player> player = new ArrayList<Player>();
		Player p1 = new Player("Jesse",0,0,1);
		p1.addMoney(1000);
		p1.setGameToken(0);
		p1.setCurrentLocation(0);
		player.add(p1);
		
		Player p2 = new Player("Michael",0,0,2);
		p2.addMoney(0);
		p2.setGameToken(6);
		p2.setCurrentLocation(0);
		player.add(p2);
		
		Player p3 = new Player("Jen",0,0,3);
		p3.addMoney(1000);
		p3.setGameToken(2);
		p3.setCurrentLocation(0);
		player.add(p3);
		
		Player p4 = new Player("Zach",0,0,4);
		p4.addMoney(1000);
		p4.setGameToken(3);
		p4.setCurrentLocation(0);
		player.add(p4);
		
		Player p5 = new Player("Mum",0,0,5);
		p5.addMoney(1000);
		p5.setGameToken(4);
		p5.setCurrentLocation(0);
		player.add(p5);
		
		new MainWindow(player).setVisible(true);
		
		//new LoginWindow().setVisible(true);
		*/
	}
}
