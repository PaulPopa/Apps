package networkCon;

import javax.swing.JFrame;

public class MainFrame 
{
	public static final int FRAME_WIDTH = 600;
	public static final int FRAME_HEIGHT = 400;
	
	public static final JFrame m_frame = new JFrame("Communication and networking");
	
	public static void main (String[] args)
	{
		Login login = new Login();
		login.loginDialog();
		
		m_frame.setResizable(false);
		m_frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		m_frame.setLocationRelativeTo(null);
		m_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		m_frame.setVisible(true);
	}
}

