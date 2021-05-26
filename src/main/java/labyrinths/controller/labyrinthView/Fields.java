package labyrinths.controller.labyrinthView;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import labyrinths.model.Labyrinth;
import labyrinths.model.Type;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Fields {
    List<List<Button>> fields;
    ModificationPanel modificationPanel;
    Fields(Labyrinth labyrinthModel, ModificationPanel modificationPanel) {
        this.modificationPanel = modificationPanel;
        int height = labyrinthModel.getHeight(), width = labyrinthModel.getWidth();
        fields = new ArrayList<>();
        for(int i=0; i<height; ++i) {
            List<Button> row = new ArrayList<>();
            for (int j = 0; j < width; ++j) {
                Button field = new Button("");
                field.setBackground(free);
                final int finalI = i, finalJ = j;
                field.setOnAction(e -> this.modificationPanel.modify(finalI, finalJ));
                row.add(field);
            }
            fields.add(row);
        }
    }
    public List<Button> get(int index) {
        return fields.get(index);
    }

    ImageView getImageView(Button button, String name) {
        Image image = new Image(new File("src/main/resources/drawable/"+name).toURI().toString());
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.fitHeightProperty().bind(button.prefHeightProperty().subtract(12));
        imageView.fitWidthProperty().bind(button.prefWidthProperty().subtract(18));
        return imageView;
    }

    Background free = new Background(
            new BackgroundFill(Color.GAINSBORO, CornerRadii.EMPTY, Insets.EMPTY));
    Background wall = new Background(
            new BackgroundFill(Color.DIMGRAY, CornerRadii.EMPTY, Insets.EMPTY));
    Background path = new Background(
            new BackgroundFill(Color.CORAL, CornerRadii.EMPTY, Insets.EMPTY));
    Background highlighted = new Background(
            new BackgroundFill(Color.BURLYWOOD, CornerRadii.EMPTY, Insets.EMPTY));
    void changeFieldType(int height, int width, Type type) {
        Button button = fields.get(height).get(width);
        switch (type) {
            case FREE:
                button.setGraphic(null);
                button.setBackground(free);
                break;
            case WALL:
                button.setBackground(wall);
                break;
            case START:
                button.setGraphic(getImageView(button, "adventurer.png"));
                break;
            case TARGET:
                button.setGraphic(getImageView(button, "treasure.jpg"));
                break;
            case PATH:
                button.setBackground(path);
                break;
            case HIGHLIGHTED:
                button.setBackground(highlighted);
                break;
        }
    }
}
