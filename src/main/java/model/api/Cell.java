package model.api;

import java.util.List;
import utility.Pair;

/**
 * Rapresents the base of a Cell.
 */
public interface Cell {

    /**
     * Returns the cell position.
     * 
     * @return the cell position
     */
    Pair getPosition();

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
