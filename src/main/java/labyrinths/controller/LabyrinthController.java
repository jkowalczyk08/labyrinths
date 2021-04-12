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
import javafx.stage.Stage;
import labyrinths.App;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //labyrinthPane.setBackground(new Background(new BackgroundFill(Color.ROSYBROWN, CornerRadii.EMPTY, Insets.EMPTY)));
        constructEmptyLabyrinth(15, 30);
    }
    void constructEmptyLabyrinth(int wight, int height) {
        fields = new ArrayList<>();
        for(int i=0; i<height; ++i) {
            List<Button> row = new ArrayList<>();
            for (int j = 0; j < wight; ++j) {
                Button field = new Button("");
                field.prefWidthProperty().bind(labyrinthPane.widthProperty());
                field.prefHeightProperty().bind(labyrinthPane.heightProperty());
                row.add(field);
            }
            fields.add(row);
        }

        GridPane labyrinth = new GridPane();
        for(int i=0; i<height; ++i) {
            for(int j=0; j<wight; ++j){
                labyrinth.add(fields.get(i).get(j), i, j);
            }
        }
        labyrinth.prefWidthProperty().bind(labyrinthPane.widthProperty());
        labyrinth.prefHeightProperty().bind(labyrinthPane.heightProperty());
        labyrinthPane.getChildren().add(labyrinth);
    }
}
