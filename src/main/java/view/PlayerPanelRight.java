package view;

import javafx.application.Platform;
import javafx.scene.control.Label;
import model.Position;
import model.api.Game;
import view.PlayerGroup.DiceImageView;

/**
 * Player panel on the right.
 */
@SuppressWarnings("all")
public final class PlayerPanelRight extends PlayerPanel {

    private final PlayerGroup topPlayer;
    private PlayerGroup bottomPlayer;

    /**
     * Constructor.
     * 
     * @param game the game
     */
    public PlayerPanelRight(final Game game) {
        super(game);
        this.topPlayer = createTopPlayer(this.getTopPos(), game);
        this.getChildren().add(this.topPlayer);

        if (this.getPlayersNumber() > 2) {
            this.bottomPlayer = createBottomPlayer(this.getBottomPos(), game);
            this.getChildren().add(this.bottomPlayer);
        }
    }

    @Override
    protected PlayerGroup createTopPlayer(final Position pos, final Game game) {

        String playerName = game.getPlayers().get(1).getName();
        if (this.getPlayersNumber()  > 2) {
            playerName = game.getPlayers().get(2).getName();
        }

        final PlayerGroup g = new PlayerGroup(
            pos,
            playerName,
            LABEL_NAME_TOP_Y_LAYOUT,
            LABEL_COINS_TOP_Y_LAYOUT,
            DICE_TOP_Y_LAYOUT
        );

        // TODO Change dice image when it is computer player turn
        // TODO add another dice image when is used DADUPLO

        g.getChildren().addAll(
            g.getPlayerAvatar(), g.getPlayerAvatarInner(), g.getPlayerName(), g.getPlayerCoins(), g.getDiceImage());

        return g;
    }

    @Override
    protected PlayerGroup createBottomPlayer(final Position pos, final Game game) {

        final PlayerGroup g = new PlayerGroup(
                pos,
                game.getPlayers().get(3).getName(),
                LABEL_NAME_BOTTOM_Y_LAYOUT,
                LABEL_COINS_BOTTOM_Y_LAYOUT,
                DICE_BOTTOM_Y_LAYOUT
        );

        g.getChildren().addAll(
            g.getPlayerAvatar(), g.getPlayerAvatarInner(), g.getPlayerName(), g.getPlayerCoins(), g.getDiceImage());

        return g;
    }

    @Override
    protected Label getTopPlayerCoins() {
        return this.topPlayer.getPlayerCoins();
    }

    @Override
    protected Label getBottomPlayerCoins() {
        return this.bottomPlayer.getPlayerCoins();
    }

    @Override
    protected DiceImageView getTopPlayerDice() {
        return this.topPlayer.getDiceImage();
    }

    @Override
    protected DiceImageView getBottomPlayerDice() {
        return this.bottomPlayer.getDiceImage();
    }

    @Override
    public void refresh(final int coinsBottom, final int coinsTop, final int diceBottomNum, final int diceTopNum) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                getTopPlayerCoins().setText("Ludollari: " + coinsTop);
                if (diceTopNum > 0) {
                    getTopPlayerDice().updateDiceImage(diceTopNum);
                }
                if (getPlayersNumber() > 2) {
                    getBottomPlayerCoins().setText("Ludollari: " + coinsBottom);
                    if (diceBottomNum > 0) {
                        getBottomPlayerDice().updateDiceImage(diceBottomNum);
                    }
                }
            }
        });
    }

}
