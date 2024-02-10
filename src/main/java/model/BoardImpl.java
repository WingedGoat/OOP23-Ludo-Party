package model;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import model.PlayerHome.HomePosition;
import model.api.Board;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import utility.Constants;
import utility.Index;

/**
 * Game Board Builder.
 */
public final class BoardImpl implements Board {
    // private static final Logger LOGGER = LogManager.getRootLogger();
    // LOGGER.error("cells: " + cells.toString());

    private final Set<Position> cells;
    private final Set<Position> bottomLeftHouse;
    private final Set<Position> bottomLeftSafePath;

    private final Set<Position> topLeftHouse;
    private final Set<Position> topLeftSafePath;

    private final Set<Position> topRightHouse;
    private final Set<Position> topRightSafePath;

    private final Set<Position> bottomRightHouse;
    private final Set<Position> bottomRighSafePath;

    /**
     * Constructor.
     */
    public BoardImpl() {
        /*
         * this.cells = IntStream.range(0, Constants.CELLS_NUMBER)
         * .mapToObj(i -> i)
         * .flatMap(i -> IntStream.range(0, Constants.CELLS_NUMBER)
         * .mapToObj(j -> new Position(i, j)))
         * .collect(Collectors.toCollection(() -> new HashSet<>()));
         */
        this.bottomLeftHouse = createBottomLeftHouse();
        this.bottomLeftSafePath = createBottomLeftSafePath();

        this.topLeftHouse = createTopLeftHouse();
        this.topLeftSafePath = createTopLeftSafePath();

        this.topRightHouse = createTopRightHouse();
        this.topRightSafePath = createTopRightSafePath();

        this.bottomRightHouse = createBottomRightHouse();
        this.bottomRighSafePath = createBottomRightSafePath();

        this.cells = new HashSet<>();
    }

    // getters

    @Override
    public Set<Position> getBottomLeftHouse() {
        return Set.copyOf(this.bottomLeftHouse);
    }

    @Override
    public Set<Position> getBottomLeftSafePath() {
        return Set.copyOf(this.bottomLeftSafePath);
    }

    @Override
    public Set<Position> getTopLeftHouse() {
        return Set.copyOf(this.topLeftHouse);
    }

    @Override
    public Set<Position> getTopLeftSafePath() {
        return Set.copyOf(this.topLeftSafePath);
    }

    @Override
    public Set<Position> getTopRightHouse() {
        return Set.copyOf(this.topRightHouse);
    }

    @Override
    public Set<Position> getTopRightSafePath() {
        return Set.copyOf(this.topRightSafePath);
    }

    @Override
    public Set<Position> getBottomRightHouse() {
        return Set.copyOf(this.bottomRightHouse);
    }

    @Override
    public Set<Position> getBottomRighSafePath() {
        return Set.copyOf(this.bottomRighSafePath);
    }

    @Override
    public Set<Position> getCells() {
        return Set.copyOf(cells);
    }

    /**
     * Returns the positions of the house at {@link HomePosition#BOTTOM_LEFT}
     * corner.
     * 
     * @return the house positions at bottom left corner
     */
    private Set<Position> createBottomLeftHouse() {

        return IntStream.range(Index.NINE, Constants.CELLS_NUMBER)
                .mapToObj(i -> i)
                .flatMap(i -> IntStream.range(0, Index.SIX)
                        .mapToObj(j -> new Position(i, j)))
                .collect(Collectors.toCollection(() -> new HashSet<>()));
    }

    /**
     * Returns the positions of the house at {@link HomePosition#TOP_LEFT} corner.
     * 
     * @return the house positions at top left corner
     */
    private Set<Position> createTopLeftHouse() {

        return IntStream.range(0, Index.SIX)
                .mapToObj(i -> i)
                .flatMap(i -> IntStream.range(0, Index.SIX)
                        .mapToObj(j -> new Position(i, j)))
                .collect(Collectors.toCollection(() -> new HashSet<>()));
    }

    /**
     * Returns the positions of the house at {@link HomePosition#TOP_RIGHT} corner.
     * 
     * @return the house positions at top right corner
     */
    private Set<Position> createTopRightHouse() {

        return IntStream.range(0, Index.SIX)
                .mapToObj(i -> i)
                .flatMap(i -> IntStream.range(Index.NINE, Constants.CELLS_NUMBER)
                        .mapToObj(j -> new Position(i, j)))
                .collect(Collectors.toCollection(() -> new HashSet<>()));
    }

    /**
     * Returns the positions of the house at {@link HomePosition#BOTTOM_RIGHT}
     * corner.
     * 
     * @return the house positions at bottom right corner
     */
    private Set<Position> createBottomRightHouse() {

        return IntStream.range(Index.NINE, Constants.CELLS_NUMBER)
                .mapToObj(i -> i)
                .flatMap(i -> IntStream.range(Index.NINE, Constants.CELLS_NUMBER)
                        .mapToObj(j -> new Position(i, j)))
                .collect(Collectors.toCollection(() -> new HashSet<>()));
    }

    /**
     * Returns the safe path of the {@link HomePosition#BOTTOM_LEFT} player.
     * {@see The BLUE player}
     * 
     * @return the safe path of the bottom left player (the human)
     */
    private Set<Position> createBottomLeftSafePath() {

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
    private Set<Position> createTopLeftSafePath() {

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
    private Set<Position> createTopRightSafePath() {

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
    private Set<Position> createBottomRightSafePath() {

        final Set<Position> safePath = new HashSet<>();
        safePath.add(new Position(Index.EIGHT, Index.THIRTEEN));
        for (int j = Index.EIGHT; j <= Index.THIRTEEN; j++) {
            safePath.add(new Position(Index.SEVEN, j));
        }

        return safePath;
    }
/* 
    private Set<Position> createBottomLeftPawnsStartPosition() {
        final Set<Position> startPositions = new HashSet<>();
        startPositions.addAll(Set.of(
                new Position(Index.TWO, Index.ELEVEN),
                new Position(Index.THREE, Index.ELEVEN),
                new Position(Index.TWO, Index.TWELVE),
                new Position(Index.THREE, Index.TWELVE)));

        return startPositions;
    }

    private Set<Position> createTopLeftPawnsStartPosition() {
        final Set<Position> startPositions = new HashSet<>();
        startPositions.addAll(Set.of(
                new Position(Index.TWO, Index.TWO),
                new Position(Index.TWO, Index.THREE),
                new Position(Index.THREE, Index.TWO),
                new Position(Index.THREE, Index.THREE)));

        return startPositions;
    }

    private Set<Position> createTopRightPawnsStartPosition() {
        final Set<Position> startPositions = new HashSet<>();
        startPositions.addAll(Set.of(
                new Position(Index.ELEVEN, Index.TWO),
                new Position(Index.TWELVE, Index.TWO),
                new Position(Index.ELEVEN, Index.THREE),
                new Position(Index.TWELVE, Index.THREE)));

        return startPositions;
    }

    private Set<Position> createBottomRightPawnsPosition() {
        final Set<Position> startPositions = new HashSet<>();
        startPositions.addAll(Set.of(
                new Position(Index.ELEVEN, Index.ELEVEN),
                new Position(Index.TWELVE, Index.ELEVEN),
                new Position(Index.ELEVEN, Index.TWELVE),
                new Position(Index.TWELVE, Index.TWELVE)));

        return startPositions;
    }
*/
}
