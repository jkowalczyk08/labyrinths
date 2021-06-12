package labyrinths.controller.gameView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import labyrinths.App;
import labyrinths.controller.labyrinthView.ChangesApplier;
import labyrinths.controller.labyrinthView.Fields.Fields;
import labyrinths.controller.labyrinthView.Fields.ImageViewPane;
import labyrinths.controller.labyrinthView.Getter;
import labyrinths.model.Field;
import labyrinths.model.Labyrinth;
import labyrinths.model.Result;
import labyrinths.model.Type;

import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.ResourceBundle;

public class LabyrinthGame implements Initializable {

    Labyrinth labyrinthModel;
    ChangesApplier changesApplier;
    Cover cover;
    static LabyrinthGame instance;

    static LabyrinthGame getInstance() {
        return instance;
    }

    void apply(Result result) {
        if(result.getWin()) {
            end();
            return;
        }
        changesApplier.quickApply(result);
        cover.apply(result);
    }
    @FXML
    public AnchorPane mainPane;
    @FXML
    public Button backBtn;
    @FXML
    public Pane labyrinthPane, coverPane, stackPane;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BackgroundImage myBI = new BackgroundImage(Getter.getImage("white_background.png"),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        mainPane.setBackground(new Background(myBI));
        labyrinthModel = GameGetter.getLabyrinthModel();
        Fields fields = new Fields(null, labyrinthModel.getHeight(), labyrinthModel.getWidth());
        fields.addFields(labyrinthPane);
        cover = new Cover(labyrinthModel.getHeight(), labyrinthModel.getWidth());
        cover.addCover(coverPane);
        changesApplier = new ChangesApplier(fields);
        apply(GameGetter.getInitResult());
        instance = this;
        initializeButtons();
    }

    public void end() {
        StackPane temp = new StackPane();
        Label label = new Label("Congratulations!");
        label.setTextFill(Color.DARKVIOLET);
        label.setFont((new Font( "Verdana", 60)));
        label.setBackground(Getter.getBackground(Color.SNOW));
        stackPane.getChildren().add(label);
        coverPane.setVisible(false);
        GameGetter.removeHandler();
    }
    void initializeButtons() {
        backBtn.setOnAction(actionEvent -> {
            App.mainStage.setMaximized(false);
            App.mainStage.setScene(App.scene);
        });
    }
}
