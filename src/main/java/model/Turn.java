package model;

import model.api.Player;

/**
 * This class handles current player's turn.
 */
public class Turn {

    private int diceResult;

    /**
     * set current player.
     * @param player current player.
     */
    private void setCurrentPlayer(final Player player) {
        this.diceResult = player.throwDice();
    }

    /**
     * return the result of the Dice.
     * @return the Dice's result.
     */
    public int getDiceResult() {
        return this.diceResult;
    }

    /**
     * Set the player for next turn.
     * @param player the player of the next turn.
     */
    public void changeTurn(final Player player) {
        setCurrentPlayer(player);
    }
}
