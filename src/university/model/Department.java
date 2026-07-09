package university.model;

import java.io.Serializable;

public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    private String deptName;

    public Department(String name) {

        this.deptName = name;
    }

    public String getDeptName() {

        return deptName;
    }

    public void setDeptName(String name) {

        this.deptName = name;
    }

    @Override
    public String toString() {

        return deptName;
    }
}