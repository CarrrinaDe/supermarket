package proiect;

import java.util.ListIterator;

public class StrategyA implements Strategy {
	public Item execute(WishList wishList) {
		if (wishList.isEmpty() == true)
			return null;
		Item min = wishList.getItem(0);
		for (ListIterator<Item> i = wishList.listIterator(1); i.hasNext();) {
			Item item = i.next();
			if (item.getPrice() < min.getPrice()) 
				min = item;
		}
		return min;
	}
}
