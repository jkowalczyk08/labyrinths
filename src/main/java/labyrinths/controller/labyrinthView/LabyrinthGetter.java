package labyrinths.controller.labyrinthView;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import labyrinths.model.Labyrinth;
import labyrinths.model.LabyrinthPreset;

import java.io.IOException;

public class LabyrinthGetter {
    static Labyrinth labyrinthModel;

    static Labyrinth getLabyrinthModel() {
        return labyrinthModel;
    }

    public static Scene getLabyrinthScene(int height, int width, LabyrinthPreset preset) throws IOException {
        labyrinthModel = new Labyrinth(height, width);
        FXMLLoader fxmlLoader = new FXMLLoader(LabyrinthController.class.getResource("labyrinth.fxml"));
        return new Scene(fxmlLoader.load());
    }
}
