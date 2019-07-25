package proiect;

public class BookDepartment extends Department {
	public BookDepartment(DepartmentBuilder builder) {
		super(builder);
	}
	void accept(ShoppingCart sc) {
		sc.visit(this);
	}
}
