package model;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import utility.Position;

/**
 * Generate the house positions once.
 */
public final class PlayerHome {

    private static Set<Position> bottomLeftHouse;
    private static Set<Position> topLeftHouse;
    private static Set<Position> topRightHouse;
    private static Set<Position> bottomRightHouse;

    /**
     * Index 2.
     */
    private static final int TWO = 2;
    /**
     * Index 3.
     */
    private static final int THREE = 3;
    /**
     * Index 11.
     */
    private static final int ELEVEN = 11;
    /**
     * Index 12.
     */
    private static final int TWELVE = 12;
    /**
     * Index six.
     */
    private static final int SIX = 6;
    /**
     * Index nine.
     */
    private static final int NINE = 9;
    /**
     * Cells number.
     */
    private static final int NCELLS = 15;


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
        BOTTOM_LEFT(new Position(TWO, ELEVEN), new Position(THREE, ELEVEN), 
                    new Position(TWO, TWELVE), new Position(THREE, TWELVE)),
        /**
         * Home positioned at top left corner.
         */
        TOP_LEFT(new Position(TWO, TWO), new Position(TWO, THREE), 
                 new Position(THREE, TWO), new Position(THREE, THREE)),
        /**
         * Home positioned at top right corner.
         */
        TOP_RIGHT(new Position(ELEVEN, TWO), new Position(TWELVE, TWO), 
                  new Position(ELEVEN, THREE), new Position(TWELVE, THREE)),
        /**
         * Home positioned at bottom right corner.
         */
        BOTTOM_RIGHT(new Position(ELEVEN, ELEVEN), new Position(TWELVE, ELEVEN), 
                     new Position(TWELVE, ELEVEN), new Position(TWELVE, TWELVE));

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
     * Return the set of positions of the player home at {@link HomePosition pos}.
     * 
     * @param pos the position
     * 
     * @return the set of positions of the player home
     */
    public static Set<Position> getPlayerHome(final HomePosition pos) {
        return getHome(pos);
    }

    /**
     * Returns the house at the specified position.
     * 
     * @param pos the position
     * 
     * @return the set of positions of the specified house
     */
    private static Set<Position> getHome(final HomePosition pos) {

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
                // error FIXME
        }

        return home;
    }

    /**
     * Returns the house at position {@link HomePosition#BOTTOM_LEFT}.
     * 
     * @return the house at bottom left position
     */
    private static Set<Position> createBottomLeftHouse() {

        return IntStream.range(NINE, NCELLS)
                .mapToObj(i -> i)
                .flatMap(i -> IntStream.range(0, SIX)
                        .mapToObj(j -> new Position(i, j)))
                .collect(Collectors.toCollection(() -> new HashSet<>()));
    }

    /**
     * Returns the house at position {@link HomePosition#TOP_LEFT}.
     * 
     * @return the house at top left position
     */
    private static Set<Position> createTopLeftHouse() {

        return IntStream.range(0, SIX)
                .mapToObj(i -> i)
                .flatMap(i -> IntStream.range(0, SIX)
                        .mapToObj(j -> new Position(i, j)))
                .collect(Collectors.toCollection(() -> new HashSet<>()));
    }

    /**
     * Returns the house at position {@link HomePosition#TOP_RIGHT}.
     * 
     * @return the house at top right position
     */
    private static Set<Position> createTopRightHouse() {

        return IntStream.range(0, SIX)
                .mapToObj(i -> i)
                .flatMap(i -> IntStream.range(NINE, NCELLS)
                        .mapToObj(j -> new Position(i, j)))
                .collect(Collectors.toCollection(() -> new HashSet<>()));
    }

    /**
     * Returns the house at position {@link HomePosition#BOTTOM_RIGHT}.
     * 
     * @return the house at bottom right position
     */
    private static Set<Position> createBottomRightHouse() {

        return IntStream.range(NINE, NCELLS)
                .mapToObj(i -> i)
                .flatMap(i -> IntStream.range(NINE, NCELLS)
                        .mapToObj(j -> new Position(i, j)))
                .collect(Collectors.toCollection(() -> new HashSet<>()));
    }

}
