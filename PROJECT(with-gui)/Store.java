package proiect;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

public class Store {
	
	private static Store obj = null;
	String name;
	private HashMap<Integer, Department> departments;
	private Vector<Customer> customers;
	
	private Store() {
		departments = new HashMap<Integer, Department>();
		customers = new Vector<Customer>();
	}
	public static Store getInstance() {
		if (obj == null)
			obj = new Store();
		return obj;
	}
	// seteaza numele magazinului
	void setName(String n) { 
		name = n;
	}
	void enter(Customer c) {
		customers.add(c);
	}
	void exit(Customer c) {
		customers.remove(c);
	}
	ShoppingCart getShoppingCart(Double b) {
		return new ShoppingCart(b);	
	}
	Vector<Customer> getCustomers() {
		return customers;
	}
	HashMap<Integer, Department> getDepartments() {
		return departments;
	}
	void addDepartment(Department d) {
		departments.put(d.getId(), d);
	}
	Department getDepartment(Integer id) {
		return departments.get(id);
	}
	// intoarce clientul cu numele n
	Customer getCustomer(String n) { 
		for (int i = 0; i < customers.size(); i++) {
			if (customers.get(i).name.equals(n) == true)  // am gasit clientul cu nume n
				return customers.get(i);
		}
		return null;
	}
	// intoarce produsul cu ID-ul id
	Item getItem(Integer id) { 
		for (Iterator<Department> it = departments.values().iterator(); it.hasNext();) {
			HashMap<Integer, Item> items = it.next().getItems(); //items dintr-un dep
			if (items.get(id) != null) //daca exista item cu id acela
				return items.get(id); //l-am gasit
		}
		return null;
	}
}
