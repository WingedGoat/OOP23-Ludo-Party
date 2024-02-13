package model;

import java.util.Map;
import java.util.HashMap;

import model.api.Game;
import model.api.Item;
import model.api.Player;

/**
 * This class handles current Computer player's turn.
 */
public final class Turn {

    private Player currentPlayer;
    private int diceResult;
    private final Map<Integer, Item> showcase = new HashMap<>();

    /**
     * Constructor.
     * 
     * @param player the player which start the game.
     */
    public Turn(final Player player) {
        this.currentPlayer = player;
    }

    /**
     * Main method of this class, used to play the whole turn.
     * @param player who currently has the turn.
     * @param game the current game.
     */
    public void play(final Player player, final Game game) {
        //possibilità di giocare BONUS
        //subito prima di tirare il dado: calcolare effetto combinato degli "item applied" sul Player
        this.diceResult = player.rollDice();
        //movimento
        //possibilità di giocare BONUS o MALUS
        //game.sellingItem(player, this.showcase.values().iterator().next());
    }

    /**
     * return the result of the Dice.
     * @return the Dice result.
     */
    public int getDiceResult() {
        return this.diceResult;
    }

    /**
     * Set the showcase with its current items.
     * @param showcase with the items it currently contains.
     */
    public void setShowcase(final Map<Integer, Item> showcase) {
        this.showcase.putAll(showcase);
    }

    /**
     * Returns the current player.
     * 
     * @return the current player
     */
    public Player getCurrentPlayer() {
        return new PlayerImpl(this.currentPlayer);
    }

    /**
     * Sets the current player.
     * 
     * @param player the current player
     */
    public void passTurnTo(final Player player) {
        this.currentPlayer = player;
    }

    /**
     * Set the last Dice result.
     * @param diceResult the last Dice result.
     */
    public void setDiceResult(final int diceResult) {
        this.diceResult = diceResult;
    }

}
