package proiect;

public class StrategyC implements Strategy {
	public Item execute(WishList wishList) {
		if (wishList.isEmpty() == true)
			return null;
		return wishList.getLastAdded();
	}
}
