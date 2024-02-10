package model;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import utility.Index;

/**
 * Generate the house positions once.
 */

public final class PlayerHome {

    /**
     * Cells number.
     */
    private static final int NCELLS = 15;

    private static Set<Position> bottomLeftHouse;
    private static Set<Position> topLeftHouse;
    private static Set<Position> topRightHouse;
    private static Set<Position> bottomRightHouse;

    static {
        bottomLeftHouse = createBottomLeftHouse();
        topLeftHouse = createTopLeftHouse();
        topRightHouse = createTopRightHouse();
        bottomRightHouse = createBottomRightHouse();
    }

    /**
     * Home Position.
     *
     * {@link #BOTTOM_LEFT},
     * {@link #TOP_LEFT},
     * {@link #TOP_RIGHT},
     * {@link #BOTTOM_RIGHT}
     */
    public enum HomePosition {
        /**
         * Home positioned at bottom left corner.
         */
        BOTTOM_LEFT(new Position(Index.TWO, Index.ELEVEN), new Position(Index.THREE, Index.ELEVEN), 
                    new Position(Index.TWO, Index.TWELVE), new Position(Index.THREE, Index.TWELVE)),
        /**
         * Home positioned at top left corner.
         */
        TOP_LEFT(new Position(Index.TWO, Index.TWO), new Position(Index.TWO, Index.THREE), 
                 new Position(Index.THREE, Index.TWO), new Position(Index.THREE, Index.THREE)),
        /**
         * Home positioned at top right corner.
         */
        TOP_RIGHT(new Position(Index.ELEVEN, Index.TWO), new Position(Index.TWELVE, Index.TWO), 
                  new Position(Index.ELEVEN, Index.THREE), new Position(Index.TWELVE, Index.THREE)),
        /**
         * Home positioned at bottom right corner.
         */
        BOTTOM_RIGHT(new Position(Index.ELEVEN, Index.ELEVEN), new Position(Index.TWELVE, Index.ELEVEN), 
                     new Position(Index.ELEVEN, Index.TWELVE), new Position(Index.TWELVE, Index.TWELVE));

        private List<Position> pawnPositions = new ArrayList<>();

        HomePosition(final Position p1, final Position p2, final Position p3, final Position p4) {
            pawnPositions.addAll(List.of(p1, p2, p3, p4));
        }

        /**
         * Returns the start position of the pawns in their initial home.
         * @return the list of the pawn start positions
         */
        public List<Position> getPawnPositions() {
            return List.copyOf(pawnPositions);
        }

    }

    private PlayerHome() { }

    /**
     * Returns the house at the specified position.
     * 
     * @param pos the position
     * 
     * @return the set of positions of the specified house
     */
    public static Set<Position> getPlayerHome(final HomePosition pos) {

        Set<Position> home = null;
        switch (pos) {
            case BOTTOM_LEFT:
                home = bottomLeftHouse;
                break;
            case TOP_LEFT:
                home = topLeftHouse;
                break;
            case TOP_RIGHT:
                home = topRightHouse;
                break;
            case BOTTOM_RIGHT:
                home = bottomRightHouse;
                break;
            default:
                // error
        }

        return home;
    }

    /**
     * Returns the house at position {@link HomePosition#BOTTOM_LEFT}.
     * 
     * @return the house at bottom left position
     */
    private static Set<Position> createBottomLeftHouse() {

        return IntStream.range(Index.NINE, NCELLS)
                .mapToObj(i -> i)
                .flatMap(i -> IntStream.range(0, Index.SIX)
                        .mapToObj(j -> new Position(i, j)))
                .collect(Collectors.toCollection(() -> new HashSet<>()));
    }

    /**
     * Returns the house at position {@link HomePosition#TOP_LEFT}.
     * 
     * @return the house at top left position
     */
    private static Set<Position> createTopLeftHouse() {

        return IntStream.range(0, Index.SIX)
                .mapToObj(i -> i)
                .flatMap(i -> IntStream.range(0, Index.SIX)
                        .mapToObj(j -> new Position(i, j)))
                .collect(Collectors.toCollection(() -> new HashSet<>()));
    }

    /**
     * Returns the house at position {@link HomePosition#TOP_RIGHT}.
     * 
     * @return the house at top right position
     */
    private static Set<Position> createTopRightHouse() {

        return IntStream.range(0, Index.SIX)
                .mapToObj(i -> i)
                .flatMap(i -> IntStream.range(Index.NINE, NCELLS)
                        .mapToObj(j -> new Position(i, j)))
                .collect(Collectors.toCollection(() -> new HashSet<>()));
    }

    /**
     * Returns the house at position {@link HomePosition#BOTTOM_RIGHT}.
     * 
     * @return the house at bottom right position
     */
    private static Set<Position> createBottomRightHouse() {

        return IntStream.range(Index.NINE, NCELLS)
                .mapToObj(i -> i)
                .flatMap(i -> IntStream.range(Index.NINE, NCELLS)
                        .mapToObj(j -> new Position(i, j)))
                .collect(Collectors.toCollection(() -> new HashSet<>()));
    }

}
