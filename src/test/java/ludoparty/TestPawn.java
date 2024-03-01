package ludoparty;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ludoparty.model.GameImpl;
import ludoparty.model.PawnImpl;
import ludoparty.model.Position;
import ludoparty.model.api.Game;
import ludoparty.model.api.Cell.CellType;
import ludoparty.utils.BColor;
import ludoparty.utils.Constants;
import ludoparty.utils.Index;

/**
 * Pawn tests.
 * 
 * @author Boshiba
 */
class TestPawn {

    private Game game;
    private PawnImpl pawn;
    private PawnImpl enemyPawn;
    private PawnImpl friendlyPawn;

    @BeforeEach
    void init() {
        game = new GameImpl("Boshiba", Constants.PLAYERS_NUM_4);
        pawn = new PawnImpl(new Position(Index.FOUR, Index.THIRTEEN), CellType.BOTTOM_LEFT_HOUSE, BColor.BLUE);
        enemyPawn = new PawnImpl(new Position(Index.THIRTEEN, Index.ONE), CellType.TOP_RIGHT_HOUSE, BColor.GREEN);
        friendlyPawn = new PawnImpl(new Position(Index.ONE, Index.TEN), CellType.BOTTOM_LEFT_HOUSE, BColor.BLUE);
    }

    @Test
    void testPawnColor() {
        assertEquals(BColor.BLUE, pawn.getColor());
        assertNotEquals(BColor.RED, pawn.getColor());
    }

    @Test
    void testPawnImpossibleMovement() {
        pawn.move(Index.FOUR, game);
        assertEquals(new Position(Index.FOUR, Index.THIRTEEN), pawn.getPosition());
        pawn.move(Index.SIX, game);
        pawn.move(Index.FIVEHUNDRED, game);
        assertEquals(new Position(Index.SIX, Index.THIRTEEN), pawn.getPosition());
    }

    @Test
    void testPawnEating() {
        pawn.move(Index.SIX, game);
        pawn.move(Index.ONE, game);
        enemyPawn.move(Index.SIX, game);
        enemyPawn.move(Index.FIVE, game);
        enemyPawn.move(Index.SIX, game);
        enemyPawn.move(Index.TWO, game);
        enemyPawn.move(Index.SIX, game);
        enemyPawn.move(Index.SIX, game);
        enemyPawn.move(Index.TWO, game);
        enemyPawn.move(Index.TWO, game);
        assertEquals(new Position(Index.SIX, Index.TWELVE), pawn.getPosition());
    }

    @Test
    void testPawnNotEatenInSafeCell() {
        pawn.setPosition(new Position(Index.SIX, Index.THIRTEEN));
        enemyPawn.move(Index.SIX, game);
        enemyPawn.move(Index.FIVE, game);
        enemyPawn.move(Index.SIX, game);
        enemyPawn.move(Index.TWO, game);
        enemyPawn.move(Index.SIX, game);
        enemyPawn.move(Index.SIX, game);
        enemyPawn.move(Index.TWO, game);
        enemyPawn.move(Index.ONE, game);
        assertEquals(new Position(Index.SIX, Index.THIRTEEN), pawn.getPosition());
    }

    @Test
    void testSamePlayerPawnsNotEaten() {
        pawn.setPosition(pawn.getStartPosition());
        pawn.move(Index.SIX, game);
        pawn.move(Index.ONE, game);
        friendlyPawn.move(Index.SIX, game);
        friendlyPawn.move(Index.ONE, game);
        assertEquals(new Position(Index.SIX, Index.TWELVE), friendlyPawn.getPosition());
        assertEquals(new Position(Index.SIX, Index.TWELVE), pawn.getPosition());
    }

}
