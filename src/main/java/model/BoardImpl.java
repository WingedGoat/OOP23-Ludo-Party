package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import model.api.Board;
import model.api.Cell;
import model.api.Cell.Type;

import utility.Constants;
import utility.Index;

/**
 * Game Board Builder.
 */
public final class BoardImpl implements Board {
    //private static final Logger LOGGER = LogManager.getRootLogger();

    private final Set<Cell> cells = new HashSet<>();

    private final Set<Position> bottomLeftHouse;
    private final Set<Position> bottomLeftSafePath;
    private final List<Position> bottomLeftPawnsStartPos;

    private final Set<Position> topLeftHouse;
    private final Set<Position> topLeftSafePath;
    private final List<Position> topLeftPawnsStartPos;

    private final Set<Position> topRightHouse;
    private final Set<Position> topRightSafePath;
    private final List<Position> topRightPawnsStartPos;

    private final Set<Position> bottomRightHouse;
    private final Set<Position> bottomRighSafePath;
    private final List<Position> bottomRightPawnsStartPos;

    private final Set<Position> shops;

    /**
     * Constructor.
     */
    public BoardImpl() {

        this.bottomLeftHouse = createBottomLeftHouse();
        this.bottomLeftSafePath = createBottomLeftSafePath();
        this.bottomLeftPawnsStartPos = createBottomLeftPawnsStartPosition();

        this.topLeftHouse = createTopLeftHouse();
        this.topLeftSafePath = createTopLeftSafePath();
        this.topLeftPawnsStartPos = createTopLeftPawnsStartPosition();

        this.topRightHouse = createTopRightHouse();
        this.topRightSafePath = createTopRightSafePath();
        this.topRightPawnsStartPos = createTopRightPawnsStartPosition();

        this.bottomRightHouse = createBottomRightHouse();
        this.bottomRighSafePath = createBottomRightSafePath();
        this.bottomRightPawnsStartPos = createBottomRightPawnsPosition();

        this.shops = createShops();

        fillBoard();
    }

    private void fillBoard() {
        for (int i = 0; i < Constants.CELLS_NUMBER; i++) {
            for (int j = 0; j < Constants.CELLS_NUMBER; j++) {
                final Position pos = new Position(i, j);

                Cell cell;
                if (this.bottomLeftHouse.contains(pos)) {
                    cell = new CellImpl(pos, Type.BOTTOM_LEFT_HOUSE);
                } else if (this.topLeftHouse.contains(pos)) {
                    cell = new CellImpl(pos, Type.TOP_LEFT_HOUSE);
                } else if (this.topRightHouse.contains(pos)) {
                    cell = new CellImpl(pos, Type.TOP_RIGHT_HOUSE);
                } else if (this.bottomRightHouse.contains(pos)) {
                    cell = new CellImpl(pos, Type.BOTTOM_RIGHT_HOUSE);
                } else if (this.bottomLeftSafePath.contains(pos)) {
                    cell = new CellImpl(pos, true, Type.BOTTOM_LEFT_SAFE_PATH);
                } else if (this.topLeftSafePath.contains(pos)) {
                    cell = new CellImpl(pos, true, Type.TOP_LEFT_SAFE_PATH);
                } else if (this.topRightSafePath.contains(pos)) {
                    cell = new CellImpl(pos, true, Type.TOP_RIGHT_SAFE_PATH);
                } else if (this.bottomRighSafePath.contains(pos)) {
                    cell = new CellImpl(pos, true, Type.BOTTOM_RIGHT_SAFE_PATH);
                } else if (this.shops.contains(pos)) {
                    cell = new CellImpl(pos, false, true, false, Type.WHITE_PATH);
                } else {
                    cell = new CellImpl(pos, false, false, false, Type.WHITE_PATH);
                }

                this.cells.add(cell);
            }
        }
    }

    // getters

    @Override
    public Set<Position> getBottomLeftHouse() {
        final var set = new HashSet<Position>();
        set.addAll(this.bottomLeftHouse);
        return set;
    }

    @Override
    public Set<Position> getBottomLeftSafePath() {
        final var set = new HashSet<Position>();
        set.addAll(this.bottomLeftSafePath);
        return set;
    }

    @Override
    public Set<Position> getTopLeftHouse() {
        final var set = new HashSet<Position>();
        set.addAll(this.topLeftHouse);
        return set;
    }

    @Override
    public Set<Position> getTopLeftSafePath() {
        final var set = new HashSet<Position>();
        set.addAll(this.topLeftSafePath);
        return set;
    }

    @Override
    public Set<Position> getTopRightHouse() {
        final var set = new HashSet<Position>();
        set.addAll(this.topRightHouse);
        return set;
    }

    @Override
    public Set<Position> getTopRightSafePath() {
        final var set = new HashSet<Position>();
        set.addAll(this.topRightSafePath);
        return set;
    }

    @Override
    public Set<Position> getBottomRightHouse() {
        final var set = new HashSet<Position>();
        set.addAll(this.bottomRightHouse);
        return set;
    }

    @Override
    public Set<Position> getBottomRighSafePath() {
        final var set = new HashSet<Position>();
        set.addAll(this.bottomRighSafePath);
        return set;
    }

    @Override
    public List<Position> getBottomLeftPawnsStartPos() {
        return List.copyOf(this.bottomLeftPawnsStartPos);
    }

    @Override
    public List<Position> getTopLeftPawnsStartPos() {
        return List.copyOf(this.topLeftPawnsStartPos);
    }

    @Override
    public List<Position> getTopRightPawnsStartPos() {
        return List.copyOf(this.topRightPawnsStartPos);
    }

    @Override
    public List<Position> getBottomRightPawnsStartPos() {
        return List.copyOf(this.bottomRightPawnsStartPos);
    }

    @Override
    public Set<Position> getShops() {
        return Set.copyOf(this.shops);
    }

    @Override
    public Set<Cell> getCells() {
        return Set.copyOf(this.cells);
    }

    // create methods

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

    private Set<Position> createShops() {
        final Set<Position> sh = new HashSet<>();
        sh.add(new Position(Index.EIGHT, 0));
        sh.add(new Position(0, Index.SIX));
        sh.add(new Position(Index.SIX, Index.FOURTEEN));
        sh.add(new Position(Index.FOURTEEN, Index.EIGHT));

        return sh;
    }
 
    // pawns start positions

    private List<Position> createBottomLeftPawnsStartPosition() {
        final List<Position> startPositions = new ArrayList<>();
        startPositions.addAll(Set.of(
                new Position(Index.TWO, Index.ELEVEN),
                new Position(Index.THREE, Index.ELEVEN),
                new Position(Index.TWO, Index.TWELVE),
                new Position(Index.THREE, Index.TWELVE)));

        return startPositions;
    }

    private List<Position> createTopLeftPawnsStartPosition() {
        final List<Position> startPositions = new ArrayList<>();
        startPositions.addAll(Set.of(
                new Position(Index.TWO, Index.TWO),
                new Position(Index.TWO, Index.THREE),
                new Position(Index.THREE, Index.TWO),
                new Position(Index.THREE, Index.THREE)));

        return startPositions;
    }

    private List<Position> createTopRightPawnsStartPosition() {
        final List<Position> startPositions = new ArrayList<>();
        startPositions.addAll(Set.of(
                new Position(Index.ELEVEN, Index.TWO),
                new Position(Index.TWELVE, Index.TWO),
                new Position(Index.ELEVEN, Index.THREE),
                new Position(Index.TWELVE, Index.THREE)));

        return startPositions;
    }

    private List<Position> createBottomRightPawnsPosition() {
        final List<Position> startPositions = new ArrayList<>();
        startPositions.addAll(Set.of(
                new Position(Index.ELEVEN, Index.ELEVEN),
                new Position(Index.TWELVE, Index.ELEVEN),
                new Position(Index.ELEVEN, Index.TWELVE),
                new Position(Index.TWELVE, Index.TWELVE)));

        return startPositions;
    }

}