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

    @FXML
    AnchorPane mainPane;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainPane.setBackground(new Background(
                new BackgroundFill(Color.GHOSTWHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        Labyrinth labyrinthModel = LabyrinthGetter.getLabyrinthModel();
        constructEmptyLabyrinth(labyrinthModel);
        initializePanel(labyrinthModel);
    }

    @FXML
    Pane labyrinthPane;
    void constructEmptyLabyrinth(Labyrinth labyrinthModel) {
        fields = new Fields(labyrinthModel);
        int height = labyrinthModel.getHeight(), width = labyrinthModel.getWidth();
        GridPane gridPane = new GridPane();
        for(int i=0; i<height; ++i) {
            for(int j=0; j<width; ++j) {
                Button field = fields.get(i).get(j);
                field.prefWidthProperty().bind(labyrinthPane.heightProperty().divide(height));
                field.prefHeightProperty().bind(labyrinthPane.heightProperty().divide(height));
                gridPane.add(field, j, i);
            }
        }
        labyrinthPane.getChildren().add(gridPane);
        labyrinthPane.prefHeightProperty().bind(mainPane.heightProperty());
        labyrinthPane.prefWidthProperty().bind(mainPane.widthProperty());
    }

    @FXML
    Button backBtn,  startStopBtn, fastForwardBtn, pauseBtn;
    @FXML
    ToggleButton freeBtn, wallBtn, startBtn, targetBtn;
    @FXML
    ChoiceBox<String> algorithmBox;
    @FXML
    ImageView startStopImg, fastForwardImg, pauseImg;
    @FXML
    ProgressBar progressBar;
    void initializePanel(Labyrinth labyrinthModel) {
        File file = new File("src/main/resources/drawable/fastForward.png");
        fastForwardImg.setImage(new Image(file.toURI().toString()));
        File file2 = new File("src/main/resources/drawable/pause.png");
        pauseImg.setImage(new Image(file2.toURI().toString()));

        ModificationPanel modificationPanel = new ModificationPanel(freeBtn, wallBtn, startBtn, targetBtn);
        modificationPanel.initialize();

        ChangesApplier applier = new ChangesApplier(fields);
        ControlPanelLogic logic = new ControlPanelLogic(labyrinthModel, applier, modificationPanel, progressBar, algorithmBox);
        applier.initialize(logic);

        ControlPanel panel = new ControlPanel(logic, backBtn, startStopBtn, fastForwardBtn, pauseBtn, startStopImg);
        panel.initialize();
    }
}
