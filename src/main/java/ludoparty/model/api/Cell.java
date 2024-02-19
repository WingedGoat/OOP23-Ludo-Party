package ludoparty.model.api;

import java.util.List;

import ludoparty.model.Position;

/**
 * Rapresents a Cell with a position and some flags
 * (e.g. isSafe) which indicate the type of the cell.
 */
public interface Cell {

    /**
     * Cell Type.
     */
    enum CellType {
        /**
         * Bottom left house.
         */
        BOTTOM_LEFT_HOUSE,
        /**
         * Bottom left safe path.
         */
        BOTTOM_LEFT_SAFE_PATH,
        /**
         * Top left house.
         */
        TOP_LEFT_HOUSE,
        /**
         * Top left safe path.
         */
        TOP_LEFT_SAFE_PATH,
        /**
         * Top right house.
         */
        TOP_RIGHT_HOUSE,
        /**
         * Top right safe path.
         */
        TOP_RIGHT_SAFE_PATH,
        /**
         * Bottom right house.
         */
        BOTTOM_RIGHT_HOUSE,
        /**
         * Bottom right safe path.
         */
        BOTTOM_RIGHT_SAFE_PATH,
        /**
         * End cell.
         */
        END_CELL,
        /**
         * White cell.
         */
        WHITE_CELL;
    }

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
     * Returns the list of the pawns in that cell.
     * 
     * @return list with pawns
     */
    List<Pawn> getPawns();

    /**
     * Gets the type of the cell.
     * 
     * @return the cell type
     */
    CellType getType();

    /**
     * Adds a pawn to a cell.
     * 
     * @param p
     */
    void addPawn(Pawn p);

    /**
     * Removes a pawn from the cell.
     * 
     * @param p
     */
    void removePawn(Pawn p);
}
