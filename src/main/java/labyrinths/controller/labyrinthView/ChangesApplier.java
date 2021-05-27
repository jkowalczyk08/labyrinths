package labyrinths.controller.labyrinthView;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import labyrinths.controller.Config;
import labyrinths.model.Field;
import labyrinths.model.Result;

import java.util.*;


public class ChangesApplier {
    Fields fields;
    ControlPanelLogic logic;
    Timeline timeline;

    ChangesApplier(Fields fields) {
        this.fields = fields;
    }
    public void initialize(ControlPanelLogic logic) {
        this.logic = logic;
    }

    public void quickApply(Result result) {
        for(Field field : result.getChanges())
            fields.changeFieldType(field.getH(), field.getW(), field.getType());
    }
    Result toDo = new Result();
    int index = 0;
    void makeOneChange() {
        List<Field> changes = toDo.getChanges();
        if(index >= changes.size())
            return;
        Field field = changes.get(index);
        fields.changeFieldType(field.getH(), field.getW(), field.getType());
        logic.getProgressBar().setProgress((double)index++/changes.size());
        if(index == changes.size()) {
            logic.end();
        }
    }
    public void applyChanges(Result result, long waitMillis) {
        index=0;
        toDo = result;
        timeline = new Timeline (
                new KeyFrame(Duration.millis(waitMillis), e -> makeOneChange()));
        timeline.setCycleCount(toDo.getChanges().size());
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
        while(index<toDo.getChanges().size())
            makeOneChange();
    }
}
