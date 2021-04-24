module labyrinths {
    requires javafx.controls;
    requires javafx.fxml;

    opens labyrinths to javafx.fxml;
    opens labyrinths.controller to javafx.fxml;
    opens labyrinths.controller.labyrinthView to javafx.fxml;
    opens labyrinths.controller.UI to javafx.fxml;
    opens labyrinths.controller.menu to javafx.fxml;

    exports labyrinths;
    exports labyrinths.controller;
    exports labyrinths.controller.UI;
    exports labyrinths.controller.menu;
}