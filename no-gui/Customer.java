import java.util.Iterator;
import java.util.ListIterator;
import java.util.Vector;

public class Customer implements Observer {
	String name;
	ShoppingCart sc;
	WishList wl;
	private Vector<Notification> notifications;
	
	Customer(String n, ShoppingCart sc, char strategy) {
		name = n;
		this.sc = sc;
		wl = new WishList(strategy);
		notifications = new Vector<Notification>();
	}
	public void update(Notification n) {
		notifications.add(n);
		switch (n.type) {
			case ADD:
				break;
			case REMOVE:
				for (ListIterator<Item> i = wl.listIterator(); i.hasNext();) {
					Item item = i.next();
					if (item.getID().equals(n.productID)) {
						wl.remove(item);
						if (wl.isInterestedIn(n.departmentID) == false)
							((Department) Store.getInstance().getDepartment(n.departmentID)).removeObserver(this);
						break;
					}
				}
				for (ListIterator<Item> i = sc.listIterator(); i.hasNext();) {
					Item item = i.next();
					if (item.getID().equals(n.productID)) {
						sc.remove(item);
						if (sc.isInterestedIn(n.departmentID) == false)
							((Department) Store.getInstance().getDepartment(n.departmentID)).exit(this);
						break;
					}
				}
				break;
			case MODIFY:	
				for (ListIterator<Item> i = wl.listIterator(); i.hasNext();) {
					Item item = i.next();
					if (item.getID().equals(n.productID)) {
						wl.remove(item);
						wl.add(Store.getInstance().getItem(n.productID).getCopy());
						break;
					}
				}
				for (ListIterator<Item> i = sc.listIterator(); i.hasNext();) {
					Item item = i.next();
					if (item.getID().equals(n.productID)) {
						sc.remove(item);
						sc.add(Store.getInstance().getItem(n.productID).getCopy());
						break;
					}
				}
				break;
		}
	}	
	public String toString() {
		return name;
	}
	public String showNotifications() { 
		Iterator<Notification> it = notifications.iterator();
		if (it.hasNext() == false) 
			return "[]";
		String res = "[";
		while (it.hasNext() == true) {
			res += it.next();
			if (it.hasNext() == true)
				res += ", ";
		}
		res += "]";
		return res;
	}
	public Vector<Notification> getNotifications() {
		return notifications;
	}
}
