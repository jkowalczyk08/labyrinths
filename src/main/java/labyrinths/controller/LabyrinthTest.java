package labyrinths.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LabyrinthTest extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = LabyrinthGetter.getLabyrinthScene(15, 30);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
    public static void main(String[] args) {
        Application.launch();
    }
}
