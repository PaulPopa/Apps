package networkCon;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import com.sun.mail.imap.IMAPFolder;

public class SpamPanel
{	
	public static Message[] m_messages = null;
	public static IMAPFolder m_folder = null;
	public static Store m_store = null;
	public static JPanel m_contentPanel = new JPanel();
	public static JPanel[] m_panels = null;
	
	@SuppressWarnings("unused")
	private Login m_login = new Login();
	
	public static String username = null;
	public static String password = null;
	
	public static String m_wordToLookFor = "";
	public static boolean m_search = false;
	public static int m_showDialog = -1;
	
	/**
	 * Sets up the spam panel as in the inbox panel
	 */
	public SpamPanel()
	{
		username = Login.getUserName();
		password = Login.getPassword();	   
		
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");
		
		props.setProperty("mail.user", username);
		props.setProperty("mail.password", password);

		Session session = Session.getDefaultInstance(props);

		try 
		{
			m_store = session.getStore("imaps");
			m_store.connect("imap.googlemail.com",username, password);

			m_folder = (IMAPFolder) m_store.getFolder("[Gmail]/Spam"); 

			if(!m_folder.isOpen())
			{
				m_folder.open(Folder.READ_WRITE);
			}
			
			m_messages = m_folder.getMessages();
			m_panels = new JPanel[(int)Math.ceil((double)m_messages.length / 9)];
			 
			for(int i = 0; i <= m_panels.length - 1 ; i++)
			{
				m_panels[i] = new JPanel();
				m_panels[i].setLayout(null);
				m_panels[i].setBackground(Color.WHITE);
			}
			
			m_contentPanel.setLayout(null);
			m_contentPanel.setBackground(Color.WHITE);
			
			MessagesPanel.addRequiredToPanels(m_search);
		    MessagesPanel.addButtons();
		    MessagesPanel.addLabels();
		}
		catch (MessagingException e) 
		{
		}
	}
	
	/**
	 * Gets the messages from e-mail
	 * @return the message
	 */
	public static Message[] getMessage()
	{
		return m_messages;
	}
	
	/**
	 * Adds buttons and labels for each panel
	 */
	public static void addRequiredToPanels(boolean search)
	{
		m_search = search;
		try
		{
			int j = 0;
			int n = 0;
			if(getMessage().length > 8)
			{
				n = 8;
			}
			else
			{
				n = getMessage().length % 9 - 1;
			}
			for(int i = 0; i <= m_panels.length - 1 ; i++)
			{
				m_panels[i].removeAll();
				m_panels[i].updateUI();
				final int nextIndex = i;
				final int backIndex = i;
				int m_ButtonsPosition = 3;
				int m_LabelsPosition = 3;
				
				if(search == false)
				{
					m_wordToLookFor = "";
				}
				else
				{
					if(m_showDialog == -1)
					{
						JPanel panel = new JPanel();
						UIManager.put("OptionPane.background", Color.white);
						UIManager.put("Panel.background", Color.white);
						m_wordToLookFor =(String) JOptionPane.showInputDialog(panel, "Enter word you want to search for:", "Input",
					        JOptionPane.WARNING_MESSAGE, new ImageIcon(MessagesPanel.class.getClassLoader().getResource("networkCon/resources/search_image.png")), null, "");

						if(m_wordToLookFor == null)
						{
							m_wordToLookFor = "";
						}
					}
					m_showDialog++;
				}
	
				for(int k = j ; k <= n; k++)
				{
					final int m_tickPosition = m_ButtonsPosition;
					final int toBeAddedIndex = k;
					
					try 
					{
						if(getMessage()[k].getSubject().contains(m_wordToLookFor) 
						|| getMessage()[k].getContent().toString().contains(m_wordToLookFor))
						{ 
							ImageIcon m_mailImage = new ImageIcon(MessagesPanel.class.getClassLoader().getResource("networkCon/resources/mail_image.png"));
							JLabel m_mail =new JLabel("", m_mailImage, JLabel.CENTER);

							//CREATES THE BUTTONS FOR EACH PANEL
							ButtonCreator button = new ButtonCreator(getMessage()[k].getSubject(), new Point(20,  m_ButtonsPosition * 25), new Dimension(100, 15));
							JButton buttonToBeAdded = button.getButton();
							buttonToBeAdded.addActionListener(new ActionListener()
							{
								@SuppressWarnings("deprecation")
								public void actionPerformed(ActionEvent e)
								{
									try 
									{
										//CREATES THE FROM WHO LABEL
										JLabel m_fromWho = new JLabel();
										m_fromWho.setText("From: " + InternetAddress.toString(getMessage()[toBeAddedIndex].getFrom()));
										m_fromWho.setLocation(100, 25);
										m_fromWho.setSize(200, 50);
										m_fromWho.setHorizontalAlignment(JLabel.LEFT);
										m_fromWho.setVerticalAlignment(JLabel.CENTER);
										// -- END OF FROM WHO LABEL --
										
										//CREATES THE CONTENT DISPLAY LABEL
										JTextArea m_content = new JTextArea();
										m_content.setText(getMessage()[toBeAddedIndex].getContent().toString());
										m_content.setLocation(100, 100);
										m_content.setSize(400, 200);
										m_content.setLineWrap(true);
										m_content.setWrapStyleWord(true);
										m_content.setEditable(false);
										m_content.setCaretPosition(0);
										
										JScrollPane m_scroller = new JScrollPane(m_content);
										m_scroller.setLocation(100,100);
										m_scroller.setSize(400, 200);
										m_scroller.setBorder(BorderFactory.createEmptyBorder());
										// -- END OF CONTENT DISPLAY LABEL --
										
										//CREATES THE DELETE BUTTON FOR CHECK INBOX PANEL
										ImageIcon m_deleteImage = new ImageIcon(MessagesPanel.class.getClassLoader().getResource("networkCon/resources/bin_image.png"));
										ImageIcon m_deleteImageRed = new ImageIcon(MessagesPanel.class.getClassLoader().getResource("networkCon/resources/bin_delete_image.png"));
										ButtonCreator m_deleteButton = new ButtonCreator("", new Point (525, 275), new Dimension(55, 50));
										
										JButton m_deleteButtonToBeAdded = m_deleteButton.getButton();
										m_deleteButtonToBeAdded.setIcon(m_deleteImage);
										m_deleteButtonToBeAdded.setRolloverIcon(m_deleteImageRed);
										m_deleteButtonToBeAdded.setHorizontalAlignment(JLabel.CENTER);
										m_deleteButtonToBeAdded.setVerticalAlignment(JLabel.CENTER);
										m_deleteButtonToBeAdded.addActionListener(new ActionListener()
										{
											public void actionPerformed(ActionEvent e)
											{
												try 
												{
													m_messages[toBeAddedIndex].setFlag(Flags.Flag.DELETED, true);
													m_folder.expunge();
													
													Message[] m_messagesNew = m_folder.getMessages();
													System.out.println(m_messagesNew.length);
													System.out.println(m_messages.length);
													
													if(m_messagesNew.length != m_messages.length)
													{
														m_messages = m_folder.getMessages();
														addRequiredToPanels(m_search);
														addButtons();
														addLabels();		
														
														m_contentPanel.setVisible(false);
														m_panels[backIndex].setVisible(true);
														MainFrame.m_frame.add(m_panels[backIndex]);
														MainFrame.m_frame.validate();
														MainFrame.m_frame.repaint();
													}
												
												}
												catch (MessagingException e1) 
												{
												}
											}
										});
										
										//CREATES THE MARK AS READ BUTTON FOR CHECK INBOX PANEL
										ButtonCreator m_markAsUnReadButton = new ButtonCreator("Mark as Unread", new Point (400, 325), new Dimension(200, 25));
										JButton m_markAsUnReadToBeAdded = m_markAsUnReadButton.getButton();
										m_markAsUnReadToBeAdded.setHorizontalAlignment(JLabel.RIGHT);
										m_markAsUnReadToBeAdded.setVerticalAlignment(JLabel.CENTER);
										m_markAsUnReadToBeAdded.addActionListener(new ActionListener()
										{
											public void actionPerformed(ActionEvent e)
											{
												try 
												{
													m_messages[toBeAddedIndex].setFlag(Flags.Flag.SEEN, false);
													//CREATES A MAILBOX IMAGE IF NOT READ
													m_mail.setText("New Mail");
													m_mail.setVisible(true);
													m_mail.setLocation(7, m_tickPosition * 25);
													m_mail.setSize(30,15);
													m_mail.setHorizontalAlignment(JLabel.LEFT);
													m_mail.setVerticalAlignment(JLabel.CENTER);
													m_panels[nextIndex].add(m_mail);
													// -- END OF MAILBOX IMAGE CREATION --
												} 
												catch (MessagingException e1) 
												{
												}
												
												m_contentPanel.setVisible(false);
												m_panels[backIndex].setVisible(true);
												MainFrame.m_frame.add(m_panels[backIndex]);
												MainFrame.m_frame.validate();
												MainFrame.m_frame.repaint();
											}
										});
										// -- END OF MARK AS READ BUTTON --
												
										//CREATES THE BACK BUTTON FOR CHECK INBOX PANEL
										
										ButtonCreator m_backButton = new ButtonCreator("Back", new Point(0, 325), new Dimension(200,25));
										JButton m_backButtonToBeAdded = m_backButton.getButton();
										m_backButtonToBeAdded.setHorizontalAlignment(JLabel.LEFT);
										m_backButtonToBeAdded.setVerticalAlignment(JLabel.CENTER);
										
										m_backButtonToBeAdded.addActionListener(new ActionListener()
										{
											public void actionPerformed(ActionEvent e)
											{
												m_mail.setVisible(false); // sets the mail invisible
												
												m_contentPanel.setVisible(false);
												m_panels[backIndex].setVisible(true);
												MainFrame.m_frame.add(m_panels[backIndex]);
												MainFrame.m_frame.validate();
												MainFrame.m_frame.repaint();
												
											}
										});
										// -- END OF BACK BUTTON FOR CHECK INBOX PANEL
										
										//CREATES A DATE LABEL FOR EMAILS
										JLabel m_dateLabel = new JLabel();
										m_dateLabel.setText(getMessage()[toBeAddedIndex].getReceivedDate().toGMTString());
										m_dateLabel.setLocation(350, 25);
										m_dateLabel.setSize(150, 50);
										m_dateLabel.setHorizontalAlignment(JLabel.RIGHT);
										m_dateLabel.setVerticalAlignment(JLabel.CENTER);
										// -- END OF DATE LABEL CREATION --
										
										m_contentPanel.removeAll();
										m_contentPanel.updateUI();
										m_contentPanel.add(m_scroller);
										m_contentPanel.add(m_fromWho);
										m_contentPanel.add(m_backButtonToBeAdded);
										m_contentPanel.add(m_deleteButtonToBeAdded);
										m_contentPanel.add(m_markAsUnReadToBeAdded);
										m_contentPanel.add(m_dateLabel);
										
										m_contentPanel.setVisible(true);
										m_panels[backIndex].setVisible(false);
										
										MainFrame.m_frame.add(m_contentPanel);
										MainFrame.m_frame.validate();
										MainFrame.m_frame.repaint();
									} 
									catch (IOException e1) 
									{
										e1.printStackTrace();
									} 
									catch (MessagingException e1) 
									{
										e1.printStackTrace();
									}
								}
							});
							
							//DISABLES BUTTONS WHICH ARE NOT TEXT/PLAIN
							try 
							{
								if(getMessage()[k].getContent().toString().contains("javax.mail") || getMessage()[k].getContent().toString().isEmpty())
								{
									buttonToBeAdded.setEnabled(false);
								}
							}
							catch (IOException e1) 
							{
							}
							// -- END OF DISABLE BUTTONS
							
							m_panels[i].add(buttonToBeAdded);
							m_ButtonsPosition++;
							// -- END OF BUTTONS --
							System.out.println(InternetAddress.toString(getMessage()[k].getFrom()));
							
							//CREATES THE FROM FOR EACH PANEL
							JLabel m_mailFrom = new JLabel();
							m_mailFrom.setText(InternetAddress.toString(getMessage()[k].getFrom()));
							m_mailFrom.setSize(150, 15);
							m_mailFrom.setHorizontalAlignment(JLabel.LEFT);
							m_mailFrom.setVerticalAlignment(JLabel.CENTER);
							m_mailFrom.setLocation(150, m_LabelsPosition * 25);
							m_panels[i].add(m_mailFrom);
							// -- END OF FROM LABELS --
							
							//CREATES THE CONTENTS FOR EACH PANEL
							JLabel m_mailText = new JLabel();
							try 
							{
								if(!getMessage()[k].getContent().toString().contains("javax.mail") && !getMessage()[k].getContent().toString().isEmpty())
								{
									m_mailText.setText(getMessage()[k].getContent().toString());
								}
								else
								{
									m_mailText.setForeground(Color.GRAY);
									m_mailText.setText("NOT Text/Plain type");
								}
							
							m_mailText.setSize(200, 15);
							m_mailText.setHorizontalAlignment(JLabel.LEFT);
							m_mailText.setVerticalAlignment(JLabel.CENTER);
							m_mailText.setLocation(330, m_LabelsPosition * 25);
							
							m_panels[i].add(m_mailText);
							m_LabelsPosition++;
							}
							
							catch (IOException e) 
							{
							}
							// -- END OF CONTENTS FOR PANELS
						}
					} 
					catch (IOException e1) 
					{
					}
				}
				
				// SETS THE NEXT BUTTON FOR EACH PANEL
				ButtonCreator m_nextButton = new ButtonCreator("Next", new Point(400, 325), new Dimension(200,25));
				JButton m_nextButtonToBeAdded = m_nextButton.getButton();
				m_nextButtonToBeAdded.setHorizontalAlignment(JLabel.RIGHT);
				m_nextButtonToBeAdded.setVerticalAlignment(JLabel.CENTER);
				
				m_nextButtonToBeAdded.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						m_panels[nextIndex].setVisible(false);
						m_panels[nextIndex + 1].setVisible(true);
						MainFrame.m_frame.add(m_panels[nextIndex + 1]);
						MainFrame.m_frame.validate();
						MainFrame.m_frame.repaint();
					}
				});
				if(i != m_panels.length-1)
				{
					m_panels[i].add(m_nextButtonToBeAdded);
				}
				// -- END OF NEXT BUTTON FOR EACH PANEL
				
				// SETS THE BACK BUTTON FOR EACH PANEL
				ButtonCreator m_backButton = new ButtonCreator("Back", new Point(0, 325), new Dimension(200,25));
				JButton m_backButtonToBeAdded = m_backButton.getButton();
				m_backButtonToBeAdded.setHorizontalAlignment(JLabel.LEFT);
				m_backButtonToBeAdded.setVerticalAlignment(JLabel.CENTER);
				
				m_backButtonToBeAdded.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						m_panels[backIndex].setVisible(false);
						m_panels[backIndex - 1].setVisible(true);
						MainFrame.m_frame.add(m_panels[backIndex - 1]);
						MainFrame.m_frame.validate();
						MainFrame.m_frame.repaint();
					}
				});
				if(i != 0)
				{
					m_panels[i].add(m_backButtonToBeAdded);
				}
				// -- END OF BACK BUTTON FOR EACH PANEL
				
				//SETS UP THE LABEL SHOWING NR OF EMAILS
				JLabel m_nrEmails = new JLabel();
				int m_newMessagesLength = m_folder.getMessages().length;
				m_nrEmails.setText("Showing " + Integer.toString(j + 1) + " - " + Integer.toString(n + 1) + " out of " + Integer.toString(m_newMessagesLength));
				m_nrEmails.setLocation(200, 325);
				m_nrEmails.setSize(200, 15);
				m_nrEmails.setHorizontalAlignment(JLabel.CENTER);
				m_nrEmails.setVerticalAlignment(JLabel.CENTER);
				m_nrEmails.setForeground(Color.GRAY.darker());
				m_nrEmails.setFont(new Font("Garamond", Font.BOLD, 12));
				
				m_panels[i].add(m_nrEmails);
				// -- END OF LABELS --
				
				// SETS THE REFRESH BUTTON FOR EACH PANEL
				ButtonCreator m_refreshButton = new ButtonCreator("Refresh", new Point (400, 25), new Dimension(200, 25));
				JButton m_refreshButtonToBeAdded = m_refreshButton.getButton();
				m_refreshButtonToBeAdded.setHorizontalAlignment(JLabel.RIGHT);
				m_refreshButtonToBeAdded.setVerticalAlignment(JLabel.CENTER);
				m_refreshButtonToBeAdded.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						try 
						{
							Message[] m_messagesNew = m_folder.getMessages();
							System.out.println(m_messagesNew.length);
							System.out.println(m_messages.length);
							if(m_search == false)
							{
								if(m_messagesNew.length != m_messages.length)
								{
									m_messages = m_folder.getMessages();
									addRequiredToPanels(false);
									addButtons();
									addLabels();
								}
							}
							else
							{
								m_messages = m_folder.getMessages();
								addRequiredToPanels(false);
								addButtons();
								addLabels();
							}
							m_folder.expunge();
						} 
						catch (MessagingException e1) 
						{
						}
					}
				});
				
				m_panels[i].add(m_refreshButtonToBeAdded);
				// -- END OF REFRESH BUTTON --
				
				if(n+9 <= getMessage().length - 1)
				{
					n = n + 9;
					j = j + 9;
				}
				else if (n + 9 > getMessage().length - 1 && n != getMessage().length)
				{
					n = n + getMessage().length % 9;
					j = j + 9;
				}
			}
		}
		catch (MessagingException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds Buttons to the panels
	 */
	public static void addButtons()
	{
		//HOME BUTTON
		for(int i = 0; i <= m_panels.length - 1 ; i++)
		{
			final int buttonIndex = i;
			ButtonCreator m_goBackCreator = new ButtonCreator("Back To Home", new Point(200, 345), new Dimension(200, 15));
			JButton m_goBackButton = m_goBackCreator.getButton();
			m_goBackButton.setFont(new Font("Garamond", Font.PLAIN, 15));
			m_goBackButton.setHorizontalAlignment(SwingConstants.CENTER);
			m_goBackButton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					m_panels[buttonIndex].setVisible(false);
					PanelHandler.switchPanel(PanelHandler.MAIN_PANEL);
					MainFrame.m_frame.validate();
					MainFrame.m_frame.repaint();
				}
			});
			// -- END OF HOME BUTTON --
			m_panels[i].add(m_goBackButton);
		}
	}
	
	/**
	 * Adds the labels for each panel
	 */
	public static void addLabels()
	{
		for (int i = 0; i <= m_panels.length - 1 ; i++)
		{
			JLabel m_showingLabel = new JLabel();
			m_showingLabel.setText("Showing e-mails in descendant order");
			m_showingLabel.setSize(300,30);
			m_showingLabel.setLocation(150, 25);
			m_showingLabel.setFont(new Font("Garamond", Font.PLAIN, 18));
			m_showingLabel.setHorizontalAlignment(JLabel.CENTER);
			
			m_panels[i].add(m_showingLabel);
		}
	}
	
	/**
	 * Gets the first panel for the frame
	 * @return first panel
	 */
	public JPanel getPanel()
	{
		if(m_messages.length == 0)
		{
			return new JPanel();
		}
		else
		{
			return m_panels[0];
		}
	}
}
