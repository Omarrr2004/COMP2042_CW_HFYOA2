package brickGame;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * The StartGame class contains methods for starting a new game and setting up the game scene.
 */
public class StartGame {
    /**
     * Initiates a new game by resetting the game state and initializing game components.
     * This method is called to start a new game from the beginning.
     *
     * @param main The main game object containing game state and configurations.
     */
    public static void newGame(Main main) {
        main.isGameStarted = true;
        // Resetting game state for a new game
        main.level = 1;
        main.score = 0;
        main.heart = 3;
        main.destroyedBlockCount = 0;
        main.isGoldStatus = false;
        main.isExistHeartBlock = false;

        // Initialize game components
        main.initBall();
        main.initBreak();
        main.initBoard();

        // Set up the game scene
        main.setupGameScene();
    }

    /**
     * Sets up the game scene, including UI components and game elements.
     * This method initializes the game scene with all necessary components and starts the game engine.
     *
     * @param main The main game object containing game state, elements, and the primary stage.
     */
    public static void setUpGame(Main main) {
        main.root = new Pane();
        main.scoreLabel = new Label("Score: " + main.score);
        main.heartLabel = new Label("Heart: " + main.heart);
        main.levelLabel = new Label("Level: " + main.level);

        // Position labels
        main.heartLabel.setTranslateX(main.sceneWidth - 70);
        main.levelLabel.setTranslateY(20);

        main.root.getChildren().addAll(main.rect, main.ball, main.scoreLabel, main.heartLabel, main.levelLabel);
        for (Block block : main.blocks) {
            main.root.getChildren().add(block.rect);
        }

        Scene scene = new Scene(main.root, main.sceneWidth, main.sceneHeigt);
        scene.getStylesheets().add("style.css");
        scene.setOnKeyPressed(main);

        // Configure and show the primary stage
        main.primaryStage.setTitle("Brick Game");
        main.primaryStage.setScene(scene);
        main.primaryStage.show();

        // Start the game engine
        main.engine = new GameEngine();
        main.engine.setOnAction(main);
        main.engine.setFps(120);
        main.engine.start();
    }
}
