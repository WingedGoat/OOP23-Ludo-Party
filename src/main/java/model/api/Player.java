package model.api;

import java.util.List;

import model.Color;
import model.PlayerHome.HomePosition;

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
     * Returns the value of the dice thrown.
     * 
     * @return the value of the dice
     */
    int throwDice();

    /**
     * Returns a wallet.
     * 
     * @return a wallet
     */
    Wallet getWallet();

}
