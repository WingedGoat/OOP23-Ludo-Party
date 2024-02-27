package ludoparty.model;

import ludoparty.model.api.Player;
import ludoparty.model.api.Turn;

/**
 * This class handles current Computer player's turn.
 */
public final class TurnImpl implements Turn {

    private Player currentPlayer;

    /**
     * Constructor.
     * 
     * @param player the player which start the game.
     */
    public TurnImpl(final Player player) {
        this.currentPlayer = player;
    }

    @Override
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    @Override
    public void passTurnTo(final Player player) {
        getCurrentPlayer().malusExpired();
        this.currentPlayer = player;
        getCurrentPlayer().bonusExpired();
    }

}
