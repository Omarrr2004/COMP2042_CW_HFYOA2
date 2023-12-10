package brickGame;


import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;

public class Block implements Serializable {
    private static Block block = new Block(-1, -1, 99);

    public int row;
    public int column;


    public boolean isDestroyed = false;

    private Color color;
    public int type;

    public int x;
    public int y;

    private int width = 100;
    private int height = 30;
    private int paddingTop = height * 2;
    private int paddingH = 50;
    public Rectangle rect;


    public static int NO_HIT = -1;
    public static final int HIT_RIGHT = 0;
    public static final int HIT_BOTTOM = 1;
    public static final int HIT_LEFT = 2;
    public static final int HIT_TOP = 3;

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

    public Block(int row, int column, int type) {
        this.row = row;
        this.column = column;
        this.type = type;

        draw();
    }

    private void draw() {
        x = (column * width) + paddingH;
        y = (row * height) + paddingTop;

        rect = new Rectangle();
        rect.setWidth(width);
        rect.setHeight(height);
        rect.setArcHeight(5);
        rect.setArcWidth(5);
        rect.setX(x);
        rect.setY(y);

        if (type == BLOCK_CHOCO) {
            Image image = new Image("choco.jpg");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else if (type == BLOCK_HEART) {
            Image image = new Image("heart.jpg");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else if (type == BLOCK_BLOCK1) {
            Image image = new Image("block1.jpg");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else if (type == BLOCK_BLOCK2) {
            Image image = new Image("block2.jpg");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else if (type == BLOCK_BLOCK3) {
            Image image = new Image("block3.jpg");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else if (type == BLOCK_BLOCK4) {
            Image image = new Image("block4.jpg");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else if (type == BLOCK_BLOCK5) {
            Image image = new Image("block5.jpg");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else if (type == BLOCK_BLOCK6) {
            Image image = new Image("block6.jpg");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else if (type == BLOCK_BLOCK7) {
            Image image = new Image("block7.jpg");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else if (type == BLOCK_BLOCK8) {
            Image image = new Image("block8.jpg");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else if (type == BLOCK_BLOCK9) {
            Image image = new Image("block9.jpg");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else if (type == BLOCK_BLOCK10) {
            Image image = new Image("block10.jpg");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else if (type == BLOCK_BLOCK11) {
            Image image = new Image("block11.jpg");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else if (type == BLOCK_BLOCK12) {
            Image image = new Image("block12.jpg");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else if (type == BLOCK_BLOCK13) {
            Image image = new Image("block13.jpg");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else if (type == BLOCK_BLOCK14) {
            Image image = new Image("block14.jpg");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else if (type == BLOCK_BLOCK15) {
            Image image = new Image("block15.jpg");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else if (type == BLOCK_BLOCK16) {
            Image image = new Image("block16.jpg");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else if (type == BLOCK_STAR) {
            Image image = new Image("star.jpg");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else {
            rect.setFill(color);
        }

    }


    public int checkHitToBlock(double xBall, double yBall, double ballRadius) {
        if (isDestroyed) {
            return NO_HIT;
        }

        // Calculate ball's bounding box
        double ballLeft = xBall - ballRadius;
        double ballRight = xBall + ballRadius;
        double ballTop = yBall - ballRadius;
        double ballBottom = yBall + ballRadius;

        // Define the block's boundaries
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

    public static int getPaddingTop() {
        return block.paddingTop;
    }

    public static int getPaddingH() {
        return block.paddingH;
    }

    public static int getHeight() {
        return block.height;
    }

    public static int getWidth() {
        return block.width;
    }

}