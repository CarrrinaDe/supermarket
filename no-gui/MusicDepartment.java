public class MusicDepartment extends Department {
	public MusicDepartment(DepartmentBuilder builder) {
		super(builder);
	}
	void accept(ShoppingCart sc) {
		sc.visit(this);
	}
}
