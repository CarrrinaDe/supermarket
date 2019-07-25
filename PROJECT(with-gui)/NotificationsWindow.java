package proiect;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Iterator;

import javax.swing.*;

public class NotificationsWindow extends JFrame {
	GridBagConstraints c = new GridBagConstraints();
	DefaultListModel dlm;
	JList list;
	JScrollPane sp;
	Customer customer;
	
	public NotificationsWindow(String text, Customer cust) {
		super(text);
		customer = cust;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setLayout(new GridBagLayout());
		c.anchor = GridBagConstraints.CENTER;
		dlm = new DefaultListModel();
		list = new JList(dlm);
		for (Iterator<Notification> it = customer.getNotifications().iterator(); it.hasNext();) 
			dlm.addElement(it.next());
		list.setCellRenderer(new NotificationRenderer());
		sp = new JScrollPane(list);
		c.gridx = 0;
		c.gridy = 0;
		add(sp, c);
		setVisible(true);
	}
}
