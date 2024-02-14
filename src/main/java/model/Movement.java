package model;

import model.api.Game;
import model.api.Pawn;
import utility.BColor;
import java.util.List;
import java.util.ArrayList;
import utility.Constants;
import utility.Index;

/**
 * This class handles the movement of the whole pawn system.
 */
public final class Movement {

    private final List<List<Position>> pathColors = new ArrayList<>();

    /**
     * The constructor of the class Movement.
     */
    public Movement() {
        pathColors.add(buildBlue());
        pathColors.add(buildRed());
        pathColors.add(buildGreen());
        pathColors.add(buildYellow());
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
            if (diceResult == Index.SIX) {
                step(pawn, game, 0);
                eatenPawns(pawn, game);
            }
        } else {
            final int index = pathColors.get(color.ordinal()).indexOf(pawn.getPosition());
            if (index + diceResult < pathColors.get(color.ordinal()).size() && index + diceResult >= 0) {
                step(pawn, game, pathColors.get(color.ordinal()).indexOf(pawn.getPosition()) + diceResult);
                eatenPawns(pawn, game);
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
        for (final var c : g.getBoardCells()) {
            if (c.getPosition().equals(p.getPosition())) {
                c.removePawn(p);
            }
        }

        p.setPosition(pathColors.get(p.getColor().ordinal()).get(index));

        for (final var c : g.getBoardCells()) {
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

    private void homeStep(final Pawn p, final Game game) {
        for (final var c : game.getBoardCells()) {
            if (c.getPosition().equals(p.getPosition())) {
                c.removePawn(p);
            }
        }

        p.setPosition(p.getStartPosition());

        for (final var c : game.getBoardCells()) {
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
        for (final var c : game.getBoardCells()) {
            if (c.getPosition().equals(pos)) {
                return !c.isSafe();
            }
        }
        return false;
    }

    private Pawn getPawn(final int k, final Game game) {
        return game.getPlayers().get(k / Constants.PLAYER_PAWNS).getPawns().get(k % Constants.PLAYER_PAWNS);
    }

    // provare ad aiutare chi gestisce i turni e le win
    // facendo un metodo pubblico che ritorna se un player ha vinto
    // con tutte e 4 le pedine al centro e magari pure in
    // ordine in base a chi ha finito prima

    private List<Position> buildBlue() {
        final PathBuilder pb = new PathBuilder(Index.SIX, Index.THIRTEEN);
        pb.addUp(Index.FIVE);
        pb.addLeft(Index.SIX);
        pb.addUp(Index.TWO);
        pb.addRight(Index.SIX);
        pb.addUp(Index.SIX);
        pb.addRight(Index.TWO);
        pb.addDown(Index.SIX);
        pb.addRight(Index.SIX);
        pb.addDown(Index.TWO);
        pb.addLeft(Index.SIX);
        pb.addDown(Index.SIX);
        pb.addLeft(Index.ONE);
        pb.addUp(Index.SEVEN);
        return List.copyOf(pb.getPath());
    }

    private List<Position> buildRed() {
        final PathBuilder pb = new PathBuilder(Index.ONE, Index.SIX);
        pb.addRight(Index.FIVE);
        pb.addUp(Index.SIX);
        pb.addRight(Index.TWO);
        pb.addDown(Index.SIX);
        pb.addRight(Index.SIX);
        pb.addDown(Index.TWO);
        pb.addLeft(Index.SIX);
        pb.addDown(Index.SIX);
        pb.addLeft(Index.TWO);
        pb.addUp(Index.SIX);
        pb.addLeft(Index.SIX);
        pb.addUp(Index.ONE);
        pb.addRight(Index.SEVEN);
        return List.copyOf(pb.getPath());
    }

    private List<Position> buildGreen() {
        final PathBuilder pb = new PathBuilder(Index.EIGHT, Index.ONE);
        pb.addDown(Index.FIVE);
        pb.addRight(Index.SIX);
        pb.addDown(Index.TWO);
        pb.addLeft(Index.SIX);
        pb.addDown(Index.SIX);
        pb.addLeft(Index.TWO);
        pb.addUp(Index.SIX);
        pb.addLeft(Index.SIX);
        pb.addUp(Index.TWO);
        pb.addRight(Index.SIX);
        pb.addUp(Index.SIX);
        pb.addRight(Index.ONE);
        pb.addDown(Index.SEVEN);
        return List.copyOf(pb.getPath());
    }

    private List<Position> buildYellow() {
        final PathBuilder pb = new PathBuilder(Index.THIRTEEN, Index.EIGHT);
        pb.addLeft(Index.FIVE);
        pb.addDown(Index.SIX);
        pb.addLeft(Index.TWO);
        pb.addUp(Index.SIX);
        pb.addLeft(Index.SIX);
        pb.addUp(Index.TWO);
        pb.addRight(Index.SIX);
        pb.addUp(Index.SIX);
        pb.addRight(Index.TWO);
        pb.addDown(Index.SIX);
        pb.addRight(Index.SIX);
        pb.addDown(Index.ONE);
        pb.addLeft(Index.SEVEN);
        return List.copyOf(pb.getPath());
    }
}
