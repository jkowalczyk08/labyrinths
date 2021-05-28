package labyrinths.controller.labyrinthView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import labyrinths.controller.labyrinthView.Fields.Fields;
import labyrinths.model.Labyrinth;

import java.net.URL;
import java.util.ResourceBundle;

import static javafx.application.Application.launch;

public class LabyrinthController implements Initializable {
    Fields fields;
    ModificationPanel modificationPanel;

    @FXML
    AnchorPane mainPane;
    @FXML
    ToggleButton changeBtn, startBtn, targetBtn, teleportBtn;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BackgroundImage myBI = new BackgroundImage(Getter.getImage("white_background.png"),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        mainPane.setBackground(new Background(myBI));
        Labyrinth labyrinthModel = LabyrinthGetter.getLabyrinthModel();
        modificationPanel = new ModificationPanel(changeBtn, startBtn, targetBtn, teleportBtn);
        constructEmptyLabyrinth(labyrinthModel);
        initializePanel(labyrinthModel);
    }

    @FXML
    Pane labyrinthPane;
    void constructEmptyLabyrinth(Labyrinth labyrinthModel) {
        fields = new Fields(modificationPanel, labyrinthModel.getHeight(), labyrinthModel.getWidth());
        fields.addFields(labyrinthPane);
        labyrinthPane.prefHeightProperty().bind(mainPane.heightProperty());
        labyrinthPane.prefWidthProperty().bind(mainPane.widthProperty());
    }


    @FXML
    Button backBtn,  startStopBtn, fastForwardBtn, pauseBtn, saveBtn;
    @FXML
    ChoiceBox<String> algorithmBox;
    @FXML
    ImageView startStopImg, fastForwardImg, pauseImg, changeImg, targetImg, startImg, teleportImg;
    @FXML
    ProgressBar progressBar;
    void initializePanel(Labyrinth labyrinthModel) {
        fastForwardImg.setImage(Getter.getImage("fastForward.png"));
        pauseImg.setImage(Getter.getImage("pause.png"));
        changeImg.setImage(Getter.getImage("changeTile.png"));
        targetImg.setImage(Getter.getImage("treasure.jpg"));
        startImg.setImage(Getter.getImage("adventurer.png"));
        teleportImg.setImage(Getter.getImage("teleportTile.png"));

        ChangesApplier applier = new ChangesApplier(fields);
        ControlPanelLogic logic = new ControlPanelLogic(labyrinthModel, applier, modificationPanel, progressBar, algorithmBox);
        applier.initialize(logic);
        modificationPanel.initialize(logic);

        ControlPanel panel = new ControlPanel(logic, backBtn, saveBtn, startStopBtn, fastForwardBtn, pauseBtn, startStopImg);
        panel.initialize();
    }
}
