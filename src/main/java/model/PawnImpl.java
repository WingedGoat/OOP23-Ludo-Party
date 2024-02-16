package model;

import model.api.Cell.CellType;
import utils.BColor;
import model.api.Pawn;

/**
 * Pawn Implementation.
 */
public final class PawnImpl implements Pawn {

    private final Position startPosition;
    private Position currentPosition;
    private final int itemNo;
    private final CellType playerHouse;
    private final BColor color;

    /**
     * Constructor.
     * 
     * @param pos
     *                    the position of the pawn in the board
     * @param index
     *                    the index of the pawn in the list
     * @param playerHouse
     *                    the home position in the board
     * @param color
     *                    the color of the pawn
     */
    public PawnImpl(final Position pos, final int index, final CellType playerHouse, final BColor color) {
        this.startPosition = pos;
        this.currentPosition = pos;
        this.itemNo = index;
        this.playerHouse = playerHouse;
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
    public BColor getColor() {
        return color;
    }

    @Override
    public CellType getPlayerHouse() {
        return playerHouse;
    }
}
