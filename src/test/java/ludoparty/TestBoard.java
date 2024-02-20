package ludoparty;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ludoparty.model.api.Board;
import ludoparty.model.api.Cell.CellType;
import ludoparty.model.BoardImpl;
import ludoparty.model.Position;
import ludoparty.utils.Constants;
import ludoparty.utils.Index;

/**
 * Board tests.
 * @author icolavita
 */
class TestBoard {

    private Board board;

    @BeforeEach void init() {
        board = new BoardImpl();
    }

    @Test void testBoardSize() {
        assertEquals(Constants.CELLS_NUMBER * Constants.CELLS_NUMBER, board.getCells().size());
        assertNotEquals(0, board.getShops().size());
    }

    @Test void testEndCell() {
        assertEquals(CellType.END_CELL, board.getEndCell().getType());
    }

    @Test void testShopPositions() {
        assertTrue(board.getShops().contains(new Position(Index.TWO, Index.EIGHT)));
        assertTrue(board.getShops().contains(new Position(Index.SIX, Index.TWO)));
        assertTrue(board.getShops().contains(new Position(Index.TWELVE, Index.SIX)));
        assertTrue(board.getShops().contains(new Position(Index.EIGHT, Index.TWELVE)));
    }

}
