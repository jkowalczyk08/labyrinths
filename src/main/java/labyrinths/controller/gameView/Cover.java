package labyrinths.controller.gameView;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import labyrinths.controller.labyrinthView.Fields.ImageViewPane;
import labyrinths.controller.labyrinthView.Getter;
import labyrinths.model.Field;
import labyrinths.model.Result;
import labyrinths.model.Type;

public class Cover {
    int w;
    int h;
    int tileSize = 30;
    int startW = 2000-tileSize/2;
    int startH = 1100-tileSize/2;
    ImageView preset;

    public Cover(int height, int width) {
        h = height;
        w = width;
    }

    public void addCover(Pane pane) {
        preset = new ImageView(Getter.getImage("fog_of_war.png"));
        preset.setPreserveRatio(true);
        preset.setViewport(new Rectangle2D(startW, startH, w*tileSize, h*tileSize));
        ImageViewPane imageViewPane = new ImageViewPane();
        imageViewPane.setImageView(preset);
        StackPane stackPane = new StackPane();
        stackPane.minWidthProperty().bind(pane.widthProperty());
        stackPane.minHeightProperty().bind(pane.heightProperty());
        stackPane.getChildren().add(imageViewPane);
        pane.getChildren().add(stackPane);
    }

    public void apply(Result result) {
        for(Field field : result.getChanges()){
            if(field.getType() == Type.START) {
                preset.setViewport(new Rectangle2D(startW-field.getW()*tileSize, startH-field.getH()*tileSize,
                        w*tileSize, h*tileSize));
            }
        }
    }
}
