package university.model;

public class Staff extends Person {

    protected String role;
    protected Department department;

    public Staff(String id, String name, String email, String phone, String role) {
        super(id, name, email, phone);
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}