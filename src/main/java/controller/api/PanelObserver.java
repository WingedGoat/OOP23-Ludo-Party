package controller.api;
/**
 * The observer for the player panels.
 */
public interface PanelObserver {

    /**
     * Updates the amount of coins of the left player panel.
     * 
     * @param coinsBottom the amount of coins of the bottom player
     * @param coinsTop the amount of coins of the top player
     */
    void updateLeftPlayersCoins(int coinsBottom, int coinsTop);

    /**
     * Updates the amount of coins of the right player panel.
     * 
     * @param coinsBottom the amount of coins of the bottom player
     * @param coinsTop the amount of coins of the top player
     */
    void updateRightPlayersCoins(int coinsBottom, int coinsTop);

//    void updateDiceImage();

}
