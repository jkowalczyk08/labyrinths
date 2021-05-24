package labyrinths.controller.labyrinthView;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import labyrinths.model.Labyrinth;
import labyrinths.model.LabyrinthPreset;
import labyrinths.model.Result;

import java.io.IOException;

public class LabyrinthGetter {
    static Labyrinth labyrinthModel;
    static Result initResult;

    public static Labyrinth getLabyrinthModel() {
        return labyrinthModel;
    }
    public static Result getInitResult() {
        return initResult;
    }

    public static Scene getLabyrinthScene(Labyrinth labyrinthModel, Result initResult) throws IOException {
        LabyrinthGetter.labyrinthModel = labyrinthModel;
        LabyrinthGetter.initResult = initResult;
        FXMLLoader fxmlLoader = new FXMLLoader(LabyrinthController.class.getResource("labyrinth.fxml"));
        return new Scene(fxmlLoader.load());
    }
}
