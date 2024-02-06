package view;

import java.io.File;
import java.nio.file.Path;

import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import utility.Position;
/**
 * Player panel basic class.
 */
public class PlayerPanel extends AnchorPane {
    /**
     * file separator.
     */
    protected static final String FILE_SEPARATOR = System.getProperty("file.separator");
    /**
     * image path.
     */
    protected static final String START_DICE_IMAGE_PATH = Path.of("." + FILE_SEPARATOR + "resources" + FILE_SEPARATOR
        + "images" + FILE_SEPARATOR + "dices" + FILE_SEPARATOR + "dice-1.png").toString();
    /**
     * pane height.
     */
    protected static final int PANE_HEIGHT = 600;
    // circle props
    /**
     * circle radius.
     */
    protected static final int CIRCLE_RADIUS = 80;
    /**
     * inner circle radius.
     */
    protected static final int INNER_CIRCLE_RADIUS = 77;
    /**
     * circle color.
     */
    protected static final String CIRCLE_COLOR = "#555555";
    /**
     * inner circle color.
     */
    protected static final String INNER_CIRCLE_COLOR = "#F1F1F1";
    // components positions
    /**
     * x.
     */
    protected static final int LABEL_X_LAYOUT = 60;
    /**
     * AA.
     */
    protected static final int LABEL_TOP_NAME_Y_LAYOUT = 230;
    /**
     * AA.
     */
    protected static final int LABEL_TOP_COINS_Y_LAYOUT = 250;
    /**
     * AA.
     */
    protected static final int LABEL_BOTTOM_NAME_Y_LAYOUT = 350;
    /**
     * AA.
     */
    protected static final int LABEL_BOTTOM_COINS_Y_LAYOUT = 370;
    /**
     * AA.
     */
    protected static final int DICE_WIDTH = 40;
    /**
     * AA.
     */
    protected static final int DICE_HEIGHT = 40;
//    protected static final int DICE_X_LAYOUT = 120;
//    protected static final int DICE_TOP_Y_LAYOUT = 210;
//    protected static final int DICE_BOTTOM_Y_LAYOUT = 340;
    /**
     * AA.
     */
    protected static final int CENTOSETTANTA = 170;
    //private static final int DUEEVENTI = 220;
    /**
     * AA.
     */
    protected static final int TREEQUARANTA = 340;
    /**
     * AA.
     */
    protected static final int DUEESESSANTA = 260;

    /**
     * Creates the outer circle of the player avatar.
     * 
     * @param pos pos
     * 
     * @return the player avatar
     */
    protected Circle createPlayerAvatar(final Position pos) {
        return new Circle(pos.getX(), pos.getY(), CIRCLE_RADIUS, Color.valueOf(CIRCLE_COLOR));
    }

    /**
     * Creates the inner circle of the player avatar.
     * 
     * @param pos pos
     * 
     * @return the player avatar
     */
    protected Circle createPlayerInnerAvatar(final Position pos) {
        return new Circle(pos.getX(), pos.getY(), INNER_CIRCLE_RADIUS, Color.valueOf(INNER_CIRCLE_COLOR));
    }

    /**
     * Creates the label with the name of the player.
     * 
     * @param name the player name
     * 
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
     * 
     * @return label with the coin amount
     */
    protected Label createCoinLabelTopPanel(final int coins) {
        final Label playerCoins = new Label("Ludollari: " + coins);
        playerCoins.setLayoutX(LABEL_X_LAYOUT);
        playerCoins.setLayoutY(LABEL_TOP_COINS_Y_LAYOUT);

        return playerCoins;
    }

    /**
     * Creates the dice image to show at the player side.
     * 
     * @return the dice image
     */
    protected ImageView createDicImageView() {
        final ImageView diceImage = new ImageView();

        final File file =  new File(START_DICE_IMAGE_PATH);
        diceImage.setImage(new Image(file.toURI().toString()));
        diceImage.setFitHeight(DICE_HEIGHT);
        diceImage.setFitWidth(DICE_WIDTH);

        final Glow glow = new Glow(.8);
        diceImage.setOnMouseEntered(mouseEvent -> {
            diceImage.setEffect(glow);
            diceImage.setCursor(Cursor.HAND);
        });
        diceImage.setOnMouseExited(mouseEvent -> {
            diceImage.setEffect(null);
        });

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

        final String diceImagePath = Path.of("." + FILE_SEPARATOR + "resources" + FILE_SEPARATOR
            + "images" + FILE_SEPARATOR + "dices" + FILE_SEPARATOR + "dice-" + number + ".png").toString();

        final File file =  new File(diceImagePath);
        diceImage.setImage(new Image(file.toURI().toString()));

        return diceImage;
    }

}
