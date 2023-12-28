package model.api;

import model.Color;

/**
 * Represents a player.
 */
public interface Player {

    /**
     * Returns the name of the player.
     * @return the name
     */
    String getName();

    /**
     * Returns the number of coins owned by the player.
     * @return the coins
     */
    int getCoins();

    /**
     * Returns the color of the home box.
     * @return the color
     */
    Color getColor();

    /**
     * Returns true if it's the turn of the player,
     * false otherwise.
     * @return true if it's the player turn
     */
    boolean isPlayerTurn();

    /**
     * Returns the value of the dice thrown.
     * @return the value of the dice
     */
    int throwDice();

    /**
     * Returns a wallet.
     * @return a wallet
     */
    Wallet getWallet();
}
