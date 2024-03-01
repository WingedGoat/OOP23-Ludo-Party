package ludoparty.view.api;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import ludoparty.model.Position;
import ludoparty.model.api.Game;

/**
 * The player panel interface. 
 * Defines useful getters to retrieve each player graphic component.
 */
public interface PlayerPanel {

    /**
     * Gets the top player coins label.
     * @return the top player coins label
     */
    Label getTopPlayerCoins();

    /**
     * Gets the bottom player coins label.
     * @return the bottom player coins label
     */
    Label getBottomPlayerCoins();

    /**
     * Gets the top player dice image.
     * @return the top player dice image
     */
    ImageView getTopPlayerDice();

    /**
     * Gets the bottom player dice image.
     * @return the bottom player dice image
     */
    ImageView getBottomPlayerDice();

    /**
     * Refreshes the components of the player panel.
     * 
     * @param coinsBottom the amount of coins of the bottom player
     * @param coinsEarned the amount of coins earned in the last turn
     * @param coinsTop the amount of coins of the top player
     * @param diceBottomNum the result of the dice rolled of the bottom player
     * @param diceTopNum the result of the dice rolled of the top player
     */
    void refresh(int coinsBottom, int coinsEarned, int coinsTop, int diceBottomNum, int diceTopNum);

    /**
     * Creates the player Group at bottom corner.
     * 
     * @param pos the position of the player avatar
     * @param game the game
     * @return the player group
     */
    Group createBottomPlayer(Position pos, Game game);

    /**
     * Creates the player Group at top corner.
     * 
     * @param pos the position of the player avatar
     * @param game the game
     * @return the player group
     */
    Group createTopPlayer(Position pos, Game game);

}
