package labyrinths.controller.labyrinthView;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ProgressBar;
import labyrinths.controller.Config;
import labyrinths.model.Labyrinth;
import labyrinths.model.Result;

import java.util.List;

import static javafx.collections.FXCollections.observableArrayList;
import static labyrinths.controller.labyrinthView.ControlPanel.ButtonType.*;

public class ControlPanelLogic implements Config {
    Labyrinth labyrinthModel;
    ControlPanel controlPanel;
    ProgressBar progressBar;
    ChangesApplier applier;
    ModificationPanel modificationPanel;
    ChoiceBox<String> algorithmBox;

    public ProgressBar getProgressBar() {
        return progressBar;
    }
    public Labyrinth getLabyrinthModel() {
        return labyrinthModel;
    }

    ControlPanelLogic(Labyrinth labyrinthModel, ChangesApplier applier, ModificationPanel modificationPanel,
                      ProgressBar progressBar, ChoiceBox<String> algorithmBox) {
        this.labyrinthModel = labyrinthModel;
        this.applier = applier;
        this.progressBar = progressBar;
        this.algorithmBox = algorithmBox;
        this.modificationPanel = modificationPanel;
    }

    public void initialize(ControlPanel controlPanel) {
        this.controlPanel = controlPanel;
        quickApply(LabyrinthGetter.getInitResult());
        controlPanel.setToStart();
        controlPanel.setDisable(FAST_FORWARD, true);
        controlPanel.setDisable(PAUSE, true);
        List<String> algorithms = labyrinthModel.availableAlgorithms();
        algorithmBox.setItems(observableArrayList(algorithms));
        algorithmBox.setValue(algorithms.get(0));
    }
    void quickApply(Result result) {
        applier.quickApply(result);
    }
    void launch(Result result) {
        controlPanel.setDisable(FAST_FORWARD, false);
        controlPanel.setDisable(PAUSE, false);
        controlPanel.setToStop();
        modificationPanel.setDisable(true);
        applier.applyChanges(result);
    }
    void start() {
        launch(labyrinthModel.perform(algorithmBox.getValue()));
    }
    void stop() {
        applier.stop();
        controlPanel.setToGoOn();
    }
    void goOn() {
        applier.goOn();
        controlPanel.setToStop();
    }
    void fastForward() {
        applier.fastForward();
    }
    void pause() {
        fastForward();
        quickApply(labyrinthModel.getClear());
        controlPanel.setToStart();
        controlPanel.setDisable(START_STOP, false);
        controlPanel.setDisable(PAUSE, true);
        progressBar.setProgress(0);
        modificationPanel.setDisable(false);
    }
    public void end() {
        progressBar.setProgress(1);
        controlPanel.setDisable(START_STOP, true);
        controlPanel.setDisable(FAST_FORWARD, true);
    }
}
