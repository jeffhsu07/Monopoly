package resources;
/**
 * CSCI 201 Final Project
 * Group 14:
 *                 Monopoly
 * Team Members:
 *                 Matthew van Niekerk
 *                 Jesse Werner
 *                 Brandon Ho
 *                 Nicholas Terrile
 *                 Kuiren "James" Su
 *                 Chin-Yuan "Jeffrey" Hsu
 */


import java.util.ArrayList;

import client.ProgressArea;
import utilities.Constants;

public class CommunityChestStuff 
{
	private int deckPosition;
	private ArrayList<Player> players;
	private ProgressArea progress;
	
	public CommunityChestStuff(ArrayList<Player> woo, ProgressArea lesgo)
	{
		deckPosition = 0;
		players = woo;
		progress = lesgo;
	}

	public void handleCommunity(Player p)
	{
		progress.addProgress(p.getName() + " has drawn a community chest card that reads: ");
		if(deckPosition == 17)
			deckPosition = 0;
		
		if(deckPosition == 0)
		{
			progress.addProgress("Advance to Go \n");
			p.setCurrentLocation(0);
			p.addMoney(Constants.goMoney);
		}
		else if(deckPosition == 1)
		{
			p.addMoney(75);
		}
		else if(deckPosition == 2)
		{
			p.addMoney(-50);
		}
		else if(deckPosition == 3)
		{
			p.setJailCards(p.getJailCards() + 1);
		}
		else if(deckPosition == 4)
		{
			p.setCurrentLocation(10);
			p.setInJail(true);
		}
		else if(deckPosition == 5)
		{
			for(Player aa : players)
			{
				if(!aa.getName().equals(p.getName()))
				{
					aa.addMoney(-10);
					p.addMoney(10);
				}
			}
		}
		else if(deckPosition == 6)
		{
			for(Player aa : players)
			{
				if(!aa.getName().equals(p.getName()))
				{
					aa.addMoney(-50);
					p.addMoney(50);
				}
			}
		}
		else if(deckPosition == 7)
		{
			p.addMoney(20);
		}
		else if(deckPosition == 8)
		{
			p.addMoney(100);
		}
		else if(deckPosition == 9)
		{
			p.addMoney(-100);
		}
		else if(deckPosition == 10)
		{
			p.addMoney(-50);
		}
		else if(deckPosition == 11)
		{
			p.addMoney(25);
		}
		else if(deckPosition == 12)
		{
			int owed = 0;
			for(Property a : p.getProperties())
			{
				if(a.getHotel())
					owed += 115;
				else
				{
					owed += a.getNumHouses() * 40;
				}
			}
			p.addMoney(-owed);
		}
		else if(deckPosition == 13)
		{
			p.addMoney(10);
		}
		else if(deckPosition == 14)
		{
			p.addMoney(100);
		}
		else if(deckPosition == 15)
		{
			p.addMoney(45);
		}
		else if(deckPosition == 16)
		{
			p.addMoney(100);
		}
		deckPosition++;
	}
	
}
