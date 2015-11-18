package sqlDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class LecturerReport 
{
	Connection m_dbConn = JDBC.dbConn;
	StudentReport report = new StudentReport(m_dbConn);

	public LecturerReport(Connection dbConn) 
	{
		m_dbConn = dbConn;
	}
	
	public void lecturerReport()
	{
		Scanner in = new Scanner(System.in);
		try
		{
			m_dbConn = DriverManager.getConnection(JDBC.dbName, "pxp420", "Birmingham123");
			
			System.out.println("Enter the lecturer ID: ");
			System.out.println("1 - Mark, 2 - Volker, 3 - Jon, 4 - Martin, 5 - Achim");
			int lecturerID = in.nextInt();
			
			while (lecturerID < 1 || lecturerID > 5)
			{
				System.err.println("Enter a number between 1 and 5");
				System.out.println();
				System.out.println("1 - Mark, 2 - Volker, 3 - Jon, 4 - Martin, 5 - Achim");
				lecturerID = in.nextInt();  
			}

			for(int i=1; i<=5; i++)
			{
			String selectStudentID = "SELECT Student.studentID " + " FROM Student, StudentRegistration, Tutor "  + 
					" WHERE StudentRegistration.yearOfStudy = ? AND Tutor.lecturerID = ? "
															 + "AND Student.studentID = StudentRegistration.studentID "
															 + "AND Student.studentID = Tutor.studentID" + " ORDER BY Student.studentID ASC";
			
			PreparedStatement students = m_dbConn.prepareStatement(selectStudentID);
			
			students.setInt(1, i);
			students.setInt(2, lecturerID);
			
			ResultSet studentSet = students.executeQuery();

				while(studentSet.next())
				{
					System.err.println("STUDENT: " + Integer.toString(studentSet.getInt(1)));
					report.studentReport(studentSet.getInt(1), false);
					System.out.println();
				}
			}
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
