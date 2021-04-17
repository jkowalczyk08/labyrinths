module labyrinths {
    requires javafx.controls;
    requires javafx.fxml;

    opens labyrinths to javafx.fxml;
    opens labyrinths.controller to javafx.fxml;
    opens labyrinths.controller.labyrinthView to javafx.fxml;

    exports labyrinths;
    exports labyrinths.controller;
}