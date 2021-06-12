package labyrinths.controller.labyrinthView;

import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;


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
    Button generateBtn;

    ModificationPanel(ToggleButton changeBtn, ToggleButton startBtn,
                      ToggleButton targetBtn, ToggleButton teleportBtn, Button generateBtn) {
        this.changeBtn = changeBtn;
        this.startBtn = startBtn;
        this.targetBtn = targetBtn;
        this.teleportBtn = teleportBtn;
        buttons = Arrays.asList(changeBtn, startBtn, targetBtn, teleportBtn);
        this.generateBtn = generateBtn;
    }
    public void initialize(ControlPanelLogic logic) {
        this.logic = logic;
        generateBtn.setOnAction(e -> logic.getRandom());
        for(ToggleButton button : buttons) {
            button.setOnAction(e -> changeCurrent(button));
        }
        initializeBtnTooltips();
    }



    private void changeCurrent(ToggleButton button) {
        if(current != null)
            current.setSelected(false);
        if(current == button)
            current = null;
        else
            current = button;
    }

    public void setDisable(boolean x) {
        generateBtn.setDisable(x);
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

    private void initializeBtnTooltips() {
        Tooltip changeTooltip = new Tooltip("Press to modify labyrinth layout\nClick on walls to remove them\nor click on free fields to add a wall");
        Tooltip startTooltip = new Tooltip("Press to select starting field");
        Tooltip targetTooltip = new Tooltip("Press to select target field");
        Tooltip teleportTooltip = new Tooltip("Press to add teleports");
        Tooltip generateTooltip = new Tooltip("Press to generate labyrinth layout");
        for(Tooltip t : Arrays.asList(changeTooltip,startTooltip,targetTooltip,teleportTooltip,generateTooltip)) {
            t.setShowDelay(Duration.millis(300));
            t.setStyle("-fx-font-size: 15");
        }

        changeBtn.setTooltip(changeTooltip);
        startBtn.setTooltip(startTooltip);
        targetBtn.setTooltip(targetTooltip);
        teleportBtn.setTooltip(teleportTooltip);
        generateBtn.setTooltip(generateTooltip);
    }
}
