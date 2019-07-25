import java.io.*;
import java.util.*;

public class Test {

	public static void main(String[] args) throws IOException {
		// "store.txt"
		Scanner scanner = new Scanner(new File("store.txt"));
		Store.getInstance().setName(scanner.nextLine()); // nume magazin
		Integer n, i; StringTokenizer st;
		while (scanner.hasNextLine() == true) {
			st = new StringTokenizer(scanner.nextLine(), ";\n");
			String name = st.nextToken(); // NumeDep;ID
			Department d = null;
			switch (name) {
				case "BookDepartment":
					d = new BookDepartment.DepartmentBuilder().name(name).ID(Integer.parseInt(st.nextToken())).build();
					break;
				case "MusicDepartment":
					d = new MusicDepartment.DepartmentBuilder().name(name).ID(Integer.parseInt(st.nextToken())).build();
					break; 
				case "SoftwareDepartment":
					d = new SoftwareDepartment.DepartmentBuilder().name(name).ID(Integer.parseInt(st.nextToken())).build();
					break;
				case "VideoDepartment":
					d = new VideoDepartment.DepartmentBuilder().name(name).ID(Integer.parseInt(st.nextToken())).build();
					break;
			}
			n = Integer.parseInt(scanner.nextLine()); // nr de produse
			for (i = 0; i < n; i++) {
				st = new StringTokenizer(scanner.nextLine(), ";\n"); // Nume;ID;preț
				Item item = new Item(st.nextToken(), Integer.parseInt(st.nextToken()), Double.parseDouble(st.nextToken()));
				item.setDepID(d.getId());
				d.addItem(item);
			}
			Store.getInstance().addDepartment(d);
		}
		scanner.close();
		// "customers.txt"
		scanner = new Scanner(new File("customers.txt"));
		n = Integer.parseInt(scanner.nextLine()); // nr de clienti
		for (i = 0; i < n; i++) {
			st = new StringTokenizer(scanner.nextLine(), ";\n"); // nume;buget;strategie
			Store.getInstance().enter(new Customer(st.nextToken(), Store.getInstance().getShoppingCart(Double.parseDouble(st.nextToken())), st.nextToken().charAt(0)));
		}
		scanner.close();
		// events.txt
		BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
		String s;
		scanner = new Scanner(new File("events.txt"));
		n = Integer.parseInt(scanner.nextLine()); // nr de evenimente
		for (i = 0; i < n; i++) {
			st = new StringTokenizer(scanner.nextLine(), ";\n"); // un eveniment
			s = st.nextToken();
			switch(s.charAt(0)) {
			case 'a':
				if (s.contains("Item")) { // addItem;ItemID;ShoppingCart/WishList;CustomerName
					int itemID = Integer.parseInt(st.nextToken());
					String which = st.nextToken(); 
					Customer c = Store.getInstance().getCustomer(st.nextToken());
					Item it = Store.getInstance().getItem(itemID).getCopy();
					if (which.charAt(0) == 'S') 
						c.sc.add(it);
					else {
						c.wl.add(it);
						Store.getInstance().getDepartment(it.getDepID()).addObserver(c);
					}
				} else if (s.contains("Product")) { // addProduct;DepID;ItemID;Price;Name
					int depID = Integer.parseInt(st.nextToken());
					int itemID = Integer.parseInt(st.nextToken());
					Double price = Double.parseDouble(st.nextToken());
					String itemName = st.nextToken();
					Item item = new Item(itemName, itemID, price);
					item.setDepID(depID);
					Store.getInstance().getDepartment(depID).addItem(item);
					Store.getInstance().getDepartment(depID).notifyAllObservers(new Notification(new Date(), NotificationType.ADD, depID, itemID));
				} else if (s.contains("accept")) { // accept;DepID;CustomerName
					int depID = Integer.parseInt(st.nextToken());
					Customer c = Store.getInstance().getCustomer(st.nextToken());
					Store.getInstance().getDepartment(depID).accept(c.sc);
				}
				break;
			case 'd': 
				if (s.contains("Item")) { //delItem;ItemID;ShoppingCart/WishList;CustomerName
					int itemID = Integer.parseInt(st.nextToken());
					String which = st.nextToken(); 
					Customer c = Store.getInstance().getCustomer(st.nextToken()); 
					Item it = Store.getInstance().getItem(itemID);
					if (which.charAt(0) == 'S') 
						c.sc.remove(it);
					else {
						c.wl.remove(it);
						if (c.wl.isInterestedIn(it.getDepID()) == false)
							Store.getInstance().getDepartment(it.getDepID()).removeObserver(c);
					}
				} else if (s.contains("Product")) { //delProduct;ItemID
					int itemID = Integer.parseInt(st.nextToken());
					Item item = Store.getInstance().getItem(itemID);
					Department dep = Store.getInstance().getDepartment(item.getDepID());
					dep.notifyAllObservers(new Notification(new Date(), NotificationType.REMOVE, item.getDepID(), itemID));
					dep.getItems().remove(itemID);
				}
				break;
			case 'g':
				if (s.contains("Items")) { // getItems;ShoppingCart/WishList;CustomerName
					String which = st.nextToken();
					String cust = st.nextToken();
					if (which.charAt(0) == 'S') 
						writer.write(Store.getInstance().getCustomer(cust).sc.printList()+"\n");
					else 
						writer.write(Store.getInstance().getCustomer(cust).wl.printList()+"\n");
				} else if (s.contains("Item")) { // getItem;CustomerName
					Customer c = Store.getInstance().getCustomer(st.nextToken());
					Item chosen = c.wl.executeStrategy();
					writer.write(chosen+"\n");
					if (c.sc.add(chosen) == true) {
						c.wl.remove(chosen);
						if (c.wl.isInterestedIn(chosen.getDepID()) == false)
							Store.getInstance().getDepartment(chosen.getDepID()).removeObserver(c);
					}
				} else if (s.contains("Total")) { // getTotal;ShoppingCart/WishList;CustomerName
					String which = st.nextToken(); 
					Customer c = Store.getInstance().getCustomer(st.nextToken());
					if (which.charAt(0) == 'S') 
						writer.write(c.sc.getTotalPrice()+"\n");
					else 
						writer.write(c.wl.getTotalPrice()+"\n");
				} else if (s.contains("Observers")) { // getObservers;DepID
					int depID = Integer.parseInt(st.nextToken());
					writer.write(Store.getInstance().getDepartment(depID).showObservers()+"\n");
				} else if (s.contains("Notifications")) { // getNotifications;CustomerName
					String c = st.nextToken();
					writer.write(Store.getInstance().getCustomer(c).showNotifications()+"\n");
				}
				break;
			case 'm': // modifyProduct;DepID;ItemID;Price
				int depID = Integer.parseInt(st.nextToken());
				int itemID = Integer.parseInt(st.nextToken());
				Double p = Double.parseDouble(st.nextToken());
				Store.getInstance().getDepartment(depID).getItems().get(itemID).modifyPrice(p);
				Store.getInstance().getDepartment(depID).notifyAllObservers(new Notification(new Date(), NotificationType.MODIFY, depID, itemID));
				break;
			}
		}
		scanner.close();
		writer.close();
	}
}
