package ludoparty.controller.api;
/**
 * The observer for the player panels.
 */
public interface PanelObserver {

    /**
     * Updates the amount of coins of the left player panel.
     * 
     * @param coinsBottom the amount of coins of the bottom player
     * @param earnedCoins the amount of coins earned during the last turn
     * @param coinsTop the amount of coins of the top player
     * @param diceBottomNum the result of the dice rolled of the bottom player
     * @param diceTopNum the result of the dice rolled of the top player
     */
    void updateLeftPlayerPanel(int coinsBottom, int earnedCoins, int coinsTop, int diceBottomNum, int diceTopNum);

    /**
     * Updates the amount of coins of the right player panel.
     * 
     * @param coinsBottom the amount of coins of the bottom player
     * @param earnedCoins zero for this panel
     * @param coinsTop the amount of coins of the top player
     * @param diceBottomNum the result of the dice rolled of the bottom player
     * @param diceTopNum the result of the dice rolled of the top player
     */
    void updateRightPlayerPanel(int coinsBottom, int earnedCoins, int coinsTop, int diceBottomNum, int diceTopNum);

}
