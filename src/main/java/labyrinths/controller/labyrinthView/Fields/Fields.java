package labyrinths.controller.labyrinthView.Fields;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import labyrinths.controller.Config;
import labyrinths.controller.labyrinthView.ModificationPanel;
import labyrinths.model.Field;

public class Fields implements Config {
    ModificationPanel modificationPanel;
    Tiles tiles;
    Tiles backgrounds;
    Images images;
    int height;
    int width;

    public Fields(ModificationPanel modificationPanel, int height, int width, boolean withEyes) {
        this.modificationPanel = modificationPanel;
        tiles = new Tiles(height, width);
        backgrounds = new Tiles(height, width);
        images = new Images(height, width, withEyes);
        this.height = height;
        this.width = width;
    }
    StackPane createStackPane(int i, int j) {
        StackPane stackPane = new StackPane();
        Pane pane = backgrounds.get(i).get(j);
        Pane field = tiles.get(i).get(j);
        ImageViewPane imageViewPane = new ImageViewPane();
        imageViewPane.setImageView(images.getImages(i).get(j));
        stackPane.getChildren().addAll(pane, field, imageViewPane);
        return stackPane;
    }

    public void addFields(Pane pane) {
        GridPane gridPane = new GridPane();
        for(int i=0; i<height; ++i) {
            for(int j=0; j<width; ++j) {
                StackPane stackPane = createStackPane(i, j);
                stackPane.prefWidthProperty().bind(pane.heightProperty().divide(height));
                stackPane.prefHeightProperty().bind(pane.heightProperty().divide(height));
                stackPane.setStyle("-fx-border-color: gray; -fx-border-width: 1;");
                final int finalI = i, finalJ = j;
                if(modificationPanel != null)
                    stackPane.setOnMouseClicked(e -> this.modificationPanel.modify(finalI, finalJ));
                gridPane.add(stackPane, j, i);
            }
        }
        pane.getChildren().add(gridPane);
    }

    public Tiles getTiles() {
        return tiles;
    }
    public Tiles getBackgrounds() {
        return backgrounds;
    }

    public void changeType(Field field) {
        tiles.changeType(field);
        images.changeType(field);
    }
}
