package labyrinths.controller.UI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsUIController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeSliders();
        initializeButtons();
    }

    @FXML
    Button saveBtn;
    @FXML
    Button cancelBtn;
    @FXML
    Slider heightSlider;
    @FXML
    Slider widthSlider;



    void initializeValues() {
        heightSlider.setValue(UIController.properties.getHeight());
        widthSlider.setValue(UIController.properties.getWidth());
    }

    void initializeSliders() {
        initializeValues();
    }

    void initializeButtons() {
        saveBtn.setOnAction(actionEvent -> {
            try {
                UIController.properties.setHeight((int) heightSlider.getValue());
                UIController.properties.setWidth((int) widthSlider.getValue());
                UITest.setRoot("UI");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        cancelBtn.setOnAction(actionEvent -> {
            try {
                UITest.setRoot("UI");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
