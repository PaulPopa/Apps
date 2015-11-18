package networkCon;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class NewMailPanel 
{
	public static JPanel m_panel = new JPanel();
	public JLabel m_showAttachement = null;
	int i = 1;

	public static ArrayList<String> selectedFile = new ArrayList<String>();
	
	public JTextField m_toWho = null;
	public JTextField m_cc = null;
	public JTextField m_subject = null;
	public JTextArea m_content =  null;
	
	public SendSMTPMail m_sendMail = new SendSMTPMail();
	
	public static String m_toWhoContent = "";
	public static String m_toCCContent = "";
	public static String m_subjectContent = "";
	public static String m_getContent = "";
	
	public NewMailPanel()
	{
		m_panel.setLayout(null);
		m_panel.setBackground(Color.WHITE);
		this.addTextFields();
		this.addTextArea();
		this.addButtons();
	}
	
	/**
	 * Creates the labels with To, Cc and Subject on them
	 */
	public void addTextFields()
	{
		//CREATES THE TO WHO TEXT FIELD
		JLabel m_toWhoLabel = new JLabel("TO:");
		m_toWhoLabel.setLocation(50,50);
		m_toWhoLabel.setSize(50, 25);
		m_toWhoLabel.setFont(new Font("Serif", Font.BOLD, 14));
		m_toWhoLabel.setHorizontalAlignment(JLabel.LEFT);
		m_toWhoLabel.setVerticalAlignment(JLabel.CENTER);
		
		m_toWho = new JTextField();
		m_toWho.setLocation(125, 50);
		m_toWho.setBorder(null);
		m_toWho.setSize(325, 25);
		m_toWho.setEditable(true);
		
		//CREATE THE CC TEXT FIELD
		JLabel m_ccLabel = new JLabel("CC:");
		m_ccLabel.setLocation(50,75);
		m_ccLabel.setSize(50, 25);
		m_ccLabel.setFont(new Font("Serif", Font.BOLD, 14));
		m_ccLabel.setHorizontalAlignment(JLabel.LEFT);
		m_ccLabel.setVerticalAlignment(JLabel.CENTER);
		
		m_cc = new JTextField();
		m_cc.setLocation(125, 75);
		m_cc.setBorder(null);
		m_cc.setSize(325, 25);
		m_cc.setEditable(true);
		// -- END OF CREATE THE CC TEXT FIELD
		
		//CREATE THE SUBJECT TEXT FIELD
		JLabel m_subjectLabel = new JLabel("Subject:");
		m_subjectLabel.setLocation(50,100);
		m_subjectLabel.setSize(50, 25);
		m_subjectLabel.setFont(new Font("Serif", Font.BOLD, 14));
		m_subjectLabel.setHorizontalAlignment(JLabel.LEFT);
		m_subjectLabel.setVerticalAlignment(JLabel.CENTER);

		m_subject = new JTextField();
		m_subject.setLocation(125, 100);
		m_subject.setBorder(null);
		m_subject.setSize(325, 25);
		m_subject.setEditable(true);
		// -- END OF SUBJECT THE CC TEXT FIELD
		
		m_panel.add(m_toWhoLabel);
		m_panel.add(m_toWho);
		m_panel.add(m_ccLabel);
		m_panel.add(m_cc);
		m_panel.add(m_subjectLabel);
		m_panel.add(m_subject);
	}
	
	/**
	 * Creates the content area box
	 */
	public void addTextArea()
	{
		//CREATES THE CONTENT DISPLAY LABEL
		m_content = new JTextArea();
		m_content.setEditable(true);
		m_content.setSize(500, 125);
		m_content.setLineWrap(true);
		m_content.setWrapStyleWord(true);
		m_content.setEditable(true);
		m_content.setCaretPosition(0);
		
		JScrollPane m_scroller = new JScrollPane(m_content);
		m_scroller.setLocation(50, 150);
		m_scroller.setSize(500, 125);
		m_scroller.setBorder(BorderFactory.createBevelBorder(0));
		// -- END OF CONTENT DISPLAY LABEL --
		
		m_panel.add(m_scroller);
	}
	
	/**
	 * Adds the buttons for the send mail panel
	 */
	public void addButtons()
	{
		//SETS THE SEND BUTTON
		ButtonCreator m_sendButton = new ButtonCreator("Send", new Point(75, 300), new Dimension(100, 25));
		JButton m_sendButtonToBeAdded = m_sendButton.getButton();
		m_sendButtonToBeAdded.setHorizontalAlignment(JLabel.LEFT);
		m_sendButtonToBeAdded.setVerticalAlignment(JLabel.CENTER);
		m_sendButtonToBeAdded.setFont(new Font("Serif", Font.PLAIN, 20));
		m_sendButtonToBeAdded.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				m_toWhoContent = m_toWho.getText();
				m_toCCContent = m_cc.getText();
				m_getContent = m_content.getText();
				m_subjectContent = m_subject.getText();
				m_sendMail.sendMail(true);
				
				m_panel.removeAll();
				addButtons();
				addTextArea();
				addTextFields();
				PanelHandler.switchPanel(PanelHandler.NEW_MAIL_PANEL);
				MainFrame.m_frame.validate();
				MainFrame.m_frame.repaint();
			}
		});
		
		m_panel.add(m_sendButtonToBeAdded);
		// -- END OF THE SEND BUTTON
		
		//SETS THE ATTACHEMENT BUTTON
		ImageIcon m_attachmentImage = new ImageIcon(MessagesPanel.class.getClassLoader().getResource("networkCon/resources/attachement_image.png"));
		ButtonCreator m_attachmentButton = new ButtonCreator("", new Point(500, 300), new Dimension(25, 25));

		JButton m_attachmentButtonToBeAdded = m_attachmentButton.getButton();
		m_attachmentButtonToBeAdded.setIcon(m_attachmentImage);
		m_attachmentButtonToBeAdded.setHorizontalAlignment(JLabel.CENTER);
		m_attachmentButtonToBeAdded.setVerticalAlignment(JLabel.CENTER);
		m_attachmentButtonToBeAdded.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser fileChooser = new JFileChooser();
		        int returnValue = fileChooser.showOpenDialog(null);
		        if (returnValue == JFileChooser.APPROVE_OPTION) 
		        {
		        	m_showAttachement = new JLabel();
		        	if(i == 1)
		        	{
			        	m_showAttachement.setText("Attachment: " + fileChooser.getSelectedFile().getName());
			        	m_showAttachement.setLocation(250, 300);
			        	m_showAttachement.setSize(250, 25);
			        	m_showAttachement.setFont(new Font("Serif", Font.BOLD, 14));
			        	m_showAttachement.setHorizontalAlignment(JLabel.LEFT);
			        	m_showAttachement.setVerticalAlignment(JLabel.CENTER);
			        	m_panel.add(m_showAttachement);
			        	m_panel.revalidate();
			        	m_panel.repaint();
			        	System.out.println(m_showAttachement.getText());
		        	}
		        	else if (i == 2)
		        	{
		        		System.out.println(m_showAttachement.getText());
		        		JLabel m_showAttachement = new JLabel();
		        		m_showAttachement.setText("Attachment: " + fileChooser.getSelectedFile().getName());
			        	m_showAttachement.setLocation(250, 325);
			        	m_showAttachement.setSize(250, 25);
			        	m_showAttachement.setFont(new Font("Serif", Font.BOLD, 14));
			        	m_showAttachement.setHorizontalAlignment(JLabel.LEFT);
			        	m_showAttachement.setVerticalAlignment(JLabel.CENTER);
			        	m_panel.add(m_showAttachement);
			        	m_panel.revalidate();
			        	m_panel.repaint();
			        	System.out.println(m_showAttachement.getText());
			        	m_attachmentButtonToBeAdded.setVisible(false);
		        	}
		        	i++;
		        	
					selectedFile.add(fileChooser.getSelectedFile().getAbsolutePath());
					System.out.println(selectedFile);
		        }
			}
		});
		m_panel.add(m_attachmentButtonToBeAdded);
		// -- END OF THE ATTACHMENT BUTTON
		
		//SETS THE X BUTTON
		ImageIcon m_xImage = new ImageIcon(MessagesPanel.class.getClassLoader().getResource("networkCon/resources/x_black_image.png"));
		ImageIcon m_xImageRed = new ImageIcon(MessagesPanel.class.getClassLoader().getResource("networkCon/resources/x_red_image.png"));
		ButtonCreator m_xButton = new ButtonCreator("", new Point(500, 50), new Dimension(25, 25));
		
		JButton m_xButtonToBeAdded = m_xButton.getButton();
		m_xButtonToBeAdded.setIcon(m_xImage);
		m_xButtonToBeAdded.setRolloverIcon(m_xImageRed);
		m_xButtonToBeAdded.setHorizontalAlignment(JLabel.CENTER);
		m_xButtonToBeAdded.setVerticalAlignment(JLabel.CENTER);
		m_xButtonToBeAdded.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				m_toWhoContent = m_toWho.getText();
				m_toCCContent = m_cc.getText();
				m_getContent = m_content.getText();
				m_subjectContent = m_subject.getText();
				m_sendMail.sendMail(false);
				i=1;
				m_panel.removeAll();
				selectedFile.clear();
				addButtons();
				addTextArea();
				addTextFields();
				PanelHandler.switchPanel(PanelHandler.MAIN_PANEL);
				MainFrame.m_frame.validate();
				MainFrame.m_frame.repaint();
			}
		});
		// -- END OF THE X BUTTON
		
		m_panel.add(m_xButtonToBeAdded);
	}
	
	/**
	 * Gets to who we send the message
	 * @return the to who message
	 */
	public static String getToWho()
	{
		return m_toWhoContent;
	}
	
	/**
	 * Gets who else receives our e-mail
	 * @return other message
	 */
	public static String getCC()
	{
		return m_toCCContent;
	}
	
	/**
	 * Gets the subject of the e-mail
	 * @return the subject of the e-mail
	 */
	public static String getSubject()
	{
		return m_subjectContent;
	}
	
	/**
	 * Gets the content of the e-mail
	 * @return the content of the e-mail
	 */
	public static String getContent()
	{
		return m_getContent;
	}
	
	/**
	 * Gets the list with the attachments
	 * @return the list with attachements
	 */
	public static ArrayList<String> getAttachmentPath()
	{
		return selectedFile;
	}
	
	/**
	 * Gets the panel with all the elements added
	 * @return the panel
	 */
	public JPanel getPanel()
	{
		return m_panel;
	}
}
