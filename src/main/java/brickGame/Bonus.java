package brickGame;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import java.io.Serializable;
import java.util.Random;

/**
 * Represents a bonus item in the game.
 * This class manages the properties and appearance of bonus items in the game.
 */
public class Bonus implements Serializable {
    /**
     * The graphical representation of the bonus block, typically as a rectangle with an image.
     */
    public Rectangle choco;

    /**
     * The x-coordinate of the bonus block on the game screen.
     */
    public double x;

    /**
     * The y-coordinate of the bonus block on the game screen.
     */
    public double y;

    /**
     * The timestamp of when the bonus block was created.
     */
    public long timeCreated;

    /**
     * Indicates whether the bonus has been taken by the player.
     */
    public boolean taken = false;

    /**
     * Constructs a new Bonus block at a specified row and column in the game grid.
     * The position of the bonus is calculated based on the row and column,
     * along with the dimensions and padding of the blocks in the game.
     *
     * @param row    The row position where the bonus should be placed.
     * @param column The column position where the bonus should be placed.
     */
    public Bonus(int row, int column) {
        x = (column * (Block.getWidth())) + Block.getPaddingH() + (Block.getWidth() / 2) - 15;
        y = (row * (Block.getHeight())) + Block.getPaddingTop() + (Block.getHeight() / 2) - 15;

        draw();
    }

    /**
     * Draws the bonus block on the game screen.
     * This method initializes and sets the properties of the choco block,
     * such as its size, position, and image fill based on a random selection.
     */
    private void draw() {
        choco = new Rectangle();
        choco.setWidth(30);
        choco.setHeight(30);
        choco.setX(x);
        choco.setY(y);

        // Randomly select an image for the bonus item
        String url;
        if (new Random().nextInt(20) % 2 == 0) {
            url = "bonus1.png";
        } else {
            url = "bonus2.png";
        }

        choco.setFill(new ImagePattern(new Image(url)));
    }
}
