package model.api;

import java.util.List;
import java.util.Set;

import model.Position;
import model.api.Cell.CellType;
import utils.BColor;

/**
 * Represents a player.
 */
public interface Player {

    /**
     * Player Type.
     * 
     * @see HUMAN
     * @see COMPUTER
     */
    enum PlayerType {
        /**
         * Human player type.
         */
        HUMAN,
        /**
         * Computer player type.
         */
        COMPUTER;
    }

    /**
     * Gets the name of the player.
     * 
     * @return the name
     */
    String getName();

    /**
     * Gets the player type {@link PlayerType}.
     * 
     * @return the player type
     */
    PlayerType getType();

    /**
     * Gets the color of the home box.
     * 
     * @return the color
     */
    BColor getColor();

    /**
     * Gets the position of the house cell {@link CellType}.
     * 
     * @return the position of the house cell
     */
    CellType getPlayerHouse();

    /**
     * Gets the player safe path on the board.
     * 
     * @return the safe path
     */
    Set<Position> getSafePath();

    /**
     * Gets the pawns of the player.
     * 
     * @return the pawns list
     */
    List<Pawn> getPawns();

    /**
     * Gets the amount of coins owned by the player.
     * 
     * @return the amount of coins
     */
    int getCoins();

    /**
     * Sets the coins of the player.
     * 
     * @param coins the amount of coins
     */
    void setCoins(int coins);

    /**
     * Returns true if it's the turn of the player,
     * false otherwise.
     * 
     * @return true if it's the player turn
     */
    boolean isPlayerTurn();

    /**
     * Sets the isPlayerTurn field to true.
     */
    void setPlayerTurn();

    /**
     * Gets the dice of the player.
     * 
     * @return the dice of the player
     */
    Dice getDice();

    /**
     * Returns the value of the dice thrown.
     * 
     * @return the value of the dice
     */
    int rollDice();

    /**
     * Modify the amount of coins.
     * 
     * @param value the amount of coins
     */
    void modifyCoins(int value);

    /**
     * Gets the player items.
     * 
     * @return the player items
     */
    List<Item> getPlayerItems();

    /**
     * Add an item {@link Item} in the player's inventory.
     * 
     * @param item the item
     */
    void addItemPlayer(Item item);

    /**
     * Adds the {@link Item} in the items list activated on the player.
     * 
     * @param item the item
     */
    void addToItemsApplied(Item item);

    /**
     * Gets the list of items applied.
     * 
     * @return the list of applied items
     */
    List<Item> getItemsApplied();

    /**
     * Active the {@link Item} on the designed player.
     * 
     * @param item   the item
     * @param player the designed player
     * @param pawn   the pawn affected by the item
     * @param game   the game
     */
    void useItem(Item item, Player player, Pawn pawn, Game game);

    /**
     * Remove the {@link Item.ItemType#MALUS} on the player after it is expired.
     */
    void malusExpired();

    /**
     * Remove the {@link Item.ItemType#BONUS} on the player after it is expired.
     */
    void bonusExpired();

}
