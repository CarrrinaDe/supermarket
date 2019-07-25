public class SoftwareDepartment extends Department {
	public SoftwareDepartment(DepartmentBuilder builder) {
		super(builder);
	}
	void accept(ShoppingCart sc) {
		sc.visit(this);
	}
}
