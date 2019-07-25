package proiect;

import java.awt.*;
import java.util.Iterator;

import javax.swing.*;

public class DepartmentWindow extends JFrame {
	GridBagConstraints c = new GridBagConstraints();
	Department department;
	DefaultListModel customersDLM, observersDLM;
	JList customerList, observerList;
	JScrollPane customerSP, observerSP;
	Item expensive, bought, wanted;
	
	public DepartmentWindow(String text, Department dep) {
		super(text);
		department = dep;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setLayout(new GridBagLayout());
		c.anchor = GridBagConstraints.CENTER;
		
		customersDLM = new DefaultListModel();
		customerList = new JList(customersDLM);
		for (Iterator it = department.getCustomers().iterator(); it.hasNext();)
			customersDLM.addElement(it.next());
		customerList.setCellRenderer(new CustomerRenderer());
		customerSP = new JScrollPane(customerList);
		c.gridx = 0;
		c.gridy = 0;
		add(new JLabel("CUSTOMERS"), c);
		c.gridy = 1;
		add(customerSP, c);
		
		observersDLM = new DefaultListModel();
		observerList = new JList(observersDLM);
		for (Iterator it = department.getObservers().iterator(); it.hasNext();)
			observersDLM.addElement(it.next());
		observerList.setCellRenderer(new CustomerRenderer());
		observerSP = new JScrollPane(observerList);
		c.gridy = 2;
		add(new JLabel("OBSERVERS"), c);
		c.gridy = 3;
		add(observerSP, c);
		
		c.gridy = 4;
		add(new JLabel("MOST BOUGHT"), c);
		c.gridy = 5;
		bought = new Item("", 0, 0.0);
		for (Iterator<Item> it = department.getItems().values().iterator(); it.hasNext();) {
			Item tmp = it.next();
			if (tmp.timesWasBought() > bought.timesWasBought())
				bought = tmp;
		}
		add(new JLabel(bought.getID()+"."+bought.name+" "+bought.getPrice()), c);
		
		c.gridy = 6;
		add(new JLabel("MOST WANTED"), c);
		c.gridy = 7;
		wanted = new Item("", 0, 0.0);
		for (Iterator<Item> it = department.getItems().values().iterator(); it.hasNext();) {
			Item tmp = it.next();
			if (tmp.timesWasAddedInWL() > wanted.timesWasAddedInWL())
				wanted = tmp;
		}
		add(new JLabel(wanted.getID()+"."+wanted.name+" "+wanted.getPrice()), c);
		
		c.gridy = 8;
		add(new JLabel("MOST EXPENSIVE"), c);
		c.gridy = 9;
		expensive = new Item("", 0, 0.0);
		for (Iterator<Item> it = department.getItems().values().iterator(); it.hasNext();) {
			Item tmp = it.next();
			if (tmp.getPrice() > expensive.getPrice())
				expensive = tmp;
		}
		add(new JLabel(expensive.getID()+"."+expensive.name+" "+expensive.getPrice()), c);
		setVisible(true);
	}
}
