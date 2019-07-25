import java.text.DecimalFormat;
import java.util.*;

public class ItemList {
	Comparator<Item> cmp;
	static class Node {
		Item item;
		Node prev, next;
		public Node(Item i) {
			item = i;
			prev = null;
			next = null;
		} 
	}
	Node first = null, last = null;
	class ItemIterator implements ListIterator<Item> {
		int currentIndex;
		public ItemIterator(int index) {
			currentIndex = index;
		}
		public ItemIterator() {
			currentIndex = 0;
		}
		public boolean hasNext() {
			if (getNode(currentIndex) == null)
				return false;
			return true;
		}
		public Item next() {
			currentIndex++;
			return getNode(currentIndex - 1).item;
		}
		public boolean hasPrevious() {
			if (getNode(currentIndex).prev == null)
				return false;
			return true;
		}
		public Item previous() {
			currentIndex--;
			return getNode(currentIndex).item;
		}
		public int nextIndex() {
			return currentIndex + 1;
		}
		public int previousIndex() {
			return currentIndex - 1;
		}
		public void remove() {
			ItemList.this.remove(currentIndex);
		}
		public void set(Item e) {
			getNode(currentIndex).item =(Item) e;
		}
		public void add(Item e) {
			Node n = new Node((Item) e);
			n.prev = getNode(previousIndex());
			n.next = getNode(currentIndex);
			getNode(currentIndex).prev.next = n;
			getNode(currentIndex).prev = n;
		}
	}
	public boolean add(Item element) {
		if (first == null) { // lista e vida
			first = new Node(element);
			last = first;
			return true;
		}
		if (contains(element) == true) { // lista contine elementul deja
			return false;
		}
		Node n = new Node(element);
		if (cmp.compare(n.item, first.item) <= 0) { // produsul ar trebuie sa fie pe prima pozitie
			n.next = first;
			first.prev = n;
			first = n;
			return true;
		}
		if (first.next == null) { // lista are doar un element si produsul nu ar trebuie sa fie primul => ar trebuie sa fie al doilea
			n.prev = first;
			first.next = n;
			last = n;
			return true;
		}
		Node aux = first;
		while (cmp.compare(aux.item, n.item) > 0 || cmp.compare(n.item, aux.next.item) > 0) { // i se cauta pozitia
			aux = aux.next;
			if (aux.next == null) {
				n.prev = aux;
				aux.next = n;
				last = n;
				return true;
			}
		}	
		n.prev = aux;
		n.next = aux.next;
		if (aux.next != null)
			aux.next.prev = n;
		else
			last = n;
		aux.next = n;
		return true;
	}
	public boolean addAll(Collection<? extends  Item> c) {
		for (Iterator<? extends Item> it = c.iterator(); it.hasNext();)
			add(it.next());
		return true;
	}
	public Item getItem(int index) {
		Node aux = first;
		int i = 0;
		while (i < index) {
			aux = aux.next;
			i++;
		}
		return aux.item;
	}
	public Node getNode(int index) {
		Node aux = first;
		int i = 0;
		while (i < index) {
			aux = aux.next;
			i++;
		}
		return aux;
	}
	public int indexOf(Item item) {
		Node aux = first;
		int i = 0;
		while (aux != null) {
			if (aux.item.getID().equals(item.getID())) 
				return i;
			aux = aux.next;
			i++;
		}
		return -1;
	}
	public int indexOf(Node node) {
		Node aux = first;
		int i = 0;
		while (aux != null) {
			if (aux == node) 
				return i;
			aux = aux.next;
			i++;
		}
		return -1;
	}
	public boolean contains(Node node) {
		Node aux = first;
		while (aux != null) {
			if (aux == node)
				return true;
			aux = aux.next;
		}
		return false;
	}
	public boolean contains(Item item) {
		Node aux = first;
		while (aux != null) {
			if (aux.item.getID().equals(item.getID()))
				return true;
			aux = aux.next;
		}
		return false;
	}
	public Item remove(int index) {
		Node aux = first;
		int i = 0;
		while (aux != null) {
			if (i == index) {
				if (aux.prev != null)
					aux.prev.next = aux.next;
				if (aux.next != null)
					aux.next.prev = aux.prev;
				if (aux == first)
					first = first.next;
				if (aux == last)
					last = last.prev;
				return aux.item;
			}
			aux = aux.next;
			i++;
		}
		return null;
	}
	public boolean remove(Item item) {
		Node aux = first;
		while (aux != null) {
			if (aux.item.getID().equals(item.getID())) {
				if (aux.prev != null)
					aux.prev.next = aux.next;
				if (aux.next != null)
					aux.next.prev = aux.prev;
				if (aux == first)
					first = first.next;
				if (aux == last)
					last = last.prev;
				return true;
			}
			aux = aux.next;
		}
		return false;
	}
	public boolean removeAll(Collection <?extends Item> collection) {
		for (Iterator<? extends Item> it = collection.iterator(); it.hasNext();)
			remove(it.next());
		return true;
	}
	public boolean isEmpty() {
		if (first == null)
			return true;
		return false;
	}
	public ListIterator<Item> listIterator(int index) {
		return new ItemIterator(index);
	}
	public ListIterator<Item> listIterator() {
		return new ItemIterator();
	}
	public Double getTotalPrice() {
		Node aux = first;
		Double res = 0.00;
		while (aux != null) {
			res += aux.item.getPrice();
			aux = aux.next;
		}
		return Double.valueOf(new DecimalFormat("#.##").format(res));
	}
	// verifica daca in lista exista cel putin un produs din departamentul respectiv
	public boolean isInterestedIn(Integer depID) { 
		for (ListIterator<Item> it = listIterator(); it.hasNext();) 
			if (it.next().getDepID().equals(depID)) 
				return true;
		return false;
	}
	// returneaza produsele conform formatului de la Cerinta 1
	public String printList() {
		ListIterator<Item> iter = listIterator();
		if (iter.hasNext() == false) 
			return "[]";
		String res = "[";
		while (iter.hasNext() == true) {
			res += iter.next();
			if (iter.hasNext() == true)
				res += ", ";
		}
		res += "]";
		return res;
	}
}
