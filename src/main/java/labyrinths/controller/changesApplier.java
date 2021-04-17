package labyrinths.controller;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import labyrinths.model.Field;
import labyrinths.model.Result;
import labyrinths.model.Type;

import java.util.List;

public class changesApplier implements Runnable {
    Result result;
    long waitMillis;

    changesApplier(Result result, long waitMillis) {
        this.result = result;
        this.waitMillis = waitMillis;
    }

    @Override
    public void run() {
        for(Field field : result.getChanges()) {
            Button button = LabyrinthController.getFields().get(field.getH()).get(field.getW());
            Platform.runLater(() -> changeFieldType(button, field.getType()));
            try {
                Thread.sleep(waitMillis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static void changeFieldType(Button button, Type type) {
        switch (type) {
            case WALL:
                button.setBackground(new Background(
                        new BackgroundFill(Color.DIMGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
                break;
            case START:
                button.setText("S");
                break;
            case TARGET:
                button.setText("T");
                break;
            case PATH:
                button.setBackground(new Background(
                        new BackgroundFill(Color.CORAL, CornerRadii.EMPTY, Insets.EMPTY)));
                break;
            case HIGHLIGHTED:
                button.setBackground(new Background(
                        new BackgroundFill(Color.BURLYWOOD, CornerRadii.EMPTY, Insets.EMPTY)));
                break;
        }
    }
}
