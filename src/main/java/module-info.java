module brickGame {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.media;
    requires javafx.graphics;

    opens brickGame to javafx.fxml;
    exports brickGame;
}