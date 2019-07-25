package proiect;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class WishListWindow extends JFrame {
	GridBagConstraints c = new GridBagConstraints();
	DefaultListModel dlm;
	JList list;
	JScrollPane sp;
	Customer customer;
	JButton b1, b2;
	JTextField t;
	Item selected;
	Vector<Item> items;
	
	public WishListWindow(String text, Customer cust) {
		super(text);
		customer = cust;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setLayout(new GridBagLayout());
		c.anchor = GridBagConstraints.CENTER;
		dlm = new DefaultListModel();
		items = new Vector<Item>();
		for (ListIterator<Item> it = customer.wl.listIterator(); it.hasNext();)		
			items.add(it.next());
		Collections.sort(items, new NameComparator());
		list = new JList(dlm);
		for (Iterator<Item> ii = items.iterator(); ii.hasNext();) 
			dlm.addElement(ii.next());
		list.setCellRenderer(new ItemRenderer());
		list.addListSelectionListener(listener);
		sp = new JScrollPane(list);
		c.gridx = 0;
		c.gridy = 0;
		add(sp, c);
		c.gridy = 1;
		b2 = new JButton("Remove selected product");
		b2.addActionListener(al2);
		add(b2, c);
		c.gridy = 2;
		b1 = new JButton("Add product");
		b1.addActionListener(al1);
		add(b1, c);
		c.gridy = 3;
		add(new JLabel("(insert product ID first)"), c);
		c.gridy = 4;
		t = new JTextField(10);
		add(t, c);
		setVisible(true);
	}
	// retine produsul selectat
	ListSelectionListener listener = new ListSelectionListener() {
		public void valueChanged(ListSelectionEvent e) {
			JList l = (JList) e.getSource(); 
			if(l.isSelectionEmpty())             
    	        return;
			selected = (Item) l.getSelectedValue();
		}
	}; 
	// adauga un produs in lista
	ActionListener al1 = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			Item item = Store.getInstance().getItem(Integer.parseInt(t.getText()));
			if (dlm.contains(item) == false) {
				item.wasAddedInWL();
				customer.wl.add(item.getCopy());
				Store.getInstance().getDepartment(item.getDepID()).addObserver(customer);
				dlm.removeAllElements();
				items = new Vector<Item>();
				for (ListIterator<Item> it = customer.wl.listIterator(); it.hasNext();)		
					items.add(it.next());
				Collections.sort(items, new NameComparator());
				list = new JList(dlm);
				for (Iterator<Item> ii = items.iterator(); ii.hasNext();) 
					dlm.addElement(ii.next());
			}
		}	
	};
	// elimina produsul selectat
	ActionListener al2 = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			customer.wl.remove(selected);
			if (customer.wl.isInterestedIn(selected.getDepID()) == false)
				Store.getInstance().getDepartment(selected.getDepID()).removeObserver(customer);
			dlm.removeElement(selected);
		}
	};
}
