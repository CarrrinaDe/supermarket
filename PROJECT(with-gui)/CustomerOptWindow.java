package proiect;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CustomerOptWindow extends JFrame {
	GridBagConstraints c = new GridBagConstraints();
	JButton b1, b2, b3;
	Customer customer;
	
	ActionListener al1 = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			dispose();
			new ShoppingCartWindow("Shopping Cart - "+customer.name, customer);
		}
	};
	ActionListener al2 = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			dispose();
			new WishListWindow("WishList - "+customer.name, customer);
		}
	};
	ActionListener al3 = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			dispose();
			new NotificationsWindow("Notifications - "+customer.name, customer); 
		}
	};
	public CustomerOptWindow(String text, Customer cust) {
		super(text);
		customer = cust;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setLayout(new GridBagLayout());
		c.anchor = GridBagConstraints.CENTER;
		b1 = new JButton("See Shopping Cart");
		b1.addActionListener(al1);
		c.gridx = 0;
		c.gridy = 0;
		add(b1, c);
		b2 = new JButton("See WishList");
		b2.addActionListener(al2);
		c.gridx = 1;
		add(b2, c);
		b3 = new JButton("See Notifications");
		b3.addActionListener(al3);
		c.gridx = 2;
		add(b3, c);
		setVisible(true);
	}
}
