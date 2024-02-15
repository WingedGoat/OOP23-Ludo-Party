package view;

import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
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
        final Circle playerAvatarInner = new Circle(pos.getX(), pos.getY(), INNER_CIRCLE_RADIUS, Color.LIGHTBLUE);

        final Label playerName = createNameLabelBottomPanel(ctrl.getGame().getHumanPlayer().getName());
        setNodeAnchors(playerName, 0.0);

        final Label playerCoins = createCoinsLabelBottomPanel(ctrl.getGame().getHumanPlayer().getCoins());
        setNodeAnchors(playerCoins, 0.0);

        final ImageView diceImage = createDicImageView();
        diceImage.setLayoutX(DICE_X_LAYOUT);
        diceImage.setLayoutY(DICE_Y_LAYOUT_BOTTOM);

        final Glow glow = new Glow(.8);
        diceImage.setOnMouseEntered(mouseEvent -> {
            diceImage.setEffect(glow);
            diceImage.setCursor(Cursor.HAND);
        });
        diceImage.setOnMouseExited(mouseEvent -> {
            diceImage.setEffect(null);
        });
        //TODO add another dice image when is used DADUPLO
        diceImage.setOnMouseClicked(mouseEvent -> {
            if (ctrl.canRollDice()) {
                final int diceResult = ctrl.getGame().getTurn().getCurrentPlayer().rollDice();
                ctrl.getGame().getTurn().setDiceResult(diceResult);
                showDiceNumber(diceImage, diceResult);
                /*
                 * Se con il risultato ottenuto non è possibile muovere pedine,
                 * imposto pawnMoved a true, così è già possibile premere ENTER e passare il turno (senza cliccare Pawn).
                 */
                if (!ctrl.getGame().getMovement().playerCanMovePawns(diceResult, ctrl.getGame().getPlayers().get(0))) {
                    ctrl.setPawnMoved(true);
                }
            }
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

        //TODO Change dice image when it is computer player turn
        //TODO add another dice image when is used DADUPLO

        final Group g = new Group();
        g.getChildren().addAll(playerAvatar, playerAvatarInner, playerName, playerCoins, diceImage);

        return g;
    }

}
