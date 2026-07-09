package university.ui;

import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import university.model.*;

public class FacultyDashboard {

    private BorderPane view;

    public FacultyDashboard(Stage stage, User user) {

        Faculty faculty = user.getFacultyProfile();

        VBox menu = new VBox(10);
        menu.setPadding(new Insets(15));
        menu.setPrefWidth(220);
        menu.setStyle("-fx-background-color: #1f2d3d;");

        Button profileBtn = new Button("Profile");
        Button coursesBtn = new Button("My Courses");
        Button marksBtn = new Button("Enter Marks");
        Button logoutBtn = new Button("Logout");

        style(profileBtn);
        style(coursesBtn);
        style(marksBtn);
        style(logoutBtn);

        StackPane content = new StackPane();

        // ================= PROFILE =================
        profileBtn.setOnAction(e -> {

            VBox box = new VBox(15);
            box.setPadding(new Insets(20));

            Label titleLabel = new Label("FACULTY PROFILE");
            titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

            VBox infoBox = new VBox(6);
            infoBox.setStyle("-fx-background-color: #f8f9fa; -fx-padding: 15; -fx-border-color: #e2e8f0; -fx-border-radius: 8; -fx-background-radius: 8;");

            Label idLbl = new Label("Faculty ID: " + faculty.getId());
            idLbl.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

            Label nameLbl = new Label("Name: " + faculty.getName());
            nameLbl.setStyle("-fx-font-size: 14px;");

            Label emailLbl = new Label("Email: " + (faculty.getEmail() != null && !faculty.getEmail().isEmpty() ? faculty.getEmail() : "N/A"));
            emailLbl.setStyle("-fx-font-size: 14px;");

            Label phoneLbl = new Label("Phone: " + (faculty.getPhone() != null && !faculty.getPhone().isEmpty() ? faculty.getPhone() : "N/A"));
            phoneLbl.setStyle("-fx-font-size: 14px;");

            Label deptLbl = new Label("Department: " + (faculty.getDepartment() != null && !faculty.getDepartment().isEmpty() ? faculty.getDepartment() : "N/A"));
            deptLbl.setStyle("-fx-font-size: 14px;");

            infoBox.getChildren().addAll(idLbl, nameLbl, emailLbl, phoneLbl, deptLbl);

            box.getChildren().addAll(titleLabel, infoBox);
            content.getChildren().setAll(box);
        });

        // ================= COURSES + STUDENTS =================
        coursesBtn.setOnAction(e -> {

            VBox box = new VBox(10);
            box.setPadding(new Insets(20));

            Label titleLabel = new Label("ASSIGNED COURSES & STUDENTS");
            titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
            box.getChildren().add(titleLabel);

            VBox cardsBox = new VBox(12);
            cardsBox.setPadding(new Insets(5));

            if (faculty.getCourses().isEmpty()) {
                Label noCourses = new Label("No courses currently assigned to you.");
                noCourses.setStyle("-fx-text-fill: #718096; -fx-font-style: italic;");
                cardsBox.getChildren().add(noCourses);
            } else {
                for (Course c : faculty.getCourses()) {
                    VBox card = new VBox(8);
                    card.setStyle("-fx-background-color: white; -fx-padding: 15; -fx-border-color: #cbd5e0; -fx-border-radius: 8; -fx-background-radius: 8; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.03), 4, 0.2, 0, 1);");

                    Label courseHeader = new Label(c.getCourseId() + " - " + c.getCourseName() + " (" + c.getCreditHours() + " Credits)");
                    courseHeader.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #2c3e50;");

                    String room = c.getClassroom() != null ? c.getClassroom().getRoomName() : "Not assigned";
                    Label roomLbl = new Label("Classroom / Room: " + room);
                    roomLbl.setStyle("-fx-text-fill: #4a5568; -fx-font-size: 13px; -fx-font-weight: bold;");

                    Label enrolledTitle = new Label("Enrolled Students (" + c.getStudents().size() + "):");
                    enrolledTitle.setStyle("-fx-font-weight: bold; -fx-text-fill: #4a5568; -fx-font-size: 13px; -fx-padding: 5 0 0 0;");

                    VBox studentsList = new VBox(4);
                    if (c.getStudents().isEmpty()) {
                        Label noStudents = new Label("No students enrolled yet.");
                        noStudents.setStyle("-fx-text-fill: #a0aec0; -fx-font-style: italic; -fx-font-size: 12px;");
                        studentsList.getChildren().add(noStudents);
                    } else {
                        for (Student s : c.getStudents()) {
                            Label sLbl = new Label("• " + s.getName() + " (" + s.getId() + ") | Email: " + s.getEmail());
                            sLbl.setStyle("-fx-text-fill: #2d3748; -fx-font-size: 13px;");
                            studentsList.getChildren().add(sLbl);
                        }
                    }

                    card.getChildren().addAll(courseHeader, roomLbl, enrolledTitle, studentsList);
                    cardsBox.getChildren().add(card);
                }
            }

            ScrollPane sp = new ScrollPane(cardsBox);
            sp.setFitToWidth(true);
            sp.setStyle("-fx-background-color: transparent; -fx-background-insets: 0;");
            box.getChildren().add(sp);
            VBox.setVgrow(sp, Priority.ALWAYS);

            content.getChildren().setAll(box);
        });

        // ================= MARKS =================
        marksBtn.setOnAction(e -> {
            content.getChildren().setAll(
                    new FacultyMarksView(faculty).getView()
            );
        });

        logoutBtn.setOnAction(e ->
                stage.getScene().setRoot(new LoginView(stage).getView())
        );

        menu.getChildren().addAll(profileBtn, coursesBtn, marksBtn, logoutBtn);

        view = new BorderPane();
        view.setLeft(menu);
        view.setCenter(content);

        // Auto-select profile on load
        profileBtn.fire();
    }

    private void style(Button b) {
        b.setMaxWidth(Double.MAX_VALUE);
        b.setStyle("-fx-background-color: #2c3e50; -fx-text-fill: white; -fx-padding: 10 15; -fx-background-radius: 6;");
    }

    public BorderPane getView() {
        return view;
    }
}