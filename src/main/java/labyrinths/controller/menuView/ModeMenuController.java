package labyrinths.controller.menuView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import labyrinths.App;
import labyrinths.controller.gameView.GameGetter;
import labyrinths.controller.labyrinthView.Getter;
import labyrinths.model.Labyrinth;
import labyrinths.model.LabyrinthPreset;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModeMenuController implements Initializable {

    @FXML
    Button sandboxBtn, adventureBtn;
    @FXML
    AnchorPane mainPane;

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

        BackgroundImage myBI = new BackgroundImage(Getter.getImage("white_background.png"),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        mainPane.setBackground(new Background(myBI));
    }
}
