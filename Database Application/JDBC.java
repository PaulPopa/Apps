package sqlDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC 
{
	public static String dbName = "jdbc:postgresql://dbteach2/pxp420";
	public static Connection dbConn;
	
	public static Console console = new Console(dbConn);
	
	public static void main(String[] args) throws SQLException
	{	
		try 
		{
			Class.forName("org.postgresql.Driver");
		} 
		catch (ClassNotFoundException ex) 
		{
			System.out.println("Driver not found");
		}
		
		System.out.println("PostgreSQL driver registered.");
		Connection conn = null;
		
		try 
		{
			conn = DriverManager.getConnection(dbName, "pxp420", "Birmingham123");
		} 
		catch (SQLException ex) 
		{
			ex.printStackTrace();
		}
			
		if (conn != null) 
		{
			System.out.println("Database accessed!");
		} 
		else 
		{
			System.out.println("Failed to make connection");
		}
		
		//OPENS THE CONSOLE
		console.selectOption();
	
	}
}
