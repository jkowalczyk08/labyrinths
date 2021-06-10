package labyrinths.controller.labyrinthView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import labyrinths.controller.labyrinthView.Fields.ImageViewPane;

import java.net.URL;
import java.util.ResourceBundle;

public class LabyrinthGame implements Initializable {
    @FXML
    public Button backBtn;
    @FXML
    public VBox labyrinthPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StackPane stackPane = new StackPane();
        int w = 20;
        int h = 10;
        int tileSize = 40;
        int startW = 2000-tileSize/2;
        int startH = 1100-tileSize/2;
        ImageView preset = new ImageView(Getter.getImage("fog_of_war.png"));
        preset.setViewport(new Rectangle2D(startW, startH, w*tileSize, h*tileSize));
        ImageViewPane imageViewPane = new ImageViewPane();
        imageViewPane.setImageView(preset);
        stackPane.getChildren().add(imageViewPane);
        stackPane.prefWidthProperty().bind(labyrinthPane.widthProperty());
        stackPane.prefHeightProperty().bind(labyrinthPane.heightProperty());
        labyrinthPane.setBackground(Getter.getBackground(Color.AQUA));
        labyrinthPane.getChildren().add(stackPane);
    }
}
