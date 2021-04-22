package labyrinths.controller.labyrinthView;

import labyrinths.model.Field;
import labyrinths.model.Result;


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

    public void applyChanges(Result result, long waitMillis) {
        int i=0;
        for(Field field : result.getChanges()) {
            fields.changeFieldType(field.getH(), field.getW(), field.getType());
            logic.getProgressBar().setProgress((double)i++/result.getChanges().size());
            if(!logic.ifFastForward() && waitMillis>0) {
                try {
                    synchronized (lock) {
                        if(logic.ifStopped())
                            lock.wait();
                        else
                            lock.wait(waitMillis);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        logic.end();
    }
}
