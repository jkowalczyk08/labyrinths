package labyrinths.controller.labyrinthView;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import labyrinths.model.Type;

import java.io.File;

public class Getter {
    public static Image getImage(String name) {
        File file = new File("src/main/resources/drawable/"+name);
        return new Image(file.toURI().toString());
    }
    public static Background getBackground(Color color) {
        return new Background( new BackgroundFill(color,
                    CornerRadii.EMPTY, Insets.EMPTY));
    }

    static Background free = getBackground(Color.GAINSBORO);
    static Background wall = getBackground(Color.DIMGRAY);
    static Background path = getBackground(Color.CORAL);
    static Background highlighted = getBackground(Color.BURLYWOOD);
    static Background highlighted2 = getBackground(Color.MEDIUMSEAGREEN);
    static Background teleport = getBackground(Color.MEDIUMORCHID);

    public static Background getTileBackground(Type type) {
        switch (type) {
            case FREE:
                return free;
            case WALL:
                return wall;
            case PATH:
                return path;
            case HIGHLIGHTED:
                return highlighted;
            case HIGHLIGHTED2:
                return highlighted2;
            case TELEPORT:
                return teleport;
        }
        return null;
    }
}
