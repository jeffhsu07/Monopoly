package main;

import java.util.ArrayList;

import client.MainWindow;
import resources.Player;;

public class Main {
	public static void main (String [] args){
		//new LoginWindow().setVisible(true);
		
		// For Testing Main Window
		ArrayList<Player> player = new ArrayList<Player>();
		player.add(new Player("Jesse",0,0));
		player.add(new Player("Michael",0,0));
		player.add(new Player("Jen",0,0));
		player.add(new Player("Zach",0,0));
		player.add(new Player("Mum",0,0));
		new MainWindow(player).setVisible(true);
	}
}
