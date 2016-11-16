package resources;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

import com.mysql.jdbc.Driver;


public class JDBCDriver {
	private Connection con;
	private final static String selectName = "SELECT * FROM USERNAMEANDPASSWORD WHERE MUSERNAME=?"; //need to be changed TODO
	private final static String addProduct = "INSERT INTO USERNAMEANDPASSWORD(MUSERNAME, MPASSWORD) VALUES(?,?)"; // need to be changed TODO
	public JDBCDriver()
	{
		try{
			new Driver();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public void connect()
	{
		try{
			//need to be changed TODO
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeopardy?user=root&password=kuirensu&useSSL=false");//pass word need to be changed
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public void stop()
	{
		try{
			con.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public boolean doesExist(String username)
	{
		try{
			PreparedStatement ps = con.prepareStatement(selectName);
			ps.setString(1, username);
			ResultSet result = ps.executeQuery();
			while(result.next())
			{
				System.out.println(result.getString(1) + " exists with password " + result.getString(2));
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Unable to find userbame with name: " + username);
		return false;
	} 
	
	public String getPassword(String username){
		try{
			PreparedStatement ps = con.prepareStatement(selectName);
			ps.setString(1, username);
			ResultSet result = ps.executeQuery();
			while(result.next())
			{
				System.out.println("password is: " + result.getString(2));
				return result.getString(2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Unable to find userbame with name: " + username);
		return null;
	}
	
	public void add(String username, String password)
	{
		try {
			PreparedStatement ps = con.prepareStatement(addProduct);
			ps.setString(1, username);
			ps.setString(2, password);
			ps.executeUpdate();
			System.out.println("Adding username: " + username + " with password "+ password);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	/*
	public static void main(String args[]){
		JDBCDriver jDBCDriver = new JDBCDriver();
		jDBCDriver.connect();
		jDBCDriver.doesExist("1");
		jDBCDriver.add("1", "2");
		jDBCDriver.doesExist("1");
		jDBCDriver.stop();
	}
	*/
}
