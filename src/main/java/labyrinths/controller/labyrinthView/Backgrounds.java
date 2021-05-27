package labyrinths.controller.labyrinthView;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import labyrinths.controller.Config;
import labyrinths.model.Field;
import labyrinths.model.Labyrinth;
import labyrinths.model.Type;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Backgrounds {
        List<List<Pane>> backgrounds;

        Backgrounds(Labyrinth labyrinthModel) {
            int height = labyrinthModel.getHeight(), width = labyrinthModel.getWidth();
            backgrounds = new ArrayList<>();
            for(int i=0; i<height; ++i) {
                List<Pane> row = new ArrayList<>();
                for (int j = 0; j < width; ++j) {
                    row.add(new Pane());
                }
                backgrounds.add(row);
            }
        }
        public List<Pane> get(int index) {
            return backgrounds.get(index);
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
        Background highlighted2 = new Background(
                new BackgroundFill(Color.MEDIUMSEAGREEN, CornerRadii.EMPTY, Insets.EMPTY));
        Background teleport = new Background(
                new BackgroundFill(Color.PINK, CornerRadii.EMPTY, Insets.EMPTY));
        void changeFieldType(int height, int width, Type type) {
            Pane pane = backgrounds.get(height).get(width);
            switch (type) {
                case FREE:
                    //pane.setGraphic(null);
                    pane.setBackground(free);
                    break;
                case WALL:
                    pane.setBackground(wall);
                    break;
                case START:
                    //pane.setGraphic(getImageView(pane, "adventurer.png"));
                    break;
                case TARGET:
                    //pane.setGraphic(getImageView(pane, "treasure.jpg"));
                    break;
                case PATH:
                    pane.setBackground(path);
                    break;
                case HIGHLIGHTED:
                    pane.setBackground(highlighted);
                    break;
                case HIGHLIGHTED2:
                    pane.setBackground(highlighted2);
                    break;
                case TELEPORT:
                    pane.setBackground(teleport);
                    break;
            }
        }
}
