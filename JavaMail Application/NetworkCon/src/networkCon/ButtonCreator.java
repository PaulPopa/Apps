package networkCon;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ButtonCreator 
{
	private JButton m_button = new JButton();
	
	private String m_buttonText;
	private Point m_position;
	private Dimension m_size;
	
	/**
	 * Sets the button's properties
	 * @param buttonText - the button's text
	 * @param position - the button's size and position
	 */
	public ButtonCreator(String buttonText, Point position, Dimension size)
	{
		m_buttonText = buttonText;
		m_position = position;
		m_size = size;
		
		m_button.setText(m_buttonText);
		m_button.setLocation(m_position);
		m_button.setSize(m_size);

		m_button.setFocusPainted(false);
		m_button.setContentAreaFilled(false);
		m_button.setBorderPainted(false);
		
		m_button.setBackground(Color.WHITE);
		m_button.setForeground(Color.DARK_GRAY.darker());
		m_button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		m_button.setHorizontalAlignment(SwingConstants.LEFT);
		
		m_button.setEnabled(true);
		
		this.addRollOver();
	}
	
	/**
	 * Creates a highlight when rolled over the button
	 */
	public void addRollOver()
	{
		//Adds rollOver function to the button
		m_button.addChangeListener(new ChangeListener() 
		{
			@Override
			public void stateChanged(ChangeEvent arg0) 
			{
				if (m_button.getModel().isRollover())
				{
					m_button.setForeground(Color.RED.darker().darker());
				}
				else
				{
					m_button.setForeground(Color.DARK_GRAY.darker());
				}
			}
			
		});
	}
	
	/**
	 * Gets the button with the changes done to it
	 * @return m_button - the button
	 */
	public JButton getButton()
	{
		return m_button;
	}
}
