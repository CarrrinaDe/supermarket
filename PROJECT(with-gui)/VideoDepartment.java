package proiect;

public class VideoDepartment extends Department {
	public VideoDepartment(DepartmentBuilder builder) {
		super(builder);
	}
	void accept(ShoppingCart sc) {
		sc.visit(this);
	}
}
