package labyrinths.controller.labyrinthView;

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
    ControlPanel controlPanel;
    final Object lock = new Object();

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
            if(!fastForward && waitMillis>0) {
                try {
                    synchronized (lock) {
                        lock.wait(waitMillis);
                        if(stopped)
                            lock.wait();
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
        fastForward();
        controlPanel.setDisable(START_STOP, false);
        controlPanel.setDisable(PAUSE, true);
        controlPanel.setToStart();
        progressBar.setProgress(0);
        //function to clear here
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
}
