package view;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

import controller.api.Controller;
import model.Position;

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

        final Group g3 = createTopPlayer(this.getTopPos(), controller);
        this.getChildren().add(g3);

        if (controller.getGame().getPlayers().size() > 2) {
            final Group g4 = createBottomPlayer(this.getBottomPos(), controller);
            this.getChildren().add(g4);
        }
    }

    @Override
    protected Group createTopPlayer(final Position pos, final Controller ctrl) {

        final Circle playerAvatar = createPlayerAvatar(pos);
        final Circle playerAvatarInner = createPlayerInnerAvatar(pos);

        String name = ctrl.getGame().getPlayers().get(1).getName();
        if (ctrl.getPlayersNumber() > 2) {
            name = ctrl.getGame().getPlayers().get(2).getName();
        }
        final Label playerName = createNameLabelTopPanel(name);
        setNodeAnchors(playerName, 0.0);

        int coins = ctrl.getGame().getPlayers().get(1).getCoins();
        if (ctrl.getPlayersNumber() > 2) {
            coins = ctrl.getGame().getPlayers().get(2).getCoins();
        }
        final Label playerCoins = createCoinsLabelTopPanel(coins);
        setNodeAnchors(playerCoins, 0.0);

        final ImageView diceImage = createDicImageView();
        diceImage.setLayoutX(DICE_X_LAYOUT);
        diceImage.setLayoutY(DICE_Y_LAYOUT_TOP);

        //TODO Change dice image when it is computer player turn

        final Group g = new Group();
        g.getChildren().addAll(playerAvatar, playerAvatarInner, playerName, playerCoins, diceImage);

        return g;
    }

    @Override
    protected Group createBottomPlayer(final Position pos, final Controller ctrl) {

        final Circle playerAvatar = createPlayerAvatar(pos);
        final Circle playerAvatarInner = createPlayerInnerAvatar(pos);

        final Label playerName = createNameLabelBottomPanel(ctrl.getGame().getPlayers().get(3).getName());
        setNodeAnchors(playerName, 0.0);

        final Label playerCoins = createCoinsLabelBottomPanel(ctrl.getGame().getPlayers().get(3).getCoins());
        setNodeAnchors(playerCoins, 0.0);

        final ImageView diceImage = createDicImageView();
        diceImage.setLayoutX(DICE_X_LAYOUT);
        diceImage.setLayoutY(DICE_Y_LAYOUT_BOTTOM);

        //TODO Change dice image when it is computer player turn

        final Group g = new Group();
        g.getChildren().addAll(playerAvatar, playerAvatarInner, playerName, playerCoins, diceImage);

        return g;
    }

}
