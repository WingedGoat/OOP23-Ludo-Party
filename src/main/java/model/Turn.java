package model;

import model.api.Player;

/**
 * This class handles current player's turn.
 */
public class Turn {

    private int diceResult;

    /**
     * return the result of the Dice.
     * @return the Dice result.
     */
    public int getDiceResult() {
        return this.diceResult;
    }

    /**
     * Set the player for this turn.
     * @param player the player of this turn.
     */
    public void changeTurn(final Player player) {
        this.diceResult = player.rollDice();
    }
}
