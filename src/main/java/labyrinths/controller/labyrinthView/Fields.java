package labyrinths.controller.labyrinthView;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import labyrinths.model.Labyrinth;
import labyrinths.model.Type;

import java.util.ArrayList;
import java.util.List;

public class Fields {
    List<List<Button>> fields;
    Fields(Labyrinth labyrinthModel) {
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
    }
    public List<Button> get(int index) {
        return fields.get(index);
    }

    void changeFieldType(int height, int width, Type type) {
        Button button = fields.get(height).get(width);
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
