package university.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    private static final String USER_FILE = "users.dat";
    private static final String STUDENT_FILE = "students.dat";
    private static final String FACULTY_FILE = "faculty.dat";
    private static final String COURSE_FILE = "courses.dat";
    private static final String CLASSROOM_FILE = "classrooms.dat";
    private static final String DEPT_FILE = "departments.dat";

    // SAVE
    private static void save(Object data, String fileName) {
        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // LOAD
    private static Object load(String fileName) {
        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(fileName))) {
            return in.readObject();
        } catch (Exception e) {
            return null;
        }
    }

    // USERS
    public static List<User> loadUsers() {
        List<User> list = (List<User>) load(USER_FILE);
        return (list != null) ? list : new ArrayList<>();
    }

    public static void saveUsers(List<User> users) {
        save(users, USER_FILE);
    }

    // STUDENTS
    public static List<Student> loadStudents() {
        List<Student> list = (List<Student>) load(STUDENT_FILE);
        return (list != null) ? list : new ArrayList<>();
    }

    public static void saveStudents(List<Student> students) {
        save(students, STUDENT_FILE);
    }

    //  FACULTY
    public static List<Faculty> loadFaculty() {
        List<Faculty> list = (List<Faculty>) load(FACULTY_FILE);
        return (list != null) ? list : new ArrayList<>();
    }

    public static void saveFaculty(List<Faculty> faculty) {
        save(faculty, FACULTY_FILE);
    }

    // COURSES
    public static List<Course> loadCourses() {
        List<Course> list = (List<Course>) load(COURSE_FILE);
        return (list != null) ? list : new ArrayList<>();
    }

    public static void saveCourses(List<Course> courses) {
        save(courses, COURSE_FILE);
    }

    //  CLASSROOMS
    public static List<ClassRoom> loadClassrooms() {
        List<ClassRoom> list = (List<ClassRoom>) load(CLASSROOM_FILE);
        return (list != null) ? list : new ArrayList<>();
    }

    public static void saveClassrooms(List<ClassRoom> classrooms) {
        save(classrooms, CLASSROOM_FILE);
    }

    // DEPARTMENTS
    public static List<Department> loadDepartments() {
        List<Department> list = (List<Department>) load(DEPT_FILE);
        return (list != null) ? list : new ArrayList<>();
    }

    public static void saveDepartments(List<Department> departments) {
        save(departments, DEPT_FILE);
    }
}