package labyrinths.controller.labyrinthView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import labyrinths.controller.labyrinthView.LabyrinthGetter;
import labyrinths.model.Labyrinth;
import labyrinths.model.LabyrinthPreset;

import java.io.IOException;

public class LabyrinthTest extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = LabyrinthGetter.getLabyrinthScene(15, 30, LabyrinthPreset.SNAKE);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        Application.launch();
    }
}
