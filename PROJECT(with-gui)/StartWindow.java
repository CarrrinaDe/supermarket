package proiect;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.*;

public class StartWindow extends JFrame {;
	JButton b;
	
	public StartWindow(String text) {
		super(text);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setLayout(new GridBagLayout());
		b = new JButton("LOAD STORE"); 
		ActionListener al = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// "store.txt"
				Scanner scanner = null;
				try {
					scanner = new Scanner(new File("/home/carrrina/Documents/test03/store.txt"));
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				Store.getInstance().setName(scanner.nextLine());
				Integer n, i; StringTokenizer st;
				while (scanner.hasNextLine() == true) {
					st = new StringTokenizer(scanner.nextLine(), ";\n");
					String name = st.nextToken(); // NumeDep;ID
					Department d = null;
					switch (name) {
						case "BookDepartment":
							d = new BookDepartment.DepartmentBuilder().name(name).ID(Integer.parseInt(st.nextToken())).build();
							break;
						case "MusicDepartment":
							d = new MusicDepartment.DepartmentBuilder().name(name).ID(Integer.parseInt(st.nextToken())).build();
							break; 
						case "SoftwareDepartment":
							d = new SoftwareDepartment.DepartmentBuilder().name(name).ID(Integer.parseInt(st.nextToken())).build();
							break;
						case "VideoDepartment":
							d = new VideoDepartment.DepartmentBuilder().name(name).ID(Integer.parseInt(st.nextToken())).build();
							break;
					}
					n = Integer.parseInt(scanner.nextLine()); // nr de produse
					for (i = 0; i < n; i++) {
						st = new StringTokenizer(scanner.nextLine(), ";\n"); // Nume;ID;preț
						Item item = new Item(st.nextToken(), Integer.parseInt(st.nextToken()), Double.parseDouble(st.nextToken()));
						item.setDepID(d.getId());
						d.addItem(item);
					}
					Store.getInstance().addDepartment(d);
				}
				scanner.close();
				// "customers.txt"
				try {
					scanner = new Scanner(new File("/home/carrrina/Documents/test03/customers.txt"));
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				n = Integer.parseInt(scanner.nextLine()); // nr de clienti
				for (i = 0; i < n; i++) {
					st = new StringTokenizer(scanner.nextLine(), ";\n"); // un client
					Store.getInstance().enter(new Customer(st.nextToken(), Store.getInstance().getShoppingCart(Double.parseDouble(st.nextToken())), st.nextToken().charAt(0)));
				}
				scanner.close();
				dispose();
				new AdminWindow(Store.getInstance().name);
			}
        };       
        b.addActionListener(al);
        b.setPreferredSize(new Dimension(200, 50));
        add(b);
        pack();
        setVisible(true);
	}
	
	public static void main(String[] args) {
		StartWindow w = new StartWindow("Start Window");
	}
}
