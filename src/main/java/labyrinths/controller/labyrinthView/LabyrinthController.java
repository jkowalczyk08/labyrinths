package labyrinths.controller.labyrinthView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
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
        labyrinthPane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        fields = new Fields(labyrinthModel);
        int height = labyrinthModel.getHeight(), width = labyrinthModel.getWidth();
        GridPane gridPane = new GridPane();
        for(int i=0; i<height; ++i) {
            for(int j=0; j<width; ++j) {
                Button field = fields.get(i).get(j);
                field.prefWidthProperty().bind(labyrinthPane.widthProperty().divide(width));
                field.prefHeightProperty().bind(labyrinthPane.heightProperty().divide(height));
                gridPane.add(field, j, i);
            }
        }
        labyrinthPane.getChildren().add(gridPane);
        labyrinthPane.prefHeightProperty().bind(mainPane.heightProperty());
        labyrinthPane.prefWidthProperty().bind(mainPane.widthProperty());
    }

    @FXML
    Button dfsBtn, startStopBtn, fastForwardBtn, pauseBtn;
    @FXML
    ImageView startStopImg, fastForwardImg, pauseImg;
    @FXML
    ProgressBar progressBar;
    void initializePanel(Labyrinth labyrinthModel) {
        File file2 = new File("src/main/resources/drawable/fastForward.png");
        fastForwardImg.setImage(new Image(file2.toURI().toString()));
        File file3 = new File("src/main/resources/drawable/pause.png");
        pauseImg.setImage(new Image(file3.toURI().toString()));

        progressBar.prefWidthProperty().bind(labyrinthPane.widthProperty());
        ChangesApplier applier = new ChangesApplier(fields);
        ControlPanelLogic logic = new ControlPanelLogic(labyrinthModel, applier, progressBar);
        applier.initialize(logic);

        ControlPanel panel = new ControlPanel(logic, startStopBtn, fastForwardBtn, pauseBtn, startStopImg);
        panel.initialize();
    }
}
