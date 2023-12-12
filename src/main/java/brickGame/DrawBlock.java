package brickGame;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * The DrawBlock class is responsible for drawing the visual representation of blocks in the game.
 * It handles the positioning, sizing, and texturing of block elements based on their attributes.
 */
public class DrawBlock {
    /**
     * Renders a block on the game screen using its properties.
     * The method sets the position and size of the block and applies an image pattern.
     * Each block type corresponds to a different image.
     *
     * @param block The Block object to be rendered.
     */
    public static void blockDrawings(Block block) {
        block.x = (block.column * block.width) + block.paddingH;
        block.y = (block.row * block.height) + block.paddingTop;

        // Initialize the block's shape and set its properties
        block.rect = new Rectangle();
        block.rect.setWidth(block.width);
        block.rect.setHeight(block.height);
        block.rect.setArcHeight(5);
        block.rect.setArcWidth(5);
        block.rect.setX(block.x);
        block.rect.setY(block.y);

        if (block.type == block.BLOCK_CHOCO) {
            Image image = new Image("choco.jpg");
            ImagePattern pattern = new ImagePattern(image);
            block.rect.setFill(pattern);
        } else if (block.type == block.BLOCK_HEART) {
            Image image = new Image("heart.jpg");
            ImagePattern pattern = new ImagePattern(image);
            block.rect.setFill(pattern);
        } else if (block.type == block.BLOCK_BLOCK1) {
            Image image = new Image("block1.jpg");
            ImagePattern pattern = new ImagePattern(image);
            block.rect.setFill(pattern);
        } else if (block.type == block.BLOCK_BLOCK2) {
            Image image = new Image("block2.jpg");
            ImagePattern pattern = new ImagePattern(image);
            block.rect.setFill(pattern);
        } else if (block.type == block.BLOCK_BLOCK3) {
            Image image = new Image("block3.jpg");
            ImagePattern pattern = new ImagePattern(image);
            block.rect.setFill(pattern);
        } else if (block.type == block.BLOCK_BLOCK4) {
            Image image = new Image("block4.jpg");
            ImagePattern pattern = new ImagePattern(image);
            block.rect.setFill(pattern);
        } else if (block.type == block.BLOCK_BLOCK5) {
            Image image = new Image("block5.jpg");
            ImagePattern pattern = new ImagePattern(image);
            block.rect.setFill(pattern);
        } else if (block.type == block.BLOCK_BLOCK6) {
            Image image = new Image("block6.jpg");
            ImagePattern pattern = new ImagePattern(image);
            block.rect.setFill(pattern);
        } else if (block.type == block.BLOCK_BLOCK7) {
            Image image = new Image("block7.jpg");
            ImagePattern pattern = new ImagePattern(image);
            block.rect.setFill(pattern);
        } else if (block.type == block.BLOCK_BLOCK8) {
            Image image = new Image("block8.jpg");
            ImagePattern pattern = new ImagePattern(image);
            block.rect.setFill(pattern);
        } else if (block.type == block.BLOCK_BLOCK9) {
            Image image = new Image("block9.jpg");
            ImagePattern pattern = new ImagePattern(image);
            block.rect.setFill(pattern);
        } else if (block.type == block.BLOCK_BLOCK10) {
            Image image = new Image("block10.jpg");
            ImagePattern pattern = new ImagePattern(image);
            block.rect.setFill(pattern);
        } else if (block.type == block.BLOCK_BLOCK11) {
            Image image = new Image("block11.jpg");
            ImagePattern pattern = new ImagePattern(image);
            block.rect.setFill(pattern);
        } else if (block.type == block.BLOCK_BLOCK12) {
            Image image = new Image("block12.jpg");
            ImagePattern pattern = new ImagePattern(image);
            block.rect.setFill(pattern);
        } else if (block.type == block.BLOCK_BLOCK13) {
            Image image = new Image("block13.jpg");
            ImagePattern pattern = new ImagePattern(image);
            block.rect.setFill(pattern);
        } else if (block.type == block.BLOCK_BLOCK14) {
            Image image = new Image("block14.jpg");
            ImagePattern pattern = new ImagePattern(image);
            block.rect.setFill(pattern);
        } else if (block.type ==block. BLOCK_BLOCK15) {
            Image image = new Image("block15.jpg");
            ImagePattern pattern = new ImagePattern(image);
            block.rect.setFill(pattern);
        } else if (block.type == block.BLOCK_BLOCK16) {
            Image image = new Image("block16.jpg");
            ImagePattern pattern = new ImagePattern(image);
            block.rect.setFill(pattern);
        } else if (block.type == block.BLOCK_STAR) {
            Image image = new Image("star.jpg");
            ImagePattern pattern = new ImagePattern(image);
            block.rect.setFill(pattern);
        } else {
            block.rect.setFill(block.color);
        }
    }
}
