package university.model;

import java.io.Serializable;

public class Grade implements Serializable {

    private Course course;
    private double mid;
    private double finals;

    public Grade(Course course) {
        this.course = course;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public double getMid() {
        return mid;
    }

    public double getFinals() {
        return finals;
    }

    public void setMid(double mid) {
        this.mid = mid;
    }

    public void setFinals(double finals) {
        this.finals = finals;
    }

    public double getTotal() {
        return mid + finals;
    }
}