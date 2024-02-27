package ludoparty.view;

import ludoparty.model.Position;
import ludoparty.model.api.Game;
import ludoparty.utils.Constants;
import ludoparty.view.PlayerGroup.DiceImageView;
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

/**
 * Player panel on the left.
 */
public final class PlayerPanelLeftImpl extends AbstractPlayerPanel {

    private final PlayerGroup bottomPlayer;
    @SuppressWarnings("PMD")
    private PlayerGroup topPlayer;

    /**
     * Constructor.
     * 
     * @param game the game
     */
    public PlayerPanelLeftImpl(final Game game) {
        super(game);
        this.bottomPlayer = createBottomPlayer(this.getBottomPos(), game);
        this.getChildren().add(this.bottomPlayer);

        if (game.getPlayers().size() > Constants.PLAYERS_NUM_2) {
            this.topPlayer = createTopPlayer(this.getTopPos(), game);
            this.getChildren().add(this.topPlayer);
        }
    }

    @Override
    public PlayerGroup createBottomPlayer(final Position pos, final Game game) {

        final PlayerGroup g = new PlayerGroup(
                pos,
                game.getHumanPlayer().getName(),
                LABEL_NAME_BOTTOM_Y_LAYOUT,
                LABEL_COINS_BOTTOM_Y_LAYOUT,
                DICE_BOTTOM_Y_LAYOUT
        );

        g.getPlayerAvatarInner().setFill(Color.LIGHTBLUE);

        final DiceImageView diceImage = g.getDiceImage();
        diceImage.setOnMouseEntered(mouseEvent -> {
            diceImage.setCursor(Cursor.HAND);
        });

        //TODO add another dice image when is used DADUPLO
        diceImage.setOnMouseClicked(mouseEvent -> {
            if (game.getHumanPlayer().canRollDice()) {
                final int diceResult = game.getHumanPlayer().rollDice();
                game.getTurn().getCurrentPlayer().setDiceResult(diceResult);

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
    public PlayerGroup createTopPlayer(final Position pos, final Game game) {

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
    public Label getBottomPlayerCoins() {
        return this.bottomPlayer.getPlayerCoins();
    }

    @Override
    public Label getTopPlayerCoins() {
        return this.topPlayer.getPlayerCoins();
    }

    @Override
    public DiceImageView getBottomPlayerDice() {
        return this.bottomPlayer.getDiceImage();
    }

    @Override
    public DiceImageView getTopPlayerDice() {
        return this.topPlayer.getDiceImage();
    }

    @Override
    public void refresh(final int coinsBottom, final int coinsTop, final int diceBottomNum, final int diceTopNum) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                getBottomPlayerCoins().setText("Ludollari: " + coinsBottom);
                if (diceBottomNum > 0) {
                    getBottomPlayerDice().updateDiceImage(diceBottomNum);
                }
                if (getPlayersNumber() > Constants.PLAYERS_NUM_2) {
                    getTopPlayerCoins().setText("Ludollari: " + coinsTop);
                    if (diceTopNum > 0) {
                        getTopPlayerDice().updateDiceImage(diceTopNum);
                    }
                }
            }
        });
    }

}
