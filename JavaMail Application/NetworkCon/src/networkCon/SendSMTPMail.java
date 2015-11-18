package networkCon;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class SendSMTPMail 
{
	public Store m_store = null;
	public MimeMessage message = null;
	
	public static String username = null;
	public static String password = null;
	
	public SendSMTPMail()
	{
	}
	
	/**
	 * Sends the mail to specific person
	 * @param notDraft specifies if the message is sent or saved as drafts
	 */
	public void sendMail(boolean notDraft)
	{
		username = Login.getUserName();
		password = Login.getPassword();	 
		String smtphost = "smtp.gmail.com";

		Properties props = System.getProperties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", smtphost);
		props.put("mail.smtp.port", "587");
		
		props.setProperty("mail.user", username);
		props.setProperty("mail.password", password);
		
		Session session = Session.getDefaultInstance(props);
		
		try 
		{
			m_store = session.getStore("imaps");
			m_store.connect("imap.googlemail.com",username, password);
			
			message = new MimeMessage(session);
			message.setFrom(new InternetAddress("pxp420mail@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(NewMailPanel.getToWho()));
			message.setRecipients(Message.RecipientType.CC,
					InternetAddress.parse(NewMailPanel.getCC()));
			
			message.setSubject(NewMailPanel.getSubject());

			
			Multipart multipart = new MimeMultipart("alternative");
			
			ArrayList<String> filename = NewMailPanel.getAttachmentPath();
			if(!filename.isEmpty())
			{
				for(int i = 0; i < filename.size(); i++)
				{
					DataSource source = new FileDataSource(filename.get(i));
					
				
						BodyPart messageImagePart = new MimeBodyPart();
						BodyPart messageTextPart = new MimeBodyPart();
						
						messageImagePart.setDataHandler(new DataHandler(source));
						messageImagePart.setFileName(filename.get(i));
						
						messageTextPart.setText(NewMailPanel.getContent());
						
						multipart.addBodyPart(messageTextPart);
						
						multipart.addBodyPart(messageImagePart);
						message.setContent(multipart);
				}
			}
				
			else
			{
				message.setText(NewMailPanel.getContent());
			}
			
			message.saveChanges();

			
			if(notDraft == true)
			{
				//TRANSPORT MESSAGES
				Transport tr = session.getTransport("smtp");		
				tr.connect(smtphost, username, password);
				tr.sendMessage(message, message.getAllRecipients()); 

				JPanel panel = new JPanel();
				UIManager.put("OptionPane.background", Color.white);
				UIManager.put("Panel.background", Color.white);
				JOptionPane.showMessageDialog(panel, "Message sent", "Success", JOptionPane.OK_OPTION, new ImageIcon(SendSMTPMail.class.getClassLoader().getResource("networkCon/resources/tick_image.png")));
				MainFrame.m_frame.add(panel);
				PanelHandler.switchPanel(PanelHandler.NEW_MAIL_PANEL);
				MainFrame.m_frame.validate();
				MainFrame.m_frame.repaint();
				
			}
			else
			{
				//SAVES MESSAGES AS DRAFT
				if(!message.getSubject().isEmpty())
				{
				    Folder draftsMailBoxFolder = m_store.getFolder("[Gmail]/Drafts");//[Gmail]/Drafts
				    draftsMailBoxFolder.open(Folder.READ_WRITE); 
				    message.setFlag(Flag.DRAFT, true);
	
				    MimeMessage draftMessages[] = {message};
				    draftsMailBoxFolder.appendMessages(draftMessages);
				    
					JPanel panel = new JPanel();
					UIManager.put("OptionPane.background", Color.white);
					UIManager.put("Panel.background", Color.white);
					JOptionPane.showMessageDialog(panel, "Message saved as draft", "Saved", JOptionPane.OK_OPTION, new ImageIcon(SendSMTPMail.class.getClassLoader().getResource("networkCon/resources/tick_image.png")));
					MainFrame.m_frame.add(panel);
					PanelHandler.switchPanel(PanelHandler.NEW_MAIL_PANEL);
					MainFrame.m_frame.validate();
					MainFrame.m_frame.repaint();
				}
				else
				{
					PanelHandler.switchPanel(PanelHandler.NEW_MAIL_PANEL);
					MainFrame.m_frame.validate();
					MainFrame.m_frame.repaint();
				}
			}
		} 
		
		catch (MessagingException e) 
		{
			//IN CASE THE E-MAIL DOESNT EXIST
			final JPanel panel = new JPanel();
			UIManager.put("OptionPane.background", Color.white);
			UIManager.put("Panel.background", Color.white);
			JOptionPane.showMessageDialog(panel, "The e-mail address you want to send to does not exist!", "Could not send message", JOptionPane.ERROR_MESSAGE, new ImageIcon(SendSMTPMail.class.getClassLoader().getResource("networkCon/resources/cross_image.jpg")));
			MainFrame.m_frame.add(panel);
			PanelHandler.switchPanel(PanelHandler.NEW_MAIL_PANEL);
			MainFrame.m_frame.validate();
			MainFrame.m_frame.repaint();
		}
		catch (NullPointerException e)
		{
			//IN CASE THE E-MAIL IS NULL
			final JPanel panel = new JPanel();
			UIManager.put("OptionPane.background", Color.white);
			UIManager.put("Panel.background", Color.white);
			JOptionPane.showMessageDialog(panel, "Plese type an e-mail address to send to.", "Could not send message", JOptionPane.ERROR_MESSAGE, new ImageIcon(SendSMTPMail.class.getClassLoader().getResource("networkCon/resources/cross_image.jpg")));
			MainFrame.m_frame.add(panel);
			PanelHandler.switchPanel(PanelHandler.NEW_MAIL_PANEL);
			MainFrame.m_frame.validate();
			MainFrame.m_frame.repaint();
		}
	}
	
	/**
	 * Gets the store
	 * @return m_store - gets the store
	 */
	public Store getStore()
	{
		return m_store;
	}
	
	/**
	 * Gets the message that contains not only text/plain
	 * @return the message
	 */
	public MimeMessage getMessage()
	{
		return message;
	}
}
