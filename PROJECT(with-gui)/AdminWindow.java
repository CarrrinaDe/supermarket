package proiect;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

public class AdminWindow extends JFrame {
	GridBagConstraints c = new GridBagConstraints();
	JButton add, remove, modify, orderNameAsc, orderPriceAsc, orderPriceDesc;
	DefaultListModel itemDLM, departmentDLM, customerDLM;
	JList itemList, departmentList, customerList;
	JScrollPane itemSP, departmentSP, customerSP;
	Item selected; 
	Vector<Item> items;
	JTextField t;
	JFrame jf1, jf2;
	
	public AdminWindow(String text) {
		super(text);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setLayout(new GridBagLayout());
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = 0;
		add(new JLabel("PRODUCTS"), c);
		orderNameAsc = new JButton("Order by name (Asc.)");
		orderNameAsc.addActionListener(order1);
		orderPriceAsc = new JButton("Order by price (Asc.)");
		orderPriceAsc.addActionListener(order2);
		orderPriceDesc = new JButton("Order by price (Desc.)");
		orderPriceDesc.addActionListener(order3);
		c.gridy = 1;
		add(orderNameAsc, c);
		c.gridy = 2;
		add(orderPriceAsc, c);
		c.gridy = 3;
		add(orderPriceDesc, c);
		
		items = new Vector<Item>();
		itemDLM = new DefaultListModel();
		for (Iterator<Department> d = (Iterator) Store.getInstance().getDepartments().values().iterator(); d.hasNext();) 
			for (Iterator<Item> it = (Iterator) d.next().getItems().values().iterator(); it.hasNext();) {
				Item i = it.next();
				items.add(i);
				itemDLM.addElement(i);
			}
		itemList = new JList(itemDLM);
		itemList.addListSelectionListener(itemListener);
		itemList.setCellRenderer(new ItemRenderer());
		itemSP = new JScrollPane(itemList);
		c.gridy = 4;
		add(itemSP, c);
		add = new JButton("Add new product");
		add.addActionListener(e1);
		remove = new JButton("Remove selected product");
		remove.addActionListener(e2);
		modify = new JButton("Modify selected product price");
		modify.addActionListener(e3);
		t = new JTextField(10);
		c.gridy = 5;
		add(remove, c);
		c.gridy = 6;
		add(modify, c);
		c.gridy = 7;
		add(new JLabel("(insert new price first)"), c);
		c.gridy = 8;
		add(t, c);
		c.gridy = 9;
		add(add, c);
		
		departmentDLM = new DefaultListModel();
		for (Iterator it = Store.getInstance().getDepartments().values().iterator(); it.hasNext();) 
			departmentDLM.addElement(it.next());
		departmentList = new JList(departmentDLM);
		departmentList.addListSelectionListener(departmentListener);
		departmentList.setCellRenderer(new DepartmentRenderer());
		departmentSP = new JScrollPane(departmentList);
		c.gridx = 1;
		c.gridy = 3;
		add(new JLabel("DEPARTMENTS"), c);
		c.gridy = 4;
		add(departmentSP, c);
		
		customerDLM = new DefaultListModel();
		customerList = new JList(customerDLM);
		for (Iterator it = Store.getInstance().getCustomers().iterator(); it.hasNext();) 
			customerDLM.addElement(it.next());
		customerList.addListSelectionListener(customerListener);
		customerList.setCellRenderer(new CustomerRenderer());
		customerSP = new JScrollPane(customerList);
		c.gridx = 2;
		c.gridy = 3;
		add(new JLabel("CUSTOMERS"), c);
		c.gridy = 4;
		add(customerSP, c);
		jf1 = null;
		jf2 = null;
        setVisible(true);
	}
	// retine produsul selectat
	ListSelectionListener itemListener = new ListSelectionListener() {
		public void valueChanged(ListSelectionEvent e) {
			JList l = (JList) e.getSource();
			if(l.isSelectionEmpty())             
    	        return;
			selected = (Item) l.getSelectedValue();
		}
	};
	// deschide fereastra CustomerOpt
	ListSelectionListener customerListener = new ListSelectionListener() {
		public void valueChanged(ListSelectionEvent e) {
			JList l = (JList) e.getSource();
			if(l.isSelectionEmpty())             
    	        return;
			if (jf1 == null || jf1.isDisplayable() == false)
				jf1 = new CustomerOptWindow("Customer Options", (Customer) l.getSelectedValue());
		}
	};
	//deschide fereastra departamentului selectat
	ListSelectionListener departmentListener = new ListSelectionListener() {
		public void valueChanged(ListSelectionEvent e) {
			JList l = (JList) e.getSource();
			if(l.isSelectionEmpty())             
    	        return;
			if (jf2 == null || jf2.isDisplayable() == false)
				jf2 = new DepartmentWindow(((Department) l.getSelectedValue()).getName(), (Department) l.getSelectedValue());
		}
	};
	// ordoneaza produsele alfabetic dupa nume
	ActionListener order1 = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (itemSP != null)
				itemSP.setVisible(false);
			itemDLM = new DefaultListModel();
			Collections.sort(items, new NameComparator());
			for (Iterator<Item> ii = items.iterator(); ii.hasNext();)
				itemDLM.addElement(ii.next());
			itemList = new JList(itemDLM);
			itemList.addListSelectionListener(itemListener);
			itemList.setCellRenderer(new ItemRenderer());
			itemSP = new JScrollPane(itemList);
			c.gridx = 0;
			c.gridy = 4;
			add(itemSP, c);
			setVisible(true);
		}
	};
	// ordoneaza produsele crescator dupa pret
	ActionListener order2 = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (itemSP != null)
				itemSP.setVisible(false);
			itemDLM = new DefaultListModel();
			Collections.sort(items, new PriceComparatorAsc());
			for (Iterator<Item> ii = items.iterator(); ii.hasNext();)
				itemDLM.addElement(ii.next());
			itemList = new JList(itemDLM);
			itemList.addListSelectionListener(itemListener);
			itemList.setCellRenderer(new ItemRenderer());
			itemSP = new JScrollPane(itemList);
			c.gridx = 0;
			c.gridy = 4;
			add(itemSP, c);
			setVisible(true);
		}
	};
	// ordoneaza produsele descrescator dupa pret
	ActionListener order3 = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (itemSP != null)
				itemSP.setVisible(false);
			itemDLM = new DefaultListModel();
			Collections.sort(items, new PriceComparatorDesc());
			for (Iterator<Item> ii = items.iterator(); ii.hasNext();)
				itemDLM.addElement(ii.next());
			itemList = new JList(itemDLM);
			itemList.addListSelectionListener(itemListener);
			itemList.setCellRenderer(new ItemRenderer());
			itemSP = new JScrollPane(itemList);
			c.gridx = 0;
			c.gridy = 4;
			add(itemSP, c);
			setVisible(true);
		}
	};
	// deschide fereastra AddProduct si adauga produsul 
	ActionListener e1 = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			AddProductWindow apw = new AddProductWindow("Add new product");
			apw.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		    apw.addWindowListener(new WindowAdapter() {
		        public void windowClosing(WindowEvent event) {
		            apw.dispose();
		            items = new Vector<Item>();
					itemDLM = new DefaultListModel();
					for (Iterator<Department> d = (Iterator) Store.getInstance().getDepartments().values().iterator(); d.hasNext();) 
						for (Iterator<Item> it = (Iterator) d.next().getItems().values().iterator(); it.hasNext();) {
							Item i = it.next();
							items.add(i);
							itemDLM.addElement(i);
						}
					itemList = new JList(itemDLM);
					itemList.addListSelectionListener(itemListener);
					itemList.setCellRenderer(new ItemRenderer());
					if (itemSP != null)
						itemSP.setVisible(false);
					itemSP = new JScrollPane(itemList);
					c.gridx = 0;
					c.gridy = 4;
					add(itemSP, c);
					setVisible(true);
					repaint();
		        }
		    });
		}
	};
	// elimina produsul selectat
	ActionListener e2 = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			Department dep1 = Store.getInstance().getDepartment(selected.getDepID());
			dep1.getItems().remove(selected);
			dep1.notifyAllObservers(new Notification(new Date(), NotificationType.REMOVE, dep1.getId(), selected.getID()));
			items.remove(selected);
			itemDLM.removeElement(selected);
			repaint();
		}
	};
	// modifica pretul
	ActionListener e3 = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			Double newPrice = Double.parseDouble(t.getText());
			items.get(items.indexOf(selected)).modifyPrice(newPrice);
			Store.getInstance().getDepartment(selected.getDepID()).notifyAllObservers(new Notification(new Date(), NotificationType.MODIFY, selected.getDepID(), selected.getID()));
			((Item) itemDLM.getElementAt(itemDLM.indexOf(selected))).modifyPrice(newPrice);
			selected.modifyPrice(newPrice);
			repaint();
		}
	};
}
