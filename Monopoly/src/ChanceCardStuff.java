import resources.Player;
import resources.Property;
import utilities.Constants;

public class ChanceCardStuff 
{
	
	private int deckPosition;
	
	public ChanceCardStuff()
	{
		deckPosition = 0;
	}

	public void handleChance(Player p)
	{
		if(deckPosition == 16)
			deckPosition = 0;
		
		if(deckPosition == 0)
		{
			p.addMoney(Constants.goMoney);
			p.setCurrentLocation(0);
		}
		else if(deckPosition == 1)
		{
			if(p.getCurrentLocation() > 24)
				p.addMoney(Constants.goMoney);
			p.setCurrentLocation(24);
		}
		else if(deckPosition == 2)
		{
			if(p.getCurrentLocation() >= 13 && p.getCurrentLocation() < 29)
			{
				p.setCurrentLocation(29);
			}
			else if(p.getCurrentLocation() <= 39)
			{
				p.setCurrentLocation(13);
				p.addMoney(Constants.goMoney);
			}
			else
				p.setCurrentLocation(13);
		}
		else if(deckPosition == 3)
		{
			//5, 15, 25, 35
			if(p.getCurrentLocation() < 5 )
			{
				p.setCurrentLocation(5);
			}
			else if(p.getCurrentLocation() > 35)
			{
				p.setCurrentLocation(5);
				p.addMoney(Constants.goMoney);
			}
			else if(p.getCurrentLocation() < 15)
			{
				p.setCurrentLocation(15);
			}
			else if(p.getCurrentLocation() < 25)
			{
				p.setCurrentLocation(25);
			}
			else 
			{
				p.setCurrentLocation(35);
			}
		}
		else if(deckPosition == 4)
		{
			if(p.getCurrentLocation() > 11)
			{
				p.addMoney(Constants.goMoney);
			}
		
			p.setCurrentLocation(11);
		}
		else if(deckPosition == 5)
		{
			p.addMoney(50);
		}
		else if(deckPosition == 6)
		{
			p.setJailCards(p.getJailCards() + 1);
		}
		else if(deckPosition == 7)
		{
			if(p.getCurrentLocation() >= 3)
			{
				p.setCurrentLocation(p.getCurrentLocation() - 3);
			}
			else
			{
				if(p.getCurrentLocation() == 2)
					p.setCurrentLocation(39);
				else if(p.getCurrentLocation() == 1)
					p.setCurrentLocation(38);
				else if(p.getCurrentLocation() == 0)
					p.setCurrentLocation(37);
			}
			
		}
		else if(deckPosition == 8)
		{
			p.setCurrentLocation(10);
			p.setInJail(true);
		}
		else if(deckPosition == 9)
		{
			int owed = 0;
			for(Property a : p.getProperties())
			{
				if(a.getHotel())
					owed += 100;
				else
				{
					owed += a.getNumHouses() * 25;
				}
			}
			p.addMoney(-owed);
		}
		else if(deckPosition == 10)
		{
			p.addMoney(-15);
		}
		else if(deckPosition == 11)
		{
			if(p.getCurrentLocation() >= 5)
			{
				p.addMoney(Constants.goMoney);
			}
			p.setCurrentLocation(5);
		}
		else if(deckPosition == 15)
		{
			p.addMoney(100);
		}
		
		deckPosition++;
	}
}
