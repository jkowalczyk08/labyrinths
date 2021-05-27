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

public class Fields implements Config {
    List<List<Pane>> fields = new ArrayList<>();
    List<List<ImageView>> images = new ArrayList<>();
    ModificationPanel modificationPanel;
    Fields(Labyrinth labyrinthModel, ModificationPanel modificationPanel) {
        this.modificationPanel = modificationPanel;
        int height = labyrinthModel.getHeight(), width = labyrinthModel.getWidth();
        for(int i=0; i<height; ++i) {
            List<Pane> row = new ArrayList<>();
            List<ImageView> imageRow= new ArrayList<>();
            for (int j = 0; j < width; ++j) {
                Pane field = new Pane();
                field.setBackground(free);
                row.add(field);

                ImageView imageView = new ImageView();
                imageView.setPreserveRatio(true);
                imageRow.add(imageView);
            }
            fields.add(row);
            images.add(imageRow);
        }
    }
    public List<Pane> get(int index) {
        return fields.get(index);
    }
    public List<ImageView> getImages(int i) {
        return images.get(i);
    }

    Image getImage(String name) {
        return new Image(new File("src/main/resources/drawable/"+name).toURI().toString());
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
        Pane pane = fields.get(height).get(width);
        ImageView view = getImages(height).get(width);
        switch (type) {
            case FREE:
                pane.setBackground(free);
                view.setImage(null);
                break;
            case WALL:
                pane.setBackground(wall);
                break;
            case START:
                view.setImage(getImage("adventurer.png"));
                break;
            case TARGET:
                view.setImage(getImage("treasure.jpg"));
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
    public void setOpacity(Field field, double v) {
        Pane pane = fields.get(field.getH()).get(field.getW());
        pane.setOpacity(v);
    }
}
