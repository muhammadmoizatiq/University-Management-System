package university.ui;

import javafx.collections.FXCollections;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import university.model.*;

public class FacultyMarksView {

    private VBox view;

    public FacultyMarksView(Faculty faculty) {

        Label title = new Label("ENTER / UPDATE MARKS");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Course select
        Label courseLbl = new Label("Select Course");
        courseLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #555;");
        ComboBox<Course> courseBox = new ComboBox<>();
        courseBox.setItems(FXCollections.observableArrayList(faculty.getCourses()));
        courseBox.setPromptText("Select Course");
        courseBox.setMaxWidth(Double.MAX_VALUE);
        courseBox.setStyle("-fx-background-radius: 4; -fx-padding: 4;");

        // Student select
        Label studentLbl = new Label("Select Student");
        studentLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #555;");
        ComboBox<Student> studentBox = new ComboBox<>();
        studentBox.setPromptText("Select Student");
        studentBox.setMaxWidth(Double.MAX_VALUE);
        studentBox.setStyle("-fx-background-radius: 4; -fx-padding: 4;");

        // Midterm
        Label midLbl = new Label("Midterm Marks (50)");
        midLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #555;");
        TextField midField = new TextField();
        midField.setPromptText("Enter midterm marks");
        midField.setStyle("-fx-background-radius: 4; -fx-padding: 6;");

        // Finals
        Label finalLbl = new Label("Final Marks (50)");
        finalLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #555;");
        TextField finalField = new TextField();
        finalField.setPromptText("Enter final marks");
        finalField.setStyle("-fx-background-radius: 4; -fx-padding: 6;");

        Label msg = new Label();
        msg.setStyle("-fx-font-weight: bold;");

        // LOAD STUDENTS OF SELECTED COURSE
        courseBox.setOnAction(e -> {
            Course c = courseBox.getValue();
            if (c != null) {
                studentBox.setItems(FXCollections.observableArrayList(c.getStudents()));
                studentBox.setValue(null);
                midField.clear();
                finalField.clear();
                msg.setText("");
            }
        });

        // PRE-POPULATE MARKS ON STUDENT SELECT
        studentBox.setOnAction(e -> {
            Course c = courseBox.getValue();
            Student s = studentBox.getValue();
            if (c != null && s != null) {
                Grade g = s.getGrade(c);
                if (g != null) {
                    midField.setText(String.valueOf(g.getMid()));
                    finalField.setText(String.valueOf(g.getFinals()));
                    msg.setText("Loaded existing marks.");
                    msg.setStyle("-fx-text-fill: #4a5568;");
                } else {
                    midField.clear();
                    finalField.clear();
                    msg.setText("No marks entered yet.");
                    msg.setStyle("-fx-text-fill: #e65100;");
                }
            }
        });

        Button saveBtn = new Button("Save Marks");
        saveBtn.setMaxWidth(Double.MAX_VALUE);
        saveBtn.setStyle("-fx-background-color: #2e7d32; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10; -fx-background-radius: 6;");

        saveBtn.setOnAction(e -> {

            Course c = courseBox.getValue();
            Student s = studentBox.getValue();

            if (c == null || s == null) {
                msg.setText("Please select both course and student!");
                msg.setStyle("-fx-text-fill: red;");
                return;
            }

            double mid;
            double fin;

            try {
                mid = Double.parseDouble(midField.getText().trim());
                fin = Double.parseDouble(finalField.getText().trim());
            } catch (NumberFormatException nfe) {
                msg.setText("Marks must be valid numeric values!");
                msg.setStyle("-fx-text-fill: red;");
                return;
            }

            if (mid < 0 || mid > 50 || fin < 0 || fin > 50) {
                msg.setText("Marks must be between 0 and 50!");
                msg.setStyle("-fx-text-fill: red;");
                return;
            }

            // check existing grade
            Grade g = s.getGrade(c);

            if (g == null) {
                g = new Grade(c);
                s.addGrade(g);
            }

            g.setMid(mid);
            g.setFinals(fin);

            // Save to disk!
            FileManager.saveStudents(UniversityData.students);

            msg.setText("Marks saved successfully!");
            msg.setStyle("-fx-text-fill: green;");
        });

        VBox form = new VBox(8,
                courseLbl, courseBox,
                studentLbl, studentBox,
                midLbl, midField,
                finalLbl, finalField,
                saveBtn,
                msg
        );
        form.setPadding(new Insets(20));
        form.setStyle("-fx-background-color: #f9f9f9; -fx-border-color: #e0e0e0; -fx-border-radius: 8; -fx-background-radius: 8;");
        form.setMaxWidth(400);

        VBox container = new VBox(15, title, form);
        container.setPadding(new Insets(15));
        
        view = container;
    }

    public VBox getView() {
        return view;
    }
}