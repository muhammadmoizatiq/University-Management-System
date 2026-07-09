package university.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import university.model.*;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Objects;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {

//        // ================= LOAD ALL DATA FIRST =================
        UniversityData.users = FileManager.loadUsers();
        UniversityData.students = FileManager.loadStudents();
        UniversityData.faculties = FileManager.loadFaculty();
        UniversityData.courses = FileManager.loadCourses();
        UniversityData.classrooms = FileManager.loadClassrooms();
        UniversityData.departments = FileManager.loadDepartments();

        // ================= SAFE INITIALIZATION =================
        if (UniversityData.users == null) UniversityData.users = new ArrayList<>();
        if (UniversityData.students == null) UniversityData.students = new ArrayList<>();
        if (UniversityData.faculties == null) UniversityData.faculties = new ArrayList<>();
        if (UniversityData.courses == null) UniversityData.courses = new ArrayList<>();
        if (UniversityData.classrooms == null) UniversityData.classrooms = new ArrayList<>();
        if (UniversityData.departments == null) UniversityData.departments = new ArrayList<>();

        // ================= AUTO ADMIN ACCOUNT =================
        boolean adminExists = false;

        for (User u : UniversityData.users) {
            if (u.getUsername().equals("admin")) {
                adminExists = true;
                break;
            }
        }

        if (!adminExists) {
            User admin = new User("admin", "admin123", "ADMIN");
            UniversityData.users.add(admin);

            // SAVE IMMEDIATELY (IMPORTANT)
            FileManager.saveUsers(UniversityData.users);
        }

        UniversityData.resolveReferences();

        // ================= LOGIN VIEW =================
        LoginView loginView = new LoginView(stage);

        Scene scene = new Scene(loginView.getView(), 1100, 700);

        // ================= CSS (SAFE LOAD) =================
        try {
            String css = Objects.requireNonNull(
                    getClass().getResource("/styles/style.css"),
                    "CSS file not found!"
            ).toExternalForm();

            scene.getStylesheets().add(css);

        } catch (Exception e) {
            System.out.println("CSS not found, continuing without styling.");
        }

        stage.setScene(scene);
        stage.setTitle("University Management System");
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.getIcons().add(
                new Image(getClass().getResourceAsStream("/images/logo.png"))
        );
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}