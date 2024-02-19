package ludoparty.view;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import ludoparty.model.Position;
import ludoparty.model.api.Game;
/**
 * Abstract Player panel class.
 */
public abstract class PlayerPanel extends AnchorPane {

    private static final int PANE_HEIGHT = 600;

    private static final int AVATAR_X_POSITION = 110;
    private static final int AVATAR_TOP_Y_POSITION = 120;
    private static final int AVATAR_BOTTOM_Y_POSITION = 480;
    /**
     * name label y position of top player.
     */
    protected static final int LABEL_NAME_TOP_Y_LAYOUT = 230;
    /**
     * coins label y position of top player.
     */
    protected static final int LABEL_COINS_TOP_Y_LAYOUT = 250;
    /**
     * name label y position of bottom player.
     */
    protected static final int LABEL_NAME_BOTTOM_Y_LAYOUT = 350;
    /**
     * coins label y position of bottom player.
     */
    protected static final int LABEL_COINS_BOTTOM_Y_LAYOUT = 370;
    /**
     * dice image y position of top player.
     */
    protected static final int DICE_TOP_Y_LAYOUT = 260;
    /**
     * dice image y position of bottom player.
     */
    protected static final int DICE_BOTTOM_Y_LAYOUT = 340;

    private final int playersNumber;
    private final Position bottomPos;
    private final Position topPos;

    /**
     * Constructor.
     * 
     * @param game the game
     */
    protected PlayerPanel(final Game game) {
        this.playersNumber = game.getPlayers().size(); 
        this.bottomPos = new Position(AVATAR_X_POSITION, AVATAR_BOTTOM_Y_POSITION);
        this.topPos = new Position(AVATAR_X_POSITION, AVATAR_TOP_Y_POSITION);
        this.setPrefHeight(PANE_HEIGHT);
    }


    /**
     * Gets the player number of the game.
     * @return the player number
     */
    protected int getPlayersNumber() {
        return playersNumber;
    }

    /**
     * Gets the bottom position of the player avatar.
     * @return the bottom position
     */
    protected Position getBottomPos() {
        return this.bottomPos;
    }

    /**
     * Gets the top position of the player avatar.
     * @return the bottom position
     */
    protected Position getTopPos() {
        return this.topPos;
    }

    /**
     * Gets the top player coins label.
     * @return the top player coins label
     */
    protected abstract Label getTopPlayerCoins();

    /**
     * Gets the bottom player coins label.
     * @return the bottom player coins label
     */
    protected abstract Label getBottomPlayerCoins();

    /**
     * Gets the top player dice image.
     * @return the top player dice image
     */
    protected abstract ImageView getTopPlayerDice();

    /**
     * Gets the bottom player dice image.
     * @return the bottom player dice image
     */
    protected abstract ImageView getBottomPlayerDice();

    /**
     * Refreshes the components of the player panel.
     * 
     * @param coinsBottom the amount of coins of the bottom player
     * @param coinsTop the amount of coins of the top player
     * @param diceBottomNum the result of the dice rolled of the bottom player
     * @param diceTopNum the result of the dice rolled of the top player
     */
    protected abstract void refresh(int coinsBottom, int coinsTop, int diceBottomNum, int diceTopNum);

    /**
     * Creates the player Group at bottom corner.
     * 
     * @param pos the position of the player avatar
     * @param game the game
     * @return the player group
     */
    protected abstract Group createBottomPlayer(Position pos, Game game);

    /**
     * Creates the player Group at top corner.
     * 
     * @param pos the position of the player avatar
     * @param game the game
     * @return the player group
     */
    protected abstract Group createTopPlayer(Position pos, Game game);

}
