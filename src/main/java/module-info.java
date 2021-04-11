module labyrinths {
    requires javafx.controls;
    requires javafx.fxml;

    opens labyrinths to javafx.fxml;
    exports labyrinths;
    opens labyrinths.controller to javafx.fxml;
    exports labyrinths.controller;
}