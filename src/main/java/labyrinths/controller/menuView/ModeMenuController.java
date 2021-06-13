package labyrinths.controller.menuView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import labyrinths.App;
import labyrinths.controller.gameView.GameGetter;
import labyrinths.model.Labyrinth;
import labyrinths.model.LabyrinthPreset;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModeMenuController implements Initializable {

    @FXML
    Button sandboxBtn, adventureBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sandboxBtn.setOnAction(actionEvent -> App.mainStage.setScene(App.scene));

        adventureBtn.setOnAction(actionEvent -> {
            Labyrinth labyrinth = new Labyrinth(20, 30);
            labyrinth.getPreset(LabyrinthPreset.EMPTY);
            try {
                App.mainStage.setScene(GameGetter.getGameScene(labyrinth, labyrinth.getRandomLabyrinth()));
                App.mainStage.setMaximized(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
