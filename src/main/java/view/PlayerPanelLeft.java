package view;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

import controller.api.Controller;
import model.Position;

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

        final Group g1 = createBottomPlayer(this.getBottomPos(), controller);
        this.getChildren().add(g1);

        if (controller.getGame().getPlayers().size() > 2) {
            final Group g2 = createTopPlayer(this.getTopPos(), controller);
            this.getChildren().add(g2);
        }
    }

    @Override
    protected Group createBottomPlayer(final Position pos, final Controller ctrl) {

        final Circle playerAvatar = createPlayerAvatar(pos);
        final Circle playerAvatarInner = createPlayerInnerAvatar(pos);

        final Label playerName = createNameLabelTopPanel(ctrl.getGame().getHumanPlayer().getName());
        setNodeAnchors(playerName, 0.0);

        final Label playerCoins = createCoinsLabelTopPanel(ctrl.getGame().getHumanPlayer().getCoins());
        setNodeAnchors(playerCoins, 0.0);

        final ImageView diceImage = createDicImageView();
        diceImage.setLayoutX(DICE_X_LAYOUT);
        diceImage.setLayoutY(DICE_Y_LAYOUT_BOTTOM);

        //TODO
        diceImage.setOnMouseClicked(mouseEvent -> {
            final int diceResult = ctrl.getGame().getTurn().getCurrentPlayer().rollDice();
            showDiceNumber(diceImage, diceResult);
        });

        final Group g = new Group();
        g.getChildren().addAll(playerAvatar, playerAvatarInner, playerName, playerCoins, diceImage);

        return g;
    }

    @Override
    protected Group createTopPlayer(final Position pos, final Controller ctrl) {

        final Circle playerAvatar = createPlayerAvatar(pos);
        final Circle playerAvatarInner = createPlayerInnerAvatar(pos);

        final Label playerName = createNameLabelTopPanel(ctrl.getGame().getPlayers().get(1).getName());
        setNodeAnchors(playerName, 0.0);

        final Label playerCoins = createCoinsLabelTopPanel(ctrl.getGame().getPlayers().get(1).getCoins());
        setNodeAnchors(playerCoins, 0.0);

        final ImageView diceImage = createDicImageView();
        diceImage.setLayoutX(DICE_X_LAYOUT);
        diceImage.setLayoutY(DICE_Y_LAYOUT_TOP);

        //TODO
        diceImage.setOnMouseClicked(mouseEvent -> {
            // controller.get
        });

        final Group g = new Group();
        g.getChildren().addAll(playerAvatar, playerAvatarInner, playerName, playerCoins, diceImage);

        return g;
    }

}
