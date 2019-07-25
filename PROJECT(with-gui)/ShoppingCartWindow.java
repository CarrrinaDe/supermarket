package proiect;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ShoppingCartWindow extends JFrame {
	GridBagConstraints c = new GridBagConstraints();
	DefaultListModel dlm;
	JList list;
	JScrollPane sp;
	Customer customer;
	JButton b1, b2, b3, b4;
	JLabel jl1, jl2 ;
	JTextField t;
	Item selected;
	Vector<Item> items;
	
	public ShoppingCartWindow(String text, Customer cust) {
		super(text);
		customer = cust;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setLayout(new GridBagLayout());
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = 0;
		dlm = new DefaultListModel();
		items = new Vector<Item>();
		for (ListIterator<Item> it = customer.sc.listIterator(); it.hasNext();)		
			items.add(it.next());
		Collections.sort(items, new PriceComparatorAsc());
		list = new JList(dlm);
		for (Iterator<Item> ii = items.iterator(); ii.hasNext();) 
			dlm.addElement(ii.next());
		list.setCellRenderer(new ItemRenderer());
		list.addListSelectionListener(listener);
		sp = new JScrollPane(list);
		add(sp, c);
		
		c.gridy = 1;
		b4 = new JButton("BUY ALL");
		b4.addActionListener(al4);
		add(b4, c);
		c.gridy = 2;
		jl1 = new JLabel("Total: "+customer.sc.getTotalPrice());
		add(jl1, c);
		c.gridy = 3;
		jl2 = new JLabel("Budget left: "+customer.sc.getBudget());
		add(jl2, c);
		c.gridy = 4;
		
		b2 = new JButton("Remove selected product");
		b2.addActionListener(al2);
		add(b2, c);
		c.gridy = 5;
		b1 = new JButton("Add product");
		b1.addActionListener(al1);
		add(b1, c);
		c.gridy = 6;
		add(new JLabel("(insert product ID first)"), c);
		c.gridy = 7;
		t = new JTextField(10);
		add(t, c);
		c.gridy = 8;
		
		b3 = new JButton("Suggest a product from WishList");
		b3.addActionListener(al3);
		add(b3, c);
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
	// adauga un produs
	ActionListener al1 = new ActionListener() {
		public void actionPerformed(ActionEvent e) { 
			Item i = Store.getInstance().getItem(Integer.parseInt(t.getText()));
			if (dlm.contains(i) == false) {
				customer.sc.add(i.getCopy());
				dlm.removeAllElements();
				for (Iterator<Item> it = customer.sc.listIterator(); it.hasNext();) 
					dlm.addElement(it.next());
				c.gridx = 0;
				c.gridy = 0;
				add(sp, c);
				c.gridy = 2;
				jl1.setVisible(false);
				jl1 = new JLabel("Total: "+customer.sc.getTotalPrice());
				add(jl1, c);
				c.gridy = 3;
				jl2.setVisible(false);
				jl2 = new JLabel("Budget left: "+customer.sc.getBudget());
				add(jl2, c);
				setVisible(true);
			}
		}
	};
	// elimina un produs
	ActionListener al2 = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			customer.sc.remove(selected);
			dlm.removeElement(selected);
			selected.wasRemovedFromWL();
			c.gridx = 0;
			c.gridy = 0;
			add(sp, c);
			c.gridy = 2;
			jl1.setVisible(false);
			jl1 = new JLabel("Total: "+customer.sc.getTotalPrice());
			add(jl1, c);
			c.gridy = 3;
			jl2.setVisible(false);
			jl2 = new JLabel("Budget left: "+customer.sc.getBudget());
			add(jl2, c);
			setVisible(true);
		}
	};
	// adauga un produs din cosul de cumparaturi, conform strategiei
	ActionListener al3 = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			Item chosen = customer.wl.executeStrategy();
			if (chosen == null || customer.sc.add(chosen.getCopy()) == false)
				return;
			chosen.wasRemovedFromWL();
			if (customer.wl.isInterestedIn(chosen.getDepID()) == false)
				Store.getInstance().getDepartment(chosen.getDepID()).removeObserver(customer);
			dlm.removeAllElements();
			items = new Vector<Item>();
			for (ListIterator<Item> it = customer.sc.listIterator(); it.hasNext();)		
				items.add(it.next());
			Collections.sort(items, new PriceComparatorAsc());
			list = new JList(dlm);
			for (Iterator<Item> ii = items.iterator(); ii.hasNext();) 
				dlm.addElement(ii.next());
			c.gridx = 0;
			c.gridy = 0;
			add(sp, c);
			c.gridy = 2;
			jl1.setVisible(false);
			jl1 = new JLabel("Total: "+customer.sc.getTotalPrice());
			add(jl1, c);
			c.gridy = 3;
			jl2.setVisible(false);
			jl2 = new JLabel("Budget left: "+customer.sc.getBudget());
			add(jl2, c);
			setVisible(true);
		}
	};
	// clientul decide sa cumpere toate produsele
	ActionListener al4 = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			Double b = customer.sc.getBudget();
			customer.sc = new ShoppingCart(b);
			for (Enumeration<Item> en = dlm.elements(); en.hasMoreElements();) {
				Item tmp = en.nextElement();
				Store.getInstance().getItem(tmp.getID()).wasBought();
				if (Store.getInstance().getDepartment(tmp.getDepID()).getCustomers().contains(customer) == false)
					Store.getInstance().getDepartment(tmp.getDepID()).enter(customer);
			}
			dlm.removeAllElements();
			c.gridx = 0;
			c.gridy = 0;
			add(sp, c);
			c.gridy = 2;
			jl1.setVisible(false);
			jl1 = new JLabel("Total: "+customer.sc.getTotalPrice());
			add(jl1, c);
			c.gridy = 3;
			jl2.setVisible(false);
			jl2 = new JLabel("Budget left: "+customer.sc.getBudget());
			add(jl2, c);
			setVisible(true);
		}
	};
}
