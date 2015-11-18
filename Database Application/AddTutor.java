package sqlDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AddTutor 
{
	Connection m_dbConn = JDBC.dbConn;
	
	public AddTutor(Connection dbConn) 
	{
		m_dbConn = dbConn;
	}

	public void addTutor()
	{
		Scanner in = new Scanner(System.in);
		
		try
		{
			m_dbConn = DriverManager.getConnection(JDBC.dbName, "pxp420", "Birmingham123");
			
			PreparedStatement studentQuery = m_dbConn.prepareStatement(" SELECT studentID " + " FROM Student ");
			ResultSet rs = studentQuery.executeQuery();
			
			//Populate the Student Table with a new row
			System.out.println("Insert studentID");
			int studentID = in.nextInt();
			
			boolean studentExists = false;
			
			while (rs.next())
			{
				int sID = rs.getInt(1);
				
				if (sID == studentID)
				{
					studentExists = true;
				}
			}
			
			if ( studentExists == true )
			{
				System.out.println("Select a new tutor for the student");
				System.out.println("1 - Mark, 2 - Volker, 3 - Jon, 4 - Martin, 5 - Achim");
				int lecturerID = in.nextInt();

				while (lecturerID < 1 || lecturerID > 5)
				{
					System.err.println("Enter a number between 1 and 5");
					System.out.println();
					System.out.println("1 - Mark, 2 - Volker, 3 - Jon, 4 - Martin, 5 - Achim");
					lecturerID = in.nextInt();  
				}
				
				PreparedStatement updateQuery = m_dbConn.prepareStatement("UPDATE Tutor " + " SET lecturerID = ?" + " WHERE studentID = ? ");
				
				updateQuery.setInt(1, lecturerID);
				updateQuery.setInt(2, studentID);
				updateQuery.execute();
				
				System.out.println();
				System.out.println("Student's tutor Updated");
			}
			else
			{
				System.out.println();
				System.err.println("Cannot add a new tutor for a non-existent student");
			}
		}
		catch(SQLException exception)
		{
			exception.printStackTrace();
		}
		
		finally
		{
			try
			{
				m_dbConn.close();
				System.out.println();
			}
			catch (SQLException exception)
			{
				exception.printStackTrace();
				System.err.println("Couldn't close the connection");
			}
		}
	}
}
