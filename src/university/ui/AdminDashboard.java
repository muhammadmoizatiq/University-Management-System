package university.ui;

import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import university.model.*;

public class AdminDashboard {

    private BorderPane view;

    public AdminDashboard(Stage stage, User user) {

        VBox menu = new VBox(10);
        menu.setPadding(new Insets(15));
        menu.setPrefWidth(220);
        menu.setStyle("-fx-background-color: #1f1f1f;");

        Button students = new Button("Students");
        Button faculty = new Button("Faculty");
        Button courses = new Button("Courses");
        Button logout = new Button("Logout");

        style(students);
        style(faculty);
        style(courses);
        style(logout);

        StackPane content = new StackPane();

        // STUDENTS
        students.setOnAction(e -> {
            VBox box = new VBox(10);
            box.setPadding(new Insets(20));

            Label titleLabel = new Label("STUDENTS LIST");
            titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333;");
            box.getChildren().add(titleLabel);

            VBox studentsList = new VBox(10);
            for (Student s : UniversityData.students) {
                VBox card = new VBox(5);
                card.setStyle("-fx-background-color: white; -fx-padding: 12; -fx-border-color: #e0e0e0; -fx-border-radius: 6; -fx-background-radius: 6; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 4, 0.2, 0, 1);");

                Label nameLabel = new Label(s.getName() + " (" + s.getId() + ")");
                nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #222;");

                Label detailLabel = new Label("Email: " + s.getEmail() + " | Program: " + (s.getProgram() != null && !s.getProgram().isEmpty() ? s.getProgram() : "N/A"));
                detailLabel.setStyle("-fx-text-fill: #666; -fx-font-size: 13px;");

                StringBuilder courseStr = new StringBuilder("Enrolled Courses: ");
                if (s.getCourses().isEmpty()) {
                    courseStr.append("None");
                } else {
                    for (int i = 0; i < s.getCourses().size(); i++) {
                        Course c = s.getCourses().get(i);
                        courseStr.append(c.getCourseName()).append(" (").append(c.getCourseId()).append(")");
                        if (i < s.getCourses().size() - 1) {
                            courseStr.append(", ");
                        }
                    }
                }
                Label coursesLabel = new Label(courseStr.toString());
                coursesLabel.setStyle("-fx-text-fill: #1976d2; -fx-font-style: italic; -fx-font-size: 13px;");

                card.getChildren().addAll(nameLabel, detailLabel, coursesLabel);
                studentsList.getChildren().add(card);
            }

            ScrollPane sp = new ScrollPane(studentsList);
            sp.setFitToWidth(true);
            sp.setStyle("-fx-background-color: transparent; -fx-background-insets: 0;");
            box.getChildren().add(sp);
            VBox.setVgrow(sp, Priority.ALWAYS);

            content.getChildren().setAll(box);
        });

        // ================= FACULTY =================
        faculty.setOnAction(e -> {
            VBox box = new VBox(10);
            box.setPadding(new Insets(20));

            Label titleLabel = new Label("FACULTY MEMBERS");
            titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333;");
            box.getChildren().add(titleLabel);

            VBox facultyList = new VBox(10);
            for (Faculty f : UniversityData.faculties) {
                VBox card = new VBox(5);
                card.setStyle("-fx-background-color: white; -fx-padding: 12; -fx-border-color: #e0e0e0; -fx-border-radius: 6; -fx-background-radius: 6; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 4, 0.2, 0, 1);");

                Label nameLabel = new Label(f.getName() + " (" + f.getId() + ")");
                nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #222;");

                Label detailLabel = new Label("Email: " + f.getEmail() + " | Department: " + (f.getDepartment() != null && !f.getDepartment().isEmpty() ? f.getDepartment() : "N/A"));
                detailLabel.setStyle("-fx-text-fill: #666; -fx-font-size: 13px;");

                StringBuilder courseStr = new StringBuilder("Assigned Courses: ");
                if (f.getCourses().isEmpty()) {
                    courseStr.append("None");
                } else {
                    for (int i = 0; i < f.getCourses().size(); i++) {
                        Course c = f.getCourses().get(i);
                        courseStr.append(c.getCourseName()).append(" (").append(c.getCourseId()).append(")");
                        if (i < f.getCourses().size() - 1) {
                            courseStr.append(", ");
                        }
                    }
                }
                Label coursesLabel = new Label(courseStr.toString());
                coursesLabel.setStyle("-fx-text-fill: #2e7d32; -fx-font-style: italic; -fx-font-size: 13px;");

                card.getChildren().addAll(nameLabel, detailLabel, coursesLabel);
                facultyList.getChildren().add(card);
            }

            ScrollPane sp = new ScrollPane(facultyList);
            sp.setFitToWidth(true);
            sp.setStyle("-fx-background-color: transparent; -fx-background-insets: 0;");
            box.getChildren().add(sp);
            VBox.setVgrow(sp, Priority.ALWAYS);

            content.getChildren().setAll(box);
        });

        // ================= COURSES =================
        courses.setOnAction(e -> {

            HBox mainLayout = new HBox(20);
            mainLayout.setPadding(new Insets(15));
            HBox.setHgrow(mainLayout, Priority.ALWAYS);

            // --- LEFT COLUMN: EXISTING COURSES ---
            VBox leftCol = new VBox(10);
            leftCol.setPrefWidth(420);
            HBox.setHgrow(leftCol, Priority.ALWAYS);

            Label titleLeft = new Label("EXISTING COURSES");
            titleLeft.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #333;");

            VBox coursesListVBox = new VBox(10);
            coursesListVBox.setPadding(new Insets(5));

            ComboBox<Course> existingCourseBox = new ComboBox<>();
            
            Runnable refreshCoursesList = () -> {
                coursesListVBox.getChildren().clear();
                if (UniversityData.courses.isEmpty()) {
                    Label noCourses = new Label("No courses created yet.");
                    noCourses.setStyle("-fx-text-fill: #888; -fx-font-style: italic;");
                    coursesListVBox.getChildren().add(noCourses);
                } else {
                    for (Course c : UniversityData.courses) {
                        VBox card = new VBox(5);
                        card.setStyle("-fx-background-color: white; -fx-border-color: #dcdcdc; -fx-border-radius: 6; -fx-background-radius: 6; -fx-padding: 12; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.04), 4, 0.2, 0, 1);");

                        Label courseTitle = new Label(c.getCourseId() + " - " + c.getCourseName() + " (" + c.getCreditHours() + " Credits)");
                        courseTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #222;");

                        String facultyName = c.getFaculty() != null ? c.getFaculty().getName() : "Unassigned";
                        Label facultyLabel = new Label("Faculty: " + facultyName);
                        if (c.getFaculty() == null) {
                            facultyLabel.setStyle("-fx-text-fill: #e65100; -fx-font-style: italic; -fx-font-size: 13px;");
                        } else {
                            facultyLabel.setStyle("-fx-text-fill: #1976d2; -fx-font-size: 13px;");
                        }

                        String classroomName = c.getClassroom() != null ? c.getClassroom().getRoomName() : "Unassigned";
                        Label roomLabel = new Label("Classroom: " + classroomName);
                        if (c.getClassroom() == null) {
                            roomLabel.setStyle("-fx-text-fill: #757575; -fx-font-style: italic; -fx-font-size: 13px;");
                        } else {
                            roomLabel.setStyle("-fx-text-fill: #444; -fx-font-size: 13px;");
                        }

                        card.getChildren().addAll(courseTitle, facultyLabel, roomLabel);
                        coursesListVBox.getChildren().add(card);
                    }
                }
            };

            refreshCoursesList.run();

            ScrollPane scrollPane = new ScrollPane(coursesListVBox);
            scrollPane.setFitToWidth(true);
            scrollPane.setStyle("-fx-background-color: transparent; -fx-background-insets: 0;");
            VBox.setVgrow(scrollPane, Priority.ALWAYS);

            leftCol.getChildren().addAll(titleLeft, scrollPane);

            // --- RIGHT COLUMN: FORMS ---
            VBox rightCol = new VBox(20);
            rightCol.setPrefWidth(350);

            // Section 1: Create Course
            VBox createBox = new VBox(10);
            createBox.setStyle("-fx-background-color: #f9f9f9; -fx-padding: 15; -fx-border-color: #e0e0e0; -fx-border-radius: 8; -fx-background-radius: 8;");

            Label titleCreate = new Label("CREATE NEW COURSE");
            titleCreate.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #333;");

            TextField id = new TextField();
            id.setPromptText("Course ID (e.g. CS101)");
            id.setStyle("-fx-background-radius: 4; -fx-padding: 6;");

            TextField name = new TextField();
            name.setPromptText("Course Name");
            name.setStyle("-fx-background-radius: 4; -fx-padding: 6;");

            TextField credit = new TextField();
            credit.setPromptText("Credit Hours");
            credit.setStyle("-fx-background-radius: 4; -fx-padding: 6;");

            ComboBox<Faculty> facultyBox = new ComboBox<>();
            facultyBox.setPromptText("Select Faculty (Optional)");
            facultyBox.setMaxWidth(Double.MAX_VALUE);
            facultyBox.getItems().addAll(UniversityData.faculties);
            facultyBox.setStyle("-fx-background-radius: 4; -fx-padding: 4;");

            ComboBox<ClassRoom> roomBox = new ComboBox<>();
            roomBox.setPromptText("Select Classroom (Optional)");
            roomBox.setMaxWidth(Double.MAX_VALUE);
            roomBox.getItems().addAll(UniversityData.classrooms);
            roomBox.setStyle("-fx-background-radius: 4; -fx-padding: 4;");

            Button addBtn = new Button("Create Course");
            addBtn.setMaxWidth(Double.MAX_VALUE);
            addBtn.setStyle("-fx-background-color: #2e7d32; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8;");

            createBox.getChildren().addAll(titleCreate, id, name, credit, facultyBox, roomBox, addBtn);

            // Section 2: Assign Course Details
            VBox assignBox = new VBox(10);
            assignBox.setStyle("-fx-background-color: #f9f9f9; -fx-padding: 15; -fx-border-color: #e0e0e0; -fx-border-radius: 8; -fx-background-radius: 8;");

            Label titleAssign = new Label("ASSIGN / UPDATE COURSE");
            titleAssign.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #333;");

            existingCourseBox.setPromptText("Select Course");
            existingCourseBox.setMaxWidth(Double.MAX_VALUE);
            existingCourseBox.getItems().addAll(UniversityData.courses);
            existingCourseBox.setStyle("-fx-background-radius: 4; -fx-padding: 4;");

            ComboBox<Faculty> facultyAssignBox = new ComboBox<>();
            facultyAssignBox.setPromptText("Select Faculty");
            facultyAssignBox.setMaxWidth(Double.MAX_VALUE);
            facultyAssignBox.getItems().addAll(UniversityData.faculties);
            facultyAssignBox.setStyle("-fx-background-radius: 4; -fx-padding: 4;");

            ComboBox<ClassRoom> roomAssignBox = new ComboBox<>();
            roomAssignBox.setPromptText("Select Classroom");
            roomAssignBox.setMaxWidth(Double.MAX_VALUE);
            roomAssignBox.getItems().addAll(UniversityData.classrooms);
            roomAssignBox.setStyle("-fx-background-radius: 4; -fx-padding: 4;");

            Button assignBtn = new Button("Update Course Details");
            assignBtn.setMaxWidth(Double.MAX_VALUE);
            assignBtn.setStyle("-fx-background-color: #2d6cdf; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8;");

            assignBox.getChildren().addAll(titleAssign, existingCourseBox, facultyAssignBox, roomAssignBox, assignBtn);

            rightCol.getChildren().addAll(createBox, assignBox);

            // Fill assignments when course is selected in update form
            existingCourseBox.setOnAction(ev -> {
                Course c = existingCourseBox.getValue();
                if (c != null) {
                    facultyAssignBox.setValue(c.getFaculty());
                    roomAssignBox.setValue(c.getClassroom());
                }
            });

            // Action: Create Course
            addBtn.setOnAction(ev -> {
                String courseId = id.getText().trim();
                String courseName = name.getText().trim();
                String creditText = credit.getText().trim();

                if (courseId.isEmpty() || courseName.isEmpty() || creditText.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Course ID, Name, and Credit Hours are required.");
                    alert.showAndWait();
                    return;
                }

                int creditHrs;
                try {
                    creditHrs = Integer.parseInt(creditText);
                } catch (NumberFormatException nfe) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Credit Hours must be an integer.");
                    alert.showAndWait();
                    return;
                }

                Course c = new Course(courseId, courseName, creditHrs);
                Faculty f = facultyBox.getValue();
                ClassRoom r = roomBox.getValue();

                if (f != null) {
                    c.setFaculty(f);
                    f.assignCourse(c);
                }
                if (r != null) {
                    c.assignClassroom(r);
                }

                UniversityData.addCourse(c);
                if (f != null) {
                    FileManager.saveFaculty(UniversityData.faculties);
                }

                id.clear();
                name.clear();
                credit.clear();
                facultyBox.setValue(null);
                roomBox.setValue(null);

                refreshCoursesList.run();
                existingCourseBox.getItems().setAll(UniversityData.courses);
            });

            // Action: Update Course Details
            assignBtn.setOnAction(ev -> {
                Course c = existingCourseBox.getValue();
                Faculty newFaculty = facultyAssignBox.getValue();
                ClassRoom newRoom = roomAssignBox.getValue();

                if (c == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a course to update.");
                    alert.showAndWait();
                    return;
                }

                // Handle Faculty Reassignment
                Faculty oldFaculty = c.getFaculty();
                if (oldFaculty != null && oldFaculty != newFaculty) {
                    oldFaculty.getCourses().remove(c);
                }

                if (newFaculty != null) {
                    c.setFaculty(newFaculty);
                    newFaculty.assignCourse(c);
                } else {
                    c.setFaculty(null);
                }

                // Handle Classroom Reassignment
                c.assignClassroom(newRoom);

                // Save both files
                FileManager.saveCourses(UniversityData.courses);
                FileManager.saveFaculty(UniversityData.faculties);

                existingCourseBox.setValue(null);
                facultyAssignBox.setValue(null);
                roomAssignBox.setValue(null);

                refreshCoursesList.run();
            });

            mainLayout.getChildren().addAll(leftCol, rightCol);
            content.getChildren().setAll(mainLayout);
        });

        // ================= LOGOUT =================
        logout.setOnAction(e ->
                stage.getScene().setRoot(new LoginView(stage).getView())
        );

        menu.getChildren().addAll(students, faculty, courses, logout);

        view = new BorderPane();
        view.setLeft(menu);
        view.setCenter(content);

        // Auto click students on dashboard load
        students.fire();
    }

    private void style(Button b) {
        b.setMaxWidth(Double.MAX_VALUE);
        b.setStyle("-fx-background-color: #333; -fx-text-fill: white; -fx-padding: 10 15; -fx-background-radius: 6;");
    }

    public BorderPane getView() {
        return view;
    }
}