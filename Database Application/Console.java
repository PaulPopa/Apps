package sqlDatabase;

import java.sql.Connection;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Console 
{
	Connection m_dbConn = JDBC.dbConn;
	PopulateTables populate = new PopulateTables(m_dbConn);
	DeletePopulatedValues delete = new DeletePopulatedValues(m_dbConn);
	InsertStudent insertStudent = new InsertStudent(m_dbConn);
	StudentReport student = new StudentReport(m_dbConn);
	LecturerReport lecturer = new LecturerReport(m_dbConn);
	AddTutor tutor = new AddTutor(m_dbConn);

	public Console(Connection dbConn) 
	{
		m_dbConn = dbConn;
	}
	
	public void selectOption()
	{
		try
		{
			Scanner consoleOptions = new Scanner(System.in);
			System.out.println("0 - Exit the program");
			System.out.println("2.2 - Insert values for all tables");
			System.out.println("2.3 - Delete all values from all tables");
			System.out.println("3.1 - Insert a new Student");
			System.out.println("3.2 - Add a new tutor to a student");
			System.out.println("3.3 - Produce a report for a student");
			System.out.println("3.4 - Produce a report for a lecturer");

	
			double option = consoleOptions.nextDouble();
			
			if (option == 0)
			{
				consoleOptions.close();
				System.exit(0);
			}
			else if (option == 2.2)
			{
				populate.populateAllTables();
				this.selectOption();
			}
			else if (option == 2.3)
			{
				System.out.println("Are you sure you want to delete the values?");
				System.out.println("1 - for yes, 0 - for no");
				int yesNo = consoleOptions.nextInt();
				
				if(yesNo == 1)
				{
					delete.deleteAllFromDataBase();
					System.out.println();
					System.out.println("Values deleted");
					this.selectOption();
				}
				else if (yesNo == 0)
				{
					this.selectOption();
				}
				else
				{
					System.out.println("Back to the main console");
					this.selectOption();
				}
			}
			else if (option == 3.1)
			{
				insertStudent.Insert();
				this.selectOption();
			}
			else if (option == 3.2)
			{
				tutor.addTutor();
				this.selectOption();
			}
			
			else if (option == 3.3)
			{
				student.studentReport(-1, true);
				
				System.out.println();
				System.out.println("Type any number to continue");
				consoleOptions.nextInt();
				this.selectOption();
			}
			else if (option == 3.4)
			{
				lecturer.lecturerReport();
				this.selectOption();
			}
			else
			{
				System.out.println("Please select a number from the interval: ");
				this.selectOption();
			}
		}
		catch (InputMismatchException exception)
		{
			System.out.println();
			System.err.println("Select a number from the list");
			this.selectOption();
		}
	}


}
