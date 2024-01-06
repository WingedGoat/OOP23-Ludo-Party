package model.api;

import model.Color;
import model.PlayerHome.HomePosition;
import utility.Position;

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
     * Returns the index of the pawn in the pawn list.
     * 
     * @return itemNo
     */
    int getItemNo();

    /**
     * Returns the color of the pawn,
     * equals to the color associated to the player.
     * 
     * @return color
     */
    Color getColor();

    /**
     * Gets the position of the pawn in the board.
     * 
     * @return the position
     */
    HomePosition getHomePosition();

}
