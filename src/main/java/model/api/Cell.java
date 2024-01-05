package model.api;

import java.util.List;
import utility.Position;

/**
 * Rapresents a Cell with a position and some flags 
 * (e.g. isSafe) which indicate the type of the cell.
 */
public interface Cell {

    /**
     * Returns the cell position.
     * 
     * @return the cell position
     */
    Position getPosition();

    /**
     * Returns true if the actual cell is safe, false otherwise.
     * 
     * @return true if is a safe cell
     */
    boolean isSafe();

    /**
     * Returns true if the actual cell belongs to the home, false otherwise.
     * 
     * @return true if is a home cell
     */
    boolean isHome();

    /**
     * Returns true if the actual cell is a shop, false otherwise.
     * 
     * @return true if is a shop cell
     */
    boolean isShop();

    /**
     * Returns true if the actual cell is not populated by pawns, false otherwise.
     * 
     * @return true if is an empty cell
     */
    boolean isEmpty();

    /**
     * Returns the list of the pawns in that cell.
     * 
     * @return list with pawns
     */
    List<Pawn> getPawns();

}
