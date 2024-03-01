package ludoparty;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ludoparty.model.api.Cell.CellType;
import ludoparty.model.CellImpl;
import ludoparty.model.PawnImpl;
import ludoparty.model.Position;
import ludoparty.utils.BColor;
import ludoparty.utils.Index;

/**
 * Cell tests.
 * 
 * @author Boshiba
 */
class TestCell {

    private CellImpl cell;
    private PawnImpl pawn;
    private PawnImpl enemyPawn;
    private CellImpl safeCell;

    @BeforeEach
    void init() {
        cell = new CellImpl(new Position(Index.ZERO, Index.ZERO), CellType.WHITE_CELL);
        pawn = new PawnImpl(new Position(Index.FOUR, Index.THIRTEEN), CellType.BOTTOM_LEFT_HOUSE, BColor.BLUE);
        enemyPawn = new PawnImpl(new Position(Index.THIRTEEN, Index.ONE), CellType.TOP_RIGHT_HOUSE, BColor.GREEN);
        safeCell = new CellImpl(new Position(Index.SEVEN, Index.SIX), true, CellType.TOP_RIGHT_SAFE_PATH);
    }

    @Test
    void testAddPawn() {
        cell.addPawn(pawn);
        assertEquals(Index.ONE, cell.getPawns().size());
        assertEquals(pawn, cell.getPawns().get(0));
    }

    @Test
    void testRemovePawn() {
        cell.addPawn(pawn);
        cell.addPawn(enemyPawn);
        cell.removePawn(pawn);
        assertEquals(Index.ONE, cell.getPawns().size());
        assertEquals(BColor.GREEN, cell.getPawns().get(Index.ZERO).getColor());
    }

    @Test
    void testIsNotSafe() {
        assertFalse(cell.isSafe());
    }

    @Test
    void testIsSafe() {
        assertTrue(safeCell.isSafe());
    }

}
