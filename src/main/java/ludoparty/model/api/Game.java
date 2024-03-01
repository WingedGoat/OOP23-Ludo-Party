package ludoparty.model.api;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The Game.
 */
public interface Game {

    /**
     * Represents the game result.
     */
    enum Result {
        /**
         * Win.
         */
        WIN,
        /**
         * Play.
         */
        PLAY;
    }

    /**
     * Gets the board.
     * @return the board
     */
    Board getBoard();

    /**
     * Gets the cells of the board.
     * @return the cells of the board
    */
    Set<Cell> getBoardCells();

    /**
     * Gets the players list.
     * 
     * @return the list of players
     */
    List<Player> getPlayers();

    /**
     * Gets the human player.
     * 
     * @return the human player
     */
    Player getHumanPlayer();

    /**
     * Gets the turn.
     * 
     * @return the turn
     */
    Turn getTurn();

    /**
     * Gets the showcase with current items.
     * @return the current showcase.
     */
    Map<Integer, Item> getShowcase();

    /**
     * Called when a player attempts to use the Shop.
     * 
     * @param player trying to buy an item
     * @param item to be bought by the player
     */
    void buyItem(Player player, Item item);

    /**
     * Returns {@link Result#WIN} if the player has all its pawns
     * in the end cell.
     * @return {@link Result#WIN} if the game isOver, {@link Result#PLAY} otherwise;
     */
    Result getResult();

    /**
     * Checks if the game is over.
     * @return true if the game is over, false otherwise
     */
    boolean isOver();

    /**
     * To access the Shop class methods.
     * @return the Shop
     */
    Shop getShop();

}
