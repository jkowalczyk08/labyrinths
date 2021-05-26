package labyrinths.controller.labyrinthView;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ProgressBar;
import labyrinths.controller.Config;
import labyrinths.model.Algorithms;
import labyrinths.model.Labyrinth;
import labyrinths.model.LabyrinthPreset;
import labyrinths.model.Result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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

    boolean fastForward, stopped;
    Thread workingThread = new Thread(()->{});

    public boolean ifFastForward() {
        return fastForward;
    }
    public boolean ifStopped() {
        return stopped;
    }
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
        launch(LabyrinthGetter.getInitResult(), 0);
        pause();
        List<String> algorithms = labyrinthModel.availableAlgorithms();
        algorithmBox.setItems(observableArrayList(algorithms));
        algorithmBox.setValue(algorithms.get(0));
    }
    void quickApply(Result result) {
        applier.quickApply(result);
    }
    void launch(Result result, int waitMillis) {
        fastForward = false;
        stopped = false;
        controlPanel.setDisable(FAST_FORWARD, false);
        controlPanel.setDisable(PAUSE, false);
        controlPanel.setToStop();
        modificationPanel.setDisable(true);
        workingThread = new Thread(()->applier.applyChanges(result, waitMillis));
        workingThread.setDaemon(true);
        workingThread.start();
    }
    void start() {
        launch(labyrinthModel.perform(algorithmBox.getValue()), ALGORITHM_DELAY);
    }
    void stop() {
        synchronized (applier.getLock()) {
            stopped = true;
            applier.getLock().notify();
        }
        controlPanel.setToGoOn();
    }
    void goOn() {
        synchronized (applier.getLock()) {
            stopped = false;
            applier.getLock().notify();
        }
        controlPanel.setToStop();
    }
    void fastForward() {
        fastForward = true;
        if(stopped)
            goOn();
        try {
            workingThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    void pause() {
        fastForward();
        launch(labyrinthModel.getClear(), 0);
        fastForward();
        controlPanel.setDisable(START_STOP, false);
        controlPanel.setDisable(PAUSE, true);
        progressBar.setProgress(0);
    }
    public void end() {
        controlPanel.setToStart();
        controlPanel.setDisable(START_STOP, true);
        controlPanel.setDisable(FAST_FORWARD, true);
        modificationPanel.setDisable(false);
    }
}
