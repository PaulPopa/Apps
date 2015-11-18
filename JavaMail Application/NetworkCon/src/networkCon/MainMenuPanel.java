package networkCon;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.mail.MessagingException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class MainMenuPanel 
{
	public static JPanel m_panel = new JPanel();
	public SetSpam m_spam = new SetSpam();
	public static JTextField m_filterMessage = null;
	
	public boolean canChange = false;
	
	public MainMenuPanel()
	{
		m_panel.setLayout(null);
		m_panel.setBackground(Color.WHITE);
		addButtons();
	}
	
	/**
	 * Get the messages set for spam
	 * @return the spam message
	 */
	public static String getFilterMessage()
	{
		return m_filterMessage.getText();
	}
	
	/**
	 * Add the buttons for the main panel
	 */
	public void addButtons()
	{
		// FILTER BUTTON
		ButtonCreator m_filterButtonCreator = new ButtonCreator("Filter", new Point(300, 80), new Dimension(245, 50));
		
		JButton m_filterButton = m_filterButtonCreator.getButton();
		
		m_filterButton.setFont(new Font("Serif", Font.PLAIN, 25));
		m_filterButton.setHorizontalAlignment(SwingConstants.RIGHT);
		m_filterButton.setVerticalAlignment(SwingConstants.CENTER);
		
		m_filterButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				m_filterMessage = new JTextField();
				Object[] message = {
				    "Enter word you want to filter:", m_filterMessage,
				};
				
				final JPanel panel = new JPanel();
				UIManager.put("OptionPane.background", Color.white);
				UIManager.put("Panel.background", Color.white);
				@SuppressWarnings("unused")
				int option = JOptionPane.showConfirmDialog(panel, message, "Filter", JOptionPane.OK_CANCEL_OPTION, 0, new ImageIcon(MainMenuPanel.class.getClassLoader().getResource("networkCon/resources/spam_image.png")));
				if(getFilterMessage() != null && getFilterMessage() != "" && !getFilterMessage().isEmpty())
				{
					m_spam.transferToSpam();
					canChange = true;
				}
				
			}
		});
		m_panel.add(m_filterButton);
		
		//INBOX BUTTON
		ButtonCreator m_inboxButtonCreator = new ButtonCreator("Inbox", new Point(20, 80), new Dimension(300, 50));
		
		JButton m_inboxButton = m_inboxButtonCreator.getButton();
		
		m_inboxButton.setFont(new Font("Serif", Font.PLAIN, 25));
		m_inboxButton.setHorizontalAlignment(SwingConstants.LEFT);
		m_inboxButton.setVerticalAlignment(SwingConstants.CENTER);
		
		m_inboxButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(canChange == true)
				{
					m_spam.transferToSpam();
				}
				PanelHandler.switchPanel(PanelHandler.MESSAGES_PANEL);
				MainFrame.m_frame.validate();
				MainFrame.m_frame.repaint();
			}
		});
		
		//CREATE A MAIL BUTTON
		ButtonCreator m_newMail = new ButtonCreator("New mail", new Point(20, 130), new Dimension(300, 50));
		JButton m_newMailButton = m_newMail.getButton();
		
		m_newMailButton.setFont(new Font("Serif", Font.PLAIN, 25));
		m_newMailButton.setHorizontalAlignment(SwingConstants.LEFT);
		m_newMailButton.setVerticalAlignment(SwingConstants.CENTER);
		
		m_newMailButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				PanelHandler.switchPanel(PanelHandler.NEW_MAIL_PANEL);
				MainFrame.m_frame.validate();
				MainFrame.m_frame.repaint();
			}
		});
		
		//CREATE SEARCH MAIL BUTTON
		ButtonCreator m_searchMail = new ButtonCreator("Search mail", new Point(20, 180), new Dimension(300, 50));
		JButton m_searchMailButton = m_searchMail.getButton();
		m_searchMailButton.setFont(new Font("Serif", Font.PLAIN, 25));
		m_searchMailButton.setHorizontalAlignment(SwingConstants.LEFT);
		m_searchMailButton.setVerticalAlignment(SwingConstants.CENTER);
		
		m_searchMailButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				MessagesPanel.m_showDialog = -1;
				MessagesPanel.addRequiredToPanels(true);
				MessagesPanel.addButtons();
				MessagesPanel.addLabels();
				PanelHandler.switchPanel(PanelHandler.MESSAGES_PANEL);
				MainFrame.m_frame.validate();
				MainFrame.m_frame.repaint();
			}
		});
		
		//CREATE SPAM BUTTON
		ButtonCreator m_spam = new ButtonCreator("Spam", new Point(20, 230), new Dimension(300, 50));
		JButton m_spamButton = m_spam.getButton();
		m_spamButton.setFont(new Font("Serif", Font.PLAIN, 25));
		m_spamButton.setHorizontalAlignment(SwingConstants.LEFT);
		m_spamButton.setVerticalAlignment(SwingConstants.CENTER);
		
		m_spamButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				SpamPanel.addRequiredToPanels(false);
				SpamPanel.addButtons();
				SpamPanel.addLabels();
				PanelHandler.switchPanel(PanelHandler.SPAM_PANEL);
				MainFrame.m_frame.validate();
				MainFrame.m_frame.repaint();
			}
		});
		
		//EXIT BUTTON
		ImageIcon m_exitClosedImage = new ImageIcon(MessagesPanel.class.getClassLoader().getResource("networkCon/resources/exit_closed_image.png"));
		ImageIcon m_exitOpenImage = new ImageIcon(MessagesPanel.class.getClassLoader().getResource("networkCon/resources/exit_open_image.png"));
		
		ButtonCreator m_exitButtonCreator = new ButtonCreator("", new Point(450, 200), new Dimension(150, 150));
		
		JButton m_exitButton = m_exitButtonCreator.getButton();
		
		m_exitButton.setFont(new Font("Serif", Font.BOLD, 15));
		m_exitButton.setIcon(m_exitClosedImage);
		m_exitButton.setRolloverIcon(m_exitOpenImage);
		m_exitButton.setHorizontalAlignment(SwingConstants.LEFT);
		m_exitButton.setVerticalAlignment(SwingConstants.CENTER);
		
		//Close the program when button is pressed
		m_exitButton.addActionListener(new ActionListener() 
		{
            public void actionPerformed(ActionEvent e) 
            {
            	if (MessagesPanel.m_folder != null && MessagesPanel.m_folder.isOpen()) 
    			{ 
    				try 
    				{
    					MessagesPanel.m_folder.close(true);
    				}
    				catch (MessagingException e1) 
    				{
    					e1.printStackTrace();
    				}	 
    			}
    			if (MessagesPanel.m_store != null) 
    			{ 
    				try 
    				{
    					MessagesPanel.m_store.close();
    				} 
    				catch (MessagingException e2) 
    				{
    					e2.printStackTrace();
    				} 
    			}
            	System.exit(0);
            }
		});
		// --END OF EXIT BUTTON--
		
		m_panel.add(m_inboxButton);
		m_panel.add(m_newMailButton);
		m_panel.add(m_searchMailButton);
		m_panel.add(m_spamButton);
		m_panel.add(m_exitButton);
	}
	
	/**
	 * Gets the panel with the whole elements in it
	 * @return the panel 
	 */
	public JPanel getPanel()
	{
		return m_panel;
	}
}
