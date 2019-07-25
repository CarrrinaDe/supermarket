import java.util.Comparator;

public class NameComparator implements Comparator<Item> {
	public int compare(Item i1, Item i2) {
		return i1.name.compareTo(i2.name);
	}
}
