package labyrinths.controller.menuView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import labyrinths.App;
import labyrinths.controller.labyrinthView.LabyrinthGetter;
import labyrinths.model.Labyrinth;
import labyrinths.model.LabyrinthPreset;
import labyrinths.model.Result;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configureFileChooser();
        initializeControls();
    }
    File labyrinthFile;
    FileChooser fileChooser;

    @FXML
    Button preset10x10Btn, preset20x40Btn, preset15x30Btn, confirmBtn, chooseFileBtn;
    @FXML
    Slider heightSlider, widthSlider;
    @FXML
    ImageView preset10x10Img, preset15x30Img, preset20x40Img;
    @FXML
    Label fileLabel;

    Image preset10x10 = new Image(new File("src/main/resources/drawable/preset_10x10.png").toURI().toString());
    Image preset15x30 = new Image(new File("src/main/resources/drawable/preset_15x30.png").toURI().toString());
    Image preset20x40 = new Image(new File("src/main/resources/drawable/preset_20x40.png").toURI().toString());

    void configureFileChooser() {
        fileChooser = new FileChooser();
        fileChooser.setTitle("Select labyrinth file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("LAB file", "*.lab"));
    }

    void initializeControls() {

        heightSlider.setValue(14);
        widthSlider.setValue(30);

        chooseFileBtn.setOnAction(actionEvent -> {
            labyrinthFile = fileChooser.showOpenDialog(App.mainStage);
            if(labyrinthFile != null) {
                try {
                    String labRepresentation = Files.readString(labyrinthFile.toPath());
                    String[] labParameters = labRepresentation.split(";");
                    int height = Integer.parseInt(labParameters[0]);
                    int width = Integer.parseInt(labParameters[1]);
                    Labyrinth labyrinth = new Labyrinth(height,width);
                    Result initialResult = labyrinth.getInitialResult(labParameters[2]);
                    App.mainStage.setScene(LabyrinthGetter.getLabyrinthScene(labyrinth, initialResult));
                    App.mainStage.setMaximized(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        preset10x10Btn.setOnAction(actionEvent -> {
            try {
                Labyrinth labyrinth = new Labyrinth(10,10);
                App.mainStage.setScene(LabyrinthGetter.getLabyrinthScene(labyrinth, labyrinth.getPreset(LabyrinthPreset.PRESET_10x10)));
                App.mainStage.setMaximized(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        preset10x10Img.setImage(preset10x10);

        preset15x30Btn.setOnAction(actionEvent -> {
            try {
                Labyrinth labyrinth = new Labyrinth(15,30);
                App.mainStage.setScene(LabyrinthGetter.getLabyrinthScene(labyrinth, labyrinth.getPreset(LabyrinthPreset.PRESET_15x30)));
                App.mainStage.setMaximized(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        preset15x30Img.setImage(preset15x30);

        preset20x40Btn.setOnAction(actionEvent -> {
            try {
                Labyrinth labyrinth = new Labyrinth(20,40);
                App.mainStage.setScene(LabyrinthGetter.getLabyrinthScene(labyrinth, labyrinth.getPreset(LabyrinthPreset.PRESET_20x40)));
                App.mainStage.setMaximized(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        preset20x40Img.setImage(preset20x40);

        confirmBtn.setOnAction((actionEvent -> {
            try {
                Labyrinth labyrinth = new Labyrinth((int) heightSlider.getValue(),(int) widthSlider.getValue());
                App.mainStage.setScene(LabyrinthGetter.
                        getLabyrinthScene(labyrinth, labyrinth.getPreset(LabyrinthPreset.DEFAULT)));
                App.mainStage.setMaximized(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }
}
