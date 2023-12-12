package brickGame;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/**
 * The GameVisuals class handles the creation and setup of the game's visual elements,
 * including the main menu and audio controls.
 */
public class GameVisuals {

    /**
     * Sets up the game scenery, including the main menu and background.
     * This method initializes the visual elements of the game's main menu,
     * such as buttons and background, and configures the media player for audio.
     *
     * @param primaryStage The primary stage for this application, onto which the scene is set.
     * @param main         The main game object containing game elements and configurations.
     */
    public static void GameScenery(Stage primaryStage, Main main) {
        main.primaryStage = primaryStage;

        // Initialize the main menu pane
        main.root = new Pane();

        // Setup new game icon and button
        ImageView newGameIcon = new ImageView(new Image("startnewgame.jpg"));
        newGameIcon.setFitHeight(40);
        newGameIcon.setFitWidth(160);

        Button startNewGameButton = new Button();
        startNewGameButton.setGraphic(newGameIcon);
        startNewGameButton.setStyle("-fx-background-color: transparent;"); // Make button background transparent

        // Setup background image
        Image backgroundImage = new Image("bg.jpeg");
        BackgroundImage background = new BackgroundImage(backgroundImage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
        main.root.setBackground(new Background(background));

        // Position the start new game button
        double startX = 25;
        double startY = 275;
        startNewGameButton.setLayoutX(startX);
        startNewGameButton.setLayoutY(startY);

        // Setup media player for background music
        Media media = new Media(main.getClass().getResource("/themesong.mp3").toExternalForm());
        main.mediaPlayer = new MediaPlayer(media);
        main.mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // For continuous play

        // Setup audio control button
        main.audioButton = new Button();
        ImageView audioIcon = new ImageView(new Image("audioOn.png"));
        main.audioButton.setGraphic(audioIcon);
        main.mediaPlayer.play();

        // Set the size and position of the audio button
        double audioIconWidth = 40;
        double audioIconHeight = 40;
        audioIcon.setFitWidth(audioIconWidth);
        audioIcon.setFitHeight(audioIconHeight);
        double audioButtonX = 85;
        double audioButtonY = 320;
        main.audioButton.setLayoutX(audioButtonX);
        main.audioButton.setLayoutY(audioButtonY);
        main.audioButton.setStyle("-fx-background-color: transparent;");
        main.audioButton.setOnAction(e -> main.toggleAudio());

        // Add buttons to the menu
        main.root.getChildren().addAll(startNewGameButton, main.audioButton);

        // Create and set the menu scene
        Scene menuScene = new Scene(main.root, main.sceneWidth, main.sceneHeigt);
        startNewGameButton.setOnAction(e -> main.startNewGame());
        primaryStage.setScene(menuScene);
        primaryStage.setTitle("Game Menu");
        primaryStage.show();
    }
}
