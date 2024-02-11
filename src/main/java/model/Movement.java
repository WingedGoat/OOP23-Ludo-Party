package model;

import model.api.Pawn;
import utility.BColor;
import java.util.List;
import java.util.ArrayList;
import utility.Constants;

/**
 * This class handles the movement of the whole pawn system.
 */
public final class Movement {

    private final List<List<Position>> pathColors = new ArrayList<>();

    /**
     * The constructor of the class Movement.
     */
    public Movement() {
        pathColors.add(Constants.BLUE_STREET);
        pathColors.add(Constants.RED_STREET);
        pathColors.add(Constants.GREEN_STREET);
        pathColors.add(Constants.YELLOW_STREET);
    }

    /**
     * Move the given pawn if possible.
     * 
     * @param pawn
     * @param diceResult
     * @param game
     */
    public void move(final Pawn pawn, final int diceResult, final Game game) {
        movePawn(pawn, pawn.getColor(), diceResult, game);
    }

    /**
     * Move the given pawn and checks.
     * there is whether or not an enemy.
     * pawn in the new given pawn's cell.
     * 
     * @param pawn
     * @param color
     * @param diceResult
     * @param game
     */
    private void movePawn(final Pawn pawn, final BColor color, final int diceResult, final Game game) {

        if (pawn.getPosition().equals(pawn.getStartPosition())) {
            step(pawn, game, 0);
            eatenPawns(pawn, game);
        } else {
            final int j = pathColors.get(color.ordinal()).indexOf(pawn.getPosition());
            for (int i = j + 1; i < pathColors.get(color.ordinal()).size(); i++) {
                if (i - j <= diceResult) {
                    step(pawn, game, i);
                    eatenPawns(pawn, game);
                }
            }
        }
    }

    /*
     * @Override
     * public String toString() {
     * return "PawnImpl [startPosition=" + startPosition + ", currentPosition=" +
     * currentPosition + ", itemNo="
     * + itemNo + ", playerHouse=" + playerHouse + ", color=" + color + "]";
     * }
     */

    private void step(final Pawn p, final Game g, final int index) {
        for (final var c : g.getCells()) {
            if (c.getPosition().equals(p.getPosition())) {
                c.removePawn(p);
            }
        }

        p.setPosition(pathColors.get(p.getColor().ordinal()).get(index));

        for (final var c : g.getCells()) {
            if (c.getPosition().equals(p.getPosition())) {
                c.addPawn(p);
            }
        }
    }

    private void eatenPawns(final Pawn pawn, final Game game) {
        if (enemyIsAlone(pawn, game) && notSafe(pawn.getPosition(), game)) {
            homeStep(getEnemyPawn(pawn, game), game);
        }
    }

    private Pawn getEnemyPawn(final Pawn pawn, final Game game) {
        for (int i = 0; i < game.getPlayers().size() * Constants.PLAYER_PAWNS; i++) {
            if (!pawn.getColor().equals(getPawn(i, game).getColor())
                    && getPawn(i, game).getPosition().equals(pawn.getPosition())) {
                return getPawn(i, game);
            }
        }
        return null;
    }

    private void homeStep(final Pawn p, final Game g) {
        for (final var c : g.getCells()) {
            if (c.getPosition().equals(p.getPosition())) {
                c.removePawn(p);
            }
        }

        p.setPosition(p.getStartPosition());

        for (final var c : g.getCells()) {
            if (c.getPosition().equals(p.getPosition())) {
                c.addPawn(p);
            }
        }
    }

    /*
     * private void eatenPawns(final Pawn pawn, final Game game) {
     * 
     * for (int k = 0; k < game.getPlayers().size() * Constants.PLAYER_PAWNS; k++) {
     * if (enemyIsAlone(pawn, k, game)
     * && !pawn.getColor().equals(getPawn(k, game).getColor())
     * && getPawn(k, game).getPosition().equals(pawn.getPosition())) {
     * getPawn(k, game).setPosition(getPawn(k, game).getStartPosition());
     * }
     * }
     * }
     */

    private boolean enemyIsAlone(final Pawn pawn, final Game game) {

        int nEnemies = 0;

        for (int i = 0; i < game.getPlayers().size() * Constants.PLAYER_PAWNS; i++) {
            if (!pawn.getColor().equals(getPawn(i, game).getColor())
                    && getPawn(i, game).getPosition().equals(pawn.getPosition())) {
                nEnemies++;
            }
        }

        return nEnemies == 1;
    }

    private boolean notSafe(final Position pos, final Game game) {
        for (final var c : game.getCells()) {
            if (c.getPosition().equals(pos)) {
                return !c.isSafe();
            }
        }
        return false;
    }

    private Pawn getPawn(final int k, final Game game) {
        return game.getPlayers().get(k / game.getPlayers().size()).getPawns().get(k % Constants.PLAYER_PAWNS);
    }
}
