package ludoparty.model;

import ludoparty.model.api.Cell.CellType;
import ludoparty.model.api.Game;
import ludoparty.utils.BColor;
import ludoparty.utils.Index;
import ludoparty.model.api.Pawn;

/**
 * Pawn Implementation.
 */
public final class PawnImpl implements Pawn {

    private final Position startPosition;
    private Position currentPosition;
    private final CellType playerHouse;
    private final BColor color;

    /**
     * Constructor.
     * 
     * @param pos         the position of the pawn in the board
     * @param playerHouse the home position in the board
     * @param color       the color of the pawn
     */
    public PawnImpl(final Position pos, final CellType playerHouse, final BColor color) {
        this.startPosition = pos;
        this.currentPosition = pos;
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
    public BColor getColor() {
        return color;
    }

    @Override
    public CellType getPlayerHouse() {
        return playerHouse;
    }

    @Override
    public boolean canMove(final int diceResult, final Game game) {
        if (this.getPosition().equals(this.getStartPosition())) {
            if (diceResult == Index.SIX) {
                return true;
            }
        } else {
            final Position pawnPos = this.getPosition();
            final int size = Movement.getPathColors().get(this.getColor().ordinal()).size();

            if (Movement.getPathColors().get(this.getColor().ordinal()).indexOf(pawnPos) + diceResult < size
                    && Movement.noMultipleEnemyPawns(
                            Movement.getPathColors().get(this.getColor().ordinal()).indexOf(pawnPos), diceResult, this,
                            game)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void move(final int diceResult, final Game game) {
        if (this.canMove(diceResult, game)) {
            Movement.movePawn(this, getColor(), diceResult, game);
        }
    }

}
