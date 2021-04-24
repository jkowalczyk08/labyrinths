package labyrinths.controller.menu;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import labyrinths.controller.labyrinthView.LabyrinthGetter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuTestController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeControls();
    }

    @FXML
    Button preset10x10Btn;
    @FXML
    Button preset20x40Btn;
    @FXML
    Button preset15x30Btn;
    @FXML
    Slider heightSlider;
    @FXML
    Slider widthSlider;
    @FXML
    Button confirmBtn;
    void initializeControls() {

        preset10x10Btn.setOnAction(actionEvent -> {
            heightSlider.setValue(10);
            widthSlider.setValue(10);
        });
        preset15x30Btn.setOnAction(actionEvent -> {
            heightSlider.setValue(15);
            widthSlider.setValue(30);
        });
        preset20x40Btn.setOnAction(actionEvent -> {
            heightSlider.setValue(20);
            widthSlider.setValue(40);
        });

        confirmBtn.setOnAction((actionEvent -> {
            try {
                MainMenuTest.mainStage.setScene(LabyrinthGetter.
                        getLabyrinthScene((int) heightSlider.getValue(),(int) widthSlider.getValue()));
                MainMenuTest.mainStage.setMaximized(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }
}
