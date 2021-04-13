package labyrinths.controller;

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
    @FXML
    Pane labyrinthPane;

    List<List<Button>> fields;
    Labyrinth labyrinthModel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //labyrinthPane.setBackground(new Background(new BackgroundFill(Color.ROSYBROWN, CornerRadii.EMPTY, Insets.EMPTY)));
        int width = 15, height = 30;
        constructEmptyLabyrinth(width, height);
        Labyrinth labyrinthModel = new Labyrinth(width, height);
        applyChanges(labyrinthModel.getDefault());
    }
    void constructEmptyLabyrinth(int width, int height) {
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

    void applyChanges(Result result) {
        for(Field field : result.getChanges()) {
            Button button = fields.get(field.getX()).get(field.getY());
            switch (field.getType()) {
                case WALL:
                    button.setBackground(new Background(
                            new BackgroundFill(Color.CHOCOLATE, CornerRadii.EMPTY, Insets.EMPTY)));
                    break;
                case START:
                    button.setText("S");
                    break;
                case TARGET:
                    button.setText("T");
                    break;
            }
        }
    }
}
