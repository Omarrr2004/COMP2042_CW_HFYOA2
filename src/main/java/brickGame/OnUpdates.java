package brickGame;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

/**
 * The OnUpdates class contains methods for updating the game state and processing physics.
 * These methods are called during the game loop to update game elements and handle interactions.
 */
public class OnUpdates {
    /**
     * Updates the game state including UI labels, position of game elements, and handling block hits.
     * This method is called repeatedly in the game loop to refresh the game visuals and state.
     *
     * @param main The main game object containing game state and elements.
     */
    public static void gameUpdate(Main main) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                main.scoreLabel.setText("Score: " + main.score);
                main.heartLabel.setText("Heart : " + main.heart);

                main.rect.setX(main.xBreak);
                main.rect.setY(main.yBreak);
                main.ball.setCenterX(main.xBall);
                main.ball.setCenterY(main.yBall);

                for (Bonus choco : main.chocos) {
                    choco.choco.setY(choco.y);
                }
            }
        });
        for (Block block : main.blocks) {
            int hitCode = block.checkHitToBlock(main.xBall, main.yBall, main.ballRadius);
            if (hitCode != Block.NO_HIT && !block.isDestroyed) {
                // Check if the block hit is BLOCK_BLOCK1
                if (block.type == Block.BLOCK_BLOCK1) {
                    main.changePaddleWidth(true); // for increasing paddle width
                }
                // Check if the block hit is BLOCK_BLOCK2
                else if (block.type == Block.BLOCK_BLOCK2) {
                    main.changePaddleWidth(false); // for decreasing paddle width
                }
            }
        }


        if (main.yBall >= Block.getPaddingTop() && main.yBall <= (Block.getHeight() * (main.level + 1)) + Block.getPaddingTop()) {
            for (final Block block : main.blocks) {
                int hitCode = block.checkHitToBlock(main.xBall, main.yBall, main.ballRadius);
                if (hitCode != Block.NO_HIT) {
                    main.score += 1;

                    new Score().show(block.x, block.y, 1, main);

                    block.rect.setVisible(false);
                    block.isDestroyed = true;
                    main.destroyedBlockCount++;
                    main.resetCollideFlags();

                    if (block.type == Block.BLOCK_CHOCO) {
                        final Bonus choco = new Bonus(block.row, block.column);
                        choco.timeCreated = main.time;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                main.root.getChildren().add(choco.choco);
                            }
                        });
                        main.chocos.add(choco);
                    }

                    if (block.type == Block.BLOCK_STAR) {
                        main.goldTime = main.time;
                        main.ball.setFill(new ImagePattern(new Image("goldball.png")));
                        System.out.println("gold ball");
                        main.root.getStyleClass().add("goldRoot");
                        main.isGoldStatus = true;
                    }

                    if (block.type == Block.BLOCK_HEART) {
                        main.heart++;
                    }
                    if(main.isGoldStatus){

                    }
                    else{
                        if (hitCode == Block.HIT_RIGHT) {
                            main.collideToRightBlock = true;
                        } else if (hitCode == Block.HIT_BOTTOM) {
                            main.collideToBottomBlock = true;
                        } else if (hitCode == Block.HIT_LEFT) {
                            main.collideToLeftBlock = true;
                        } else if (hitCode == Block.HIT_TOP) {
                            main.collideToTopBlock = true;
                        }

                    }
                }}
        }
    }
    /**
     * Processes the physics aspects of the game.
     * This includes moving bonuses, checking for level completion, and updating ball physics.
     *
     * @param main The main game object containing game state and elements.
     */
    public static void physicsProcess(Main main) {
        main.checkDestroyedCount();
        main.setPhysicsToBall();

        if (main.time - main.goldTime > 2500) {
            main.ball.setFill(new ImagePattern(new Image("ball.png")));
            main.root.getStyleClass().remove("goldRoot");
            main.isGoldStatus = false;
        }

        for (Bonus choco : main.chocos) {
            if (!choco.taken) {
                choco.y += ((main.time - choco.timeCreated) / 1000.0) + 1.0;
                choco.choco.setY(choco.y);

                // Check for collision with the paddle
                if (choco.y + choco.choco.getHeight() >= main.yBreak &&
                        choco.y <= main.yBreak + main.breakHeight &&
                        choco.x + choco.choco.getWidth() >= main.xBreak &&
                        choco.x <= main.xBreak + main.breakWidth) {

                    choco.taken = true;
                    choco.choco.setVisible(false);
                    main.score += 3;
                    new Score().show(choco.x, choco.y, 3, main);
                }
            }
        }
    }

}
