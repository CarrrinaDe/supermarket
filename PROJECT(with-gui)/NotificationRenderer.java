package proiect;

import java.awt.*;
import javax.swing.*;

public class NotificationRenderer extends JPanel implements ListCellRenderer {
	JLabel jl = new JLabel();
	public NotificationRenderer() {
         setOpaque(true);
     }
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		Notification n = (Notification) value;
		switch (n.type) {
			case ADD:
				jl.setText(n.date+": a fost adaugat un produs cu ID "+n.productID+" in departamentul cu ID "+n.departmentID);
				break;
			case REMOVE:
				jl.setText(n.date+": a fost eliminat produsul cu ID "+n.productID+" din departamentul cu ID "+n.departmentID);
				break;
			case MODIFY:
				jl.setText(n.date+": a fost modificat pretul produsului cu ID "+n.productID+" din departamentul cu ID "+n.departmentID);
				break;
		}
		setForeground(isSelected ? Color.WHITE : Color.BLACK);
		setBackground(isSelected ? Color.RED : (index % 2 == 0 ? Color.PINK : Color.YELLOW));		
		add(jl);
		return this; 
	}	
}
