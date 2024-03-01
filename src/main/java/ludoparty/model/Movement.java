package ludoparty.model;

import ludoparty.model.api.Game;
import ludoparty.model.api.Item;
import ludoparty.model.api.Pawn;
import ludoparty.utils.BColor;
import ludoparty.utils.Constants;
import ludoparty.utils.Index;

import java.util.List;

/**
 * This class handles the movement of the whole pawn system.
 */
public final class Movement {

    private static final List<List<Position>> PATH_COLORS = List.of(buildBlue(), buildRed(), buildGreen(),
            buildYellow());

    private Movement() {
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
    public static void movePawn(final Pawn pawn, final BColor color, final int diceResult, final Game game) {

        if (pawn.getPosition().equals(pawn.getStartPosition())) {
            if (diceResult == Index.SIX) {
                step(pawn, game, 0);
                eatenPawns(pawn, game);
            }
        } else {
            final int index = PATH_COLORS.get(color.ordinal()).indexOf(pawn.getPosition());
            if (index + diceResult < PATH_COLORS.get(color.ordinal()).size() && index + diceResult >= 0) {
                step(pawn, game, PATH_COLORS.get(color.ordinal()).indexOf(pawn.getPosition()) + diceResult);
                eatenPawns(pawn, game);
            }
        }
    }

    private static void step(final Pawn p, final Game g, final int index) {
        for (final var c : g.getBoardCells()) {
            if (c.getPosition().equals(p.getPosition())) {
                c.removePawn(p);
            }
        }

        p.setPosition(PATH_COLORS.get(p.getColor().ordinal()).get(index));

        for (final var c : g.getBoardCells()) {
            if (c.getPosition().equals(p.getPosition())) {
                c.addPawn(p);
            }
        }
    }

    private static void eatenPawns(final Pawn pawn, final Game game) {
        if (enemyIsAlone(pawn, game) && notSafe(pawn.getPosition(), game)
                && !pawn.getPosition().equals(new Position(Index.SEVEN, Index.SEVEN))
                && !isEnemyOnBastione(getEnemyPawn(pawn, game), game)) {
            homeStep(getEnemyPawn(pawn, game), game);
        }
    }

    private static Pawn getEnemyPawn(final Pawn pawn, final Game game) {
        for (int i = 0; i < game.getPlayers().size() * Constants.PLAYER_PAWNS; i++) {
            if (!pawn.getColor().equals(getPawn(i, game).getColor())
                    && getPawn(i, game).getPosition().equals(pawn.getPosition())) {
                return getPawn(i, game);
            }
        }
        return null;
    }

    private static void homeStep(final Pawn p, final Game game) {
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

    private static boolean enemyIsAlone(final Pawn pawn, final Game game) {

        int nEnemies = 0;

        for (int i = 0; i < game.getPlayers().size() * Constants.PLAYER_PAWNS; i++) {
            if (!pawn.getColor().equals(getPawn(i, game).getColor())
                    && getPawn(i, game).getPosition().equals(pawn.getPosition())) {
                nEnemies++;
            }
        }

        return nEnemies == 1;
    }

    private static boolean isEnemyOnBastione(final Pawn enemyPawn, final Game game) {
        for (int i = 0; i < game.getPlayers().size(); i++) {
            if (game.getPlayers().get(i).getPawns().contains(enemyPawn)
                    && game.getPlayers().get(i).getItemsApplied().contains(Item.BASTIONE)) {
                return true;
            }
        }

        return false;
    }

    private static boolean notSafe(final Position pos, final Game game) {
        for (final var c : game.getBoardCells()) {
            if (c.getPosition().equals(pos)) {
                return !c.isSafe();
            }
        }
        return false;
    }

    private static Pawn getPawn(final int k, final Game game) {
        return game.getPlayers().get(k / Constants.PLAYER_PAWNS).getPawns().get(k % Constants.PLAYER_PAWNS);
    }

    private static boolean noMultiple(final int index, final int diceResult, final Pawn pawn, final Game game) {

        int enemyCounter = 0;
        final int size = PATH_COLORS.get(pawn.getColor().ordinal()).size();

        if (index + diceResult < size
                && !notSafe(PATH_COLORS.get(pawn.getColor().ordinal()).get(index + diceResult), game)) {
            return true;
        } else {
            for (int i = 0; i < game.getPlayers().size() * Constants.PLAYER_PAWNS; i++) {
                final Pawn actualPawn = getPawn(i, game);
                if (!pawn.getColor().equals(actualPawn.getColor())
                        && PATH_COLORS.get(pawn.getColor().ordinal()).get(index + diceResult)
                                .equals(actualPawn.getPosition())) {
                    enemyCounter++;
                }
            }
        }

        return enemyCounter < 2;
    }

    /**
     * Checks in the next candidate position if there is more than one enemy pawn.
     * 
     * @param index
     * @param diceResult
     * @param pawn
     * @param game
     * @return the result
     */
    public static boolean noMultipleEnemyPawns(final int index, final int diceResult, final PawnImpl pawn,
            final Game game) {
        return noMultiple(index, diceResult, pawn, game);
    }

    /**
     * Returns if there is at least one enemy pawn in the same position of a player
     * pawn.
     * 
     * @param game the game
     * @return the result
     */
    public static boolean enemyPawnOntoPawn(final Game game) {
        for (int i = 0; i < Constants.PLAYER_PAWNS; i++) {
            for (int j = Constants.PLAYER_PAWNS; j < game.getPlayers().size() * Constants.PLAYER_PAWNS; j++) {
                if (getPawn(i, game).getPosition().equals(getPawn(j, game).getPosition())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Gets the paths that the pawns of the players should follow according to their
     * color.
     * 
     * @return the paths of the players
     */
    public static List<List<Position>> getPathColors() {
        return List.copyOf(Movement.PATH_COLORS);
    }

    private static List<Position> buildBlue() {
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

    private static List<Position> buildRed() {
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

    private static List<Position> buildGreen() {
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

    private static List<Position> buildYellow() {
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

    @Override
    public String toString() {
        return "Movement []";
    }
}
