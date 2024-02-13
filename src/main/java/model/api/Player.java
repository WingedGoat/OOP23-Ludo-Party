package model.api;

import java.util.List;
import java.util.Set;

import model.Position;
import model.api.Cell.Type;
import utility.BColor;

/**
 * Represents a player.
 */
public interface Player {

    /**
     * Player Type.
     * {@see HUMAN},
     * {@see COMPUTER}
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
    BColor getColor();

    /**
     * Gets the position of the house cell.
     * 
     * @return the position of the house cell
     */
    Type getPlayerHouse();

    /**
     * Gets the player safe path on the board.
     * 
     * @return the safe path
     */
    Set<Position> getSafePath();

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
     * Sets the isPlayerTurn field to true.
     */
    void setPlayerTurn();

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
     * Modify the amount of coins.
     * 
     * @param value
     */
    void modifyCoins(int value);

    /**
     * Return the player items.
     * 
     * @return the player items
     */
    List<Item> getPlayerItems();

    /**
     * Add an item in the player's inventory.
     * 
     * @param item
     */
    void addItemPlayer(Item item);

    /**
     * Add the item in the List of item activated on the player.
     * 
     * @param item
     */
    void addToItemsApplied(Item item);

    /**
     * Returns the list of applied items.
     * 
     * @return the list of applied items
     */
    List<Item> getItemsApplied();

    /**
     * Active the item on the designed player.
     * 
     * @param item
     * @param player
     */
    void useItem(Item item, Player player/* , Pawn pawn */);

    /**
     * Remove the malus on the player after it is expired.
     */
    void malusExpired();

    /**
     * Remove the bonus on the player after it is expired.
     */
    void bonusExpired();

}
