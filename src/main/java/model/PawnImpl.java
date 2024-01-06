package model;

import model.PlayerHome.HomePosition;
import model.api.Pawn;
import utility.Position;

/**
 * Pawn Implementation.
 */
public final class PawnImpl implements Pawn {

    private final Position startPosition;
    private Position currentPosition;
    private final int itemNo;
    private final HomePosition homePosition;
    private final Color color;

    /**
     * Constructor.
     * 
     * @param pos
     *          the position of the pawn in the board
     * @param index
     *          the index of the pawn in the list
     * @param homePos
     *          the home position in the board
     * @param color
     *          the color of the pawn
     */
    public PawnImpl(final Position pos, final int index, final HomePosition homePos, final Color color) {
        this.startPosition = pos;
        this.currentPosition = pos;
        this.itemNo = index;
        this.homePosition = homePos;
        this.color = color;
    }

    @Override
    public Position getStartPosition() {
        return startPosition;
    }

    @Override
    public Position getPosition() {
        return currentPosition;
    }

    @Override
    public void setPosition(final Position position) {
        this.currentPosition = position;
    }

    @Override
    public int getItemNo() {
        return itemNo;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public HomePosition getHomePosition() {
        return homePosition;
    }

    @Override
    public String toString() {
        return "PawnImpl [startPosition=" + startPosition + ", currentPosition=" + currentPosition + ", itemNo="
                + itemNo + ", homePosition=" + homePosition + ", color=" + color + "]";
    }

}
