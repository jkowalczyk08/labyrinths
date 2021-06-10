package labyrinths.controller.labyrinthView;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameTest extends Application {
    public static Scene scene;
    public static Stage mainStage;

    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        scene = new Scene(loadFXML());
        mainStage.setScene(scene);
        mainStage.setMaximized(true);
        mainStage.show();
    }
    private Parent loadFXML() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("labyrinth_game.fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
