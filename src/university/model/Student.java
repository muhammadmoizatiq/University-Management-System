package university.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String email;
    private String phone;
    private String program;

    private ArrayList<Course> courses = new ArrayList<>();
    private ArrayList<Grade> grades = new ArrayList<>();

    public Student(String id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }


    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

//Basics
    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }

    public ArrayList<Course> getCourses() { return courses; }
    public ArrayList<Grade> getGrades() { return grades; }

// Enroll Course
    public void enrollCourse(Course c) {
        if (c != null && !courses.contains(c)) {
            courses.add(c);
            c.addStudent(this);

            // ensure grade exists
            boolean exists = false;
            for (Grade g : grades) {
                if (g.getCourse().equals(c)) {
                    exists = true;
                    break;
                }
            }

            if (!exists) {
                grades.add(new Grade(c));
            }
        }
    }

   //Grades
    public void addGrade(Grade g) {
        grades.add(g);
    }

    public Grade getGrade(Course course) {
        for (Grade g : grades) {
            if (g.getCourse() != null && g.getCourse().equals(course)) {
                return g;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return id + " - " + name;
    }
}