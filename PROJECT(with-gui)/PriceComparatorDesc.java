package proiect;

import java.util.Comparator;

public class PriceComparatorDesc implements Comparator<Item> {
	public int compare(Item i1, Item i2) {
		if (i1.getPrice() > i2.getPrice())
			return -1;
		else if (i1.getPrice() == i2.getPrice()) {
			return i1.name.compareTo(i2.name);
		}
		else
			return 1;
	}
}
