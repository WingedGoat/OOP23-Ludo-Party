package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javafx.scene.control.Button;

import model.PlayerHome.HomePosition;
import model.api.Cell;
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
    private final Map<Button, Cell> cells = new HashMap<>();

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
     * Returns the cells of the board.
     * @return the board cells.
     */
    public Map<Button, Cell> getCells() {
        final Map<Button, Cell> myCells = new HashMap<>();
        for (final Entry<Button, Cell> e : this.cells.entrySet()) {
            myCells.put(e.getKey(), e.getValue());
        }
        return myCells;
    }

    /**
     * Add to cells Map a new button and his Cell.
     * @param button the button to add to Map cells.
     * @param i the y coordinate of the new Button.
     * @param j the x coordinate of the new Button.
     */
    public void addToCells(final Button button, final int i, final int j) {
        this.cells.put(button, new CellImpl(new Position(j, i)));
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
