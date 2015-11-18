package sqlDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class StudentReport 
{
	Connection m_dbConn = JDBC.dbConn;
	Scanner in = new Scanner(System.in);

	public StudentReport(Connection dbConn) 
	{
		m_dbConn = dbConn;
	}
	
	public void studentReport(int studentID, boolean personalTutor)
	{
		try
		{
			m_dbConn = DriverManager.getConnection(JDBC.dbName, "pxp420", "Birmingham123");

			PreparedStatement studentQuery = m_dbConn.prepareStatement("SELECT studentID " + "FROM Student");
			ResultSet studentSet = studentQuery.executeQuery();
			
			if(studentID == -1)
			{
				System.out.println("Insert studentID");
				studentID = in.nextInt();
			}
			boolean studentExists = false;
			
			while(studentSet.next())
			{
				int fromTableID = studentSet.getInt(1);
				
				if(fromTableID == studentID)
				{
					studentExists = true;
				}
			}
			
			if (studentExists == false)
			{
				System.err.println("StudentID is not existent in the database");
				System.out.println();
				System.out.println("Please select another student");
				this.studentReport(-1, true);
			}
			
			else
			{
				//SELECTS the title string, fore name and family name
				String selectFullName = "SELECT titleString, forename, familyName" + " FROM Student, Titles " + " WHERE studentID = ? AND Student.titleID = Titles.titleID";
				PreparedStatement studentFullName = m_dbConn.prepareStatement(selectFullName);
				
				studentFullName.setInt(1, studentID);
				ResultSet student = studentFullName.executeQuery();
				
				while(student.next())
				{
					String titleString = student.getString(1);
					String foreName = student.getString(2);
					String familyName = student.getString(3);
					System.out.println("FULL NAME:         " + titleString.replaceAll("\\s","") +" "+ foreName.replaceAll("\\s","") + " " + familyName.replaceAll("\\s",""));
				}
				studentFullName.close();
				
				//SELECTS the date of birth
				String selectDoB = "SELECT dateOfBirth" + " FROM Student " + " WHERE studentID = ? ";
				PreparedStatement studentDateOfBirth = m_dbConn.prepareStatement(selectDoB);
				studentDateOfBirth.setInt(1, studentID);
				
				student = studentDateOfBirth.executeQuery();
				
				while(student.next())
				{
					String dateOfBirth = student.getString(1);
					System.out.println("DATE OF BIRTH:     " + dateOfBirth.replaceAll("\\s",""));
				}
				studentDateOfBirth.close();
				
				//SELECTS the studentID
				System.out.println("STUDENT ID:        " + studentID);
				
				//SELECTS the year of study
				String selectYearofStudy = "SELECT yearOfStudy " + " FROM Student, StudentRegistration " + 
																   " WHERE Student.studentID = ? AND Student.studentID = StudentRegistration.studentID";
				PreparedStatement studentYearOfStudy = m_dbConn.prepareStatement(selectYearofStudy);
				studentYearOfStudy.setInt(1, studentID);
				
				student = studentYearOfStudy.executeQuery();
				
				while(student.next())
				{
					int yearOfStudy = student.getInt(1);
					System.out.println("YEAR OF STUDY:     " + yearOfStudy);
				}
				studentYearOfStudy.close();
				
				//SELECTS the registration type
				String selectRegistrationType = " SELECT description " + " FROM Student, RegistrationType, StudentRegistration " + " WHERE Student.studentID = ? "
						+ "AND RegistrationType.registrationTypeID = StudentRegistration.registrationTypeID "
						+ "AND StudentRegistration.studentID = Student.studentID";
				PreparedStatement studentRegistrationType = m_dbConn.prepareStatement(selectRegistrationType);
				studentRegistrationType.setInt(1, studentID);
				
				student = studentRegistrationType.executeQuery();
				
				while(student.next())
				{
					String registrationType = student.getString(1);
					System.out.println("REGISTRATION TYPE: " + registrationType);//prints registration type
				}
				studentRegistrationType.close();
				
				//SELECTS the email Address and postal Address
				String selectAddress = "SELECT eMailAddress, postalAddress " + " FROM Student, StudentContact " + 
						" WHERE Student.studentID = ? AND Student.studentID = StudentContact.studentID ";
				PreparedStatement studentAddress = m_dbConn.prepareStatement(selectAddress);
				studentAddress.setInt(1, studentID);
				
				student = studentAddress.executeQuery();
				
				while(student.next())
				{
					String eMailAddress = student.getString(1);
					String postalAddress = student.getString(2);
					System.out.println("EMAIL ADDRESS:     " + eMailAddress);
					System.out.println("POSTAL ADDRESS:    " + postalAddress);
				}
				studentAddress.close();
				
				//SELECTS Emergency contact details
				String selectEmergencyDetails = "SELECT name, eMailAddress, postalAddress " + " FROM Student, NextOfKinContact " +
						" WHERE Student.studentID = ? AND Student.studentID = NextOfKinContact.studentID";
				PreparedStatement studentEmergencyDetails = m_dbConn.prepareStatement(selectEmergencyDetails);
				studentEmergencyDetails.setInt(1, studentID);
				
				student = studentEmergencyDetails.executeQuery();
				
				while(student.next())
				{
					String contactName = student.getString(1);
					String contactEmail = student.getString(2);
					String contactAddress = student.getString(3);
					System.out.println();
					System.out.println("--EMERGENCY CONTACT--");
					System.out.println("NAME:              " + contactName);
					System.out.println("EMAIL:             " + contactEmail);
					System.out.println("ADDRESS:           " + contactAddress);
				}
				studentEmergencyDetails.close();
				
				//SELECTS the personal tutor
				
				if (personalTutor == true)
				{
					String selectPersonalTutor = "SELECT Lecturer.foreName, Lecturer.familyName" + " FROM Lecturer, Student, Tutor " + 
							" WHERE Student.studentID = ? AND Tutor.studentID = Student.studentID AND Lecturer.lecturerID = Tutor.LecturerID";
					PreparedStatement studentPersonalTutor = m_dbConn.prepareStatement(selectPersonalTutor);
					studentPersonalTutor.setInt(1, studentID);
					
					student = studentPersonalTutor.executeQuery();
					
					while(student.next())
					{
						String foreName = student.getString(1);
						String familyName = student.getString(2);
						System.out.println("PERSONAL TUTOR:    " + foreName.replaceAll("\\s","") + " " + familyName.replaceAll("\\s",""));//prints name of personal tutor
					}
					studentPersonalTutor.close();
				}
			}
		}
		catch (SQLException exception)
		{
			System.err.println("StudentID is not existent in the database");
			System.out.println("Please select another student");
			this.studentReport(-1, true);
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
