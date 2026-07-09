package university.ui;

import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import university.model.*;

public class StudentDashboard {

    private BorderPane view;

    public StudentDashboard(Stage stage, User user) {

        Student student = user.getStudentProfile();

        VBox menu = new VBox(10);
        menu.setPadding(new Insets(15));
        menu.setPrefWidth(220);
        menu.setStyle("-fx-background-color: #2c3e50;");

        Button profile = new Button("Profile");
        Button courses = new Button("Courses");
        Button marks = new Button("Marks");
        Button logout = new Button("Logout");

        style(profile);
        style(courses);
        style(marks);
        style(logout);

        StackPane content = new StackPane();

        // PROFILE
        profile.setOnAction(e -> {

            VBox box = new VBox(15);
            box.setPadding(new Insets(20));

            Label titleLabel = new Label("STUDENT PROFILE");
            titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

            VBox infoBox = new VBox(6);
            infoBox.setStyle("-fx-background-color: #f8f9fa; -fx-padding: 15; -fx-border-color: #e2e8f0; -fx-border-radius: 8; -fx-background-radius: 8;");
            
            Label idLbl = new Label("Registration No: " + student.getId());
            idLbl.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
            
            Label nameLbl = new Label("Name: " + student.getName());
            nameLbl.setStyle("-fx-font-size: 14px;");
            
            Label emailLbl = new Label("Email: " + student.getEmail());
            emailLbl.setStyle("-fx-font-size: 14px;");
            
            Label progLbl = new Label("Program: " + (student.getProgram() != null && !student.getProgram().isEmpty() ? student.getProgram() : "N/A"));
            progLbl.setStyle("-fx-font-size: 14px;");

            infoBox.getChildren().addAll(idLbl, nameLbl, emailLbl, progLbl);

            Label coursesTitle = new Label("ENROLLED COURSES & INSTRUCTORS");
            coursesTitle.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #2c3e50; -fx-padding: 10 0 0 0;");

            VBox coursesList = new VBox(8);
            if (student.getCourses().isEmpty()) {
                Label noCourses = new Label("Not enrolled in any courses.");
                noCourses.setStyle("-fx-text-fill: #718096; -fx-font-style: italic;");
                coursesList.getChildren().add(noCourses);
            } else {
                for (Course c : student.getCourses()) {
                    HBox row = new HBox(10);
                    row.setStyle("-fx-background-color: white; -fx-padding: 10; -fx-border-color: #e2e8f0; -fx-border-radius: 6; -fx-background-radius: 6;");
                    
                    Label cName = new Label(c.getCourseName() + " (" + c.getCourseId() + ")");
                    cName.setStyle("-fx-font-weight: bold;");
                    
                    String teacherName = c.getFaculty() != null ? c.getFaculty().getName() : "TBA";
                    Label cTeacher = new Label("Instructor: " + teacherName);
                    cTeacher.setStyle("-fx-text-fill: #4a5568;");

                    Region spacer = new Region();
                    HBox.setHgrow(spacer, Priority.ALWAYS);

                    row.getChildren().addAll(cName, spacer, cTeacher);
                    coursesList.getChildren().add(row);
                }
            }

            box.getChildren().addAll(titleLabel, infoBox, coursesTitle, coursesList);
            content.getChildren().setAll(box);
        });

        //COURSES
        courses.setOnAction(e -> {

            HBox layout = new HBox(20);
            layout.setPadding(new Insets(15));
            HBox.setHgrow(layout, Priority.ALWAYS);

            // Left side: Enrolled Courses List
            VBox left = new VBox(10);
            left.setPrefWidth(400);
            HBox.setHgrow(left, Priority.ALWAYS);

            Label titleLeft = new Label("MY ENROLLED COURSES");
            titleLeft.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #2c3e50;");

            VBox listVBox = new VBox(10);
            listVBox.setPadding(new Insets(5));

            Runnable refreshEnrolled = () -> {
                listVBox.getChildren().clear();
                if (student.getCourses().isEmpty()) {
                    Label emptyLbl = new Label("No enrolled courses");
                    emptyLbl.setStyle("-fx-text-fill: #718096; -fx-font-style: italic;");
                    listVBox.getChildren().add(emptyLbl);
                } else {
                    for (Course c : student.getCourses()) {
                        VBox card = new VBox(5);
                        card.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cbd5e0; -fx-border-radius: 6; -fx-background-radius: 6; -fx-padding: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.03), 4, 0.2, 0, 1);");
                        
                        Label cTitle = new Label(c.getCourseId() + " - " + c.getCourseName());
                        cTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

                        String facName = c.getFaculty() != null ? c.getFaculty().getName() : "TBA";
                        Label fLbl = new Label("Faculty: " + facName);
                        fLbl.setStyle("-fx-text-fill: #4a5568; -fx-font-size: 12px;");

                        String room = c.getClassroom() != null ? c.getClassroom().getRoomName() : "TBA";
                        Label rLbl = new Label("Room: " + room);
                        rLbl.setStyle("-fx-text-fill: #4a5568; -fx-font-size: 12px;");

                        card.getChildren().addAll(cTitle, fLbl, rLbl);
                        listVBox.getChildren().add(card);
                    }
                }
            };
            refreshEnrolled.run();

            ScrollPane leftScroll = new ScrollPane(listVBox);
            leftScroll.setFitToWidth(true);
            leftScroll.setStyle("-fx-background-color: transparent; -fx-background-insets: 0;");
            VBox.setVgrow(leftScroll, Priority.ALWAYS);

            left.getChildren().addAll(titleLeft, leftScroll);

            // Right side: CourseEnrollmentView (pass refresh callback)
            CourseEnrollmentView enrollmentView = new CourseEnrollmentView(student, refreshEnrolled);
            VBox right = enrollmentView.getView();
            right.setPrefWidth(350);

            layout.getChildren().addAll(left, right);
            content.getChildren().setAll(layout);
        });

        //  MARKS
        marks.setOnAction(e -> {

            VBox box = new VBox(15);
            box.setPadding(new Insets(20));

            Label titleLabel = new Label("ACADEMIC PERFORMANCE & MARKS");
            titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
            box.getChildren().add(titleLabel);

            TableView<StudentMarkRow> table = new TableView<>();
            
            TableColumn<StudentMarkRow, String> idCol = new TableColumn<>("Course ID");
            idCol.setCellValueFactory(new PropertyValueFactory<>("courseId"));
            idCol.setPrefWidth(100);

            TableColumn<StudentMarkRow, String> nameCol = new TableColumn<>("Course Name");
            nameCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));
            nameCol.setPrefWidth(220);

            TableColumn<StudentMarkRow, String> roomCol = new TableColumn<>("Room");
            roomCol.setCellValueFactory(new PropertyValueFactory<>("roomNo"));
            roomCol.setPrefWidth(100);

            TableColumn<StudentMarkRow, String> midCol = new TableColumn<>("Midterm (50)");
            midCol.setCellValueFactory(new PropertyValueFactory<>("mid"));
            midCol.setPrefWidth(100);

            TableColumn<StudentMarkRow, String> finCol = new TableColumn<>("Final (50)");
            finCol.setCellValueFactory(new PropertyValueFactory<>("fin"));
            finCol.setPrefWidth(100);

            TableColumn<StudentMarkRow, String> totCol = new TableColumn<>("Total (100)");
            totCol.setCellValueFactory(new PropertyValueFactory<>("total"));
            totCol.setPrefWidth(100);

            table.getColumns().addAll(idCol, nameCol, roomCol, midCol, finCol, totCol);

            // Populate table rows
            javafx.collections.ObservableList<StudentMarkRow> markRows = javafx.collections.FXCollections.observableArrayList();
            for (Course c : student.getCourses()) {
                Grade g = student.getGrade(c);
                String midVal = g != null ? String.valueOf(g.getMid()) : "N/A";
                String finVal = g != null ? String.valueOf(g.getFinals()) : "N/A";
                String totVal = g != null ? String.valueOf(g.getTotal()) : "N/A";
                String roomVal = c.getClassroom() != null ? c.getClassroom().getRoomNo() : "TBA";
                
                markRows.add(new StudentMarkRow(c.getCourseId(), c.getCourseName(), roomVal, midVal, finVal, totVal));
            }
            
            table.setItems(markRows);
            box.getChildren().add(table);
            VBox.setVgrow(table, Priority.ALWAYS);

            content.getChildren().setAll(box);
        });

        logout.setOnAction(e ->
                stage.getScene().setRoot(new LoginView(stage).getView())
        );

        menu.getChildren().addAll(profile, courses, marks, logout);

        view = new BorderPane();
        view.setLeft(menu);
        view.setCenter(content);

        // Auto click profile on load
        profile.fire();
    }

    private void style(Button b) {
        b.setMaxWidth(Double.MAX_VALUE);
        b.setStyle("-fx-background-color: #34495e; -fx-text-fill: white; -fx-padding: 10 15; -fx-background-radius: 6;");
    }

    public BorderPane getView() {
        return view;
    }

    // Helper static class to wrap row data for the marks TableView
    public static class StudentMarkRow {
        private final String courseId;
        private final String courseName;
        private final String roomNo;
        private final String mid;
        private final String fin;
        private final String total;

        public StudentMarkRow(String courseId, String courseName, String roomNo, String mid, String fin, String total) {
            this.courseId = courseId;
            this.courseName = courseName;
            this.roomNo = roomNo;
            this.mid = mid;
            this.fin = fin;
            this.total = total;
        }

        public String getCourseId() { return courseId; }
        public String getCourseName() { return courseName; }
        public String getRoomNo() { return roomNo; }
        public String getMid() { return mid; }
        public String getFin() { return fin; }
        public String getTotal() { return total; }
    }
}