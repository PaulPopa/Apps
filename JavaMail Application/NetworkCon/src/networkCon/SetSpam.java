package networkCon;

import java.io.IOException;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;

import com.sun.mail.imap.IMAPFolder;

public class SetSpam 
{
	public static IMAPFolder m_folder = null;
	public static Store m_store = null;
	
	public SetSpam()
	{
	}
	
	/**
	 * Sets the spam for the user
	 */
	public void transferToSpam()
	{
		try 
		{
			if(!MessagesPanel.m_folder.isOpen())
			{
				MessagesPanel.m_folder.open(Folder.READ_WRITE);
			}
	
			Message[] m_messages = MessagesPanel.m_folder.getMessages();
			Flags spam = new Flags("Spam");
			
			for(int i = 0; i < m_messages.length; i++)
			{
				m_messages[i].setFlags(spam, false);
				try 
				{
					if(m_messages[i].getSubject().contains(MainMenuPanel.getFilterMessage())
					|| m_messages[i].getContent().toString().contains(MainMenuPanel.getFilterMessage()))
					{
						m_messages[i].setFlags(spam, true);
					    Folder spamMailBoxFolder = MessagesPanel.m_store.getFolder("[Gmail]/Spam");//[Gmail]/Spam
					    spamMailBoxFolder.open(Folder.READ_WRITE); 

					    Message draftMessages[] = {m_messages[i]};
					    spamMailBoxFolder.appendMessages(draftMessages);
					}
				} 
				catch (IOException e) 
				{
				}
			}
		}
		
		catch(MessagingException e)
		{
		}
	}
}
