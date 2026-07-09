package university.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegisterChoiceView {

    private BorderPane view;

    public RegisterChoiceView(Stage stage) {

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #eef2f7, #dfe9f3);");

        Label title = new Label("SELECT REGISTRATION TYPE");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #333;");

        Button studentBtn = new Button("Student Registration");
        Button facultyBtn = new Button("Faculty Registration");
        Button backBtn = new Button("Back to Login");

        studentBtn.setMaxWidth(Double.MAX_VALUE);
        facultyBtn.setMaxWidth(Double.MAX_VALUE);
        backBtn.setMaxWidth(Double.MAX_VALUE);

        studentBtn.setStyle("-fx-background-color: #2d6cdf; -fx-text-fill: white; -fx-padding: 10 15; -fx-background-radius: 8;");
        facultyBtn.setStyle("-fx-background-color: #2d6cdf; -fx-text-fill: white; -fx-padding: 10 15; -fx-background-radius: 8;");
        backBtn.setStyle("-fx-background-color: #444; -fx-text-fill: white; -fx-padding: 10 15; -fx-background-radius: 8;");

        studentBtn.setOnAction(e ->
                stage.getScene().setRoot(
                        new RegisterView(stage, "STUDENT").getView()
                )
        );

        facultyBtn.setOnAction(e ->
                stage.getScene().setRoot(
                        new RegisterView(stage, "FACULTY").getView()
                )
        );

        backBtn.setOnAction(e ->
                stage.getScene().setRoot(new LoginView(stage).getView())
        );

        VBox card = new VBox(15, title, studentBtn, facultyBtn, backBtn);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(30));
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