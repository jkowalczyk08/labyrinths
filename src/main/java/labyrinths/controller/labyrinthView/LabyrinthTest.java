package labyrinths.controller.labyrinthView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import labyrinths.controller.labyrinthView.LabyrinthGetter;
import labyrinths.model.Labyrinth;
import labyrinths.model.LabyrinthPreset;
import labyrinths.model.Result;

import java.io.IOException;

public class LabyrinthTest extends Application { //class for test purpose only
    @Override
    public void start(Stage stage) throws IOException {
        Labyrinth labyrinth = new Labyrinth(15, 30);
        Result result = labyrinth.getPreset(LabyrinthPreset.SNAKE);
        Scene scene = LabyrinthGetter.getLabyrinthScene(labyrinth, result);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        Application.launch();
    }
}
