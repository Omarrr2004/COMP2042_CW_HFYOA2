package brickGame;

import javafx.application.Platform;
import javafx.scene.shape.Rectangle;

/**
 * The Paddle class contains methods for managing the paddle's size and state in the game.
 * It includes functionality for changing the paddle size and resetting it to the default state.
 */
public class Paddle {

    /**
     * Changes the size of the paddle based on the specified increase or decrease request.
     * The method ensures that the paddle's size change occurs no more frequently than every 9 seconds.
     *
     * @param increase Specifies whether to increase (true) or decrease (false) the paddle size.
     * @param main     The main game object containing game state and configurations.
     * @param rect     The rectangle shape representing the paddle.
     */

    public static void paddleChange(boolean increase, Main main, Rectangle rect) {
        Platform.runLater(() -> {
            long currentTime = System.currentTimeMillis();
            if (currentTime - main.lastWidthChangeTime >= 9000) {
                if ((increase && main.paddleState != Main.PaddleState.INCREASED) || (!increase && main.paddleState != Main.PaddleState.DECREASED)) {
                    double originalWidth = rect.getWidth();
                    double newWidth = increase ? originalWidth * 1.25 : originalWidth * 0.75;
                    double newX = main.xBreak;

                    if (newX + newWidth > main.sceneWidth) {
                        newX = main.sceneWidth - newWidth;
                    } else if (newX < 0) {
                        newX = 0;
                    }

                    main.xBreak = newX;
                    rect.setWidth(newWidth);
                    main.paddleState = increase ? Main.PaddleState.INCREASED : Main.PaddleState.DECREASED;

                    main.lastWidthChangeTime = currentTime;
                    main.resetPaddleWidthAfterDelay();
                }
            }
        });
    }

    /**
     * Starts a thread to reset the paddle width to its default size after a delay.
     * The delay is set to allow temporary size changes to the paddle.
     *
     * @param main The main game object containing game state and configurations.
     */
    public static void resetPaddleWidth(Main main) {
        new Thread(() -> {
            try {
                Thread.sleep(9000); // Wait for 9 seconds
                Platform.runLater(main::resetPaddleWidthToDefault);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Resets the paddle to its default width and adjusts its position if necessary.
     * This method is called to revert any temporary size changes to the paddle.
     *
     * @param main The main game object containing game state and configurations.
     * @param rect The rectangle shape representing the paddle.
     */
    public static void resetPaddleToDefault(Main main, Rectangle rect) {
        Platform.runLater(() -> {
            if (main.paddleState != Main.PaddleState.NORMAL) {
                rect.setWidth(main.breakWidth);
                if (main.xBreak + main.breakWidth > main.sceneWidth) {
                    main.xBreak = main.sceneWidth - main.breakWidth;
                }
                main.paddleState = Main.PaddleState.NORMAL;
            }
        });
    }
}
