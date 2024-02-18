package view;

import java.io.File;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.Position;
import utils.BColor;
import utils.Index;
import view.utils.ResourcePath;
/**
 * Player Group with player avatar, player name, coins and dice image.
 */
public class PlayerGroup extends Group {

    private static final Logger LOGGER = LogManager.getRootLogger();

    private static final int CIRCLE_RADIUS = 80;
    private static final int INNER_CIRCLE_RADIUS = 77;
    private static final int LABEL_X_LAYOUT = 60;
    private static final int DICE_X_LAYOUT = 170;
    private static final int DICE_WIDTH = 40;
    private static final int DICE_HEIGHT = 40;

    private final Circle playerAvatar;
    private final Circle playerAvatarInner;
    private final Label playerName;
    private final Label playerCoins;
    private final DiceImageView diceImage;

    /**
     * Constructor.
     * 
     * @param pos the player avatar position
     * @param name the player name
     * @param yPosName the y position of the player name label
     * @param yPosCoins the y position of the player coins label
     * @param yPosDice the y position of the player dice
     */
    PlayerGroup(final Position pos, final String name, final int yPosName, final int yPosCoins, final int yPosDice) {
        this.playerAvatar = createPlayerAvatar(pos);
        this.playerAvatarInner = createPlayerInnerAvatar(pos);

        this.playerName = new PlayerLabel(name, yPosName);
        setNodeAnchors(this.playerName);

        this.playerCoins = new CoinsLabel("Ludollari: 0", yPosCoins);
        setNodeAnchors(this.playerCoins);

        this.diceImage = new DiceImageView(yPosDice);
    }


    // getters

    /**
     * Gets the player avatar (the circle).
     * @return the player avatar
     */
    public Circle getPlayerAvatar() {
        return playerAvatar;
    }

    /**
     * Gets the inner player avatar.
     * @return the inner player avatar
     */
    public Circle getPlayerAvatarInner() {
        return playerAvatarInner;
    }

    /**
     * Gets the label with the player name.
     * @return the label with the player name
     */
    public Label getPlayerName() {
        return playerName;
    }

    /**
     * Gets the label with the quantity of coins of the player.
     * @return the coins label
     */
    public Label getPlayerCoins() {
        return playerCoins;
    }

    /**
     * Gets the dice image view.
     * @return the dice image
     */
    public DiceImageView getDiceImage() {
        return diceImage;
    }

    // private methods

    private Circle createPlayerAvatar(final Position pos) {
        return new Circle(pos.getX(), pos.getY(), CIRCLE_RADIUS, BColor.GREY_CIRCLE.get());
    }

    private Circle createPlayerInnerAvatar(final Position pos) {
        return new Circle(pos.getX(), pos.getY(), INNER_CIRCLE_RADIUS, BColor.GREY.get());
    }

    private void setNodeAnchors(final Label label) {
        AnchorPane.setLeftAnchor(label, 0.0);
        AnchorPane.setRightAnchor(label, 0.0);
    }


    // inner classes

    private static class CoinsLabel extends Label {

        CoinsLabel(final String text, final int yPos) {
            super(text);
            this.setLayoutX(LABEL_X_LAYOUT);
            this.setLayoutY(yPos);
        }
    }

    private static class PlayerLabel extends Label {

        PlayerLabel(final String text, final int yPos) {
            super(text);
            this.setLayoutX(LABEL_X_LAYOUT);
            this.setLayoutY(yPos);
        }
    }
 
    /**
     * Dice Image View.
     */
    protected static class DiceImageView extends ImageView {

        private File file = new File(ResourcePath.DICE_IMG_FACE_ONE.getPath());

        DiceImageView(final int yPos) {
            this.setImage(new Image(file.toURI().toString()));
            this.setFitHeight(DICE_HEIGHT);
            this.setFitWidth(DICE_WIDTH);
            this.setLayoutX(DICE_X_LAYOUT);
            this.setLayoutY(yPos);
        }

        /**
         * Updates the dice number on the dice image showed.
         * 
         * @param number the new number to show
         */
        public void updateDiceImage(final int number) {

            String diceImagePath = "";

            switch (number) {
                case Index.ONE:
                    diceImagePath = ResourcePath.DICE_IMG_FACE_ONE.getPath();
                    break;
                case Index.TWO:
                    diceImagePath = ResourcePath.DICE_IMG_FACE_TWO.getPath();
                    break;
                case Index.THREE:
                    diceImagePath = ResourcePath.DICE_IMG_FACE_THREE.getPath();
                    break;
                case Index.FOUR:
                    diceImagePath = ResourcePath.DICE_IMG_FACE_FOUR.getPath();
                    break;
                case Index.FIVE:
                    diceImagePath = ResourcePath.DICE_IMG_FACE_FIVE.getPath();
                    break;
                case Index.SIX:
                    diceImagePath = ResourcePath.DICE_IMG_FACE_SIX.getPath();
                    break;

                default:
                    // error
                    break;
            }

            if (!"".equals(diceImagePath)) {
                this.file = new File(diceImagePath);
                this.setImage(new Image(file.toURI().toString()));
            } else {
                LOGGER.error("dice image path: " + diceImagePath + " not found");
            }
        }
    }

}

