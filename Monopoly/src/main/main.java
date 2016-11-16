package main;
<<<<<<< HEAD
import client.ManagePropertiesWindow;
import client.ManageBuildingsWindow;
import client.PlayerInformationWindow;
public class main {
	public static void main(String [] args){
		PlayerInformationWindow mpw = new PlayerInformationWindow();
		mpw.setVisible(true);
=======

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
>>>>>>> 3027365277ab3be3e728f9d5526ffdb41fcdfcaa
	}
	
}
