package university.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import university.model.*;

public class RegisterView {

    private BorderPane view;

    public RegisterView(Stage stage, String type) {

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #eef2f7, #dfe9f3);");

        Label title = new Label(type + " REGISTRATION");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #333; -fx-padding: 0 0 10 0;");

        // Helper to create a labeled field
        VBox idGroup = createFieldGroup("ID", "e.g. 21F-9087");
        TextField idField = (TextField) idGroup.getChildren().get(1);

        VBox nameGroup = createFieldGroup("Full Name", "Enter full name");
        TextField nameField = (TextField) nameGroup.getChildren().get(1);

        VBox emailGroup = createFieldGroup("Email", "e.g. name@university.edu");
        TextField emailField = (TextField) emailGroup.getChildren().get(1);

        VBox phoneGroup = createFieldGroup("Phone", "e.g. +923001234567");
        TextField phoneField = (TextField) phoneGroup.getChildren().get(1);

        String extraPrompt = type.equals("STUDENT") ? "e.g. BSCS" : "e.g. Computer Science";
        VBox extraGroup = createFieldGroup(type.equals("STUDENT") ? "Program" : "Department", extraPrompt);
        TextField extraField = (TextField) extraGroup.getChildren().get(1);

        VBox usernameGroup = createFieldGroup("Username", "Choose a username");
        TextField usernameField = (TextField) usernameGroup.getChildren().get(1);

        // Password field is special
        VBox passwordGroup = new VBox(4);
        Label passLabel = new Label("Password");
        passLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #555; -fx-font-size: 12px;");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter password (min 6 chars)");
        passwordField.setStyle("-fx-background-radius: 6; -fx-padding: 8;");
        passwordGroup.getChildren().addAll(passLabel, passwordField);

        Label msg = new Label();
        msg.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");

        Button registerBtn = new Button("REGISTER");
        Button backBtn = new Button("BACK TO LOGIN");

        registerBtn.setMaxWidth(Double.MAX_VALUE);
        backBtn.setMaxWidth(Double.MAX_VALUE);

        registerBtn.setStyle("-fx-background-color: #2d6cdf; -fx-text-fill: white; -fx-padding: 10; -fx-font-weight: bold; -fx-background-radius: 8;");
        backBtn.setStyle("-fx-background-color: #444; -fx-text-fill: white; -fx-padding: 10; -fx-font-weight: bold; -fx-background-radius: 8;");

        registerBtn.setOnAction(e -> {
            String idVal = idField.getText().trim();
            String nameVal = nameField.getText().trim();
            String emailVal = emailField.getText().trim();
            String phoneVal = phoneField.getText().trim();
            String extraVal = extraField.getText().trim();
            String userVal = usernameField.getText().trim();
            String passVal = passwordField.getText().trim();

            if (idVal.isEmpty() || nameVal.isEmpty() || userVal.isEmpty() || passVal.isEmpty()) {
                msg.setText("Fill required fields (ID, Name, Username, Password)");
                msg.setStyle("-fx-text-fill: red;");
                return;
            }

            if (passVal.length() < 6) {
                msg.setText("Password must be at least 6 characters");
                msg.setStyle("-fx-text-fill: red;");
                return;
            }

            // Check duplicate username
            for (User u : UniversityData.users) {
                if (u.getUsername().equals(userVal)) {
                    msg.setText("Username already exists");
                    msg.setStyle("-fx-text-fill: red;");
                    return;
                }
            }

            if (type.equals("STUDENT")) {
                Student s = new Student(idVal, nameVal, emailVal, phoneVal);
                s.setProgram(extraVal);

                User u = new User(userVal, passVal, "STUDENT");
                u.setStudentProfile(s);

                UniversityData.addStudent(s);
                UniversityData.addUser(u);

                msg.setText("Student Registered Successfully");
                msg.setStyle("-fx-text-fill: green;");
            } else {
                Faculty f = new Faculty(idVal, nameVal, emailVal, phoneVal, extraVal);

                User u = new User(userVal, passVal, "FACULTY");
                u.setFacultyProfile(f);

                UniversityData.addFaculty(f);
                UniversityData.addUser(u);

                msg.setText("Faculty Registered Successfully");
                msg.setStyle("-fx-text-fill: green;");
            }

            // Clear fields on success
            idField.clear();
            nameField.clear();
            emailField.clear();
            phoneField.clear();
            extraField.clear();
            usernameField.clear();
            passwordField.clear();
        });

        backBtn.setOnAction(e ->
                stage.getScene().setRoot(new LoginView(stage).getView())
        );

        VBox card = new VBox(12,
                title,
                idGroup,
                nameGroup,
                emailGroup,
                phoneGroup,
                extraGroup,
                usernameGroup,
                passwordGroup,
                registerBtn,
                backBtn,
                msg
        );

        card.setPadding(new Insets(30));
        card.setMaxWidth(400);
        card.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 12;" +
                "-fx-border-radius: 12;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 12, 0.2, 0, 4);"
        );

        StackPane centerStack = new StackPane(card);
        centerStack.setAlignment(Pos.CENTER);
        centerStack.setPadding(new Insets(20));

        ScrollPane scrollPane = new ScrollPane(centerStack);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background-insets: 0;");
        
        root.setCenter(scrollPane);
        view = root;
    }

    private VBox createFieldGroup(String labelText, String promptText) {
        VBox group = new VBox(4);
        Label label = new Label(labelText);
        label.setStyle("-fx-font-weight: bold; -fx-text-fill: #555; -fx-font-size: 12px;");
        TextField field = new TextField();
        field.setPromptText(promptText);
        field.setStyle("-fx-background-radius: 6; -fx-padding: 8;");
        group.getChildren().addAll(label, field);
        return group;
    }

    public BorderPane getView() {
        return view;
    }
}