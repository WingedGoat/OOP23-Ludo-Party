package model.api;

import model.Color;
import utility.Pair;

/**
 * 
 */
public interface Pawn {

    /**
     * Returns the color of the pawn,
     * equals to the color associated to the player.
     * 
     * @return color
     */
    Color getColor();

    /**
     * Returns the index of the pawn in the pawn list.
     * 
     * @return itemNo
     */
    int getItemNo();

    /**
     * Gets the position of the pawn in the board.
     * 
     * @return the position
     */
    Pair getPosition();

    /**
     * Sets the position of the pawn in the board.
     * 
     * @param position the position to set
     */
    void setPosition(Pair position);

}
