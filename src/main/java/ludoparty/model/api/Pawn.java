package ludoparty.model.api;

import ludoparty.model.Position;
import ludoparty.model.api.Cell.CellType;
import ludoparty.utils.BColor;

/**
 * Represents the pawn of a player, which is moved in the board.
 */
public interface Pawn {

    /**
     * Gets the start position of the pawn.
     * 
     * @return the start position
     */
    Position getStartPosition();

    /**
     * Gets the current position of the pawn.
     * 
     * @return the current position
     */
    Position getPosition();

    /**
     * Sets the position of the pawn in the board.
     * 
     * @param position the position to set
     */
    void setPosition(Position position);

    /**
     * Returns the color of the pawn,
     * equals to the color associated to the player.
     * 
     * @return color
     */
    BColor getColor();

    /**
     * Gets the player house of the pawn in the board.
     * 
     * @return the player house
     */
    CellType getPlayerHouse();

    /**
     * Checks if the pawn can exit from its house or
     * it can move from a position to another.
     * 
     * @param game       the game
     * @param diceResult the dice result
     * @return if the pawn can move
     */
    boolean canMove(int diceResult, Game game);

    /**
     * Move the given pawn if possible.
     * 
     * @param diceResult the dice result
     * @param game       the game
     */
    void move(int diceResult, Game game);

}
