package university.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import university.model.*;

public class LoginView {

    private BorderPane view;

    public LoginView(Stage stage) {

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #eef2f7, #dfe9f3);");

        Label title = new Label("UNIVERSITY LOGIN");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        Label userLabel = new Label("Username");
        TextField username = new TextField();

        Label passLabel = new Label("Password");
        PasswordField password = new PasswordField();

        Label msg = new Label();
        msg.setStyle("-fx-text-fill: red;");

        Button loginBtn = new Button("LOGIN");
        Button registerBtn = new Button("REGISTER");

        loginBtn.setMaxWidth(Double.MAX_VALUE);
        registerBtn.setMaxWidth(Double.MAX_VALUE);

        loginBtn.setStyle("-fx-background-color: #2d6cdf; -fx-text-fill: white;");
        registerBtn.setStyle("-fx-background-color: #444; -fx-text-fill: white;");

        loginBtn.setOnAction(e -> {

            String u = username.getText().trim();
            String p = password.getText().trim();

            if (u.isEmpty() || p.isEmpty()) {
                msg.setText("Please fill all fields");
                return;
            }

            for (User user : UniversityData.users) {

                if (user.getUsername().equals(u)
                        && user.getPassword().equals(p)) {

                    if (user.getRole().equals("STUDENT")) {
                        stage.getScene().setRoot(
                                new StudentDashboard(stage, user).getView()
                        );
                    } else if (user.getRole().equals("FACULTY")) {
                        stage.getScene().setRoot(
                                new FacultyDashboard(stage, user).getView()
                        );
                    } else {
                        stage.getScene().setRoot(
                                new AdminDashboard(stage, user).getView()
                        );
                    }
                    return;
                }
            }

            msg.setText("Invalid credentials");
        });

        registerBtn.setOnAction(e ->
                stage.getScene().setRoot(new RegisterChoiceView(stage).getView())
        );

        ImageView logo = new ImageView(
                new Image(getClass().getResourceAsStream("/images/logo.png"))
        );

        logo.setFitWidth(110);
        logo.setFitHeight(110);
        logo.setPreserveRatio(true);

        VBox.setMargin(logo, new Insets(-10, 0, 10, 0));

        VBox card = new VBox(10,
                logo,
                title,
                userLabel, username,
                passLabel, password,
                loginBtn,
                registerBtn,
                msg
        );

        card.setPadding(new Insets(30));
        card.setAlignment(Pos.CENTER);
        card.setMaxWidth(350);

        card.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 12;" +
                        "-fx-border-radius: 12;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 12, 0.2, 0, 4);"
        );

        StackPane center = new StackPane(card);
        center.setAlignment(Pos.CENTER);

        root.setCenter(center);

        view = root;
    }

    public BorderPane getView() {
        return view;
    }
}