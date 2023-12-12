package brickGame;

import javafx.scene.input.KeyEvent;

/**
 * The GameHandling class manages key events and movements within the game.
 * It provides static methods to handle keyboard inputs and control game elements like the paddle.
 */
public class GameHandling {

    /**
     * Handles key events during the game.
     * This method maps specific key presses to game actions, such as moving the paddle or pausing the game.
     *
     * @param event The KeyEvent representing the key action by the player.
     * @param main  The main game object that contains the game's state and logic.
     */
    public static void keyHandling(KeyEvent event, Main main) {
        switch (event.getCode()) {
            case LEFT:
                main.move(main.LEFT);
                break;
            case RIGHT:
                main.move(main.RIGHT);
                break;
            case P:
                main.pauseGame();
                break;
            case M:
                main.toggleAudio();
                break;
        }
    }

    /**
     * Controls the movement of the paddle in the game.
     * This method runs in a separate thread and updates the paddle's position based on the direction.
     *
     * @param direction The direction of paddle movement, either left or right.
     * @param main      The main game object that contains the paddle and other game elements.
     */
    public static void movementOfPaddle(int direction, Main main) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int sleepTime = 5;
                int movementIncrement = 2;
                for (int i = 0; i < 30; i++) {
                    // Movement logic for the paddle
                    if (direction == main.RIGHT) {
                        // Move right
                        if (main.xBreak + main.rect.getWidth() + movementIncrement <= main.sceneWidth) {
                            main.xBreak += movementIncrement;
                        }
                    } else if (direction == main.LEFT) {
                        // Move left
                        if (main.xBreak - movementIncrement >= 0) {
                            main.xBreak -= movementIncrement;
                        }
                    }
                    main.centerBreakX = main.xBreak + main.rect.getWidth() / 2;
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (i >= 20) {
                        sleepTime = i;
                    }
                }
            }
        }).start();
    }

    /**
     * Toggles the pause state of the game.
     * When paused, the game displays a pause message and stops the game engine.
     * Resuming the game restarts the game engine.
     *
     * @param main The main game object, which includes the game's pause state and engine.
     */
    public static void Pause(Main main) {
        main.isPaused = !main.isPaused;
        if (main.isPaused) {
            new Score().showMessage("Paused", main);
            main.engine.stop();
        } else {
            main.engine.start();
        }
    }
}
