package proiect;

public class WishList extends ItemList {
	private Item lastAdded;
	Strategy strategy;
	
	public WishList(char ch) {
		super();
		lastAdded = null;	
		cmp = new NameComparator();
		switch (ch) {
			case 'A':
				strategy = new StrategyA();
				break;
			case 'B':
				strategy = new StrategyB();
				break;
			case 'C':
				strategy = new StrategyC();
				break;
		}
	}
	public boolean add(Item element) {
		lastAdded = element;
		return super.add(element); 
	}
	// returneaza ultimul produs adaugat
	public Item getLastAdded() {
		return lastAdded;
	}
	//returneaza produs selectat din wl conform strategiei
	Item executeStrategy() {
		return strategy.execute(this);
	}
}
