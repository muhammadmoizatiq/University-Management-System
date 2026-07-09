package university.model;

import java.util.ArrayList;
import java.util.List;

public class UniversityData {

    public static List<User> users;
    public static List<Student> students;
    public static List<Faculty> faculties;
    public static List<Course> courses;
    public static List<ClassRoom> classrooms;
    public static List<Department> departments;

    static {

        users = FileManager.loadUsers();
        students = FileManager.loadStudents();
        faculties = FileManager.loadFaculty();
        courses = FileManager.loadCourses();
        classrooms = FileManager.loadClassrooms();
        departments = FileManager.loadDepartments();

        if (users == null) users = new ArrayList<>();
        if (students == null) students = new ArrayList<>();
        if (faculties == null) faculties = new ArrayList<>();
        if (courses == null) courses = new ArrayList<>();
        if (classrooms == null) classrooms = new ArrayList<>();
        if (departments == null) departments = new ArrayList<>();

        // SEED CLASSROOMS IF EMPTY
        if (classrooms.isEmpty()) {
            classrooms.add(new ClassRoom("101", "Block A", 40));
            classrooms.add(new ClassRoom("102", "Block A", 45));
            classrooms.add(new ClassRoom("103", "Block B", 50));
            classrooms.add(new ClassRoom("201", "Block B", 40));
            classrooms.add(new ClassRoom("202", "Block C", 60));
            FileManager.saveClassrooms(classrooms);
        }


        boolean adminExists = false;
        for (User u : users) {
            if (u.getUsername().equals("admin")) {
                adminExists = true;
                break;
            }
        }

        if (!adminExists) {
            users.add(new User("admin", "admin123", "ADMIN"));
            FileManager.saveUsers(users);
        }

        resolveReferences();
    }

    public static void addUser(User u) {
        users.add(u);
        FileManager.saveUsers(users);
    }

    public static void addStudent(Student s) {
        students.add(s);
        FileManager.saveStudents(students);
    }

    public static void addFaculty(Faculty f) {
        faculties.add(f);
        FileManager.saveFaculty(faculties);
    }

    public static void addCourse(Course c) {
        courses.add(c);
        FileManager.saveCourses(courses);
    }

    public static void addDepartment(Department d) {
        departments.add(d);
        FileManager.saveDepartments(departments);
    }

    public static void resolveReferences() {
        // Build maps for quick lookup
        java.util.Map<String, Student> studentMap = new java.util.HashMap<>();
        for (Student s : students) {
            studentMap.put(s.getId(), s);
         //   s.getCourses().clear();
        }

        java.util.Map<String, Faculty> facultyMap = new java.util.HashMap<>();
        for (Faculty f : faculties) {
            facultyMap.put(f.getId(), f);
            f.getCourses().clear();
        }

        java.util.Map<String, Course> courseMap = new java.util.HashMap<>();
        for (Course c : courses) {
            courseMap.put(c.getCourseId(), c);
            c.getStudents().clear();
        }

        java.util.Map<String, ClassRoom> roomMap = new java.util.HashMap<>();
        for (ClassRoom r : classrooms) {
            roomMap.put(r.getRoomNo(), r);
        }

        // Link Course -> Classroom
        for (Course c : courses) {
            if (c.getClassroom() != null) {
                ClassRoom r = roomMap.get(c.getClassroom().getRoomNo());
                if (r != null) {
                    c.assignClassroom(r);
                } else {
                    c.assignClassroom(null);
                }
            }
        }

        // Link Course -> Faculty
        for (Course c : courses) {
            if (c.getFaculty() != null) {
                Faculty f = facultyMap.get(c.getFaculty().getId());
                if (f != null) {
                    c.setFaculty(f);
                    f.assignCourse(c);
                } else {
                    c.setFaculty(null);
                }
            }
        }

        // Link Student -> Course
        for (Student s : students) {
            java.util.ArrayList<Course> resolvedCourses = new java.util.ArrayList<>();
            for (Course c : s.getCourses()) {
                Course canonicalCourse = courseMap.get(c.getCourseId());
                if (canonicalCourse != null) {
                    resolvedCourses.add(canonicalCourse);
                    canonicalCourse.addStudent(s);
                }
            }
            s.getCourses().clear();
            s.getCourses().addAll(resolvedCourses);

            // Link Grade -> Course
            for (Grade g : s.getGrades()) {
                if (g.getCourse() != null) {
                    Course canonicalCourse = courseMap.get(g.getCourse().getCourseId());
                    if (canonicalCourse != null) {
                        g.setCourse(canonicalCourse);
                    }
                }
            }
        }

        // Link User -> Student/Faculty Profiles
        for (User u : users) {
            if (u.getStudentProfile() != null) {
                Student s = studentMap.get(u.getStudentProfile().getId());
                if (s != null) {
                    u.setStudentProfile(s);
                }
            }
            if (u.getFacultyProfile() != null) {
                Faculty f = facultyMap.get(u.getFacultyProfile().getId());
                if (f != null) {
                    u.setFacultyProfile(f);
                }
            }
        }
    }
}