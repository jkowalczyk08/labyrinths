package labyrinths.controller.labyrinthView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import labyrinths.model.Labyrinth;
import labyrinths.model.Result;

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
    Button clearBtn;
    @FXML
    Button dfsBtn;
    void initializeButtons() {
        clearBtn.setOnAction(actionEvent -> applyChanges(labyrinthModel.getDefault(), 0));
        dfsBtn.setOnAction(actionEvent -> applyChanges(labyrinthModel.perform("dfs"), 10)); //should be an enum
    }
}
