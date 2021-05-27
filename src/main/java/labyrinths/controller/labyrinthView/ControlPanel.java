package labyrinths.controller.labyrinthView;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import labyrinths.App;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ControlPanel {
    public enum ButtonType {
        START_STOP, FAST_FORWARD, PAUSE
    }
    Button backBtn, saveBtn, startStopBtn, fastForwardBtn, pauseBtn;
    ControlPanelLogic controlPanelLogic;
    ImageView startStopImg;
    ControlPanel(ControlPanelLogic controlLogic, Button backBtn, Button saveBtn, Button startStopBtn, Button fastForwardBtn, Button pauseBtn,
                 ImageView startStopImg) {
        this.controlPanelLogic = controlLogic;
        this.startStopBtn = startStopBtn;
        this.fastForwardBtn = fastForwardBtn;
        this.pauseBtn = pauseBtn;
        this.startStopImg = startStopImg;
        this.backBtn = backBtn;
        this.saveBtn = saveBtn;
    }
    public void initialize() {
        controlPanelLogic.initialize(this);

        backBtn.setOnAction(actionEvent -> back());
        saveBtn.setOnAction(actionEvent -> save());
        startStopBtn.setOnAction(actionEvent -> controlPanelLogic.start());
        fastForwardBtn.setOnAction(actionEvent -> controlPanelLogic.fastForward());
        pauseBtn.setOnAction(actionEvent -> controlPanelLogic.pause());
    }
    public void back() {
        App.mainStage.setMaximized(false);
        App.mainStage.setScene(App.scene);
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

    public void save() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save labyrinth");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("LAB file", "*.lab"));
        File lab = fileChooser.showSaveDialog(App.mainStage);
        if(lab != null) {
            String labString = controlPanelLogic.labyrinthModel.getSaveString();
            try {
                Files.writeString(lab.toPath(),labString);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
