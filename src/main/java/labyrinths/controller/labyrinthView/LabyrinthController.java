package labyrinths.controller.labyrinthView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import labyrinths.model.Labyrinth;
import labyrinths.model.Result;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.application.Application.launch;

public class LabyrinthController implements Initializable {
    static Fields fields;
    static Labyrinth labyrinthModel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        labyrinthModel = LabyrinthGetter.getLabyrinthModel();
        fields = new Fields(labyrinthModel);
        constructEmptyLabyrinth();
        applyChanges(labyrinthModel.getDefault(), 0);
        initializeButtons();
    }

    @FXML
    Pane labyrinthPane;
    void constructEmptyLabyrinth() {
        int height = labyrinthModel.getHeight(), width = labyrinthModel.getWidth();
        GridPane labyrinth = new GridPane();
        for(int i=0; i<height; ++i) {
            for(int j=0; j<width; ++j) {
                Button field = fields.get(i).get(j);
                field.prefWidthProperty().bind(labyrinthPane.widthProperty());
                field.prefHeightProperty().bind(labyrinthPane.heightProperty());
                labyrinth.add(field, j, i);
            }
        }
        labyrinth.prefWidthProperty().bind(labyrinthPane.widthProperty());
        labyrinth.prefHeightProperty().bind(labyrinthPane.heightProperty());
        labyrinthPane.getChildren().add(labyrinth);
    }

    void applyChanges(Result result, long waitMillis) {
        new Thread(new changesApplier(result, waitMillis, fields)).start();
    }

    @FXML
    Button dfsBtn, startStopBtn, fastForwardBtn, pauseBtn;
    @FXML
    ImageView startStopImg, fastForwardImg, pauseImg;
    @FXML
    ProgressBar progressBar;

    void initializeButtons() {
        File file = new File("src/main/resources/drawable/start.png");
        startStopImg.setImage(new Image(file.toURI().toString()));
        File file2 = new File("src/main/resources/drawable/fastForward.png");
        fastForwardImg.setImage(new Image(file2.toURI().toString()));
        File file3 = new File("src/main/resources/drawable/pause.png");
        pauseImg.setImage(new Image(file3.toURI().toString()));

        startStopBtn.setOnAction(actionEvent -> applyChanges(labyrinthModel.perform("dfs"), 10));//should be an enum
        fastForwardBtn.setDisable(true);
        pauseBtn.setDisable(true);//should be an enum
        progressBar.prefWidthProperty().bind(labyrinthPane.widthProperty().divide(2));
    }
}
