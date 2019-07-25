package proiect;

import java.awt.*;
import javax.swing.*;

public class ItemRenderer extends JPanel implements ListCellRenderer {
	JLabel name = new JLabel(), price = new JLabel(), dep = new JLabel();
	public ItemRenderer() {
         setOpaque(true);
     }
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		name.setText(((Item) value).getID()+"."+((Item) value).name);
		price.setText("; "+((Item) value).getPrice());
		dep.setText("; "+((Item) value).getDepID()+"."+Store.getInstance().getDepartment(((Item) value).getDepID()).getName());;
		setForeground(isSelected ? Color.WHITE : Color.BLACK);
		setBackground(isSelected ? Color.RED : (index % 2 == 0 ? Color.PINK : Color.YELLOW));		
		add(name);
		add(price);
		add(dep);
		return this;
	}	
}
