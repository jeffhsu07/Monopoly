package utilities;
	
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

public class Constants {
	
	public static final int defaultPort = 1234;
	public static final String defaultHostname = "localhost";
	public static ArrayList<Integer> propertyLocations = new ArrayList<>(Arrays.asList(1,3,// Group 1
			6,8,9, 		// Group 2
			11,13,14,	// Group 3
			16,18,19,	// Group 4
			21,23,24,	// Group 5
			26,27,29,	// Group 6
			31,32,34,	// Group 7
			37,39));	// Group 8
	
	// Properties Separated by group for monopoly checking
	public static ArrayList<Integer> group1Locations = new ArrayList<>(Arrays.asList(1,3));
	public static ArrayList<Integer> group2Locations = new ArrayList<>(Arrays.asList(6,8,9));
	public static ArrayList<Integer> group3Locations = new ArrayList<>(Arrays.asList(11,13,14));
	public static ArrayList<Integer> group4Locations = new ArrayList<>(Arrays.asList(16,18,19));
	public static ArrayList<Integer> group5Locations = new ArrayList<>(Arrays.asList(21,23,24));
	public static ArrayList<Integer> group6Locations = new ArrayList<>(Arrays.asList(26,27,29));
	public static ArrayList<Integer> group7Locations = new ArrayList<>(Arrays.asList(31,32,34));
	public static ArrayList<Integer> group8Locations = new ArrayList<>(Arrays.asList(37,39));
	
	// Properties Color for each monopoly group
	public static Color group1Color = Color.MAGENTA;
	public static Color group2Color = Color.CYAN;
	public static Color group3Color = Color.PINK;
	public static Color group4Color = Color.ORANGE;
	public static Color group5Color = Color.RED;
	public static Color group6Color = Color.YELLOW;
	public static Color group7Color = Color.GREEN;
	public static Color group8Color = Color.BLUE;
	
	public static int incomeTax = 200;
	public static int luxuryTax = 75;
	public static int goMoney = 200;
	
	public static int jailLocation = 11;
}
