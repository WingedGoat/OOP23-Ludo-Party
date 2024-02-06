package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.PlayerHome.HomePosition;
import model.api.Cell;
import model.api.Item;
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
    private final Player humanPlayer;
    private final List<Player> players;
    private final Turn turn;

    private final Set<Cell> cells = new HashSet<>();
    private final ShopImpl shop;

    /**
     * Constructor.
     * 
     * @param playerName the player name
     * @param playersNumber the number of players
     */
    public Game(final String playerName, final int playersNumber) {

        this.playersNumber = playersNumber;

        // add players
        this.humanPlayer = new PlayerImpl(playerName, PlayerType.HUMAN, 
            Color.BLUE, HomePosition.BOTTOM_LEFT);
        final Player p1 = new PlayerImpl("Player 2", PlayerType.COMPUTER, 
            Color.YELLOW, HomePosition.TOP_RIGHT);
        this.players = new ArrayList<>(List.of(this.humanPlayer, p1));

        if (playersNumber > players.size()) {
            final Player p2 = new PlayerImpl("Player 3", PlayerType.COMPUTER, 
                Color.GREEN, HomePosition.TOP_LEFT);
            final Player p3 = new PlayerImpl("Player 4", PlayerType.COMPUTER, 
                Color.RED, HomePosition.BOTTOM_RIGHT);
            this.players.add(p2);
            this.players.add(p3);
        }

        // create turn
        turn = new Turn(this.humanPlayer);
        //turn.passTurnTo(this.humanPlayer);

        shop = new ShopImpl();
    }

    /**
     * Return the number of players.
     * @return the players number
     */
    public int getPlayersNumber() {
        return playersNumber;
    }

    /**
     * Return the players list.
     * 
     * @return the list of players
     */
    public List<Player> getPlayers() {
        return List.copyOf(players);
    }

    /**
     * Return the human player.
     * 
     * @return the human player
     */
    public Player getHumanPlayer() {
        return new PlayerImpl(this.humanPlayer);
    }

    /**
     * Returns the turn.
     * 
     * @return the turn
     */
    public Turn getTurn() {
        return this.turn;
    }

    /**
     * Sets the set of cells of the board.
     * @param cells the board cells, as saved in the Controller.

    public void setCells(final Collection<Cell> cells) {
        this.cells = Set.copyOf(cells);
    }
     */
    /**
     * Return a Set of the board cells.
     * @return the board cells.
     */
    public Set<Cell> getCells() {
        return Set.copyOf(this.cells);
    }

    /**
     * Return the showcase with current items.
     * @return the current showcase.
     */
    public Map<Integer, Item> getShowcase() {
        return this.shop.getShowcase();
    }

    /**
     * Called when a player attempts to use the Shop.
     * 
     * @param player trying to buy an Item.
     * @param item to be bought by the Player.
     * @return a string representing the outcome of the transaction.
     */
    public String sellingItem(final Player player, final Item item) {
        return this.shop.sellingItem((PlayerImpl) player, item);
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
