package labyrinths;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import labyrinths.controller.menuView.MainMenuController;

import java.io.IOException;

public class App extends Application {

    public static Scene scene, modeScene;
    public static Stage mainStage;

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        scene = new Scene(loadFXML("MainMenu.fxml"));
        modeScene = new Scene(loadFXML("ModeMenu.fxml"));
        mainStage.setScene(modeScene);
        mainStage.show();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuController.class.getResource(fxml));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
