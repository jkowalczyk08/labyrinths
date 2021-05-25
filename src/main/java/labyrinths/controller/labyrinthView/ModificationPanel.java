package labyrinths.controller.labyrinthView;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.List;

public class ModificationPanel {
    ToggleButton freeBtn;
    ToggleButton wallBtn;
    ToggleButton startBtn;
    ToggleButton targetBtn;
    List<ToggleButton> buttons;
    ToggleButton current = null;
    ModificationPanel(ToggleButton freeBtn, ToggleButton wallBtn, ToggleButton startBtn, ToggleButton targetBtn) {
        this.freeBtn = freeBtn;
        this.wallBtn = wallBtn;
        this.startBtn = startBtn;
        this.targetBtn = targetBtn;
        buttons = Arrays.asList(freeBtn, wallBtn, startBtn, targetBtn);
    }
    public void initialize() {
        for(ToggleButton button : buttons) {
            button.setOnAction(e -> changeCurrent(button));
        }
    }

    private void changeCurrent(ToggleButton button) {
        if(current != null)
            current.setSelected(false);
        current = button;
    }

    public void setDisable(boolean x) { //use this
        for(ToggleButton button : buttons)
            button.setDisable(x);
    }
}
