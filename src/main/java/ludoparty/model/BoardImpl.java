package ludoparty.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import ludoparty.model.api.Board;
import ludoparty.model.api.Cell;
import ludoparty.model.api.Cell.CellType;
import ludoparty.utils.Constants;
import ludoparty.utils.Index;

/**
 * Game Board Implementation.
 * Creates the model of the board, setting each cell type.
 */
public final class BoardImpl implements Board {

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
    private Cell endCell;

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

                final Position endCellPosition = new Position(Index.SEVEN, Index.SEVEN);
                Cell cell;
                if (this.bottomLeftHouse.contains(pos)) {
                    cell = new CellImpl(pos, CellType.BOTTOM_LEFT_HOUSE);
                } else if (this.topLeftHouse.contains(pos)) {
                    cell = new CellImpl(pos, CellType.TOP_LEFT_HOUSE);
                } else if (this.topRightHouse.contains(pos)) {
                    cell = new CellImpl(pos, CellType.TOP_RIGHT_HOUSE);
                } else if (this.bottomRightHouse.contains(pos)) {
                    cell = new CellImpl(pos, CellType.BOTTOM_RIGHT_HOUSE);
                } else if (this.bottomLeftSafePath.contains(pos)) {
                    cell = new CellImpl(pos, true, CellType.BOTTOM_LEFT_SAFE_PATH);
                } else if (this.topLeftSafePath.contains(pos)) {
                    cell = new CellImpl(pos, true, CellType.TOP_LEFT_SAFE_PATH);
                } else if (this.topRightSafePath.contains(pos)) {
                    cell = new CellImpl(pos, true, CellType.TOP_RIGHT_SAFE_PATH);
                } else if (this.bottomRighSafePath.contains(pos)) {
                    cell = new CellImpl(pos, true, CellType.BOTTOM_RIGHT_SAFE_PATH);
                } else if (endCellPosition.equals(pos)) {
                    cell = new CellImpl(pos, true, CellType.END_CELL);
                    endCell = cell;
                } else if (this.shops.contains(pos)) {
                    cell = new CellImpl(pos, false, true, false, CellType.WHITE_CELL);
                } else {
                    cell = new CellImpl(pos, false, false, false, CellType.WHITE_CELL);
                }

                this.cells.add(cell);
            }
        }
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
    public Cell getEndCell() {
        return endCell;
    }

    @Override
    public Set<Cell> getCells() {
        return Set.copyOf(this.cells);
    }

    // create methods

    private Set<Position> createHouse(final int startRow, final int endRow, final int startCol, final int endCol) {
        return IntStream.range(startRow, endRow)
                .mapToObj(i -> i)
                .flatMap(i -> IntStream.range(startCol, endCol)
                        .mapToObj(j -> new Position(i, j)))
                .collect(Collectors.toCollection(() -> new HashSet<>()));
    }

    /**
     * Creates the positions of the house at bottom left corner.
     * 
     * @return the house positions at bottom left corner
     */
    private Set<Position> createBottomLeftHouse() {
        return createHouse(Index.ZERO, Index.SIX, Index.NINE, Index.FIFTEEN);
    }

    /**
     * Creates the positions of the house at top left corner.
     * 
     * @return the house positions at top left corner
     */
    private Set<Position> createTopLeftHouse() {
        return createHouse(Index.ZERO, Index.SIX, Index.ZERO, Index.SIX);
    }

    /**
     * Creates the positions of the house at top right corner.
     * 
     * @return the house positions at top right corner
     */
    private Set<Position> createTopRightHouse() {
        return createHouse(Index.NINE, Index.FIFTEEN, Index.ZERO, Index.SIX);
    }

    /**
     * Creates the positions of the house at bottom right corner.
     * 
     * @return the house positions at bottom right corner
     */
    private Set<Position> createBottomRightHouse() {
        return createHouse(Index.NINE, Index.FIFTEEN, Index.NINE, Index.FIFTEEN);
    }

    /**
     * Creates the safe path of the player at bottom left corner.
     * 
     * @return the safe path of the bottom left player (the human)
     */
    private Set<Position> createBottomLeftSafePath() {

        final Set<Position> safePath = new HashSet<>();
        safePath.add(new Position(Index.SIX, Index.THIRTEEN));
        for (int j = Index.EIGHT; j <= Index.THIRTEEN; j++) {
            safePath.add(new Position(Index.SEVEN, j));
        }

        return safePath;
    }

    /**
     * Creates the safe path of the player at top left corner.
     * 
     * @return the safe path of the top left player
     */
    private Set<Position> createTopLeftSafePath() {

        final Set<Position> safePath = new HashSet<>();
        safePath.add(new Position(Index.ONE, Index.SIX));
        for (int i = Index.ONE; i <= Index.SIX; i++) {
            safePath.add(new Position(i, Index.SEVEN));
        }

        return safePath;
    }

    /**
     * Creates the safe path of the player at top right corner.
     * 
     * @return the safe path of the top right player
     */
    private Set<Position> createTopRightSafePath() {

        final Set<Position> safePath = new HashSet<>();
        safePath.add(new Position(Index.EIGHT, Index.ONE));
        for (int j = Index.ONE; j <= Index.SIX; j++) {
            safePath.add(new Position(Index.SEVEN, j));
        }

        return safePath;
    }

    /**
     * Creates the safe path of the player at bottom right corner.
     * 
     * @return the safe path of the bottom right player
     */
    private Set<Position> createBottomRightSafePath() {

        final Set<Position> safePath = new HashSet<>();
        safePath.add(new Position(Index.THIRTEEN, Index.EIGHT));
        for (int i = Index.EIGHT; i <= Index.THIRTEEN; i++) {
            safePath.add(new Position(i, Index.SEVEN));
        }

        return safePath;
    }

    private Set<Position> createShops() {
        final Set<Position> sh = new HashSet<>();
        sh.add(new Position(Index.TWO, Index.EIGHT));
        sh.add(new Position(Index.SIX, Index.TWO));
        sh.add(new Position(Index.TWELVE, Index.SIX));
        sh.add(new Position(Index.EIGHT, Index.TWELVE));

        return sh;
    }

    // pawns start positions

    private List<Position> createBottomLeftPawnsStartPosition() {
        final List<Position> startPositions = new ArrayList<>();
        startPositions.addAll(Set.of(
                new Position(Index.ONE, Index.TEN),
                new Position(Index.ONE, Index.THIRTEEN),
                new Position(Index.FOUR, Index.TEN),
                new Position(Index.FOUR, Index.THIRTEEN)));

        return startPositions;
    }

    private List<Position> createTopLeftPawnsStartPosition() {
        final List<Position> startPositions = new ArrayList<>();
        startPositions.addAll(Set.of(
                new Position(Index.ONE, Index.ONE),
                new Position(Index.ONE, Index.FOUR),
                new Position(Index.FOUR, Index.ONE),
                new Position(Index.FOUR, Index.FOUR)));

        return startPositions;
    }

    private List<Position> createTopRightPawnsStartPosition() {
        final List<Position> startPositions = new ArrayList<>();
        startPositions.addAll(Set.of(
                new Position(Index.TEN, Index.ONE),
                new Position(Index.TEN, Index.FOUR),
                new Position(Index.THIRTEEN, Index.ONE),
                new Position(Index.THIRTEEN, Index.FOUR)));

        return startPositions;
    }

    private List<Position> createBottomRightPawnsPosition() {
        final List<Position> startPositions = new ArrayList<>();
        startPositions.addAll(Set.of(
                new Position(Index.TEN, Index.TEN),
                new Position(Index.TEN, Index.THIRTEEN),
                new Position(Index.THIRTEEN, Index.TEN),
                new Position(Index.THIRTEEN, Index.THIRTEEN)));

        return startPositions;
    }

}
