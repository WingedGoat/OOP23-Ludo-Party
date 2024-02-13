package model.api;

import java.util.List;
import java.util.Set;
import model.Position;

/**
 * Board.
 */
public interface Board {

    /**
     * Returns the set of Positions of the house at bottom left corner.
     * 
     * @return the set of Positions
     */
    Set<Position> getBottomLeftHouse();

    /**
     * Returns the set of Positions of the safe path of the player at bottom left
     * corner.
     * 
     * @return the set of Positions
     */
    Set<Position> getBottomLeftSafePath();

    /**
     * Returns the set of Positions of the house at top left corner.
     * 
     * @return the set of Positions
     */
    Set<Position> getTopLeftHouse();

    /**
     * Returns the set of Positions of the safe path of the player at top left
     * corner.
     * 
     * @return the set of Positions
     */
    Set<Position> getTopLeftSafePath();

    /**
     * Returns the set of Positions of the house at top right corner.
     * 
     * @return the set of Positions
     */
    Set<Position> getTopRightHouse();

    /**
     * Returns the set of Positions of the safe path of the player at top right
     * corner.
     * 
     * @return the set of Positions
     */
    Set<Position> getTopRightSafePath();

    /**
     * Returns the set of Positions of the house at bottom right corner.
     * 
     * @return the set of Positions
     */
    Set<Position> getBottomRightHouse();

    /**
     * Returns the set of Positions of the safe path of the player at bottom right
     * corner.
     * 
     * @return the set of Positions
     */
    Set<Position> getBottomRighSafePath();

    /**
     * Gets the list of start positions of pawns in the bottom left house.
     * 
     * @return the list of pawns start positions
     */
    List<Position> getBottomLeftPawnsStartPos();

    /**
     * Gets the list of start positions of pawns in the top left house.
     * 
     * @return the list of pawns start positions
     */
    List<Position> getTopLeftPawnsStartPos();

    /**
     * Gets the list of start positions of pawns in the top right house.
     * 
     * @return the list of pawns start positions
     */
    List<Position> getTopRightPawnsStartPos();

    /**
     * Gets the list of start positions of pawns in the bottom right house.
     * 
     * @return the list of pawns start positions
     */
    List<Position> getBottomRightPawnsStartPos();

    /**
     * Gets the set of positions shops located in the board.
     * 
     * @return the set of shop positions
     */
    Set<Position> getShops();

    /**
     * Gets the cells of the board.
     * 
     * @return the cells
     */
    Set<Cell> getCells();

}
