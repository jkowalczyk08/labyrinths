module labyrinths {
    requires javafx.controls;
    requires javafx.fxml;

    opens labyrinths.controller.labyrinthView to javafx.fxml;
    opens labyrinths.controller.menuView to javafx.fxml;

    exports labyrinths;
    exports labyrinths.model;
    exports labyrinths.controller.menuView;
    exports labyrinths.controller.labyrinthView;
}