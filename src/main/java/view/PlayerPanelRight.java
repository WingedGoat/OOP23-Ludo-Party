package view;

import controller.api.Controller;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import utility.Position;

/**
 * Player panel on the right.
 */
public final class PlayerPanelRight extends PlayerPanel {

    /**
     * Constructor.
     * 
     * @param controller the controller
     */
    public PlayerPanelRight(final Controller controller) {

        this.setPrefHeight(PANE_HEIGHT);

        final Position bottomPos = new Position(110, 480);
        final Position topPos = new Position(110, 120);

        final Group g3 = createTopRightPlayer(topPos, controller);

        this.getChildren().add(g3);

        if (controller.getGame().getPlayers().size() > 2) {
            //add also top RIGHT group
            //and bottom LEFT group
            final Group g4 = createBottomRightPlayer(bottomPos, controller);
            this.getChildren().add(g4);
        }
    }

    // COMPUTER PLAYER (in TOP RIGHT position) [g2]
    private Group createTopRightPlayer(final Position pos, final Controller ctrl) {
        final Group g = new Group();

        final Circle playerAvatar = createPlayerAvatar(pos);
        final Circle playerAvatarInner = createPlayerInnerAvatar(pos);

        String name = ctrl.getGame().getPlayers().get(1).getName();
        if (ctrl.getPlayersNumber() > 2) {
            name = ctrl.getGame().getPlayers().get(2).getName();
        }
        final Label playerName = createNameLabelTopPanel(name);

        int coins = ctrl.getGame().getPlayers().get(1).getCoins();
        if (ctrl.getPlayersNumber() > 2) {
            coins = ctrl.getGame().getPlayers().get(2).getCoins();
        }
        final Label playerCoins = createCoinLabelTopPanel(coins);

        AnchorPane.setLeftAnchor(playerName, 0.0);
        AnchorPane.setRightAnchor(playerName, 0.0);

        AnchorPane.setLeftAnchor(playerCoins, 0.0);
        AnchorPane.setRightAnchor(playerCoins, 0.0);

        // dice
        final ImageView diceImage = createDicImageView();
        diceImage.setLayoutX(CENTOSETTANTA);
        diceImage.setLayoutY(DUEESESSANTA);

        diceImage.setOnMouseClicked(mouseEvent -> {
            //controller.get
        });

        g.getChildren().addAll(playerAvatar, playerAvatarInner, playerName, playerCoins, diceImage);

        return g;
    }

    // COMPUTER PLAYER (in BOTTOM TOP position) [g4]
    private Group createBottomRightPlayer(final Position pos, final Controller ctrl) {
        final Group g = new Group();

        final Circle playerAvatar = createPlayerAvatar(pos);
        final Circle playerAvatarInner = createPlayerInnerAvatar(pos);

        final Label playerName = new Label(ctrl.getGame().getPlayers().get(3).getName());
        playerName.setLayoutX(LABEL_X_LAYOUT);
        playerName.setLayoutY(LABEL_BOTTOM_NAME_Y_LAYOUT);

        final Label playerCoins = new Label("Ludollari: " + ctrl.getGame().getPlayers().get(3).getCoins());
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
            // controller.get
        });

        g.getChildren().addAll(playerAvatar, playerAvatarInner, playerName, playerCoins, diceImage);

        return g;
    }

}
