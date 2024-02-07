package model;

import java.util.ArrayList;
import java.util.List;
import model.api.Cell;
import model.api.Pawn;

/**
 * 
 */
public final class CellImpl implements Cell {

    private final Position position;
    private final boolean isSafe;
    private final boolean isShop;
    private final boolean isEmpty;
    private final boolean isHome;
    private final List<Pawn> pawnList;

    /**
     * Constructor.
     * 
     * @param pos
     *          the position of the cell
     * @param isSafe
     *          marks the cell as safe if it is true
     * @param isShop
     *          marks the cell as shop if it is true
     * @param isHome
     *          marks the cell as home if it is true
     */
    public CellImpl(final Position pos, final boolean isSafe, final boolean isShop, final boolean isHome) {
        this.position = pos;
        this.isSafe = isSafe;
        this.isShop = isShop;
        this.isHome = isHome;
        this.isEmpty = true;
        this.pawnList = new ArrayList<>();
    }

    /**
     * Constructor.
     * 
     * @param pos the position of the cell
     */
    public CellImpl(final Position pos) {
        this.position = pos;
        this.isSafe = false;
        this.isShop = false;
        this.isHome = false;
        this.isEmpty = true;
        this.pawnList = new ArrayList<>();
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
    public List<Pawn> getPawns() {
        return List.copyOf(this.pawnList);
    }

    @Override
    public String toString() {
        return "CellImpl [position=" + position + ", isSafe=" + isSafe + ", isShop=" + isShop + ", isEmpty=" + isEmpty
                + ", isHome=" + isHome + ", pawnList=" + pawnList + "]";
    }

}
