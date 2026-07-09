package university.model;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private String role;

    private String email;
    private String phone;

    private Student studentProfile;
    private Faculty facultyProfile;

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public Student getStudentProfile() { return studentProfile; }
    public void setStudentProfile(Student studentProfile) { this.studentProfile = studentProfile; }

    public Faculty getFacultyProfile() { return facultyProfile; }
    public void setFacultyProfile(Faculty facultyProfile) { this.facultyProfile = facultyProfile; }

    @Override
    public String toString() {
        return username + " (" + role + ")";
    }
}