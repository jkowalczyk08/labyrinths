module labyrinths {
    requires javafx.controls;
    requires javafx.fxml;

    opens labyrinths to javafx.fxml;
    exports labyrinths;
}