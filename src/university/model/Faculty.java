package university.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Faculty extends Person implements Serializable {

    private static final long serialVersionUID = 1L;

    private String department;
    private List<Course> courses = new ArrayList<>();

    public Faculty(String id, String name, String email, String phone, String department) {
        super(id, name, email, phone);
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public List<Course> getCourses() {
        return courses;
    }

   //Assign Course
    public void assignCourse(Course c) {
        if (c != null && !courses.contains(c)) {
            courses.add(c);
            c.setFaculty(this);
        }
    }

    @Override
    public String toString() {
        return getName() + " (" + department + ")";
    }
}