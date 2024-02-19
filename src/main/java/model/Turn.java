package model;

import model.api.Player;

/**
 * This class handles current Computer player's turn.
 */
public final class Turn {

    private Player currentPlayer;

    /**
     * Constructor.
     * 
     * @param player the player which start the game.
     */
    public Turn(final Player player) {
        this.currentPlayer = player;
    }

    /**
     * Returns the current player.
     * 
     * @return the current player
     */
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    /**
     * Sets the current player.
     * 
     * @param player the current player
     */
    public void passTurnTo(final Player player) {
        this.currentPlayer.malusExpired();
        player.bonusExpired();
        this.currentPlayer = player;
    }

}
