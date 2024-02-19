package ludoparty.model.api;

/**
 * This interface handles current Computer player's turns.
 */
public interface Turn {

    /**
     * Returns the current player.
     * 
     * @return the current player
     */
    Player getCurrentPlayer();

    /**
     * Sets the current player.
     * 
     * @param player the current player
     */
    void passTurnTo(Player player);

}
