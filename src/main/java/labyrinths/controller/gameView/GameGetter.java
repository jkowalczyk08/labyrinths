package labyrinths.controller.gameView;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import labyrinths.controller.labyrinthView.LabyrinthController;
import labyrinths.model.Field;
import labyrinths.model.Labyrinth;
import labyrinths.model.Result;
import labyrinths.model.Type;

import java.io.IOException;
import java.util.Collections;

public class GameGetter {
    static Labyrinth labyrinthModel;
    static Result initResult;

    public static Labyrinth getLabyrinthModel() {
        return labyrinthModel;
    }
    public static Result getInitResult() {
        return initResult;
    }

    public static Scene getGameScene(Labyrinth labyrinthModel, Result initResult) throws IOException {
        GameGetter.labyrinthModel = labyrinthModel;
        GameGetter.initResult = initResult;
        FXMLLoader fxmlLoader = new FXMLLoader(GameGetter.class.getResource("labyrinth_game.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if(key.getCode() == KeyCode.W) {
                LabyrinthGame.getInstance().apply(new Result(Collections.singletonList(new Field(10, 10, Type.START))));
            }
        });
        return scene;
    }
}
