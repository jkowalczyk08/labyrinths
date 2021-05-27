package labyrinths.controller.labyrinthView;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import labyrinths.controller.Config;
import labyrinths.model.Field;
import labyrinths.model.Result;

import java.util.*;


public class ChangesApplier implements Config {
    Fields fields;
    ControlPanelLogic logic;
    Timeline timeline;

    ChangesApplier(Fields fields) {
        this.fields = fields;
    }
    public void initialize(ControlPanelLogic logic) {
        this.logic = logic;
    }

    public void quickApply(Result result, int index) {
        for(int i = index; i<result.getChanges().size(); ++i) {
            Field field = result.getChanges().get(i);
            fields.changeFieldType(field.getH(), field.getW(), field.getType());
        }
    }
    long startTime;
    Result toDo = new Result();
    int index = 0;
    LinkedList<Integer> transforming = new LinkedList<>();
    LinkedList<Integer> restoring = new LinkedList<>();

    void makeOneChange(int i) {
        List<Field> changes = toDo.getChanges();
        if(i >= changes.size())
            return;
        Field field = changes.get(i);
        fields.changeFieldType(field.getH(), field.getW(), field.getType());
        logic.getProgressBar().setProgress((double)i/changes.size());
    }
    void step() {
        long time = System.currentTimeMillis();
        long desiredIndex = Math.min((time-startTime)/ALGORITHM_DELAY, toDo.getChanges().size()-1);
        while(index<=desiredIndex) {
            transforming.add(index);
            ++index;
        }
        restoringStep();
        transformingStep();
        if(transforming.isEmpty() && restoring.isEmpty() &&
                desiredIndex==toDo.getChanges().size()-1) {
            timeline.stop();
            logic.end();
        }
    }
    private void transformingStep() {
        long time = System.currentTimeMillis();
        transforming.removeIf( i -> {
            long stage = (time-startTime)-i*ALGORITHM_DELAY;
            if(stage<TRANSFORMATION_TIME) {
                fields.setOpacity(toDo.getChanges().get(i), (1-(double)stage/TRANSFORMATION_TIME));
                return false;
            }
            else {
                makeOneChange(i);
                restoring.add(i);
                return true;
            }});
    }
    private void restoringStep() {
        long time = System.currentTimeMillis();
        restoring.removeIf( i -> {
            long stage = (time-startTime)-i*ALGORITHM_DELAY-TRANSFORMATION_TIME;
            if(stage<TRANSFORMATION_TIME) {
                fields.setOpacity(toDo.getChanges().get(i), ((double)stage/TRANSFORMATION_TIME));
                return false;
            }
            else {
                fields.setOpacity(toDo.getChanges().get(i), 1);
                return true;
            }});
    }
    public void applyChanges(Result result) {
        index=0;
        toDo = result;
        transforming = new LinkedList<>();
        restoring = new LinkedList<>();
        startTime = System.currentTimeMillis();
        timeline = new Timeline (
                new KeyFrame(Duration.millis(1000.0/FRAMERATE), e -> step()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    public void stop() {
        timeline.pause();
    }
    public void goOn() {
        timeline.play();
    }
    public void fastForward() {
        timeline.stop();
        for(int i : restoring)
            fields.setOpacity(toDo.getChanges().get(i), 1);
        for(int i : transforming)
            fields.setOpacity(toDo.getChanges().get(i), 1);
        quickApply(toDo, 0);
        logic.end();
    }
}
