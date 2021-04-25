package labyrinths;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import labyrinths.controller.menuView.MainMenuController;

import java.io.IOException;

public class App extends Application {

    public static Scene scene;
    public static Stage mainStage;

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        scene = new Scene(loadFXML());
        mainStage.setScene(scene);
        mainStage.show();
    }

    private static Parent loadFXML() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuController.class.getResource("MainMenu.fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
