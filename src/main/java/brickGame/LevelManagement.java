package brickGame;

import javafx.application.Platform;

/**
 * The LevelManagement class contains methods to manage level transitions and game restarts.
 */
public class LevelManagement {
    /**
     * Handles the transition to the next level in the game.
     * If the current level is completed, increments the level and resets the game state for the new level.
     *
     * @param main The main game object containing game state and configuration.
     */
    public static void theNextLevel(Main main) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    if (main.isLevelCompleted) {
                        main.level++;  // Increment level here only if the level is completed
                        System.out.println("Level incremented to: " + main.level);
                        main.isLevelCompleted = false; // Reset the flag for the next level

                        // Reset game state for the new level
                        main.vX = 1.000;
                        main.resetCollideFlags();
                        main.goDownBall = true;

                        main.isGoldStatus = false;
                        main.isExistHeartBlock = false;

                        main.hitTime = 0;
                        main.time = 0;
                        main.goldTime = 0;

                        // Clear old blocks, and initialize new ones
                        main.blocks.clear();
                        main.chocos.clear();
                        main.destroyedBlockCount = 0;
                        main.initBall();
                        main.initBreak();
                        main.initBoard();

                        // Update the game scene for the next level
                        main.updateGameScene();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    /**
     * Restarts the game from the first level.
     * Resets the game state and initializes game elements for a new start.
     *
     * @param main The main game object containing game state and configuration.
     */
    public static void gameRestart(Main main) {
        try {
            main.level = 1;
            main.heart = 3;
            main.score = 0;
            main.vX = 1.000;
            main.destroyedBlockCount = 0;
            main.resetCollideFlags();
            main.goDownBall = true;

            main.isGoldStatus = false;
            main.isExistHeartBlock = false;
            main.hitTime = 0;
            main.time = 0;
            main.goldTime = 0;

            main.blocks.clear();
            main.chocos.clear();

            main.start(main.primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the game scene, typically called after restarting the game or moving to the next level.
     * Clears the current game scene and re-adds all game elements with updated information.
     *
     * @param main The main game object containing game elements and UI components.
     */
    public static void restartGameScene(Main main) {
        main.root.getChildren().clear();
        main.root.getChildren().addAll(main.rect, main.ball, main.scoreLabel, main.heartLabel, main.levelLabel);
        for (Block block : main.blocks) {
            main.root.getChildren().add(block.rect);
        }

        // Update labels
        main.scoreLabel.setText("Score: " + main.score);
        main.heartLabel.setText("Heart: " + main.heart);
        main.levelLabel.setText("Level: " + main.level);
    }
}
