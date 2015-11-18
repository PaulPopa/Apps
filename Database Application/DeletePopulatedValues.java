package sqlDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeletePopulatedValues 
{
	Connection m_dbConn = JDBC.dbConn;
	
	public DeletePopulatedValues(Connection dbConn) 
	{
		m_dbConn = dbConn;
	}
	
	//Deletes the values from the Student Table
	public void deleteStudent()
	{
		PreparedStatement deleteStudents;
		try
		{
			m_dbConn = DriverManager.getConnection(JDBC.dbName, "pxp420", "Birmingham123" );
			String sql = "DELETE " + "FROM Student" + " WHERE 1=1";
			
			deleteStudents = m_dbConn.prepareStatement(sql);
			deleteStudents.execute();
			deleteStudents.close();
		}
		catch (SQLException exception)
		{
			exception.printStackTrace();
		}
		
		finally
		{
			try
			{
				m_dbConn.close();
			}
			catch (SQLException exception)
			{
				exception.printStackTrace();
				System.err.println("Couldn't close the connection");
			}
		}
	}
	
	//Deletes the values from The Lecturer Table
	public void deleteLecturer()
	{
		PreparedStatement deleteLecturers;
		try
		{
			m_dbConn = DriverManager.getConnection(JDBC.dbName, "pxp420", "Birmingham123" );
			String sql = "DELETE " + "FROM Lecturer" + " WHERE 1=1";
			
			deleteLecturers = m_dbConn.prepareStatement(sql);
			deleteLecturers.execute();
			deleteLecturers.close();
		}
		catch (SQLException exception)
		{
			exception.printStackTrace();
		}
		
		finally
		{
			try
			{
				m_dbConn.close();
			}
			catch (SQLException exception)
			{
				exception.printStackTrace();
				System.err.println("Couldn't close the connection");
			}
		}
	}
	
	//Deletes the values from StudentRegistration
	public void deleteStudentRegistration()
	{
		PreparedStatement deleteStudentRegistrations;
		try
		{
			m_dbConn = DriverManager.getConnection(JDBC.dbName, "pxp420", "Birmingham123" );
			String sql = "DELETE " + "FROM StudentRegistration" + " WHERE 1=1";
			
			deleteStudentRegistrations = m_dbConn.prepareStatement(sql);
			deleteStudentRegistrations.execute();
			deleteStudentRegistrations.close();
		}
		catch (SQLException exception)
		{
			exception.printStackTrace();
		}
		
		finally
		{
			try
			{
				m_dbConn.close();
			}
			catch (SQLException exception)
			{
				exception.printStackTrace();
				System.err.println("Couldn't close the connection");
			}
		}
	}
	
	//Deletes the values from StudentContact Table
	public void deleteStudentContact()
	{
		PreparedStatement deleteStudentContacts;
		try
		{
			m_dbConn = DriverManager.getConnection(JDBC.dbName, "pxp420", "Birmingham123" );
			String sql = "DELETE " + "FROM StudentContact" + " WHERE 1=1";
			
			deleteStudentContacts = m_dbConn.prepareStatement(sql);
			deleteStudentContacts.execute();
			deleteStudentContacts.close();
		}
		catch (SQLException exception)
		{
			exception.printStackTrace();
		}
		
		finally
		{
			try
			{
				m_dbConn.close();
			}
			catch (SQLException exception)
			{
				exception.printStackTrace();
				System.err.println("Couldn't close the connection");
			}
		}
	}
	
	//Delete the values from NextOfKinContact Table
	public void deleteNextOfKinContact()
	{
		PreparedStatement deleteNextOfKinContacts;
		try
		{
			m_dbConn = DriverManager.getConnection(JDBC.dbName, "pxp420", "Birmingham123" );
			String sql = "DELETE " + "FROM NextOfKinContact" + " WHERE 1=1";
			
			deleteNextOfKinContacts = m_dbConn.prepareStatement(sql);
			deleteNextOfKinContacts.execute();
			deleteNextOfKinContacts.close();
		}
		catch (SQLException exception)
		{
			exception.printStackTrace();
		}
		
		finally
		{
			try
			{
				m_dbConn.close();
			}
			catch (SQLException exception)
			{
				exception.printStackTrace();
				System.err.println("Couldn't close the connection");
			}
		}
	}
	
	//Delete the values from LecturerContact Table
	public void deleteLecturerContact()
	{
		PreparedStatement deleteLecturerContact;
		try
		{
			m_dbConn = DriverManager.getConnection(JDBC.dbName, "pxp420", "Birmingham123" );
			String sql = "DELETE " + "FROM LecturerContact" + " WHERE 1=1";
			
			deleteLecturerContact = m_dbConn.prepareStatement(sql);
			deleteLecturerContact.execute();
			deleteLecturerContact.close();
		}
		catch (SQLException exception)
		{
			exception.printStackTrace();
		}
		
		finally
		{
			try
			{
				m_dbConn.close();
			}
			catch (SQLException exception)
			{
				exception.printStackTrace();
				System.err.println("Couldn't close the connection");
			}
		}
	}
	
	//Delete the values from Tutor Table
	public void deleteTutor()
	{
		PreparedStatement deleteTutors;
		try
		{
			m_dbConn = DriverManager.getConnection(JDBC.dbName, "pxp420", "Birmingham123" );
			String sql = "DELETE " + "FROM Tutor" + " WHERE 1=1";
			
			deleteTutors = m_dbConn.prepareStatement(sql);
			deleteTutors.execute();
			deleteTutors.close();
		}
		catch (SQLException exception)
		{
			exception.printStackTrace();
		}
		
		finally
		{
			try
			{
				m_dbConn.close();
			}
			catch (SQLException exception)
			{
				exception.printStackTrace();
				System.err.println("Couldn't close the connection");
			}
		}
	}
	
	//Delete the values from Title Table
	public void deleteTitle()
	{
		PreparedStatement deleteTitles;
		try
		{
			m_dbConn = DriverManager.getConnection(JDBC.dbName, "pxp420", "Birmingham123" );
			String sql = "DELETE " + "FROM Titles" + " WHERE 1=1";
			
			deleteTitles = m_dbConn.prepareStatement(sql);
			deleteTitles.execute();
			deleteTitles.close();
		}
		catch (SQLException exception)
		{
			exception.printStackTrace();
		}
		
		finally
		{
			try
			{
				m_dbConn.close();
			}
			catch (SQLException exception)
			{
				exception.printStackTrace();
				System.err.println("Couldn't close the connection");
			}
		}
	}
	
	//Delete the values from Title Table
	public void deleteRegistrationType()
	{
		PreparedStatement deleteRegistrationTypes;
		try
		{
			m_dbConn = DriverManager.getConnection(JDBC.dbName, "pxp420", "Birmingham123" );
			String sql = "DELETE " + "FROM RegistrationType" + " WHERE 1=1";
			
			deleteRegistrationTypes = m_dbConn.prepareStatement(sql);
			deleteRegistrationTypes.execute();
			deleteRegistrationTypes.close();
		}
		catch (SQLException exception)
		{
			exception.printStackTrace();
		}
		
		finally
		{
			try
			{
				m_dbConn.close();
			}
			catch (SQLException exception)
			{
				exception.printStackTrace();
				System.err.println("Couldn't close the connection");
			}
		}
	}
	
	public void deleteAllFromDataBase()
	{
		this.deleteStudent();
		this.deleteLecturer();
		this.deleteStudentRegistration();
		this.deleteStudentContact();
		this.deleteNextOfKinContact();
		this.deleteLecturerContact();
		this.deleteTutor();
		this.deleteTitle();
		this.deleteRegistrationType();
	}

}


