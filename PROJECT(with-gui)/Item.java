package proiect;

import java.text.DecimalFormat;

public class Item {
	String name;
	private Integer ID;
	private Double price;
	private Integer departmentID, timesWasBought, timesAddedInWL;
	public Item(String n, Integer id, Double p) {
		name = n;
		ID = id;
		price = p;
		timesWasBought = 0;
		timesAddedInWL = 0;
	}
	public Integer getID() {
		return ID;
	}
	public Double getPrice() {
		return Double.valueOf(new DecimalFormat("#.##").format(price));
	}
	public void modifyPrice(Double p) {
		price = p;
	}
	public void setDepID(Integer id) {
		departmentID = id;
	}
	public Integer getDepID() {
		return departmentID;
	}
	public void wasBought() {
		timesWasBought++;
	}
	public Integer timesWasBought() {
		return timesWasBought;
	}
	public void wasAddedInWL() {
		timesAddedInWL++;
	}
	public void wasRemovedFromWL() {
		timesAddedInWL--;
	}
	public Integer timesWasAddedInWL() {
		return timesAddedInWL;
	}
	public String toString() {
		return name+";"+ID+";"+getPrice();
	}
	public Item getCopy() {
		Item copy = new Item(name, getID(), getPrice()); //pt accept sa nu se modif pretul
		copy.setDepID(getDepID());
		return copy;
	}
}
