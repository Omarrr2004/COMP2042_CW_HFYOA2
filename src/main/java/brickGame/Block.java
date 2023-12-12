package brickGame;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.io.Serializable;

/**
 * Represents a block in the game. Blocks have various properties like position, size, and type.
 * They can be hit and destroyed by the ball in the game.
 */
public class Block implements Serializable {
    private static Block block = new Block(-1, -1, 99);
    public int row;
    public int column;
    public boolean isDestroyed = false;
    public Color color;
    public int type;
    public int x;
    public int y;
    public int width = 100;
    public int height = 30;
    public int paddingTop = height * 2;
    public int paddingH = 50;
    public Rectangle rect;
    public static int NO_HIT = -1;
    public static final int HIT_RIGHT = 0;
    public static final int HIT_BOTTOM = 1;
    public static final int HIT_LEFT = 2;
    public static final int HIT_TOP = 3;

    // Constants defining different block types
    public static int BLOCK_CHOCO = 100;
    public static int BLOCK_STAR = 101;
    public static int BLOCK_HEART = 102;
    public static int BLOCK_BLOCK1 = 103;
    public static int BLOCK_BLOCK2 = 104;
    public static int BLOCK_BLOCK3 = 105;
    public static int BLOCK_BLOCK4 = 106;
    public static int BLOCK_BLOCK5 = 107;
    public static int BLOCK_BLOCK6 = 108;
    public static int BLOCK_BLOCK7 = 109;
    public static int BLOCK_BLOCK8 = 110;
    public static int BLOCK_BLOCK9 = 111;
    public static int BLOCK_BLOCK10 = 112;
    public static int BLOCK_BLOCK11 = 113;
    public static int BLOCK_BLOCK12 = 114;
    public static int BLOCK_BLOCK13 = 115;
    public static int BLOCK_BLOCK14 = 116;
    public static int BLOCK_BLOCK15 = 117;
    public static int BLOCK_BLOCK16 = 118;

    /**
     * Constructs a new Block with specified row, column, and type.
     *
     * @param row    The row position of the block.
     * @param column The column position of the block.
     * @param type   The type of the block, which determines its behavior and appearance.
     */
    public Block(int row, int column, int type) {
        this.row = row;
        this.column = column;
        this.type = type;
        draw();
    }

    /**
     * Draws the block on the game screen.
     */
    private void draw() {
        DrawBlock.blockDrawings(this);
    }

    /**
     * Checks if the ball has hit the block.
     *
     * @param xBall      The x-coordinate of the ball.
     * @param yBall      The y-coordinate of the ball.
     * @param ballRadius The radius of the ball.
     * @return An integer representing the side of the block that was hit, or NO_HIT if no collision occurred.
     */
    public int checkHitToBlock(double xBall, double yBall, double ballRadius) {
        if (isDestroyed) {
            return NO_HIT;
        }

        // Calculate ball's bounding box
        double ballLeft = xBall - ballRadius;
        double ballRight = xBall + ballRadius;
        double ballTop = yBall - ballRadius;
        double ballBottom = yBall + ballRadius;

        // Block's boundaries
        double blockLeft = x;
        double blockRight = x + width;
        double blockTop = y;
        double blockBottom = y + height;

        // Check for collision with each side of the block
        boolean collidesWithLeft = ballRight > blockLeft && ballLeft < blockLeft && yBall > blockTop && yBall < blockBottom;
        boolean collidesWithRight = ballLeft < blockRight && ballRight > blockRight && yBall > blockTop && yBall < blockBottom;
        boolean collidesWithTop = ballBottom > blockTop && ballTop < blockTop && xBall > blockLeft && xBall < blockRight;
        boolean collidesWithBottom = ballTop < blockBottom && ballBottom > blockBottom && xBall > blockLeft && xBall < blockRight;

        // Determine the side of the collision
        if (collidesWithTop) {
            return HIT_TOP;
        } else if (collidesWithBottom) {
            return HIT_BOTTOM;
        } else if (collidesWithLeft) {
            return HIT_LEFT;
        } else if (collidesWithRight) {
            return HIT_RIGHT;
        }

        return NO_HIT;
    }

    /**
     * Gets the top padding of the block.
     *
     * @return The top padding value.
     */
    public static int getPaddingTop() {
        return block.paddingTop;
    }

    /**
     * Gets the horizontal padding of the block.
     *
     * @return The horizontal padding value.
     */
    public static int getPaddingH() {
        return block.paddingH;
    }

    /**
     * Gets the height of the block.
     *
     * @return The height of the block.
     */
    public static int getHeight() {
        return block.height;
    }

    /**
     * Gets the width of the block.
     *
     * @return The width of the block.
     */
    public static int getWidth() {
        return block.width;
    }
}