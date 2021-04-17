package labyrinths.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import labyrinths.model.Labyrinth;
import labyrinths.model.Result;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.application.Application.launch;

public class LabyrinthController implements Initializable {
    static List<List<Button>> fields;
    static Labyrinth labyrinthModel;

    public static List<List<Button>> getFields() {
        return fields;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        labyrinthModel = LabyrinthGetter.getLabyrinthModel();
        constructEmptyLabyrinth();
        applyChanges(labyrinthModel.getDefault(), 0);
        initializeButtons();
    }

    @FXML
    Pane labyrinthPane;
    void constructEmptyLabyrinth() {
        int height = labyrinthModel.getHeight(), width = labyrinthModel.getWidth();
        fields = new ArrayList<>();
        for(int i=0; i<height; ++i) {
            List<Button> row = new ArrayList<>();
            for (int j = 0; j < width; ++j) {
                Button field = new Button("");
                row.add(field);
            }
            fields.add(row);
        }
        GridPane labyrinth = new GridPane();
        for(int i=0; i<height; ++i) {
            for(int j=0; j<width; ++j) {
                Button field = fields.get(i).get(j);
                field.prefWidthProperty().bind(labyrinthPane.widthProperty());
                field.prefHeightProperty().bind(labyrinthPane.heightProperty());
                labyrinth.add(field, i, j);
            }
        }
        labyrinth.prefWidthProperty().bind(labyrinthPane.widthProperty());
        labyrinth.prefHeightProperty().bind(labyrinthPane.heightProperty());
        labyrinthPane.getChildren().add(labyrinth);
    }

    void applyChanges(Result result, long waitMillis) {
        new changesApplier(result, waitMillis).run();
    }

    @FXML
    Button clearBtn;
    @FXML
    Button dfsBtn;
    void initializeButtons() {
        clearBtn.setOnAction(actionEvent -> applyChanges(labyrinthModel.getDefault(), 0));
        dfsBtn.setOnAction(actionEvent -> applyChanges(labyrinthModel.perform("dfs"), 1)); //should be an enum
    }
}
