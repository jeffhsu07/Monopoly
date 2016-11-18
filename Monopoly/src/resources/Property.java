package resources;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;



public class Property {
	private String name;
	private int numHouses;
	private int houseCost;
	private boolean hasHotel;
	private int mortgageValue;
	private Player owner;
	private int price;
	private String group;
	private int[] rentCosts;
	boolean mortgaged;
	int boardPosition; 
	

	public Property(String n, int p, String g, int[] costs, int hCost, int mValue, int bp)
	{
		owner = null;
		
		name = n;
		price = p;
		group = g;
		rentCosts = costs;
		houseCost = hCost;
		mortgageValue = mValue;
		mortgaged = false;
		hasHotel = false;
		numHouses = 0;
		boardPosition = bp;
	}
	
	public void addBuilding()
	{
		if(!group.equals("Utilities")&&!group.equals("Stations"))
		{
			if(numHouses == 4)
			{
				hasHotel = true;
			}
			else
				numHouses++;
		}
		
	}
	
	public void removeBuilding()
	{
		if(!group.equals("Utilities")&&!group.equals("Stations"))
		{
			if(hasHotel = true)
				hasHotel = false;
			else
				numHouses--;
		}
	}

	
	public int getRent()
	{
		if(!group.equals("Utilities")&&!group.equals("Stations"))
		{
			if(hasHotel)
				return rentCosts[4];
			return rentCosts[numHouses];
		}
		return 0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumHouses() {
		return numHouses;
	}

	public void setHouses(int houses) {
		this.numHouses = houses;
	}

	public int getHouseCost() {
		return houseCost;
	}

	public void setHouseCost(int houseCost) {
		this.houseCost = houseCost;
	}

	public boolean getHotel() {
		return hasHotel;
	}
	
	public boolean isMortgaged()
	{
		return mortgaged;
	}
	public void setMortgaged(boolean isMortgaged){
		mortgaged = isMortgaged;
	}
	
	public int getMortgageValue() {
		return mortgageValue;
	}

	public void setMortgageValue(int mortgageValue) {
		this.mortgageValue = mortgageValue;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}
	
	//returns true if successfully builds house
	public boolean addHouse()
	{
		if(numHouses < 4)
		{
			numHouses++;
			return true;
		}
		return false;
	}
}
/* returns array of all properties read in from fileName
private Property[] readFile(String fileName)
{
	BufferedReader br = null;
	Property[] properties = new Property[40];
	try
	{
		br = new BufferedReader(new FileReader(fileName));
		int count = 0;
	
		String line = br.readLine();
		while (line != null) 
		{
			line = line.trim();
			int[] rentPrices = new int[5];
		
			StringTokenizer st = new StringTokenizer(line, "|")
			String name = st.nextToken();
			String group = st.nextToken();
			int price = Integer.parseInt(st.nextToken());
			String rents = st.nextToken();
			StringTokenizer rt = new StringTokenizer(rents, ",");
			for(int i = 0; i < 5; i++)
			{
				rentPrices[i] = rt.nextToken();
			}
			int mValue = Integer.parseInt(st.nextToken());
			int houseCost = Integer.parseInt(st.nextToken());
			int bp = = Integer.parseInt(st.nextToken());
			properties[count] = new Property(name, price, group, rentPrices, houseCost, mValue, bp);
			
			count++;
			line = br.readLine();
		}
	}
	catch (FileNotFoundException fnfe) 
	{
		Util.printExceptionToCommand(fnfe);
	} 
	catch (IOException ioe) 
	{
		Util.printExceptionToCommand(ioe);
	} 
	finally 
	{
		if (br != null) 
		{
			try 
			{
				br.close();
			} 
			catch (IOException ioe1) 
			{
				Util.printExceptionToCommand(ioe1);
			}
		}
	}
	
	return properties;
}
*/
