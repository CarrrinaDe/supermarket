package proiect;

public class StrategyB implements Strategy {
	public Item execute(WishList wishList) {
		if (wishList.isEmpty() == true)
			return null;
		return wishList.getItem(0);
	}
}
