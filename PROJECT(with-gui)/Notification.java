package proiect;

import java.util.Date;

enum NotificationType {ADD, REMOVE, MODIFY};

public class Notification {
	Date date;
	NotificationType type;
	Integer departmentID;
	Integer productID;
	
	Notification(Date d, NotificationType t, Integer dID, Integer pID) {
		date = d;
		type = t;
		departmentID = dID;
		productID = pID;
	}
	public String toString() {
		return type+";"+productID+";"+departmentID;
	}
}
