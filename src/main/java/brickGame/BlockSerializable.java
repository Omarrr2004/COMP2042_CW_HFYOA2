package brickGame;

import java.io.Serializable;

public class BlockSerializable implements Serializable {

    /**
     * The row position of the block in the game grid.
     */
    public final int row;

    /**
     * The column position of the block in the game grid.
     */
    public final int column;

    /**
     * The type of the block, which can determine its behavior and appearance in the game.
     */
    public final int type;

    public BlockSerializable(int row , int column , int type) {
        this.row = row;
        this.column = column;
        this.type = type;
    }
}
