package labyrinths.controller.labyrinthView;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import labyrinths.model.Field;
import labyrinths.model.Labyrinth;
import labyrinths.model.Result;

import static labyrinths.controller.labyrinthView.ControlPanel.ButtonType.*;


public class ControlPanelLogic {
    Labyrinth labyrinthModel;
    Fields fields;
    ProgressBar progressBar;
    final Object lock = new Object();

    ControlPanel controlPanel;
    String algorithm = "dfs"; // change to enum
    boolean fastForward, stopped;
    Thread workingThread;


    ControlPanelLogic(Labyrinth labyrinthModel, Fields fields, ProgressBar progressBar) {
        this.labyrinthModel = labyrinthModel;
        this.fields = fields;
        this.progressBar = progressBar;
    }

    public void applyChanges(Result result, long waitMillis) {
        int i=0;
        for(Field field : result.getChanges()) {
            Button button = fields.get(field.getH()).get(field.getW());
            ChangesApplier.changeFieldType(button, field.getType());
            progressBar.setProgress((double)i++/result.getChanges().size());
            if(!fastForward){
                try {
                    if(!stopped)
                        Thread.sleep(waitMillis);
                    else {
                        synchronized (lock) {
                            lock.wait();
                        }
                    }
                } catch (InterruptedException e) {
                        e.printStackTrace();
                }
            }
        }
        controlPanel.setToStart();
        controlPanel.setDisable(START_STOP, true);
        controlPanel.setDisable(FAST_FORWARD, true);
    }

    public void initialize(ControlPanel controlPanel) {
        this.controlPanel = controlPanel;
        applyChanges(labyrinthModel.getDefault(), 0);
        pause();
    }

    void start() {
        fastForward = false;
        workingThread = new Thread(()->applyChanges(labyrinthModel.perform(algorithm), 10));
        workingThread.setDaemon(true);
        workingThread.start();
        controlPanel.setDisable(FAST_FORWARD, false);
        controlPanel.setDisable(PAUSE, false);
        controlPanel.setToStop();
    }
    void stop() {
        stopped = true;
        controlPanel.setToGoOn();
    }
    void goOn() {
        stopped = false;
        synchronized (lock) {
            lock.notify();
        }
        controlPanel.setToStop();
    }
    void pause() {
        controlPanel.setDisable(START_STOP, false);
        controlPanel.setToStart();
        controlPanel.setDisable(FAST_FORWARD, true);
        controlPanel.setDisable(PAUSE, true);
        progressBar.setProgress(0);
        controlPanel.setToStart();
        fastForward();
        stopped = false;
    }
    void fastForward() {
        controlPanel.setDisable(FAST_FORWARD, true);
        fastForward = true;
        if(stopped)
            goOn();
    }

}
