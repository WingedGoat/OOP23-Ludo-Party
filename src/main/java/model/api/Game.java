package model.api;

import java.util.List;
import java.util.Map;
import java.util.Set;

import model.Movement;
import model.Turn;

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
     * To access the Movement class methods.
     * @return the Movement.
     */
    Movement getMovement();

    /**
     * Called when a player attempts to use the Shop.
     * 
     * @param player trying to buy an item
     * @param item to be bought by the player
     */
    void buyItem(Player player, Item item);

    /**
     * Returns true if the game is over, false otherwise.
     * @param res the game result
     * @return true if the game is over
     */
    boolean isOver(Result res);

    /**
     * To access the Shop class methods.
     * @return the Shop
     */
    Shop getShop();

}
