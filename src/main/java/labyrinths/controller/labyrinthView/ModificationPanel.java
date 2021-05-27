package labyrinths.controller.labyrinthView;

import javafx.scene.control.ToggleButton;


import java.util.Arrays;
import java.util.List;

public class ModificationPanel {
    ToggleButton changeBtn;
    ToggleButton startBtn;
    ToggleButton targetBtn;
    ToggleButton teleportBtn;
    List<ToggleButton> buttons;
    ToggleButton current = null;
    ControlPanelLogic logic;

    ModificationPanel( ToggleButton changeBtn, ToggleButton startBtn,
                       ToggleButton targetBtn, ToggleButton teleportBtn) {
        this.changeBtn = changeBtn;
        this.startBtn = startBtn;
        this.targetBtn = targetBtn;
        this.teleportBtn = teleportBtn;
        buttons = Arrays.asList(changeBtn, startBtn, targetBtn, teleportBtn);
    }
    public void initialize(ControlPanelLogic logic) {
        this.logic = logic;
        for(ToggleButton button : buttons) {
            button.setOnAction(e -> changeCurrent(button));
        }
    }

    private void changeCurrent(ToggleButton button) {
        if(current != null)
            current.setSelected(false);
        if(current == button)
            current = null;
        else
            current = button;
    }

    public void setDisable(boolean x) { //use this
        for(ToggleButton button : buttons)
            button.setDisable(x);
        if(x) {
            if(current != null)
                current.setSelected(false);
            current = null;
        }
    }

    public void modify(int h, int w) {
        if(current == null)
            return;
        if(current == changeBtn) {
            logic.quickApply(logic.getLabyrinthModel().addWall(h, w));
        }
        else if(current == startBtn) {
            logic.quickApply(logic.getLabyrinthModel().setStart(h, w));
        }
        else if(current == targetBtn) {
            logic.quickApply(logic.getLabyrinthModel().setTarget(h, w));
        }
        else if(current == teleportBtn) {
            logic.quickApply(logic.getLabyrinthModel().setTeleport(h, w));
        }
    }
}
