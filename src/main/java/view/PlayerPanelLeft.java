package view;

import controller.api.Controller;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import utility.Position;

/**
 * Player panel on the left.
 */
public final class PlayerPanelLeft extends PlayerPanel {

    /**
     * Constructor.
     * 
     * @param controller the controller
     */
    public PlayerPanelLeft(final Controller controller) {

        this.setPrefHeight(PANE_HEIGHT);
        final Position bottomPos = new Position(110, 480);
        final Position topPos = new Position(110, 120);

        final Group g1 = createBottomLeftPlayer(bottomPos, controller);
        //final Group g2 = createTopRightPlayer(topPos, controller);

        //add bottom LEFT group and top RIGHT group
        this.getChildren().add(g1);

        if (controller.getGame().getPlayers().size() > 2) {
            //add also top RIGHT group
            //and bottom LEFT group
            final Group g2 = createTopLeftPlayer(topPos, controller);
            this.getChildren().add(g2);
        }
    }

    // HUMAN PLAYER (in BOTTOM LEFT position) [g1]
    private Group createBottomLeftPlayer(final Position pos, final Controller ctrl) {
        final Group g = new Group();

        final Circle playerAvatar = createPlayerAvatar(pos);
        final Circle playerAvatarInner = createPlayerInnerAvatar(pos);

        final Label playerName = new Label(ctrl.getGame().getHumanPlayer().getName());
        playerName.setLayoutX(LABEL_X_LAYOUT);
        playerName.setLayoutY(LABEL_BOTTOM_NAME_Y_LAYOUT);

        final Label playerCoins = new Label("Ludollari: " + ctrl.getGame().getHumanPlayer().getCoins());
        playerCoins.setLayoutX(LABEL_X_LAYOUT);
        playerCoins.setLayoutY(LABEL_BOTTOM_COINS_Y_LAYOUT);

        AnchorPane.setLeftAnchor(playerName, 0.0);
        AnchorPane.setRightAnchor(playerName, 0.0);

        AnchorPane.setLeftAnchor(playerCoins, 0.0);
        AnchorPane.setRightAnchor(playerCoins, 0.0);

        // dice
        final ImageView diceImage = createDicImageView();
        diceImage.setLayoutX(CENTOSETTANTA);
        diceImage.setLayoutY(TREEQUARANTA);

        diceImage.setOnMouseClicked(mouseEvent -> {
            final int diceResult = ctrl.getGame().getTurn().getCurrentPlayer().rollDice();
            showDiceNumber(diceImage, diceResult);
            //System.out.println(diceResult);
        });

        g.getChildren().addAll(playerAvatar, playerAvatarInner, playerName, playerCoins, diceImage);

        return g;
    }

    // COMPUTER PLAYER (in RIGHT TOP position) [g3]
    private Group createTopLeftPlayer(final Position pos, final Controller ctrl) {
        final Group g = new Group();

        final Circle playerAvatar = createPlayerAvatar(pos);
        final Circle playerAvatarInner = createPlayerInnerAvatar(pos);

        final Label playerName = createNameLabelTopPanel(ctrl.getGame().getPlayers().get(1).getName());
        final Label playerCoins = createCoinLabelTopPanel(ctrl.getGame().getPlayers().get(1).getCoins());

        AnchorPane.setLeftAnchor(playerName, 0.0);
        AnchorPane.setRightAnchor(playerName, 0.0);

        AnchorPane.setLeftAnchor(playerCoins, 0.0);
        AnchorPane.setRightAnchor(playerCoins, 0.0);

        // dice
        final ImageView diceImage = createDicImageView();
        diceImage.setLayoutX(CENTOSETTANTA);
        diceImage.setLayoutY(DUEESESSANTA);

        diceImage.setOnMouseClicked(mouseEvent -> {
            // controller.get
        });

        g.getChildren().addAll(playerAvatar, playerAvatarInner, playerName, playerCoins, diceImage);

        return g;
    }

}
