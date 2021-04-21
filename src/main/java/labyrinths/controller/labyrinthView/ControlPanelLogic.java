package labyrinths.controller.labyrinthView;

import javafx.scene.control.ProgressBar;
import labyrinths.model.Labyrinth;

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
        applier.applyChanges(labyrinthModel.getDefault(), 0);
        pause();
    }

    void start() {
        fastForward = false;
        workingThread = new Thread(()->applier.applyChanges(labyrinthModel.perform(algorithm), 10));
        workingThread.setDaemon(true);
        workingThread.start();
        controlPanel.setDisable(FAST_FORWARD, false);
        controlPanel.setDisable(PAUSE, false);
        controlPanel.setToStop();
    }
    void stop() {
        synchronized (applier.getLock()) {
            stopped = true;
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
        if(!stopped)
            stop();
        goOn();
        try {
            workingThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    void pause() {
        fastForward();
        controlPanel.setDisable(START_STOP, false);
        controlPanel.setDisable(PAUSE, true);
        controlPanel.setToStart();
        progressBar.setProgress(0);
        //function to clear here
    }
    public void end() {
        controlPanel.setToStart();
        controlPanel.setDisable(START_STOP, true);
        controlPanel.setDisable(FAST_FORWARD, true);
    }
}
