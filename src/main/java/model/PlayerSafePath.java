package model;

import utility.Index;
import java.util.Set;
import java.util.HashSet;

import model.PlayerHome.HomePosition;

/**
 * Player safe path.
 */
public final class PlayerSafePath {

    private static Set<Position> bottomLeftPath;
    private static Set<Position> topLeftPath;
    private static Set<Position> topRightPath;
    private static Set<Position> bottomRightPath;

    static {
        bottomLeftPath = createBottomLeftSafePath();
        topLeftPath = createTopLeftSafePath(); 
        topRightPath = createTopRightSafePath(); 
        bottomRightPath = createBottomRightSafePath(); 
    }

    private PlayerSafePath() { }

    /**
     * Returns the safe path of the player at the specified position.
     * 
     * @param pos the position
     * 
     * @return the set of positions of the specified safe path
     */
    public static Set<Position> getPath(final HomePosition pos) {

        Set<Position> safePath = null;
        switch (pos) {
            case BOTTOM_LEFT:
                safePath = bottomLeftPath;
                break;
            case TOP_LEFT:
                safePath = topLeftPath;
                break;
            case TOP_RIGHT:
                safePath = topRightPath;
                break;
            case BOTTOM_RIGHT:
                safePath = bottomRightPath;
                break;
            default:
                // error
        }

        return safePath;
    }

    /**
     * Returns the safe path of the {@link HomePosition#BOTTOM_LEFT} player.
     * {@see The BLUE player}
     * 
     * @return the safe path of the bottom left player (the human)
     */
    private static Set<Position> createBottomLeftSafePath() {

        final Set<Position> safePath = new HashSet<>();
        safePath.add(new Position(Index.THIRTEEN, Index.SIX));
        for (int i = Index.EIGHT; i <= Index.THIRTEEN; i++) {
            safePath.add(new Position(i, Index.SEVEN));
        }

        return safePath;
    }

    /**
     * Returns the safe path of the {@link HomePosition#TOP_LEFT} player.
     * {@see The RED player}
     * 
     * @return the safe path of the top left player
     */
    private static Set<Position> createTopLeftSafePath() {

        final Set<Position> safePath = new HashSet<>();
        safePath.add(new Position(Index.SIX, Index.ONE));
        for (int j = Index.ONE; j <= Index.SIX; j++) {
            safePath.add(new Position(Index.SEVEN, j));
        }

        return safePath;
    }

    /**
     * Returns the safe path of the {@link HomePosition#TOP_RIGHT} player.
     * {@see The GREEN player}
     * 
     * @return the safe path of the top right player
     */
    private static Set<Position> createTopRightSafePath() {

        final Set<Position> safePath = new HashSet<>();
        safePath.add(new Position(Index.ONE, Index.EIGHT));
        for (int i = Index.ONE; i <= Index.SIX; i++) {
            safePath.add(new Position(i, Index.SEVEN));
        }

        return safePath;
    }

    /**
     * Returns the safe path of the {@link HomePosition#BOTTOM_RIGHT} player.
     * {@see The YELLOW player}
     * 
     * @return the safe path of the bottom right player
     */
    private static Set<Position> createBottomRightSafePath() {

        final Set<Position> safePath = new HashSet<>();
        safePath.add(new Position(Index.EIGHT, Index.THIRTEEN));
        for (int j = Index.EIGHT; j <= Index.THIRTEEN; j++) {
            safePath.add(new Position(Index.SEVEN, j));
        }

        return safePath;
    }

}
