package labyrinths.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import labyrinths.model.Labyrinth;

import java.io.IOException;

public class LabyrinthGetter {
    static Labyrinth labyrinthModel;

    static Labyrinth getLabyrinthModel() {
        return labyrinthModel;
    }

    public static Scene getLabyrinthScene(int height, int width) throws IOException {
        labyrinthModel = new Labyrinth(height, width);
        FXMLLoader fxmlLoader = new FXMLLoader(LabyrinthController.class.getResource("labyrinth.fxml"));
        return new Scene(fxmlLoader.load());
    }
}
