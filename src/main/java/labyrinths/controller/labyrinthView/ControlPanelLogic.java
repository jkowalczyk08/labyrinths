package labyrinths.controller.labyrinthView;

import javafx.scene.control.ProgressBar;
import labyrinths.model.Labyrinth;
import labyrinths.model.Result;

import static labyrinths.controller.labyrinthView.ControlPanel.ButtonType.*;

public class ControlPanelLogic {
    Labyrinth labyrinthModel;
    ControlPanel controlPanel;
    ProgressBar progressBar;
    ChangesApplier applier;

    String algorithm = "dfs"; // change to enum
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

    ControlPanelLogic(Labyrinth labyrinthModel, ChangesApplier applier, ProgressBar progressBar) {
        this.labyrinthModel = labyrinthModel;
        this.applier = applier;
        this.progressBar = progressBar;
    }

    public void initialize(ControlPanel controlPanel) {
        this.controlPanel = controlPanel;
        launch(labyrinthModel.getDefault(), 0);
        pause();
    }

    void launch(Result result, int waitMillis) {
        fastForward = false;
        stopped = false;
        controlPanel.setDisable(FAST_FORWARD, false);
        controlPanel.setDisable(PAUSE, false);
        controlPanel.setToStop();
        workingThread = new Thread(()->applier.applyChanges(result, waitMillis));
        workingThread.setDaemon(true);
        workingThread.start();
    }

    void start() {
        launch(labyrinthModel.perform(algorithm), 10);
    }
    void stop() {
        stopped = true;
        synchronized (applier.getLock()) {
            applier.getLock().notify();
        }
        controlPanel.setToGoOn();
    }
    void goOn() {
        stopped = false;
        synchronized (applier.getLock()) {
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
    }
}
