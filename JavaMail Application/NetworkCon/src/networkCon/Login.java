package networkCon;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class Login 
{
	public static JTextField m_userName = null;
	public static JPasswordField m_password = null;

	public Login()
	{
	}
	
	/**
	 * Creates the login box for the user
	 */
	public void loginDialog()
	{
		m_userName = new JTextField();
		m_password = new JPasswordField();
		Object[] message = {
		    "Username:", m_userName,
		    "Password:", m_password
		};
		
		final JPanel panel = new JPanel();
		UIManager.put("OptionPane.background", Color.white);
		UIManager.put("Panel.background", Color.white);
		@SuppressWarnings("unused")
		int option = JOptionPane.showConfirmDialog(panel, message, "Login", JOptionPane.OK_CANCEL_OPTION, 0, new ImageIcon(SendSMTPMail.class.getClassLoader().getResource("networkCon/resources/login_image.png")));
		@SuppressWarnings("unused")
		PanelHandler m_panelHandler = new PanelHandler(MainFrame.m_frame);
		System.out.println(m_userName);
		System.out.println(m_password);
		
		PanelHandler.switchPanel(PanelHandler.MAIN_PANEL);
		MainFrame.m_frame.revalidate();
		MainFrame.m_frame.repaint();
	}
	
	/**
	 * Gets the username
	 * @return the username
	 */
	public static String getUserName()
	{
		return m_userName.getText();
	}
	
	/**
	 * Gets the password of the user
	 * @return the password
	 */
	public static String getPassword()
	{
		return new String(m_password.getPassword());
	}
}
