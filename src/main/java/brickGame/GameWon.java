package brickGame;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The GameWon class contains methods to handle game-winning scenarios and display winning messages.
 */
public class GameWon {

    /**
     * Checks if the final level of the game has been completed.
     * If the player has destroyed all blocks and reached the last level, the winning screen is shown.
     * Otherwise, the game proceeds to the next level.
     *
     * @param main The main game object containing game state and level information.
     */
    public static void finalLevelCount(Main main) {
        if (main.destroyedBlockCount == main.blocks.size()) {
            if (main.level == 18) {
                main.showWin(main);
                main.engine.stop();
            } else {
                main.isLevelCompleted = true;
                main.nextLevel();
            }
        }
    }

    /**
     * Displays a winning message to the player.
     * This method is called when the player wins the game. It shows an image indicating the win.
     *
     * @param main The main game object containing the game's root pane.
     */
    public static void showWinMessage(Main main) {
        Platform.runLater(() -> {
            Image image = new Image("youwin.png");
            ImageView imageView = new ImageView(image);

            imageView.setTranslateX(100);
            imageView.setTranslateY(250);
            imageView.setFitWidth(300);
            imageView.setFitHeight(300);
            imageView.setPreserveRatio(true);

            main.root.getChildren().addAll(imageView);

            main.engine.stop();
        });
    }
}
