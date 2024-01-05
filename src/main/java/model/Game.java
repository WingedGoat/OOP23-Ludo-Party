package model;

import java.util.ArrayList;
import java.util.List;
import model.api.Player;
import model.api.Player.PlayerType;
import utility.Position;

/**
 * 
 */
public class Game {

    /**
     * Represent the game result.
     */
    enum Result {
        /**
         * Win.
         */
        WIN,
        /**
         * Continue.
         */
        CONTINUE;
    }

    private static final int PAWN_NUMBER = 4;
    private static final int TWO = 2;
    private static final int ELEVEN = 11;

    private final int playersNumber;
    private List<Player> players;

    /**
     * Constructor.
     * @param playerName
     *          the player name
     * @param playersNumber
     *          the number of players
     */
    public Game(final String playerName, final int playersNumber) {
        final Player h = new PlayerImpl(playerName, PlayerType.HUMAN, 
            Color.BLUE, new Position(ELEVEN, TWO));
        final Player p1 = new PlayerImpl(playerName, PlayerType.COMPUTER, 
            Color.YELLOW, new Position(0, 0));
        players = new ArrayList<>();
        players.add(h);
        players.add(p1);
        if (playersNumber > players.size()) {
            final Player p2 = new PlayerImpl(playerName, PlayerType.COMPUTER, 
                Color.GREEN, new Position(0, 0));
            final Player p3 = new PlayerImpl(playerName, PlayerType.COMPUTER, 
                Color.RED, new Position(0, 0));
            players.add(p2);
            players.add(p3);
        }
        this.playersNumber = playersNumber;
    }

    /**
     * Gets the pawn number for each player.
     * @return PAWN_NUMBER
     */
    public static int getPawnNumber() {
        return PAWN_NUMBER;
    }

    /**
     * Return the number of players.
     * @return the players number.
     */
    public int getPlayersNumber() {
        return playersNumber;
    }

    /**
     * Return the players list.
     * @return the list of players.
     */
    public List<Player> getPlayers() {
        return List.copyOf(players);
    }

    /**
     * Return true if the game is over, false otherwise.
     * @param res the game result.
     * @return true if the game is over.
     */
    public boolean isOver(final Result res) {
        return res == Result.WIN;
    }
}
