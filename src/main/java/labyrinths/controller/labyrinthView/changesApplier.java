package labyrinths.controller.labyrinthView;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import labyrinths.model.Field;
import labyrinths.model.Result;
import labyrinths.model.Type;

public class changesApplier implements Runnable {
    Result result;
    long waitMillis;
    Fields fields;

    changesApplier(Result result, long waitMillis, Fields fields) {
        this.result = result;
        this.waitMillis = waitMillis;
        this.fields = fields;
    }

    @Override
    public void run() {
        for(Field field : result.getChanges()) {
            Button button = fields.get(field.getH()).get(field.getW());
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
