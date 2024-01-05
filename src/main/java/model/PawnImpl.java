package model;

import model.api.Pawn;
import utility.Pair;

/**
 * Pawn Implementation.
 */
public final class PawnImpl implements Pawn {

    private final Color color;
    private final int itemNo;
    private Pair<Integer, Integer> position;

    /**
     * Constructor.
     * 
     * @param color
     *          the color of the pawn
     * @param index
     *          the index of the pawn in the pawn list
     * @param pos
     *          the position of the pawn in the board
     */
    public PawnImpl(final Color color, final int index, final Pair<Integer, Integer> pos) {
        this.color = color;
        this.itemNo = index;
        this.position = pos;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public int getItemNo() {
        return itemNo;
    }

    @Override
    public Pair<Integer, Integer> getPosition() {
        return position;
    }

    @Override
    public void setPosition(final Pair<Integer, Integer> position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "PawnImpl [color=" + color + ", itemNo =" + itemNo + ", position=" + position + "]";
    }

}
