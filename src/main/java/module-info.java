module labyrinths {
    requires javafx.controls;
    requires javafx.fxml;

    opens labyrinths.controller.labyrinthView to javafx.fxml;
    opens labyrinths.controller.menuView to javafx.fxml;

    exports labyrinths;
    exports labyrinths.controller.menuView;
}