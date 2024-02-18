package view;

import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import model.Position;
import model.api.Item;
import utils.Index;

/**
 * Player panel on the left.
 */
@SuppressWarnings("all")
public final class PlayerPanelLeft extends PlayerPanel {

    private static final double GLOW_LEVEL = 0.8;
    private final PlayerGroup bottomPlayer;
    private PlayerGroup topPlayer;

    /**
     * Constructor.
     * 
     * @param game the game
     */
    public PlayerPanelLeft(final Game game) {
        super(game);
        this.bottomPlayer = createBottomPlayer(this.getBottomPos(), game);
        this.getChildren().add(this.bottomPlayer);

        if (game.getPlayers().size() > 2) {
            this.topPlayer = createTopPlayer(this.getTopPos(), game);
            this.getChildren().add(this.topPlayer);
        }
    }

    @Override
    protected PlayerGroup createBottomPlayer(final Position pos, final Game game) {

        final PlayerGroup g = new PlayerGroup(
                pos,
                game.getHumanPlayer().getName(),
                LABEL_NAME_BOTTOM_Y_LAYOUT,
                LABEL_COINS_BOTTOM_Y_LAYOUT,
                DICE_BOTTOM_Y_LAYOUT
        );

        g.getPlayerAvatarInner().setFill(Color.LIGHTBLUE);

        final ImageView diceImage = g.getDiceImage();
        diceImage.setOnMouseEntered(mouseEvent -> {
            diceImage.setEffect(new Glow(GLOW_LEVEL));
            diceImage.setCursor(Cursor.HAND);
        });
        diceImage.setOnMouseExited(mouseEvent -> {
            diceImage.setEffect(null);
        });

        diceImage.setOnMouseClicked(mouseEvent -> {
            if (ctrl.getGame().getTurn().getCurrentPlayer().canRollDice()) {
                final int diceResult = ctrl.getGame().getTurn().getCurrentPlayer().rollDice();
                if (ctrl.getGame().getTurn().getCurrentPlayer().getItemsApplied().contains(Item.DADUPLO) 
                        || diceResult > Index.SIX) {
                    final int firstDiceResult = ctrl.getGame().getTurn().getCurrentPlayer().getFirstDice();
                    final int secondDiceResult = ctrl.getGame().getTurn().getCurrentPlayer().getSecondDice();
                    final ImageView secondDiceImage = createDicImageView();
                    //FIXME the second dice don't appear
                    secondDiceImage.setLayoutX(DICE_X_LAYOUT);
                    secondDiceImage.setLayoutY(DICE_Y_LAYOUT_BOTTOM - Index.FORTY);

                    showDiceNumber(diceImage, firstDiceResult);
                    showDiceNumber(secondDiceImage, secondDiceResult);
                    ctrl.getGame().getTurn().getCurrentPlayer().setFirstDice(0);
                    ctrl.getGame().getTurn().getCurrentPlayer().setSecondDice(0);
                    ctrl.getGame().getTurn().getCurrentPlayer().getItemsApplied().remove(Item.DADUPLO);
                    if (ctrl.getGame().getTurn().getCurrentPlayer().getItemsApplied().contains(Item.TAGLIATELO)) {
                        ctrl.getGame().getTurn().getCurrentPlayer().getItemsApplied().remove(Item.TAGLIATELO);
                    }
                } else {
                    ctrl.getGame().getTurn().setDiceResult(diceResult);
                    showDiceNumber(diceImage, diceResult);
                }

                /*
                 * Se con il risultato ottenuto non è possibile muovere pedine,
                 * imposto pawnMoved a true, così è già possibile premere ENTER e passare il
                 * turno (senza cliccare Pawn).
                 */
                if (!game.getHumanPlayer().canMovePawns(diceResult)) {
                    game.getHumanPlayer().setPawnMoved(true);
                }
            }
        });

        g.getChildren().addAll(
            g.getPlayerAvatar(), g.getPlayerAvatarInner(), g.getPlayerName(), g.getPlayerCoins(), diceImage);

        return g;
    }

    @Override
    protected PlayerGroup createTopPlayer(final Position pos, final Game game) {

        final PlayerGroup g = new PlayerGroup(
                pos,
                game.getPlayers().get(1).getName(),
                LABEL_NAME_TOP_Y_LAYOUT,
                LABEL_COINS_TOP_Y_LAYOUT,
                DICE_TOP_Y_LAYOUT
        );

        //TODO add another dice image when is used DADUPLO

        g.getChildren().addAll(
            g.getPlayerAvatar(), g.getPlayerAvatarInner(), g.getPlayerName(), g.getPlayerCoins(), g.getDiceImage());

        return g;
    }

    @Override
    protected Label getBottomPlayerCoins() {
        return this.bottomPlayer.getPlayerCoins();
    }

    @Override
    protected Label getTopPlayerCoins() {
        return this.topPlayer.getPlayerCoins();
    }

    @Override
    public void refresh(final int coinsBottom, final int coinsTop) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                getBottomPlayerCoins().setText("Ludollari: " + coinsBottom);
                if (getPlayersNumber() > 2) {
                    getTopPlayerCoins().setText("Ludollari: " + coinsTop);
                }
            }
        });
    }

}
