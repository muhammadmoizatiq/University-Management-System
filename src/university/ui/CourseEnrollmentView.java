package university.ui;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import university.model.*;

public class CourseEnrollmentView {

    private VBox view;

    public CourseEnrollmentView(Student student) {
        this(student, null);
    }

    public CourseEnrollmentView(Student student, Runnable onEnrollSuccess) {

        Label title = new Label("ENROLL IN NEW COURSE");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333;");

        // ================= COURSE LIST =================
        ComboBox<Course> courseBox = new ComboBox<>();
        courseBox.setItems(FXCollections.observableArrayList(UniversityData.courses));
        courseBox.setPromptText("Select Course to Enroll");
        courseBox.setMaxWidth(Double.MAX_VALUE);
        courseBox.setStyle("-fx-background-radius: 6; -fx-padding: 4;");

        // ================= INFO AREA =================
        Label info = new Label("Select a course to view details");
        info.setWrapText(true);
        info.setStyle("-fx-text-fill: #555; -fx-font-size: 13px; -fx-background-color: #fafafa; -fx-padding: 10; -fx-border-color: #e0e0e0; -fx-border-radius: 5; -fx-background-radius: 5;");
        info.setMinHeight(100);
        info.setMaxWidth(Double.MAX_VALUE);

        // ================= BUTTON =================
        Button enrollBtn = new Button("Enroll Course");
        enrollBtn.setMaxWidth(Double.MAX_VALUE);
        enrollBtn.setStyle("-fx-background-color: #2d6cdf; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10; -fx-background-radius: 6;");

        Label message = new Label();
        message.setStyle("-fx-font-weight: bold;");

        // ================= SHOW COURSE INFO =================
        courseBox.setOnAction(e -> {
            Course c = courseBox.getValue();
            if (c != null) {
                String facultyName = (c.getFaculty() != null) ? c.getFaculty().getName() : "Not assigned";
                String room = (c.getClassroom() != null) ? c.getClassroom().getRoomName() : "Not assigned";
                info.setText(
                        "Course ID: " + c.getCourseId() +
                        "\nCourse Name: " + c.getCourseName() +
                        "\nCredit Hours: " + c.getCreditHours() +
                        "\nFaculty: " + facultyName +
                        "\nClassroom: " + room +
                        "\nStudents Enrolled: " + c.getStudents().size()
                );
            }
        });

        // ================= ENROLL LOGIC =================
        enrollBtn.setOnAction(e -> {
            Course selected = courseBox.getValue();

            if (selected == null) {
                message.setText("Please select a course!");
                message.setStyle("-fx-text-fill: red;");
                return;
            }

            // prevent duplicate enrollment
            if (student.getCourses().contains(selected)) {
                message.setText("Already enrolled in this course!");
                message.setStyle("-fx-text-fill: #e65100;");
                return;
            }

            student.enrollCourse(selected);
            
            // Save immediately!
            FileManager.saveStudents(UniversityData.students);
            FileManager.saveCourses(UniversityData.courses);

            message.setText("Enrolled in: " + selected.getCourseName());
            message.setStyle("-fx-text-fill: green;");

            if (onEnrollSuccess != null) {
                onEnrollSuccess.run();
            }
        });

        // ================= LAYOUT =================
        VBox box = new VBox(12,
                title,
                courseBox,
                info,
                enrollBtn,
                message
        );

        box.setPadding(new Insets(15));
        box.setStyle("-fx-background-color: #f5f5f5; -fx-border-color: #e0e0e0; -fx-border-radius: 8; -fx-background-radius: 8;");

        view = box;
    }

    public VBox getView() {
        return view;
    }
}