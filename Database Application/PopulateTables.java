package sqlDatabase;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class PopulateTables 
{
	Connection m_dbConn = JDBC.dbConn;
	
	public PopulateTables(Connection dbConn) 
	{
		m_dbConn = dbConn;
	}
	
	/**
	 * Populates the Students Table with random values
	 */
	public void populateStudentsTable()
	{
		PreparedStatement populateStudents;
		try
		{
			m_dbConn = DriverManager.getConnection(JDBC.dbName, "pxp420", "Birmingham123" );
			String sql = "INSERT INTO Student" + "(studentID, titleID, foreName, familyName, dateOfBirth)" + "VALUES(?,?,?,?,?)";
			populateStudents = m_dbConn.prepareStatement(sql);
			
			//Setting The Values
			for(int i=1; i<=100; i++)
			{
				Random generator = new Random(i);
				int year = generator.nextInt(10) + 90;
				int month = generator.nextInt(12) + 1;
				int day = generator.nextInt(30) + 1;
				
				@SuppressWarnings("deprecation")
				Date randomDate = new Date(year,month,day);
				
				populateStudents.setInt(1, 145+i);
				populateStudents.setInt(2, (i%3) + 1);
				populateStudents.setString(3, "Firstname" + Integer.toString(i));
				populateStudents.setString(4, "Lastname" + Integer.toString(i));
				populateStudents.setDate(5, randomDate);
				
				populateStudents.execute();
			}
			populateStudents.close();
			System.out.println("The statement has been closed");
			System.out.println("Populated Student Table");
		}
		catch (SQLException exception)
		{
			System.err.println("Couldn't insert values into Student Table");
		}
		
		finally
		{
			try
			{
				m_dbConn.close();
				System.out.println("The connection has been closed");
				System.out.println();
			}
			catch (SQLException exception)
			{
				exception.printStackTrace();
				System.err.println("Couldn't close the connection");
			}
		}
	}
	
	/**
	 * Populates the Lecturers Table with specific values
	 */
	public void populateLecturersTable()
	{
		PreparedStatement populateLecturers = null;
		try
		{
			m_dbConn = DriverManager.getConnection(JDBC.dbName, "pxp420", "Birmingham123");
			String sql = "INSERT INTO Lecturer" + "(lecturerID, titleID, foreName, familyName)" + "VALUES(?,?,?,?)";
			populateLecturers = m_dbConn.prepareStatement(sql);
			
			//Setting the Values
			String[] foreNames = {"Mark", "Volker", "Jon", "Martin", "Achim"};
			String[] familyNames = {"Lee", "Sorge", "Rowe", "Escardo", "Jung"};
			
			for(int i=1; i<=foreNames.length; i++)
			{
				populateLecturers.setInt(1, i);
				populateLecturers.setInt(2, 1);
				populateLecturers.setString(3, foreNames[i-1]);
				populateLecturers.setString(4, familyNames[i-1]);
				
				populateLecturers.execute();
			}
			populateLecturers.close();
			System.out.println("The statement has been closed");
			System.out.println("Populated Lecturers Table");
		}
	
		catch (SQLException exception)
		{
			System.err.println("Couldn't insert values into Lecturers Table");
		}
		
		finally
		{
			try
			{
				m_dbConn.close();
				System.out.println("The connection has been closed");
				System.out.println();
			}
			catch (SQLException exception)
			{
				exception.printStackTrace();
				System.err.println("Couldn't close the connection");
			}
		}
	}
	
	/**
	 *  Populates the StudentRegistration Table with specific values
	 */
	public void populateStudentRegistrationTable()
	{
		PreparedStatement populateStudentRegistration;
		try
		{
			m_dbConn = DriverManager.getConnection(JDBC.dbName, "pxp420", "Birmingham123");
			String sql = "INSERT INTO StudentRegistration" + "(studentID, yearOfStudy, registrationTypeID)" + "VALUES(?,?,?)";
			populateStudentRegistration = m_dbConn.prepareStatement(sql);
			
			for(int i=1; i<=100; i++)
			{
				Random generator = new Random(i);
				int registrationTypeID = generator.nextInt(3) + 1;
				int yearOfStudy = generator.nextInt(4) + 1;
				
				populateStudentRegistration.setInt(1, 145 + i);
				populateStudentRegistration.setInt(2, yearOfStudy);
				populateStudentRegistration.setInt(3, registrationTypeID);
				
				populateStudentRegistration.execute();
			}			
			populateStudentRegistration.close();
			System.out.println("The statement has been closed");
			System.out.println("Populated StudentRegistration Table");
		}
		
		catch (SQLException exception)
		{
			System.err.println("Couldn't insert values into StudentRegistration Table");
		}
		
		finally
		{
			try
			{
				m_dbConn.close();
				System.out.println("The connection has been closed");
				System.out.println();
			}
			catch (SQLException exception)
			{
				exception.printStackTrace();
				System.err.println("Couldn't close the connection");
			}
		}
	}
	
	/**
	 * Populates the StudentContact Tables with specific values
	 */
	public void populateStudentContactTable()
	{
		PreparedStatement populateStudentContact = null;
		try
		{
			m_dbConn = DriverManager.getConnection(JDBC.dbName, "pxp420", "Birmingham123");
			String sql = "INSERT INTO StudentContact" + "(studentID, eMailAddress, postalAddress)" + "VALUES(?,?,?)";
			populateStudentContact = m_dbConn.prepareStatement(sql);
			
			//Setting the Values
			for(int i=1; i<=100; i++)
			{
				populateStudentContact.setInt(1, 145 + i);
				populateStudentContact.setString(2, "eMailAddress" + Integer.toString(i));
				populateStudentContact.setString(3, "postalAddress" + Integer.toString(i));
				
				populateStudentContact.execute();
			}
			
			populateStudentContact.close();
			System.out.println("The statement has been closed");
			System.out.println("Populated StudentContact Table");
		}
		
		catch (SQLException exception)
		{
			System.err.println("Couldn't insert values into StudentContact Table");
		}
		
		finally
		{
			try
			{
				m_dbConn.close();
				System.out.println("The connection has been closed");
				System.out.println();
			}
			catch (SQLException exception)
			{
				exception.printStackTrace();
				System.err.println("Couldn't close the connection");
			}
		}
	}
	
	/**
	 * Populates the NextOfKinContact Tables with specific values
	 */
	public void populateNextOfKinContactTable()
	{
		PreparedStatement populateNextOfKinContact = null;
		try
		{
			m_dbConn = DriverManager.getConnection(JDBC.dbName, "pxp420", "Birmingham123");
			String sql = "INSERT INTO NextOfKinContact" + "(studentID, name, eMailAddress, postalAddress)" + "VALUES(?,?,?,?)";
			populateNextOfKinContact = m_dbConn.prepareStatement(sql);
			
			//Setting the values
			for(int i=1; i<=100; i++)
			{
				populateNextOfKinContact.setInt(1, 145 + i);
				populateNextOfKinContact.setString(2, "name" + Integer.toString(i));
				populateNextOfKinContact.setString(3, "eMailAddress" + Integer.toString(i));
				populateNextOfKinContact.setString(4, "postalAddress" + Integer.toString(i));
				
				populateNextOfKinContact.execute();
			}
			
			populateNextOfKinContact.close();
			System.out.println("The statement has been closed");
			System.out.println("Populated NextOfKinContact Table");
		}
		
		catch (SQLException exception)
		{
			System.err.println("Couldn't insert values into NextOfKinContact Table");
		}
		
		finally
		{
			try
			{
				m_dbConn.close();
				System.out.println("The connection has been closed");
				System.out.println();
			}
			catch (SQLException exception)
			{
				exception.printStackTrace();
				System.err.println("Couldn't close the connection");
			}
		}
	}
	
	/**
	 * Populates the LecturerContact Tables with specific values
	 */
	public void populateLecturerContactTable()
	{
		PreparedStatement populateLecturerContact = null;
		try
		{
			m_dbConn = DriverManager.getConnection(JDBC.dbName, "pxp420", "Birmingham123");
			String sql = "INSERT INTO LecturerContact" + "(lecturerID, Office, eMailAddress)" + "VALUES(?,?,?)";
			populateLecturerContact = m_dbConn.prepareStatement(sql);
			
			//Setting the Values
			for(int i=1; i<=5; i++)
			{
				populateLecturerContact.setInt(1, i);
				populateLecturerContact.setString(2, "Office" + Integer.toString(i));
				populateLecturerContact.setString(3, "eMailAddress" + Integer.toString(i));
				
				populateLecturerContact.execute();
			}
			
			populateLecturerContact.close();
			System.out.println("The statement has been closed");
			System.out.println("Populated LecturerContact Table");
		}
		
		catch (SQLException exception)
		{
			System.err.println("Couldn't insert values into LecturerContact Table");
		}
		
		finally
		{
			try
			{
				m_dbConn.close();
				System.out.println("The connection has been closed");
				System.out.println();
			}
			catch (SQLException exception)
			{
				exception.printStackTrace();
				System.err.println("Couldn't close the connection");
			}
		}
	}
	
	/**
	 * Populates the Tutor Tables with specific values
	 */
	public void populateTutorTable()
	{
		PreparedStatement populateTutor;
		try
		{
			m_dbConn = DriverManager.getConnection(JDBC.dbName, "pxp420", "Birmingham123");
			String sql = "INSERT INTO Tutor" + "(studentID, lecturerID)" + "VALUES(?,?)";
			populateTutor = m_dbConn.prepareStatement(sql);
			
			//Setting the Values
			for(int i=1; i<=100; i++)
			{
				populateTutor.setInt(1, 145 + i);
				populateTutor.setInt(2, (i%5) + 1);
				
				populateTutor.execute();
			}
			
			populateTutor.close();
			System.out.println("The statement has been closed");
			System.out.println("Populated Tutor Table");
		}
		
		catch (SQLException exception)
		{
			System.err.println("Couldn't insert values into Tutor Table");
		}
		
		finally
		{
			try
			{
				m_dbConn.close();
				System.out.println("The connection has been closed");
				System.out.println();
			}
			catch (SQLException exception)
			{
				exception.printStackTrace();
				System.err.println("Couldn't close the connection");
			}
		}
	}
	
	/**
	 * Populates the Titles Table with specific Values
	 */
	public void populateTitlesTable()
	{
		PreparedStatement populateTitles;
		try
		{
			m_dbConn = DriverManager.getConnection(JDBC.dbName, "pxp420", "Birmingham123" );
			String sql= "INSERT INTO Titles" + "(titleID, titleString)" + "VALUES(?,?)";
			populateTitles = m_dbConn.prepareStatement(sql);
			
			//Setting The Values
			String[] titleName = {"Mr.", "Mrs.", "Ms.", "Dr.", "Proffessor."};
			
			for(int i=1; i<=titleName.length; i++)
			{
				populateTitles.setInt(1, i);
				populateTitles.setString(2, titleName[i-1]);
				
				populateTitles.execute();
			}
			populateTitles.close();
			System.out.println("The statement has been closed");
			System.out.println("Populated Titles Table");
		}
		
		catch (SQLException exception)
		{
			System.err.println("Couldn't insert values into Titles Table");
		}
		
		finally
		{
			try
			{
				m_dbConn.close();
				System.out.println("The connection has been closed");
				System.out.println();
			}
			catch (SQLException exception)
			{
				exception.printStackTrace();
				System.err.println("Couldn't close the connection");
			}
		}
	}
	
	/**
	 * Populates the RegistrationType Table with specific values
	 */
	public void populateRegistrationTypeTable()
	{
		PreparedStatement populateRegistration = null;
		try
		{
			m_dbConn = DriverManager.getConnection(JDBC.dbName, "pxp420", "Birmingham123");
			String sql= "INSERT INTO RegistrationType" + "(registrationTypeID, description)" + "VALUES(?,?)";
			populateRegistration = m_dbConn.prepareStatement(sql);
			
			//Setting the Values
			String[] description = {"normal", "repeat", "external"};
			
			for(int i=1; i<=description.length; i++)
			{
				populateRegistration.setInt(1, i);
				populateRegistration.setString(2, description[i-1]);
				
				populateRegistration.execute();
			}
			populateRegistration.close();
			System.out.println("The statement has been closed");
			System.out.println("Populated RegistrationType Table");
		}
		
		catch (SQLException exception)
		{
			System.err.println("Couldn't insert values into RegistrationType Table");
		}
		
		finally
		{
			try
			{
				m_dbConn.close();
				System.out.println("The connection has been closed");
				System.out.println();
			}
			catch (SQLException exception)
			{
				exception.printStackTrace();
				System.err.println("Couldn't close the connection");
			}
		}
	}
	
	public void populateAllTables()
	{
		this.populateTitlesTable();
		this.populateRegistrationTypeTable();
		this.populateStudentsTable();
		this.populateLecturersTable();
		this.populateStudentRegistrationTable();
		this.populateStudentContactTable();
		this.populateNextOfKinContactTable();
		this.populateLecturerContactTable();
		this.populateTutorTable();
	}

}
