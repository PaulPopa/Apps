package networkCon;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PanelHandler 
{
	public static JFrame m_frame = MainFrame.m_frame;
	
	private MainMenuPanel m_menuPanel = new MainMenuPanel();	
	private MessagesPanel m_messagesPanel = new MessagesPanel();
	private NewMailPanel m_newMailPanel = new NewMailPanel();
	private SpamPanel m_spamPanel = new SpamPanel();
	
	public static final int MAIN_PANEL = 0;
	public static final int MESSAGES_PANEL = 1;
	public static final int NEW_MAIL_PANEL = 2;
	public static final int SPAM_PANEL = 3;
	
	public static JPanel[] m_panelList = new JPanel[20];
	private Login m_login = new Login();
	public static int m_currentPanel = MAIN_PANEL;

	public PanelHandler(JFrame frame) 
	{
		m_frame = frame;
		setPanels();
	}
	
	/**
	 * Sets all the panels from the other classes
	 */
	public void setPanels()
	{
		try
		{
			m_panelList[MAIN_PANEL] = m_menuPanel.getPanel();
			m_panelList[MESSAGES_PANEL] = m_messagesPanel.getPanel();
			m_panelList[NEW_MAIL_PANEL] = m_newMailPanel.getPanel();
			m_panelList[SPAM_PANEL] = m_spamPanel.getPanel();
		}
		catch(NullPointerException e)
		{
			m_login.loginDialog();
		}
	}
	
	/**
	 * Switches from a panel to another panel
	 * @param panel the wanted panel
	 */
	public static void switchPanel(int panel)
	{
		m_panelList[m_currentPanel].setVisible(false);
		m_panelList[panel].setVisible(true);
		
		m_currentPanel = panel;
		m_frame.add(m_panelList[panel]);
	}
}
