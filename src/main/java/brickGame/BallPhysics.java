package brickGame;

import javafx.application.Platform;

/**
 * The BallPhysics class handles the physics and movement of the ball in the game.
 * It includes methods for setting the physics of the ball and resetting its position.
 */
public class BallPhysics {

    /**
     * Updates the position and state of the ball based on its current movement and collisions.
     *
     * @param main The main game object containing the ball and game state.
     */
    public static void physicsSetToBall(Main main) {
        if (main.goDownBall) {
            main.yBall += main.vY;
        } else {
            main.yBall -= main.vY;
        }

        if (main.goRightBall) {
            main.xBall += main.vX;
        } else {
            main.xBall -= main.vX;
        }

        if (main.yBall <= 0) {
            main.resetCollideFlags();
            main.goDownBall = true;
            return;
        }
        if (main.yBall >= main.sceneHeigt) {
            main.goDownBall = false;
            if (!main.isGoldStatus) {
                main.heart--;
                new Score().show(main.sceneWidth / 2, main.sceneHeigt / 2, -1, main);
                if (main.heart == 0) {
                    new Score().showGameOver(main);
                    main.engine.stop();
                } else {
                    main.resetBallAndBreakPosition();
                }
            }
        }

        // Collision with break
        if (main.yBall >= main.yBreak - main.ballRadius) {
            // Check if ball is within the horizontal bounds of the break
            if (main.xBall >= main.xBreak && main.xBall <= main.xBreak + main.rect.getWidth()) {
                main.hitTime = main.time;
                main.resetCollideFlags();
                main.collideToBreak = true;
                main.goDownBall = false;

                double relation = (main.xBall - main.centerBreakX) / (main.breakWidth / 2);

                // Adjust ball velocity based on collision point
                if (Math.abs(relation) <= 0.3) {
                    main.vX = Math.abs(relation);
                } else if (Math.abs(relation) > 0.3 && Math.abs(relation) <= 0.7) {
                    main.vX = (Math.abs(relation) * 1.5) + (main.level / 3.500);
                } else {
                    main.vX = (Math.abs(relation) * 2) + (main.level / 3.500);
                }

                // Determine ball direction after collision
                if (main.xBall - main.centerBreakX > 0) {
                    main.collideToBreakAndMoveToRight = true;
                } else {
                    main.collideToBreakAndMoveToRight = false;
                }
            }
        }

        // Wall collisions
        if (main.xBall >= main.sceneWidth) {
            main.resetCollideFlags();
            main.collideToRightWall = true;
        }
        if (main.xBall <= 0) {
            main.resetCollideFlags();
            main.collideToLeftWall = true;
        }

        // Determine new direction after wall collision
        if (main.collideToRightWall) {
            main.goRightBall = false;
        }
        if (main.collideToLeftWall) {
            main.goRightBall = true;
        }
        if (main.collideToBreak) {
            if (main.collideToBreakAndMoveToRight) {
                main.goRightBall = true;
            } else {
                main.goRightBall = false;
            }
        }

        // Block collisions
        if (main.collideToRightBlock) {
            main.goRightBall = true;
        }
        if (main.collideToLeftBlock) {
            main.goRightBall = true;
        }
        if (main.collideToTopBlock) {
            main.goDownBall = false;
        }
        if (main.collideToBottomBlock) {
            main.goDownBall = true;
        }
    }

    /**
     * Resets the position of the ball and the break to their initial positions.
     *
     * @param main The main game object containing the ball and break.
     */
    public static void positionReset(Main main) {
        // Reset ball position
        main.xBall = main.sceneWidth / 2.0;
        main.yBall = main.yBreak - main.ballRadius - 10;

        // Reset break position
        main.xBreak = (main.sceneWidth - main.breakWidth) / 2.0;

        // Update position of the break and ball on the screen
        Platform.runLater(() -> {
            main.rect.setX(main.xBreak);
            main.ball.setCenterX(main.xBall);
            main.ball.setCenterY(main.yBall);
        });
    }
}
