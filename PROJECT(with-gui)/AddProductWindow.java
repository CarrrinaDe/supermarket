package proiect;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.*;

public class AddProductWindow extends JFrame {
	GridBagConstraints c = new GridBagConstraints();
	JTextField t1, t2, t3, t4;
	JButton done;
	Integer depID, itemID;
	Double price;
	String name;
	
	ActionListener al = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			c.gridy = 9;
			depID = Integer.parseInt(t1.getText());
			itemID = Integer.parseInt(t2.getText());
			price = Double.parseDouble(t3.getText());
			name = t4.getText();
			if (Store.getInstance().getItem(itemID) != null) {
				add(new JLabel("The product already exists."), c);
				setVisible(true);
				return;
			}
			Item item = new Item(name, itemID, price);
			item.setDepID(depID);
			Store.getInstance().getDepartment(depID).addItem(item);
			Store.getInstance().getDepartment(depID).notifyAllObservers(new Notification(new Date(), NotificationType.ADD, depID, itemID));
			add(new JLabel("The product has been added."), c);	
			setVisible(true);
		}
	};
	public AddProductWindow(String text) {
		super(text);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setLayout(new GridBagLayout());
		c.anchor = GridBagConstraints.CENTER;
		done = new JButton("DONE");
		done.addActionListener(al);
		t1 = new JTextField(13);
		t2 = new JTextField(13);
		t3 = new JTextField(13);
		t4 = new JTextField(13);
		c.gridx = 0;
		c.gridy = 0;
		add(new JLabel("Department ID:"), c);
		c.gridy = 1;
		add(t1, c);
		c.gridy = 2;
		add(new JLabel("Product ID:"), c);
		c.gridy = 3;
		add(t2, c);
		c.gridy = 4;
		add(new JLabel("Price:"), c);
		c.gridy = 5;
		add(t3, c);
		c.gridy = 6;
		add(new JLabel("Name:"), c);
		c.gridy = 7;
		add(t4, c);
		c.gridy = 8;
		add(done, c);
		pack();
		setVisible(true);
	}
}
