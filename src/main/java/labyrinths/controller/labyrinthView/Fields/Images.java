package labyrinths.controller.labyrinthView.Fields;

import javafx.scene.image.ImageView;
import labyrinths.controller.labyrinthView.Getter;
import labyrinths.model.Field;
import labyrinths.model.Type;

import java.util.ArrayList;
import java.util.List;

public class Images {
    List<List<ImageView>> images = new ArrayList<>();
    public Images(int height, int width) {
        for(int i=0; i<height; ++i) {
            List<ImageView> imageRow= new ArrayList<>();
            for (int j = 0; j < width; ++j) {
                ImageView imageView = new ImageView();

                imageView.setPreserveRatio(true);
                imageRow.add(imageView);
            }
            images.add(imageRow);
        }
    }
    public List<ImageView> getImages(int i) {
        return images.get(i);
    }

    public void changeType(Field field) {
        ImageView view = getImages(field.getH()).get(field.getW());
        switch (field.getType()) {
            case FREE:
                view.setImage(null);
                break;
            case START:
                view.setImage(Getter.getImage("adventurer.png"));
                break;
            case TARGET:
                view.setImage(Getter.getImage("treasure.jpg"));
                break;
        }
    }
}
