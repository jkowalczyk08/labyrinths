package labyrinths.controller.labyrinthView;

import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class ControlPanel {
    public enum ButtonType {
        START_STOP, FAST_FORWARD, PAUSE
    }
    Button startStopBtn, fastForwardBtn, pauseBtn;
    ControlPanelLogic controlPanelLogic;
    ImageView startStopImg;
    ControlPanel(ControlPanelLogic controlLogic, Button startStopBtn, Button fastForwardBtn, Button pauseBtn,
                 ImageView startStopImg) {
        this.controlPanelLogic = controlLogic;
        this.startStopBtn = startStopBtn;
        this.fastForwardBtn = fastForwardBtn;
        this.pauseBtn = pauseBtn;
        this.startStopImg = startStopImg;
    }

    public void initialize() {
        controlPanelLogic.initialize(this);

        startStopBtn.setOnAction(actionEvent -> controlPanelLogic.start());
        fastForwardBtn.setOnAction(actionEvent -> controlPanelLogic.fastForward());
        pauseBtn.setOnAction(actionEvent -> controlPanelLogic.pause());
    }
    public void setDisable(ButtonType type, boolean value) {
        switch (type) {
            case START_STOP:
                startStopBtn.setDisable(value);
                break;
            case FAST_FORWARD:
                fastForwardBtn.setDisable(value);
                break;
            case PAUSE:
                pauseBtn.setDisable(value);
                break;
        }
    }
    Image stop = new Image(new File("src/main/resources/drawable/stop_grey.png").toURI().toString());
    Image start = new Image(new File("src/main/resources/drawable/start.png").toURI().toString());
    public void setToStart() {
        startStopBtn.setOnAction(actionEvent -> controlPanelLogic.start());
        startStopImg.setImage(start);
    }
    public void setToStop() {
        startStopBtn.setOnAction(actionEvent -> controlPanelLogic.stop());
        startStopImg.setImage(stop);
    }
    public void setToGoOn() {
        startStopBtn.setOnAction(actionEvent -> controlPanelLogic.goOn());
        startStopImg.setImage(start);
    }
}
