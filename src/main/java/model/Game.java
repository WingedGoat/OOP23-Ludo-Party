package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.PlayerHome.HomePosition;
import model.api.Cell;
import model.api.Player;
import model.api.Player.PlayerType;

/**
 * 
 */
public class Game {

    /**
     * Represent the game result.
     */
    public enum Result {
        /**
         * Win.
         */
        WIN,
        /**
         * Continue.
         */
        CONTINUE;
    }

    private final int playersNumber;
    private final List<Player> players;
    private Set<Cell> cells = new HashSet<>();

    /**
     * Constructor.
     * @param playerName
     *          the player name
     * @param playersNumber
     *          the number of players
     */
    public Game(final String playerName, final int playersNumber) {
        final Player h = new PlayerImpl(playerName, PlayerType.HUMAN, 
            Color.BLUE, HomePosition.BOTTOM_LEFT);
        final Player p1 = new PlayerImpl("Player 2", PlayerType.COMPUTER, 
            Color.YELLOW, HomePosition.TOP_RIGHT);
        players = new ArrayList<>();
        players.add(h);
        players.add(p1);
        if (playersNumber > players.size()) {
            final Player p2 = new PlayerImpl("Player 3", PlayerType.COMPUTER, 
                Color.GREEN, HomePosition.TOP_LEFT);
            final Player p3 = new PlayerImpl("Player 4", PlayerType.COMPUTER, 
                Color.RED, HomePosition.BOTTOM_RIGHT);
            players.add(p2);
            players.add(p3);
        }
        this.playersNumber = playersNumber;
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
     * Set the private field cells with the cells of the board.
     * @param cells the board cells, as saved in the Controller.
     */
    public void setCells(final Collection<Cell> cells) {
        this.cells = Set.copyOf(cells);
    }

    /**
     * Return a Set of the board cells.
     * @return the board cells.
     */
    public Set<Cell> getCells() {
        return Set.copyOf(this.cells);
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
