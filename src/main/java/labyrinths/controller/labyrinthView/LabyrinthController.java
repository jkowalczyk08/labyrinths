package labyrinths.controller.labyrinthView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import labyrinths.model.Labyrinth;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.application.Application.launch;

public class LabyrinthController implements Initializable {
    Fields fields;
    Backgrounds backgrounds;
    ModificationPanel modificationPanel;

    @FXML
    AnchorPane mainPane;
    @FXML
    ToggleButton changeBtn, startBtn, targetBtn, teleportBtn;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File file = new File("src/main/resources/drawable/white_background.png");
        BackgroundImage myBI = new BackgroundImage(new Image(file.toURI().toString()),
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
        fields = new Fields(labyrinthModel, modificationPanel);
        backgrounds = new Backgrounds(labyrinthModel);
        int height = labyrinthModel.getHeight(), width = labyrinthModel.getWidth();
        GridPane gridPane = new GridPane();
        for(int i=0; i<height; ++i) {
            for(int j=0; j<width; ++j) {
                StackPane stackPane = new StackPane();
                stackPane.prefWidthProperty().bind(labyrinthPane.heightProperty().divide(height));
                stackPane.prefHeightProperty().bind(labyrinthPane.heightProperty().divide(height));
                stackPane.setStyle("-fx-border-color: gray; -fx-border-width: 1;");

                Pane pane = backgrounds.get(i).get(j);
                Pane field = fields.get(i).get(j);
                ImageViewPane imageViewPane = new ImageViewPane();
                imageViewPane.setImageView(fields.getImages(i).get(j));
                stackPane.getChildren().addAll(pane, field, imageViewPane);
                final int finalI = i, finalJ = j;
                stackPane.setOnMouseClicked(e -> this.modificationPanel.modify(finalI, finalJ));
                gridPane.add(stackPane, j, i);
            }
        }
        labyrinthPane.getChildren().add(gridPane);
        labyrinthPane.prefHeightProperty().bind(mainPane.heightProperty());
        labyrinthPane.prefWidthProperty().bind(mainPane.widthProperty());
    }


    Image getImage(String name) {
        File file = new File("src/main/resources/drawable/"+name);
        return new Image(file.toURI().toString());
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
        fastForwardImg.setImage(getImage("fastForward.png"));
        pauseImg.setImage(getImage("pause.png"));
        changeImg.setImage(getImage("changeTile.png"));
        targetImg.setImage(getImage("treasure.jpg"));
        startImg.setImage(getImage("adventurer.png"));
        teleportImg.setImage(getImage("teleportTile.png"));

        ChangesApplier applier = new ChangesApplier(fields, backgrounds);
        ControlPanelLogic logic = new ControlPanelLogic(labyrinthModel, applier, modificationPanel, progressBar, algorithmBox);
        applier.initialize(logic);
        modificationPanel.initialize(logic);

        ControlPanel panel = new ControlPanel(logic, backBtn, saveBtn, startStopBtn, fastForwardBtn, pauseBtn, startStopImg);
        panel.initialize();
    }
}
