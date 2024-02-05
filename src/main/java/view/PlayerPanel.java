package view;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import utility.Position;

/**
 * The side panel with the player info.
 */
public final class PlayerPanel extends AnchorPane {

    private static final int PANE_HEIGHT = 600;
    // circle props
    private static final int CIRCLE_RADIUS = 80;
    private static final int INNER_CIRCLE_RADIUS = 77;
    private static final String CIRCLE_COLOR = "#555555";
    private static final String INNER_CIRCLE_COLOR = "#F1F1F1";
    // components positions
    private static final int LABEL_X_LAYOUT = 85;
    private static final int LABEL_TOP_NAME_Y_LAYOUT = 230;
    private static final int LABEL_TOP_COINS_Y_LAYOUT = 250;
    private static final int LABEL_BOTTOM_NAME_Y_LAYOUT = 350;
    private static final int LABEL_BOTTOM_COINS_Y_LAYOUT = 370;

    private final String playerTopName;
    private final String playerBottomName;

    /**
     * Constructor of the board side panel with the players info.
     * 
     * @param playerTopName name of the player showed on the top of the panel
     * @param playerBottomName name of the player showed on the bottom of the panel
     */
    public PlayerPanel(final String playerTopName, final String playerBottomName) {

        this.playerTopName = playerTopName;
        this.playerBottomName = playerBottomName;

        this.setPrefHeight(PANE_HEIGHT);

        this.addChildren();
    }

    private void addChildren() {
        final Group groupTop = getTopGroup();
        final Group groupBottom = getBottomGroup();

        this.getChildren().addAll(groupTop, groupBottom);
    }

    /**
     * Return the button pressed for rolling the Dice.
     * @return the button used for Dice rolling
     */
    public Button getRollDiceButton() {
        return (Button) this.getChildren().get(0);
    }

    /**
     * Return the label showing the Dice result.
     * @return the label with Dice result
     */
    public Label getDiceLabel() {
        return (Label) this.getChildren().get(1);
    }

    /**
     * Creates and return the group displayed on top of the panel.
     * The group contains the player's avatar, its name and the 
     * amount of coins picked up durign the game.
     * 
     * @return the top Group
     */
    private Group getTopGroup() {
        final Group g = new Group();

        final Position topPos = new Position(110, 120);

        final Circle playerAvatarTop = new Circle(topPos.getX(), topPos.getY(), CIRCLE_RADIUS, Color.valueOf(CIRCLE_COLOR));
        final Circle playerAvatarTopInner = 
            new Circle(topPos.getX(), topPos.getY(), INNER_CIRCLE_RADIUS, Color.valueOf(INNER_CIRCLE_COLOR));

        final Label playerTopName = new Label(this.playerTopName);
        playerTopName.setLayoutX(LABEL_X_LAYOUT);
        playerTopName.setLayoutY(LABEL_TOP_NAME_Y_LAYOUT);

        final Label playerTopCoins = new Label("Ludollari: 0");
        playerTopCoins.setLayoutX(LABEL_X_LAYOUT);
        playerTopCoins.setLayoutY(LABEL_TOP_COINS_Y_LAYOUT);

        AnchorPane.setLeftAnchor(playerTopName, 0.0);
        AnchorPane.setRightAnchor(playerTopName, 0.0);

        AnchorPane.setLeftAnchor(playerTopCoins, 0.0);
        AnchorPane.setRightAnchor(playerTopCoins, 0.0);

        g.getChildren().addAll(playerAvatarTop, playerAvatarTopInner, playerTopName, playerTopCoins);

        return g;
    }

    /**
     * Creates and return the group displayed at the bottom of the panel.
     * The group contains the player's avatar, its name and the 
     * amount of coins picked up durign the game.
     * 
     * @return the bottom Group
     */
    private Group getBottomGroup() {
        final Group g = new Group();

        final Position bottomPos = new Position(110, 480);

        final Circle playerAvatarBottom = 
            new Circle(bottomPos.getX(), bottomPos.getY(), CIRCLE_RADIUS, Color.valueOf(CIRCLE_COLOR));
        final Circle playerAvatarBottomInner = 
            new Circle(bottomPos.getX(), bottomPos.getY(), INNER_CIRCLE_RADIUS, Color.valueOf(INNER_CIRCLE_COLOR));

        final Label playerBottomName = new Label(this.playerBottomName);
        playerBottomName.setLayoutX(LABEL_X_LAYOUT);
        playerBottomName.setLayoutY(LABEL_BOTTOM_NAME_Y_LAYOUT);
        //playerBottomName.setAlignment(Pos.CENTER);

        final Label playerBottomCoins = new Label("Ludollari: 0");
        playerBottomCoins.setLayoutX(LABEL_X_LAYOUT);
        playerBottomCoins.setLayoutY(LABEL_BOTTOM_COINS_Y_LAYOUT);
        //playerBottomName.setAlignment(Pos.CENTER);

        AnchorPane.setLeftAnchor(playerBottomName, 0.0);
        AnchorPane.setRightAnchor(playerBottomName, 0.0);

        AnchorPane.setLeftAnchor(playerBottomCoins, 0.0);
        AnchorPane.setRightAnchor(playerBottomCoins, 0.0);

        g.getChildren().addAll(playerAvatarBottom, playerAvatarBottomInner, playerBottomName, playerBottomCoins);

        return g;
    }

}
