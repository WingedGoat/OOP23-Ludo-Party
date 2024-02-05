package model;

import model.PlayerHome.HomePosition;
import model.api.Pawn;
import utility.Position;
import java.util.ArrayList;
import java.util.List;

/**
 * Pawn Implementation.
 */
public final class PawnImpl implements Pawn {

    private final Position startPosition;
    private Position currentPosition;
    private final int itemNo;
    private final HomePosition homePosition;
    private final Color color;
    private static final List<List<Position>> PATH_COLORS = new ArrayList<>();

    /**
     * Constructor.
     * 
     * @param pos
     *                the position of the pawn in the board
     * @param index
     *                the index of the pawn in the list
     * @param homePos
     *                the home position in the board
     * @param color
     *                the color of the pawn
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

    /**
     * Moves the pawn.
     * 
     * @param unknownPlayerPawn player pawn
     * @param diceResult        dice result
     */
    public void move(final PawnImpl unknownPlayerPawn, final int diceResult) {

        switch (unknownPlayerPawn.getColor()) {
            case RED:
                movePawn(unknownPlayerPawn, Color.RED, diceResult);
                break;

            case GREEN:
                movePawn(unknownPlayerPawn, Color.GREEN, diceResult);
                break;

            case BLUE:
                movePawn(unknownPlayerPawn, Color.BLUE, diceResult);
                break;

            case YELLOW:
                movePawn(unknownPlayerPawn, Color.YELLOW, diceResult);
                break;

            default:
                break;
        }
    }

    /**
     * Move the given pawn and checks.
     * there is whether or not an enemy.
     * pawn in the new given pawn's cell.
     * 
     * @param pawn
     * @param color
     * @param diceResult
     */
    private void movePawn(final PawnImpl pawn, final Color color, final int diceResult) {

        if (pawn.getPosition().equals(pawn.getStartPosition())) {
            pawn.setPosition(PATH_COLORS.get(color.ordinal()).get(0));
            // eatenPawns(pawn);
        } else {
            final int j = PATH_COLORS.get(color.ordinal()).indexOf(pawn.getPosition());
            for (int i = j + 1; i < PATH_COLORS.get(color.ordinal()).size(); i++) {

                if (i - j <= diceResult) {
                    pawn.setPosition(PATH_COLORS.get(color.ordinal()).get(i));
                    // eatenPawns(pawn);
                }
            }
        }
    }

    /**
     * Move the given pawn and checks.
     * there is whether or not an enemy.
     * pawn in the new given pawn's cell.
     * 
     * @param pawn
     */
    /*
     * private void eatenPawns(final PawnImpl pawn) {
     * Game game
     * for (int k = 0; k < game.getPlayers().size() * Constants.PLAYER_PAWNS; k++) {
     * if (!pawn.getColor().equals(getPawn(k).getColor())
     * && getPawn(k).getPosition().equals(pawn.getPosition())) {
     * getPawn(k).setPosition(getPawn(k).getStartPosition());
     * }
     * }
     * 
     * }
     */

    @Override
    public String toString() {
        return "PawnImpl [startPosition=" + startPosition + ", currentPosition=" + currentPosition + ", itemNo="
                + itemNo + ", homePosition=" + homePosition + ", color=" + color + "]";
    }

}
