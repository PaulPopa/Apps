package sqlDatabase;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InsertStudent
{
	Connection m_dbConn = JDBC.dbConn;	
	public InsertStudent(Connection dbConn) 
	{
		m_dbConn = dbConn;
	}
	
	@SuppressWarnings("deprecation")
	public void Insert()
	{
		Scanner in = new Scanner(System.in);
		PreparedStatement insertStudent = null;
		PreparedStatement insertStudentRegistration = null;
		PreparedStatement insertStudentContact = null;
		PreparedStatement insertNextOfKinContact = null;
		PreparedStatement insertTutor = null;
		
		try
		{
			m_dbConn = DriverManager.getConnection(JDBC.dbName, "pxp420", "Birmingham123");
			
			//SQL String for Student Table
			String studentTable = "INSERT INTO Student" + "(studentID, titleID, foreName, familyName, dateOfBirth)" + "VALUES(?,?,?,?,?)" ;
			insertStudent = m_dbConn.prepareStatement(studentTable);
			
			//SQL String for StudentRegistration Table
			String studentRegistrationTable = "INSERT INTO StudentRegistration" + "(studentID, yearOfStudy, registrationTypeID)" + "VALUES(?,?,?)";
			insertStudentRegistration = m_dbConn.prepareStatement(studentRegistrationTable);
			
			//SQL String for StudentContact Table
			String studentContactTable = "INSERT INTO StudentContact" + "(studentID, eMailAddress, postalAddress)" + "VALUES(?,?,?)";
			insertStudentContact = m_dbConn.prepareStatement(studentContactTable);
			
			//SQL String for NextOfKinContact Table
			String studentNextOfKinContactTable = "INSERT INTO NextOfKinContact" + "(studentID, name, eMailAddress, postalAddress)" + "VALUES(?,?,?,?)";
			insertNextOfKinContact = m_dbConn.prepareStatement(studentNextOfKinContactTable);
			
			//SQL String for Tutor Table
			String studentTutorTable = "INSERT INTO Tutor" + "(studentID, lecturerID)" + "VALUES(?,?)";
			insertTutor = m_dbConn.prepareStatement(studentTutorTable);
			
			//Selects the values from Students
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
				System.err.println("Cannot insert a student that is already in the database");
				this.Insert();
			}
			
			else
			{
				// Inserts the StudentID
				insertStudent.setInt(1, studentID);	
				
				//Inserts the TitleID
				System.out.println("Insert titleID");
				System.out.println("1 - Mr. , 2 - Mrs. , 3 - Dr., 4 - Proffesor. , 5 - Ms.");
				int titleID = in.nextInt(); 
				while (titleID < 1 || titleID > 5)
				{
					System.err.println("Enter a number between 1 and 5");
					System.out.println();
					System.out.println("1 - Mr. , 2 - Mrs. , 3 - Dr., 4 - Proffesor. , 5 - Ms.");
					titleID = in.nextInt();
				}
				insertStudent.setInt(2, titleID);
				in.nextLine();
				
				//Inserts the ForeName
				System.out.println("Insert forename");
				String foreName = in.nextLine();
				insertStudent.setString(3, foreName);
			
				//Inserts the FamilyName
				System.out.println("Insert familyName");
				String familyName = in.nextLine();
				insertStudent.setString(4, familyName);
				
				//Inserts the Date of Birth
				System.out.println("Date of Birth");
				System.out.println("Insert Year of Birth");
				int year = in.nextInt();
				while ( year < 1980 || year > 2000)
				{
					System.err.println("Please select a sensible year");
					year = in.nextInt();
				}

				System.out.println("Insert Month of Birth");
				int month = in.nextInt();
				while ( month < 1 || month > 12)
				{
					System.err.println("Enter a month between 1 and 12");
					month = in.nextInt();
				}
				
				System.out.println("Insert Day of Birth");
				int day = in.nextInt();
				while (day < 1 || day > 31)
				{
					System.err.println("Enter a day between 1 and 31");
					day = in.nextInt();
				}
				
				Date date = new Date((year-1900),(month-1),day);
				insertStudent.setDate(5, date);
				insertStudent.executeUpdate();
			
				//Populate the StudentRegistration Table with a new row
				
				//Inserts the student ID
				insertStudentRegistration.setInt(1, studentID);	
				
				//Inserts year of study
				System.out.println("Insert Year Of Study");
				int yearOfStudy = in.nextInt();
				while (yearOfStudy < 1 || yearOfStudy > 5)
				{
					System.err.println("Please enter a number between 1 and 5");
					yearOfStudy = in.nextInt();
				}
				insertStudentRegistration.setInt(2, yearOfStudy);
				
				//Inserts Registration type ID
				System.out.println("Insert registration type ID");
				System.out.println("1 - normal, 2 - repeat, 3 - external");
				int registrationTypeID = in.nextInt();
				while (registrationTypeID < 1 || registrationTypeID >3)
				{
					System.err.println("Please enter a number between 1 and 3");
					System.out.println();
					System.out.println("1 - normal, 2 - repeat, 3 - external");
					registrationTypeID = in.nextInt();
				}
				insertStudentRegistration.setInt(3, registrationTypeID);
				insertStudentRegistration.executeUpdate();
				
				//Populate the StudentRegistration Table with a new row
				
				insertStudentContact.setInt(1, studentID);
				in.nextLine();
				
				//Inserts the Student Contact email Address
				System.out.println("Insert eMail Address");
				String eMailAddress = in.nextLine();
				insertStudentContact.setString(2, eMailAddress);
				
				//Inserts the Student Contact postal Address
				System.out.println("Insert postalAddress");
				String postalAddress = in.nextLine();
				insertStudentContact.setString(3, postalAddress);
				insertStudentContact.executeUpdate();
				
				//Populate the NextOfKinContact Table with a new row
				
				insertNextOfKinContact.setInt(1, studentID);
				//in.nextLine();
				
				//Inserts the Next of kin Contact's name
				System.out.println("Insert nextOfKin name");
				String name = in.nextLine();
				insertNextOfKinContact.setString(2, name);
				
				//Inserts the Next of kin Contact's emailAddress
				System.out.println("Insert nextOfKin emailAddress");
				String contactEmailAdress = in.nextLine();
				insertNextOfKinContact.setString(3, contactEmailAdress);
				
				//Inserts the Next Of kin Contact's postalAddress
				System.out.println("Insert nextOfKin postalAddress");
				String contactPostalAddress = in.nextLine();
				insertNextOfKinContact.setString(4, contactPostalAddress);
				insertNextOfKinContact.executeUpdate();
				
				//Populate the Tutor Table with a new row
				
				insertTutor.setInt(1, studentID);
				
				//Inserts the student's lecturerID
				System.out.println("Insert lecturerID for student");
				System.out.println("1 - Mark, 2 - Volker, 3 - Jon, 4 - Martin, 5 - Achim");
				int lecturerID = in.nextInt();
				while (lecturerID < 1 || lecturerID > 5)
				{
					System.err.println("Enter a number between 1 and 5");
					System.out.println();
					System.out.println("1 - Mark, 2 - Volker, 3 - Jon, 4 - Martin, 5 - Achim");
					lecturerID = in.nextInt();  
				}
				insertTutor.setInt(2, lecturerID);
				insertTutor.executeUpdate();
			}
		}
		
		catch (SQLException exception)
		{
			exception.printStackTrace();
		}
		catch (InputMismatchException exception)
		{
			System.err.println("Failed creating. Mismatch between Integer and String");
			this.Insert();
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
}
