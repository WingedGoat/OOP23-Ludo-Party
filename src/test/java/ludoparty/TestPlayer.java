package ludoparty;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import ludoparty.model.PlayerImpl;
import ludoparty.model.api.Player;
import ludoparty.model.Position;
import ludoparty.model.api.Cell.CellType;
import ludoparty.model.api.Player.PlayerType;
import ludoparty.utils.BColor;
import ludoparty.utils.Index;

/**
 * Player tests.
 * @author icolavita
 */
class TestPlayer {

    private Player human;
    private Player p1;

    private static final int COINS_0 = 0;

    @BeforeEach void init() {
        final List<Position> pawnsStartPositionsHuman = List.of(
                new Position(Index.ONE, Index.TEN),
                new Position(Index.ONE, Index.THIRTEEN),
                new Position(Index.FOUR, Index.TEN),
                new Position(Index.FOUR, Index.THIRTEEN)
        );

        final List<Position> pawnStartPositionsComputer =  List.of(
                new Position(Index.ONE, Index.TEN),
                new Position(Index.ONE, Index.THIRTEEN),
                new Position(Index.FOUR, Index.TEN),
                new Position(Index.FOUR, Index.THIRTEEN)
        );

        human = new PlayerImpl("John", PlayerType.HUMAN, 
            BColor.BLUE, CellType.BOTTOM_LEFT_HOUSE, pawnsStartPositionsHuman);

        p1 = new PlayerImpl("Player2", PlayerType.COMPUTER,
            BColor.GREEN, CellType.TOP_RIGHT_HOUSE, pawnStartPositionsComputer);
    }

    @Test void testPlayerColors() {
        assertEquals(BColor.BLUE, human.getColor());
        assertEquals(BColor.GREEN, p1.getColor());
    }

    @Test void testPlayerCoin() {
        assertEquals(COINS_0, human.getCoins());
        assertEquals(COINS_0, p1.getCoins());
    }

    @Test void testHasPawns() {
        assertNotEquals(Index.ZERO, human.getPawns().size());
        assertEquals(Index.FOUR, p1.getPawns().size());
    }

    @Test void testPawnStartPositions() {
        assertNotNull(human.getPawns().get(0));
        assertNotEquals(new Position(0, 0), human.getPawns().get(0).getPosition());
    }

}
