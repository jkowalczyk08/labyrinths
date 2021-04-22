package labyrinths.controller.labyrinthView;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import labyrinths.model.Labyrinth;
import labyrinths.model.Type;

import java.io.File;
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

    ImageView getImageView(Button button, String name) {
        Image image = new Image(new File("src/main/resources/drawable/"+name).toURI().toString());
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.fitHeightProperty().bind(button.prefHeightProperty().subtract(10));
        imageView.fitWidthProperty().bind(button.prefWidthProperty().subtract(17));
        return imageView;
    }

    void changeFieldType(int height, int width, Type type) {
        Button button = fields.get(height).get(width);
        switch (type) {
            case FREE:
                button.setStyle("-fx-background-color: white;");
                break;
            case WALL:
                button.setStyle("-fx-background-color: dimgrey;");
                break;
            case START:
                button.setGraphic(getImageView(button, "adventurer.png"));
                break;
            case TARGET:
                button.setGraphic(getImageView(button, "treasure.jpg"));
                break;
            case PATH:
                button.setStyle("-fx-background-color: coral;");
                break;
            case HIGHLIGHTED:
                button.setStyle("-fx-background-color: burlywood;");
                break;
        }
    }
}
