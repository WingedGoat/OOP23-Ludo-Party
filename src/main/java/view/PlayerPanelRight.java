package view;

import javafx.application.Platform;
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
        super(controller);
        final Group g3 = createTopPlayer(this.getTopPos(), controller);
        this.getChildren().add(g3);

        if (this.getPlayersNumber() > 2) {
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

        this.createCoinsLabelTopPanel();
        setNodeAnchors(this.getTopPlayerCoins(), 0.0);

        final ImageView diceImage = createDicImageView();
        diceImage.setLayoutX(DICE_X_LAYOUT);
        diceImage.setLayoutY(DICE_Y_LAYOUT_TOP);
        //TODO add another dice image when is used DADUPLO
        final Group g = new Group();
        g.getChildren().addAll(playerAvatar, playerAvatarInner, playerName, this.getTopPlayerCoins(), diceImage);

        return g;
    }

    @Override
    protected Group createBottomPlayer(final Position pos, final Controller ctrl) {

        final Circle playerAvatar = createPlayerAvatar(pos);
        final Circle playerAvatarInner = createPlayerInnerAvatar(pos);

        final Label playerName = createNameLabelBottomPanel(ctrl.getGame().getPlayers().get(3).getName());
        setNodeAnchors(playerName, 0.0);

        this.createCoinsLabelBottomPanel();
        setNodeAnchors(this.getBottomPlayerCoins(), 0.0);

        final ImageView diceImage = createDicImageView();
        diceImage.setLayoutX(DICE_X_LAYOUT);
        diceImage.setLayoutY(DICE_Y_LAYOUT_BOTTOM);
        //TODO add another dice image when is used DADUPLO
        final Group g = new Group();
        g.getChildren().addAll(playerAvatar, playerAvatarInner, playerName, this.getBottomPlayerCoins(), diceImage);

        return g;
    }

    @Override
    public void refresh(final int coinsBottom, final int coinsTop) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                getTopPlayerCoins().setText("Ludollari: " + coinsTop);
                if (getPlayersNumber() > 2) {
                    getBottomPlayerCoins().setText("Ludollari: " + coinsBottom);
                }
            }
        });
    }

}
