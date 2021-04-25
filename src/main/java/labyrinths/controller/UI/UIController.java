package labyrinths.controller.UI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import labyrinths.model.Labyrinth;
import labyrinths.model.LabyrinthPreset;
import labyrinths.model.Result;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UIController implements Initializable {

    static Fields fields;
    static Labyrinth labyrinthModel;
    protected static Properties properties = new Properties();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        labyrinthModel = new Labyrinth(properties.getHeight(), properties.getWidth());
        fields = new Fields(labyrinthModel);
        constructEmptyLabyrinth();
        applyChanges(labyrinthModel.getPreset(LabyrinthPreset.SNAKE),0);
        initializeButtons();
    }

    @FXML
    Pane labyrinthPane;
    void constructEmptyLabyrinth() {
        int height = labyrinthModel.getHeight(), width = labyrinthModel.getWidth();
        GridPane labyrinth = new GridPane();
        for(int i=0; i<height; ++i) {
            for(int j=0; j<width; ++j) {
                Button field = fields.get(i).get(j);
                field.prefWidthProperty().bind(labyrinthPane.widthProperty());
                field.prefHeightProperty().bind(labyrinthPane.heightProperty());
                labyrinth.add(field, j, i);
            }
        }
        labyrinth.prefWidthProperty().bind(labyrinthPane.widthProperty());
        labyrinth.prefHeightProperty().bind(labyrinthPane.heightProperty());
        labyrinthPane.getChildren().add(labyrinth);
    }

    void applyChanges(Result result, long waitMillis) {
        new Thread(new changesApplier(result, waitMillis, fields)).start();
    }


    @FXML
    Button clearBtn;
    @FXML
    Button dfsBtn;
    @FXML
    Button settingsBtn;
    void initializeButtons() {
        /*
        TODO: clearBtn should ideally apply changes labyrinthModel.clear() and labyrinthModel.getDefault()
                (so that it clears only the path, not the labyrinth walls)
         */
        //clearBtn.setOnAction(actionEvent -> applyChanges(labyrinthModel.getDefault(), 0));
        dfsBtn.setOnAction(actionEvent -> applyChanges(labyrinthModel.perform("dfs"), 10));
        settingsBtn.setOnAction(actionEvent -> {
            try {
                UITest.setRoot("settingsUI");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
