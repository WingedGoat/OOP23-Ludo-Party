package ludoparty.view;

import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import ludoparty.model.Position;
import ludoparty.model.api.Game;
import ludoparty.view.api.PlayerPanel;
/**
 * Abstract Player panel class.
 */
public abstract class AbstractPlayerPanel extends AnchorPane implements PlayerPanel {

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
    protected static final int DICE_TOP_Y_LAYOUT = 234;
    /**
     * dice image y position of bottom player.
     */
    protected static final int DICE_BOTTOM_Y_LAYOUT = 352;

    private final int playersNumber;
    private final Position bottomPos;
    private final Position topPos;

    /**
     * Constructor.
     * 
     * @param game the game
     */
    protected AbstractPlayerPanel(final Game game) {
        this.playersNumber = game.getPlayers().size(); 
        this.bottomPos = new Position(AVATAR_X_POSITION, AVATAR_BOTTOM_Y_POSITION);
        this.topPos = new Position(AVATAR_X_POSITION, AVATAR_TOP_Y_POSITION);
        this.setPrefHeight(PANE_HEIGHT);
        this.setBackground(new Background(new BackgroundFill(Color.valueOf("#fdfcfc"), CornerRadii.EMPTY, Insets.EMPTY)));
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

}
