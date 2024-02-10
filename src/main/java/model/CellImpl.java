package model;

import java.util.ArrayList;
import java.util.List;
import model.api.Cell;
import model.api.Pawn;

/**
 * Cell Impl.
 */
public final class CellImpl implements Cell {

    private final Position position;
    private final boolean isSafe;
    private final boolean isShop;
    private final boolean isEmpty;
    private final boolean isHome;
    private final Type type;
    private final List<Pawn> pawns;

    /**
     * Constructor.
     * 
     * @param pos the position of the cell
     * @param isSafe marks the cell as safe if it is true
     * @param isShop marks the cell as shop if it is true
     * @param isHome marks the cell as home if it is true
     * @param type the cell type
     */
    public CellImpl(final Position pos, final boolean isSafe, final boolean isShop, final boolean isHome, final Type type) {
        this.position = pos;
        this.isSafe = isSafe;
        this.isShop = isShop;
        this.isHome = isHome;
        this.type = type;
        this.isEmpty = true;
        this.pawns = new ArrayList<>();
    }

    /**
     * Constructor for home cells.
     * 
     * @param pos the 2D position
     * @param type the cell type { @link Cell.Type }
     */
    public CellImpl(final Position pos, final Type type) {
        this(pos, false, false, true, type);
    }

    /**
     * Constructor for safe cells.
     * 
     * @param pos the 2D position
     * @param isSafe is safe cell
     * @param type the cell type { @link Cell.Type }
     */
    public CellImpl(final Position pos, final boolean isSafe, final Type type) {
        this(pos, isSafe, false, false, type);
    }

    @Override
    public Position getPosition() {
        return this.position;
    }

    @Override
    public boolean isSafe() {
        return this.isSafe;
    }

    @Override
    public boolean isHome() {
        return this.isHome;
    }

    @Override
    public boolean isShop() {
        return this.isShop;
    }

    @Override
    public boolean isEmpty() {
        return isEmpty;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public List<Pawn> getPawns() {
        return List.copyOf(this.pawns);
    }

    @Override
    public String toString() {
        return "CellImpl [position=" + position + ", isSafe=" + isSafe + ", isShop=" + isShop + ", isEmpty=" + isEmpty
                + ", isHome=" + isHome + ", pawnList=" + pawns + "]";
    }

}
