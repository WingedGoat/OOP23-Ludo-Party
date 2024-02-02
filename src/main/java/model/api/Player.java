package model.api;

import java.util.List;

import model.Color;
import model.InventoryImpl;
import model.PlayerHome.HomePosition;
import model.PlayerImpl;

/**
 * Represents a player.
 */
public interface Player {

    /**
     * Enum player type.
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
     * Get the player type [HUMAN, COMPUTER].
     * 
     * @return the player type
     */
    PlayerType getType();

    /**
     * Returns the color of the home box.
     * 
     * @return the color
     */
    Color getColor();

    /**
     * Gets the position of the house cell.
     * 
     * @return the position of the house cell
     */
    HomePosition getHomePosition();

    /**
     * Get the pawns of the current player.
     * 
     * @return the pawns list
     */
    List<Pawn> getPawns();

    /**
     * Returns the number of coins owned by the player.
     * 
     * @return the coins
     */
    int getCoins();

    /**
     * Sets the coins of the current player.
     * 
     * @param coins the coins
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
     * Returns the type of Dice the player owns,
     * everyone starts with a BasicDiceImpl.
     * 
     * @return the Dice of the player.
     */
    Dice getDice();

    /**
     * Returns the value of the dice thrown.
     * 
     * @return the value of the dice.
     */
    int rollDice();

    /**
     * Returns a wallet.
     * 
     * @return a wallet
     */
    Wallet getWallet();

    /**
     * Returns the player inventory.
     * 
     * @return the player inventory
     */
    InventoryImpl getPlayerInventory();

    /**
     * Modify the amount of coins.
     * 
     * @param value
     */
    void modifyCoins(int value);

    /**
     * Add an item in the player's inventory.
     * 
     * @param id
     * 
     * @param item
     */
    void addItem(Integer id, Item item);

    /**
     * Add the item in the List of item activated on the player.
     * 
     * @param item
     */
    void itemApplied(Item item);

    /**
     * Active the item on the designed player.
     * 
     * @param item
     * 
     * @param player
     */
    void useItem(Item item, PlayerImpl player);

    /**
     * Remove the malus on the player after it is expired.
     */
    void malusExpired();

    /**
     * Remove the bonus on the player after it is expired.
     */
    void bonusExpired();

}
