package labyrinths.controller.labyrinthView.Fields;

import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import labyrinths.controller.labyrinthView.Getter;
import labyrinths.model.Field;
import labyrinths.model.Type;

import java.util.ArrayList;
import java.util.List;

public class Tiles {
    List<List<Pane>> tiles = new ArrayList<>();

    public Tiles(int height, int width) {
        for(int i=0; i<height; ++i) {
            List<Pane> row = new ArrayList<>();
            for (int j = 0; j < width; ++j) {
                Pane pane = new Pane();
                pane.setBackground(Getter.getTileBackground(Type.FREE));
                row.add(pane);
            }
            tiles.add(row);
        }
    }
    public List<Pane> get(int index) {
        return tiles.get(index);
    }

    public void changeType(Field field) {
        Pane pane = tiles.get(field.getH()).get(field.getW());
        Background background = Getter.getTileBackground(field.getType());
        if(background!=null)
            pane.setBackground(background);
    }
    public void setOpacity(Field field, double v) {
        Pane pane = tiles.get(field.getH()).get(field.getW());
        pane.setOpacity(v);
    }
}
