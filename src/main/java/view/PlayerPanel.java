package view;

import java.io.File;
import java.nio.file.Path;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import controller.api.Controller;
import model.Position;
import utils.BColor;
import view.utils.ResourcePath;
/**
 * Abstract Player panel class.
 */
public abstract class PlayerPanel extends AnchorPane {

    private static final int PANE_HEIGHT = 600;
    private static final int CIRCLE_RADIUS = 80;
    /**
     * The inner circle radius.
     */
    protected static final int INNER_CIRCLE_RADIUS = 77;
    private static final String CIRCLE_COLOR = BColor.GREY_CIRCLE.getHexColor();
    private static final String INNER_CIRCLE_COLOR = BColor.GREY.getHexColor();

    private static final int LABEL_X_LAYOUT = 60;
    private static final int LABEL_TOP_NAME_Y_LAYOUT = 230;
    private static final int LABEL_TOP_COINS_Y_LAYOUT = 250;

    private static final int AVATAR_X_POSITION = 110;
    private static final int AVATAR_Y_TOP_POSITION = 120;
    private static final int AVATAR_Y_BOTTOM_POSITION = 480;

    private static final int DICE_WIDTH = 40;
    private static final int DICE_HEIGHT = 40;
    /**
     * label bottom name Y layout.
     */
    protected static final int LABEL_BOTTOM_NAME_Y_LAYOUT = 350;
    /**
     * label bottom coins Y layout.
     */
    protected static final int LABEL_BOTTOM_COINS_Y_LAYOUT = 370;
    /**
     * dice X layout.
     */
    protected static final int DICE_X_LAYOUT = 170;
    /**
     * dice Y layout at top position.
     */
    protected static final int DICE_Y_LAYOUT_TOP = 260;
    /**
     * dice Y layout at bottm position.
     */
    protected static final int DICE_Y_LAYOUT_BOTTOM = 340;

    private final Position bottomPos;
    private final Position topPos;

    PlayerPanel() {
        this.topPos = new Position(AVATAR_X_POSITION, AVATAR_Y_TOP_POSITION);
        this.bottomPos = new Position(AVATAR_X_POSITION, AVATAR_Y_BOTTOM_POSITION);
        this.setPrefHeight(PANE_HEIGHT);
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
     * Creates the player Group at bottom corner.
     * 
     * @param pos the position of the player avatar
     * @param ctrl the controller
     * @return the player Group
     */
    protected abstract Group createBottomPlayer(Position pos, Controller ctrl);

    /**
     * Creates the player Group at top corner.
     * 
     * @param pos the position of the player avatar
     * @param ctrl the controller
     * @return the player Group
     */
    protected abstract Group createTopPlayer(Position pos, Controller ctrl);

    /**
     * Sets the node anchors.
     * 
     * @param label the node
     * @param value the position
     */
    protected void setNodeAnchors(final Label label, final double value) {
        AnchorPane.setLeftAnchor(label, value);
        AnchorPane.setRightAnchor(label, value);
    }

    /**
     * Creates the outer circle of the player avatar.
     * 
     * @param pos pos
     * @return the player avatar
     */
    protected Circle createPlayerAvatar(final Position pos) {
        return new Circle(pos.getX(), pos.getY(), CIRCLE_RADIUS, Color.valueOf(CIRCLE_COLOR));
    }

    /**
     * Creates the inner circle of the player avatar.
     * 
     * @param pos pos
     * @return the player avatar
     */
    protected Circle createPlayerInnerAvatar(final Position pos) {
        return new Circle(pos.getX(), pos.getY(), INNER_CIRCLE_RADIUS, Color.valueOf(INNER_CIRCLE_COLOR));
    }

    /**
     * Creates the label with the name of the player.
     * 
     * @param name the player name
     * @return the label with the player name
     */
    protected Label createNameLabelTopPanel(final String name) {
        final Label playerName = new Label(name);
        playerName.setLayoutX(LABEL_X_LAYOUT);
        playerName.setLayoutY(LABEL_TOP_NAME_Y_LAYOUT);

        return playerName;
    }

    /**
     * Creates the label with the coin quantity of the player.
     * 
     * @param coins the amount of coins owned by the player
     * @return label with the coin amount
     */
    protected Label createCoinsLabelTopPanel(final int coins) {
        final Label playerCoins = new Label("Ludollari: " + coins);
        playerCoins.setLayoutX(LABEL_X_LAYOUT);
        playerCoins.setLayoutY(LABEL_TOP_COINS_Y_LAYOUT);

        return playerCoins;
    }

    /**
     * Creates the label with the name of the player.
     * 
     * @param name the player name
     * @return the label with the player name
     */
    protected Label createNameLabelBottomPanel(final String name) {
        final Label playerName = new Label(name);
        playerName.setLayoutX(LABEL_X_LAYOUT);
        playerName.setLayoutY(LABEL_BOTTOM_NAME_Y_LAYOUT);

        return playerName;
    }

    /**
     * Creates the label with the coin quantity of the player.
     * 
     * @param coins the amount of coins owned by the player
     * @return label with the coin amount
     */
    protected Label createCoinsLabelBottomPanel(final int coins) {
        final Label playerCoins = new Label("Ludollari: " + coins);
        playerCoins.setLayoutX(LABEL_X_LAYOUT);
        playerCoins.setLayoutY(LABEL_BOTTOM_COINS_Y_LAYOUT);

        return playerCoins;
    }

    /**
     * Creates the dice image to show at the player side.
     * 
     * @return the dice image
     */
    protected ImageView createDicImageView() {
        final ImageView diceImage = new ImageView();

        final File file = new File(ResourcePath.DICE_IMG_FACE_ONE.getPath());
        diceImage.setImage(new Image(file.toURI().toString()));
        diceImage.setFitHeight(DICE_HEIGHT);
        diceImage.setFitWidth(DICE_WIDTH);

        return diceImage;
    }

    /**
     * Change the dice number on the dice image showed.
     * 
     * @param diceImage the old dice image
     * @param number the new number to show
     * 
     * @return the dice image with the new value of the dice
     */
    protected ImageView showDiceNumber(final ImageView diceImage, final int number) {

        final String diceImagePath = Path.of(ResourcePath.DICE_IMG_FOLDER.getPath() + System.getProperty("file.separator") 
            + "dice-" + number + ".png").toString();
        final File file = new File(diceImagePath);
        diceImage.setImage(new Image(file.toURI().toString()));

        return diceImage;
    }

}
