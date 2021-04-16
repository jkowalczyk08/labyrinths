package labyrinths.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import labyrinths.App;
import labyrinths.model.Field;
import labyrinths.model.Labyrinth;
import labyrinths.model.Result;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.application.Application.launch;

public class LabyrinthController implements Initializable {

    List<List<Button>> fields;
    Labyrinth labyrinthModel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int width = 15, height = 30;
        labyrinthModel = new Labyrinth(height, width);
        constructEmptyLabyrinth(height, width);
        applyChanges(labyrinthModel.getDefault(), 0);
        initializeButtons();
    }

    @FXML
    Pane labyrinthPane;
    void constructEmptyLabyrinth(int height, int width) {
        fields = new ArrayList<>();
        for(int i=0; i<height; ++i) {
            List<Button> row = new ArrayList<>();
            for (int j = 0; j < width; ++j) {
                Button field = new Button("");
                field.prefWidthProperty().bind(labyrinthPane.widthProperty());
                field.prefHeightProperty().bind(labyrinthPane.heightProperty());
                row.add(field);
            }
            fields.add(row);
        }
        GridPane labyrinth = new GridPane();
        for(int i=0; i<height; ++i) {
            for(int j=0; j<width; ++j){
                labyrinth.add(fields.get(i).get(j), i, j);
            }
        }
        labyrinth.prefWidthProperty().bind(labyrinthPane.widthProperty());
        labyrinth.prefHeightProperty().bind(labyrinthPane.heightProperty());
        labyrinthPane.getChildren().add(labyrinth);
    }

    void applyChanges(Result result, long waitMillis) {
        for(Field field : result.getChanges()) {
            Button button = fields.get(field.getH()).get(field.getW());
            switch (field.getType()) {
                case WALL:
                    button.setBackground(new Background(
                            new BackgroundFill(Color.BROWN, CornerRadii.EMPTY, Insets.EMPTY)));
                    break;
                case START:
                    button.setText("S");
                    break;
                case TARGET:
                    button.setText("T");
                    break;
                case PATH:
                    button.setBackground(new Background(
                            new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
                    break;
                case HIGHLIGHTED:
                    button.setBackground(new Background(
                            new BackgroundFill(Color.IVORY, CornerRadii.EMPTY, Insets.EMPTY)));
                    break;
            }
            try {
                Thread.sleep(waitMillis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    Button clearBtn;
    @FXML
    Button dfsBtn;
    void initializeButtons() {
        clearBtn.setOnAction(actionEvent -> applyChanges(labyrinthModel.getDefault(), 0));
        dfsBtn.setOnAction(actionEvent -> applyChanges(labyrinthModel.perform("dfs"), 0)); //should be an enum
    }
}
