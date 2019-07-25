package proiect;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

public class Department implements Subject {
	private final String name;
	private final Integer ID;
	private HashMap<Integer, Item> items;
	private Vector<Customer> buyers; //au cump cel putin un produs
	private Vector<Customer> wishers; //au in wl cel putin un produs
	private Vector<Customer> customers; //au in sc cel putin un produs
	
	public Department(DepartmentBuilder builder) {
		this.name = builder.name;
		this.ID = builder.ID;
		items = new HashMap<Integer, Item>();
		buyers = new Vector<Customer>();
		wishers = new Vector<Customer>();
	}
	void enter(Customer c) { 
		buyers.add(c);
	}
	void exit(Customer c) {
		customers.remove(c);
	}
	Vector<Customer> getCustomers() { 
		return buyers;
	}
	Vector<Customer> getObservers() { 
		return wishers;
	}
	String getName() {
		return name;
	}
	Integer getId() {
		return ID;
	}
	public static class DepartmentBuilder {
		private String name;
		private Integer ID;
		public DepartmentBuilder name(String name) {
			this.name = name;
			return this;
		}
		public DepartmentBuilder ID(Integer ID) {
			this.ID = ID;
			return this;
		}
		public Department build() {
			return new Department(this);
		}
	}
	void addItem(Item item) {
		items.put(item.getID(), item);
	}
	HashMap<Integer, Item> getItems() {
		return items;
	}
	public void addObserver(Customer c) {
		if (wishers.contains(c) == false)
			wishers.add(c);
	}
	public void removeObserver(Customer c) {
		wishers.remove(c);
	}
	public void notifyAllObservers(Notification n) {
		for (Iterator<Customer> it = wishers.iterator(); it.hasNext();) 
			it.next().update(n);
	}
	// intoarce clientii din wishers in formatul pt output
	public String showObservers() { 
		Iterator<Customer> it = wishers.iterator();
		if (it.hasNext() == false) 
			return "[]";
		String res = "[";
		while (it.hasNext() == true) {
			res += it.next();
			if (it.hasNext() == true)
				res+= ", ";
		}
		res += "]";
		return res;
	}
	void accept(ShoppingCart sc) {
		return;
	}
}
