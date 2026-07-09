package university.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Course implements Serializable {

    private String courseId;
    private String courseName;
    private int creditHours;

    private Faculty faculty;
    private ClassRoom classroom;

    private ArrayList<Student> students = new ArrayList<>();

    public Course(String courseId, String courseName, int creditHours) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.creditHours = creditHours;
    }

    // GETTERS
    public String getCourseId() { return courseId; }
    public String getCourseName() { return courseName; }
    public int getCreditHours() { return creditHours; }
    public Faculty getFaculty() { return faculty; }
    public ClassRoom getClassroom() { return classroom; }
    public ArrayList<Student> getStudents() { return students; }

    // SETTERS
    public void setCourseId(String courseId) { this.courseId = courseId; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public void setCreditHours(int creditHours) { this.creditHours = creditHours; }

    // FACULTY
    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public void assignFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    // CLASSROOM FIX
    public void setClassroom(ClassRoom classroom) {
        this.classroom = classroom;
    }

    public void assignClassroom(ClassRoom classroom) {
        this.classroom = classroom;
    }

    // STUDENTS
    public void addStudent(Student student) {
        if (student != null && !students.contains(student)) {
            students.add(student);
        }
    }

    @Override
    public String toString() {
        return courseId + " - " + courseName;
    }
}