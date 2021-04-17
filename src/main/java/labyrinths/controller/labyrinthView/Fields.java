package labyrinths.controller.labyrinthView;

import javafx.scene.control.Button;
import labyrinths.model.Labyrinth;

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
}
