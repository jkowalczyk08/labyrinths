package labyrinths.controller.labyrinthView;

import labyrinths.model.Field;
import labyrinths.model.Result;

import java.util.Timer;


public class ChangesApplier {
    Fields fields;
    ControlPanelLogic logic;
    final Object lock = new Object();

    public Object getLock() {
        return lock;
    }

    ChangesApplier(Fields fields) {
        this.fields = fields;
    }
    public void initialize(ControlPanelLogic logic) {
        this.logic = logic;
    }

    public void quickApply(Result result) {
        System.out.println(result.getChanges());
        for(Field field : result.getChanges())
            fields.changeFieldType(field.getH(), field.getW(), field.getType());
    }
    public void applyChanges(Result result, long waitMillis) {
        System.out.println(result.getChanges());
        int i=0;
        for(Field field : result.getChanges()) {
            long lastTime = System.currentTimeMillis();
            fields.changeFieldType(field.getH(), field.getW(), field.getType());
            logic.getProgressBar().setProgress((double)i++/result.getChanges().size());
            if(!logic.ifFastForward()) {
                try {
                    synchronized (lock) {
                        if(logic.ifStopped())
                            lock.wait();
                        else
                            lock.wait(Math.max(lastTime+waitMillis-System.currentTimeMillis(), 0));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        logic.end();
    }
}
