import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.ListIterator;

public class ShoppingCart extends ItemList implements Visitor {
	private Double budget;
	public ShoppingCart(double b) {
		super();
		cmp = new PriceComparatorAsc();
		budget = b;
	} 
	public Double getBudget() {
		return Double.valueOf(new DecimalFormat("#.##").format(budget));
	}
	public void modifyBudget(Double x) {
		budget += x;
	}
	public boolean add(Item element) {
		if (element.getPrice() > budget)
			return false;
		boolean res = super.add(element);
		if (res == true)
			budget -= element.getPrice();
		return res;
	}
	public Item remove(int index) {
		Item item = super.remove(index);
		if (item != null)
			budget += item.getPrice();
		return item;
	}
	public boolean remove(Item item) { 
		boolean res = super.remove(item);
		if (res == true)
			budget += item.getPrice();
		return res;
	}
	public void visit(BookDepartment bookDepartment) {
		for (ListIterator<Item> it = listIterator(); it.hasNext();) {
			Item item  = it.next();
			if (item.getDepID().equals(bookDepartment.getId())) {
				budget += 0.10 * item.getPrice();
				remove(item);
				item.modifyPrice(0.90 * item.getPrice());
				add(item);
			}
		}
	}
	public void visit(MusicDepartment musicDepartment) {
		Double total = 0.00;
		for (ListIterator<Item> it = listIterator(); it.hasNext();) {
			Item item  = it.next();
			if (item.getDepID().equals(musicDepartment.getId()))
				total += item.getPrice();
		}
		budget += 0.10 * total;
	}
	public void visit(SoftwareDepartment softwareDepartment) {
		Double minPrice = 0.00;
		for (Iterator<Item> it = softwareDepartment.getItems().values().iterator(); it.hasNext();) {
			Item item = it.next();
			if (item.getPrice() < minPrice)
				minPrice = item.getPrice();
		}
		if (budget < minPrice) {
			for (ListIterator<Item> it = listIterator(); it.hasNext();) {
				Item item = it.next();
				if (item.getDepID().equals(softwareDepartment.getId())) {
					budget += 0.20 * item.getPrice();
					remove(item);
					item.modifyPrice(0.80 * item.getPrice());
					add(item);
				}
			}
		}
	}
	public void visit(VideoDepartment videoDepartment) {
		Double total = 0.00, maxPrice = 0.00;
		for (ListIterator<Item> it = listIterator(); it.hasNext();) {
			Item item = it.next();
			if (item.getDepID().equals(videoDepartment.getId()))
				total += item.getPrice();
		}
		budget += 0.05 * total;
		for (Iterator<Item> it = videoDepartment.getItems().values().iterator(); it.hasNext();) {
			Item item = it.next();
			if (item.getPrice() > maxPrice)
				maxPrice = item.getPrice();
		}
		if (total > maxPrice) {
			for (ListIterator<Item> it = listIterator(); it.hasNext();) {
				Item item = it.next();
				if (item.getDepID().equals(videoDepartment.getId())) {
					budget += 0.15 * item.getPrice();
					remove(item);
					item.modifyPrice(0.85 * item.getPrice());	
					add(item);
				}
			}
		}
	}
}
