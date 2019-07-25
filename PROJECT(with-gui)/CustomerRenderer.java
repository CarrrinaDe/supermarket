package proiect;

import java.awt.*;
import javax.swing.*;

public class CustomerRenderer extends JPanel implements ListCellRenderer {
	JLabel name = new JLabel();
	public CustomerRenderer() {
         setOpaque(true);
     }
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		name.setText(((Customer) value).name);
		setForeground(isSelected ? Color.WHITE : Color.BLACK);
		setBackground(isSelected ? Color.RED : (index % 2 == 0 ? Color.PINK : Color.YELLOW));		
		add(name);
		return this; 
	}	
}
