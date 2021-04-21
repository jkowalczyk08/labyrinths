package labyrinths.controller.labyrinthView;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import labyrinths.model.Field;
import labyrinths.model.Labyrinth;
import labyrinths.model.Result;
import labyrinths.model.Type;

public class ChangesApplier {
    static void changeFieldType(Button button, Type type) {
        switch (type) {
            case WALL:
                button.setBackground(new Background(
                        new BackgroundFill(Color.DIMGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
                break;
            case START:
                button.setText("S");
                break;
            case TARGET:
                button.setText("T");
                break;
            case PATH:
                button.setBackground(new Background(
                        new BackgroundFill(Color.CORAL, CornerRadii.EMPTY, Insets.EMPTY)));
                break;
            case HIGHLIGHTED:
                button.setBackground(new Background(
                        new BackgroundFill(Color.BURLYWOOD, CornerRadii.EMPTY, Insets.EMPTY)));
                break;
        }
    }
}
