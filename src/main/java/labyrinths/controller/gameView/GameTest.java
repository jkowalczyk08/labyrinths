package labyrinths.controller.gameView;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import labyrinths.model.Labyrinth;
import labyrinths.model.LabyrinthPreset;

public class GameTest extends Application {
    public static Scene scene;
    public static Stage mainStage;

    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        Labyrinth labyrinth = new Labyrinth(20, 33);
        //labyrinth.getPreset(LabyrinthPreset.EMPTY);
        //scene = GameGetter.getGameScene(labyrinth, labyrinth.getRandomLabyrinth());
        scene = GameGetter.getGameScene(labyrinth, labyrinth.getPreset(LabyrinthPreset.EMPTY));
        mainStage.setScene(scene);
        mainStage.setMaximized(true);
        mainStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
