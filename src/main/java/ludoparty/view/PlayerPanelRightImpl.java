package ludoparty.view;

import javafx.application.Platform;
import javafx.scene.control.Label;
import ludoparty.model.Position;
import ludoparty.model.api.Game;
import ludoparty.utils.Constants;
import ludoparty.view.PlayerGroup.DiceImageView;

/**
 * Player panel on the right.
 */
public final class PlayerPanelRightImpl extends AbstractPlayerPanel {

    private final PlayerGroup topPlayer;
    @SuppressWarnings("PMD")
    private PlayerGroup bottomPlayer;

    /**
     * Constructor.
     * 
     * @param game the game
     */
    public PlayerPanelRightImpl(final Game game) {
        super(game);
        this.topPlayer = createTopPlayer(this.getTopPos(), game);
        this.getChildren().add(this.topPlayer);

        if (this.getPlayersNumber() > Constants.PLAYERS_NUM_2) {
            this.bottomPlayer = createBottomPlayer(this.getBottomPos(), game);
            this.getChildren().add(this.bottomPlayer);
        }
    }

    @Override
    public PlayerGroup createTopPlayer(final Position pos, final Game game) {

        String playerName = game.getPlayers().get(1).getName();
        if (this.getPlayersNumber() > Constants.PLAYERS_NUM_2) {
            playerName = game.getPlayers().get(2).getName();
        }

        final PlayerGroup g = new PlayerGroup(
            pos,
            playerName,
            LABEL_NAME_TOP_Y_LAYOUT,
            LABEL_COINS_TOP_Y_LAYOUT,
            DICE_TOP_Y_LAYOUT
        );

        g.getChildren().addAll(
            g.getPlayerAvatar(), g.getPlayerAvatarInner(), g.getPlayerName(), g.getPlayerCoins(), g.getDiceImage());

        return g;
    }

    @Override
    public PlayerGroup createBottomPlayer(final Position pos, final Game game) {

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
    public Label getTopPlayerCoins() {
        return this.topPlayer.getPlayerCoins();
    }

    @Override
    public Label getBottomPlayerCoins() {
        return this.bottomPlayer.getPlayerCoins();
    }

    @Override
    public DiceImageView getTopPlayerDice() {
        return this.topPlayer.getDiceImage();
    }

    @Override
    public DiceImageView getBottomPlayerDice() {
        return this.bottomPlayer.getDiceImage();
    }

    @Override
    public void refresh(final int coinsBottom, final int earnedCoins, final int coinsTop, final int diceBottomNum, 
        final int diceTopNum) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                getTopPlayerCoins().setText("Ludollari: " + coinsTop);
                if (diceTopNum > 0) {
                    getTopPlayerDice().updateDiceImage(diceTopNum);
                }
                if (getPlayersNumber() > Constants.PLAYERS_NUM_2) {
                    getBottomPlayerCoins().setText("Ludollari: " + coinsBottom);
                    if (diceBottomNum > 0) {
                        getBottomPlayerDice().updateDiceImage(diceBottomNum);
                    }
                }
            }
        });
    }

}
