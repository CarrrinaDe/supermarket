public interface Subject {
	void addObserver(Customer c);
	void removeObserver(Customer c);
	void notifyAllObservers(Notification n);
}
