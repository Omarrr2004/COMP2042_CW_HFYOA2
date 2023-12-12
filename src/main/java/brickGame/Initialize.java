package brickGame;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * The Initialize class contains static methods for initializing various game elements,
 * such as the ball, the break (player-controlled block), and the game board.
 */
public class Initialize {
    /**
     * Initializes the ball for the game.
     * Sets the starting position and visual representation of the ball.
     *
     * @param main The main game object containing game settings and state.
     */
    public static void initializeBall(Main main) {
        Random random = new Random();
        main.xBall = main.sceneWidth / 2.0;  // Start in the middle of the scene

        int lastRow;
        if (main.level <= 10) {
            lastRow = (main.level - 1) * 1 + 4;  // Increase the multiplier for each level
        } else {
            lastRow = 11;  // Maximum limit of 11 rows for levels after 10
        }

        // Find the bottom position of the last block
        double lastBlockBottom = lastRow * Block.getHeight() + Block.getPaddingTop() + Block.getHeight();

        // Set yBall a little below the last block
        main.yBall = lastBlockBottom + 5;

        main.ball = new Circle();
        main.ball.setRadius(main.ballRadius);
        main.ball.setFill(new ImagePattern(new Image("ball.png")));

        main.vX = 0.0; // No horizontal movement at the start
        main.vY = 2.0;
        main.goDownBall = true;
        main.goRightBall = false;
    }

    /**
     * Initializes the break (paddle), setting its size, position, and image.
     *
     * @param main The main class of the game containing the game's state and configurations.
     * @param rect The rectangle shape representing the paddle or break.
     */
    public static void initializeBreak(Main main,Rectangle rect) {
        main.rect = new Rectangle();
        main.rect.setWidth(main.breakWidth);
        main.rect.setHeight(main.breakHeight);
        main.rect.setArcWidth(10);
        main.rect.setArcHeight(10);

        // Center the playing block at the beginning of each level
        main.xBreak = (main.sceneWidth - main.breakWidth) / 2.0;

        main.rect.setX(main.xBreak);
        main.rect.setY(main.yBreak);

        ImagePattern pattern = new ImagePattern(new Image("block.jpg"));
        main.rect.setFill(pattern);
    }

    /**
     * Initializes the game board with blocks of various types.
     * The layout and types of blocks are determined based on the game level.
     *
     * @param main The main class of the game containing the game's state and configurations.
     */
    public static void initializeBoard(Main main) {
        Random random = new Random();

        ArrayList<Integer> blockTypes = new ArrayList<>(Arrays.asList(
                Block.BLOCK_BLOCK3, Block.BLOCK_BLOCK4, Block.BLOCK_BLOCK5, Block.BLOCK_BLOCK6,
                Block.BLOCK_BLOCK7, Block.BLOCK_BLOCK8, Block.BLOCK_BLOCK9, Block.BLOCK_BLOCK10,
                Block.BLOCK_BLOCK11, Block.BLOCK_BLOCK12, Block.BLOCK_BLOCK13, Block.BLOCK_BLOCK14,
                Block.BLOCK_BLOCK15, Block.BLOCK_BLOCK16, Block.BLOCK_CHOCO
        ));

        final int MAX_ROWS = 11;
        final int BLOCKS_PER_ROW = 4;

        // Calculate the total number of blocks based on the level, with a maximum of MAX_ROWS rows.
        int rows = Math.min(main.level + 1, MAX_ROWS);
        int totalBlocks = BLOCKS_PER_ROW * rows;

        // Creating a list to keep track of all positions
        ArrayList<Integer> positions = new ArrayList<>();
        for (int i = 0; i < totalBlocks; i++) {
            positions.add(i);
        }

        // Define the number of star blocks based on the level
        int numberOfStarBlocks = main.level <= 6 ? 1 : 2;

        // Store the positions for star blocks
        ArrayList<Integer> starPositions = new ArrayList<>();
        for (int i = 0; i < numberOfStarBlocks; i++) {
            starPositions.add(positions.remove(random.nextInt(positions.size())));
        }

        // Randomly select a position for the heart block if level is greater than 5
        Integer heartPos = main.level > 5 ? positions.remove(random.nextInt(positions.size())) : null;

        // Block 1 and Block 2 positions
        Integer block1Pos = main.level >= 9 ? positions.remove(random.nextInt(positions.size())) : null;
        Integer block2Pos = main.level >= 4 ? positions.remove(random.nextInt(positions.size())) : null;

        for (int i = 0; i < totalBlocks; i++) {
            int type;
            if (starPositions.contains(i)) {
                type = Block.BLOCK_STAR;
            } else if (heartPos != null && i == heartPos) {
                type = Block.BLOCK_HEART;
            } else if (block1Pos != null && i == block1Pos) {
                type = Block.BLOCK_BLOCK1; // Block 1 for level 9 and above
            } else if (block2Pos != null && i == block2Pos) {
                type = Block.BLOCK_BLOCK2; // Block 2 for level 4 and above
            } else {
                int typeIndex = random.nextInt(blockTypes.size());
                type = blockTypes.get(typeIndex);
            }

            int row = i / BLOCKS_PER_ROW;
            int column = i % BLOCKS_PER_ROW;
            main.blocks.add(new Block(row, column, type));
        }
    }
}
