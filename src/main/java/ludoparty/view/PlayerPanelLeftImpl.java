package ludoparty.view;

import ludoparty.model.Position;
import ludoparty.model.api.Game;
import ludoparty.utils.Constants;
import ludoparty.view.PlayerGroup.DiceImageView;

import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

/**
 * Player panel on the left.
 */
public final class PlayerPanelLeftImpl extends AbstractPlayerPanel {
    private static final int COINS_LABEL_X_LAYOUT = 88;
    private static final int COINS_LABEL_Y_LAYOUT = 460;
    private static final int FONT_SIZE = 16;
    private static final int ROTATION_DURATION = 500;

    private final PlayerGroup bottomPlayer;
    private Label humanEarnedCoins;
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

        diceImage.setOnMouseClicked(mouseEvent -> {
            if (game.getHumanPlayer().canRollDice()) {
                game.getHumanPlayer().rollDice();
                final RotateTransition rotate = new RotateTransition();
                rotate.setAxis(Rotate.Z_AXIS);
                rotate.setByAngle(360);
                rotate.setDuration(Duration.millis(ROTATION_DURATION));
                rotate.setNode(diceImage);
                rotate.play();
            }
        });
        // earned coins in current turn label
        this.humanEarnedCoins = new Label();
        this.humanEarnedCoins.setVisible(false);
        this.humanEarnedCoins.setFont(Font.font("Helvetica", FontWeight.LIGHT, FONT_SIZE));
        this.humanEarnedCoins.setLayoutX(COINS_LABEL_X_LAYOUT);
        this.humanEarnedCoins.setLayoutY(COINS_LABEL_Y_LAYOUT);

        g.getChildren().addAll(
            g.getPlayerAvatar(), g.getPlayerAvatarInner(), g.getPlayerName(), g.getPlayerCoins(), diceImage, humanEarnedCoins);

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

        g.getChildren().addAll(
            g.getPlayerAvatar(), g.getPlayerAvatarInner(), g.getPlayerName(), g.getPlayerCoins(), g.getDiceImage());

        return g;
    }

    @Override
    public Label getBottomPlayerCoins() {
        return this.bottomPlayer.getPlayerCoins();
    }

    /**
     * Gets the amount of earned coins of the current turn.
     * @return the amount of earned coins
     */
    public Label getHumanPlayerEarnedCoins() {
        return this.humanEarnedCoins;
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
    public void refresh(final int coinsBottom, final int earnedCoins, final int coinsTop, final int diceBottomNum, 
        final int diceTopNum) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                getBottomPlayerCoins().setText("Ludollari: " + coinsBottom);
                if (coinsBottom > 0) {
                    getHumanPlayerEarnedCoins().setVisible(true);
                    getHumanPlayerEarnedCoins().setText("+ " + earnedCoins + " $");
                }
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
