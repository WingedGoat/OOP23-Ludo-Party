package ludoparty.controller;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ludoparty.controller.api.Controller;
import ludoparty.controller.api.PanelObserver;
import ludoparty.model.GameImpl;
import ludoparty.model.Position;
import ludoparty.model.api.Game;
import ludoparty.model.api.Game.Result;
import ludoparty.model.api.Item;
import ludoparty.model.api.Item.ItemType;
import ludoparty.model.api.Pawn;
import ludoparty.model.api.Player;
import ludoparty.utils.Constants;
import ludoparty.view.BoardScene;
import ludoparty.view.utils.ViewUtility;

/**
 * Controller used to coordinate model and view.
 */
public final class ControllerImpl implements Controller, Runnable {

    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final int MILLISECS = 150;

    private static final String NOT_ENOUGH_SPACE = "ATTENZIONE! NON HAI ABBASTANZA SPAZIO NELL'INVENTARIO!";
    private static final String NOT_ENOUGH_MONEY = "ATTENZIONE! NON HAI ABBASTANZA LUDOLLARI!";
    private static final String DUPLICATE = "ATTENZIONE! HAI GIA' QUESTO OGGETTO NEL TUO INVENTARIO!";

    private final int playersNumber;
    private final Game game;
    private final BoardScene view;
    private PanelObserver obs;
    private boolean malusClicked;
    private Item itemToUse;
    private String outcome;
    private boolean itemselled;
    private final Random r = new Random();

    /**
     * Constructor.
     * 
     * @param stage         the stage
     * @param playerName    the player name
     * @param playersNumber the number of players of the game
     */
    public ControllerImpl(final Stage stage, final String playerName, final int playersNumber) {
        this.playersNumber = playersNumber;
        this.game = new GameImpl(playerName, playersNumber);
        this.view = ViewUtility.createBoardScene(this, stage);
        this.setInputHandler();
        this.addObserver(new PanelObserver() {

            @Override
            public void updateLeftPlayerPanel(
                    final int coinsBottom, final int earnedCoins, final int coinsTop,
                    final int diceBottomNum, final int diceTopNum) {
                view.getLeftPane().refresh(coinsBottom, earnedCoins, coinsTop, diceBottomNum, diceTopNum);
            }

            @Override
            public void updateRightPlayerPanel(
                    final int coinsBottom, final int earnedCoins, final int coinsTop,
                    final int diceBottomNum, final int diceTopNum) {
                view.getRightPane().refresh(coinsBottom, earnedCoins, coinsTop, diceBottomNum, diceTopNum);
            }

        });
    }

    @Override
    public void run() {

        while (this.game.getResult() != Result.WIN) {
            if (playersNumber == Constants.PLAYERS_NUM_2) {
                updateTwoPlayersGame();
            } else {
                updateFourPlayersGame();
            }

            try {
                // Refresh each 150 milliseconds
                Thread.sleep(MILLISECS);
            } catch (InterruptedException e) {
                LOGGER.error("Refresh Thread is not sleeping");
            }
        }
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                if (playersNumber == Constants.PLAYERS_NUM_2) {
                    updateTwoPlayersGame();
                } else {
                    updateFourPlayersGame();
                }
                createSaveScoreView();
            }
        });
    }

    /**
     * Updates left and right panel for the game with two players.
     */
    private void updateTwoPlayersGame() {
        obs.updateLeftPlayerPanel(
                this.game.getHumanPlayer().getCoins(), this.game.getHumanPlayer().getEarnedCoins(), 0,
                this.game.getHumanPlayer().getDiceResult(), 0);
        obs.updateRightPlayerPanel(
                0, 0, this.game.getPlayers().get(1).getCoins(),
                0, this.game.getPlayers().get(1).getDiceResult());
    }

    /**
     * Updates left and right panel for the game with four players.
     */
    private void updateFourPlayersGame() {
        obs.updateLeftPlayerPanel(
                this.game.getHumanPlayer().getCoins(),
                this.game.getHumanPlayer().getEarnedCoins(),
                this.game.getPlayers().get(1).getCoins(),
                this.game.getHumanPlayer().getDiceResult(),
                this.game.getPlayers().get(1).getDiceResult());
        obs.updateRightPlayerPanel(
                this.game.getPlayers().get(2).getCoins(),
                0,
                this.game.getPlayers().get(3).getCoins(),
                this.game.getPlayers().get(2).getDiceResult(),
                this.game.getPlayers().get(3).getDiceResult());
    }

    private void createSaveScoreView() {
        ViewUtility.createSaveScoreStage(this);
    }

    @Override
    public int getPlayersNumber() {
        return this.playersNumber;
    }

    @Override
    public Game getGame() {
        return this.game;
    }

    @Override
    public BoardScene getView() {
        return this.view;
    }

    @Override
    public void saveScore(final String name) {
        try {
            ScoreManager.getInstance().saveScore(name, game.getTurn().getCurrentPlayer().getCoins());
        } catch (FileNotFoundException e) {
            LOGGER.error("File not found: " + e.getMessage());
        } catch (IOException e) {
            LOGGER.error("IO Exception: " + e.getMessage());
        }
    }

    private void setInputHandler() {
        // when human finish the turn
        this.view.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.SPACE)
                    && this.getGame().getTurn().getCurrentPlayer().canPassTurn(this.game)) {
                this.view.getShopPane().disableShop();
                // computer players move a pawn
                for (int i = 1; i < getPlayersNumber(); i++) {
                    if (!this.game.isOver()) {
                        final Player player = this.game.getPlayers().get(i);
                        this.game.getTurn().passTurnTo(player);
                        player.rollDice();
                        final int steps = player.getSteps();
                        int indexPawnToMove = r.nextInt(Constants.PLAYER_PAWNS);
                        // while a random movable pawn is assigned
                        while (player.canMovePawns(this.game)
                                && !player.getPawns().get(indexPawnToMove).canMove(steps, this.game)) {
                            indexPawnToMove = r.nextInt(Constants.PLAYER_PAWNS);
                        }
                        final Pawn pawn = player.getPawns().get(indexPawnToMove);
                        pawn.move(steps, this.game);

                        updatePawnPositions();
                        player.earnCoins();
                    }
                }
                // turn is again of the human player
                if (!this.game.isOver()) {
                    this.game.getTurn().passTurnTo(this.game.getHumanPlayer());
                }
            } else if (!getGame().getHumanPlayer().isMalusUsed()) {
                final Label message = new Label("DEVI USARE IL MALUS PRIMA DI PASSARE IL TURNO!");
                message.setBackground(
                    new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
                view.getShopPane().getPopupMessage(message).show(view.getWindow());
            }
            view.getContainer().requestFocus();
        });

        for (int i = 0; i < this.view.getPawns().size(); i++) {
            final Circle circle = this.view.getPawns().get(i);
            final Player player = this.game.getPlayers().get(i / Constants.PLAYER_PAWNS);
            final Pawn pawn = player.getPawns().get(i % Constants.PLAYER_PAWNS);

            circle.setOnMouseClicked(e -> {
                if (player.canMovePawn(pawn, this.game) && pawn.canMove(player.getSteps(), this.game)) {
                    pawn.move(player.getSteps(), this.game);
                    updatePawnPositions();
                    player.earnCoins();

                    if (this.game.getBoard().getShops().contains(pawn.getPosition())) {
                        this.view.getShopPane().enableShop();
                    }
                } else if (malusClicked && !getGame().getTurn().getCurrentPlayer().isMalusUsed()) {
                    if (!getGame().getTurn().getCurrentPlayer().getPawns().contains(pawn)) {
                        if (!getGame().getBoard().getEndCell().getPawns().contains(pawn)) {
                            final Label message = new Label(
                                    this.game.getTurn().getCurrentPlayer().getName() + " ha usato "
                                            + getItemToUse().getName() + " su " + player.getName());
                            message.setBackground(
                                    new Background(new BackgroundFill(Color.PURPLE, CornerRadii.EMPTY, Insets.EMPTY)));
                            view.getShopPane().getPopupMessage(message).show(view.getWindow());
                            this.game.getTurn().getCurrentPlayer().useItem(itemToUse, player, pawn, game);
                            updatePawnPositions();
                            malusClicked = false;
                            getGame().getTurn().getCurrentPlayer().setMalusUsed(true);
                            setItemToUse(null);
                        } else {
                            final Label message = new Label(
                                    "QUELLA PEDINA E' ARRIVATA ALLA CELLA FINALE, NON PUOI TOCCARLA!");
                            message.setBackground(
                                    new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
                            view.getShopPane().getPopupMessage(message).show(view.getWindow());
                        }
                    } else {
                        final Label message = new Label("HAI UN MALUS ATTIVO! DEVI USARLO SU UNA PEDINA AVVERSARIA!");
                        message.setBackground(
                                new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
                        view.getShopPane().getPopupMessage(message).show(view.getWindow());
                    }
                }
                view.getContainer().requestFocus();
            });
        }
    }

    private void addObserver(final PanelObserver obs) {
        this.obs = obs;
    }

    @Override
    public void updatePawnPositions() {
        for (int j = 0; j < getPlayersNumber() * Constants.PLAYER_PAWNS; j++) {
            final Position startPos = this.game.getPlayers().get(j / Constants.PLAYER_PAWNS)
                    .getPawns().get(j % Constants.PLAYER_PAWNS).getStartPosition();
            final Position actualPos = this.game.getPlayers().get(j / Constants.PLAYER_PAWNS)
                    .getPawns().get(j % Constants.PLAYER_PAWNS).getPosition();
            this.view.getPawns().get(j).setRadius(ViewUtility.CIRCLE_RADIUS);
            this.view.getPawns().get(j)
                    .setTranslateX((actualPos.getX() - startPos.getX()) * ViewUtility.CELL_WIDTH
                            + overlappingCircles(actualPos, this.view.getPawns().get(j), j).getX());
            this.view.getPawns().get(j).setTranslateY((actualPos.getY() - startPos.getY()) * ViewUtility.CELL_WIDTH
                    + overlappingCircles(actualPos, this.view.getPawns().get(j), j).getY());
        }
    }

    private Position overlappingCircles(final Position pos, final Circle circle, final int j) {
        if (circleIsNotAlone(pos)) {
            circle.setRadius(ViewUtility.CIRCLE_RADIUS / Constants.PLAYER_PAWNS);
            final int columnIndex = j % Constants.PLAYER_PAWNS;
            return new Position(columnIndex * 10, ViewUtility.TOP_ROW
                    + (j / Constants.PLAYERS_NUM_4) * ViewUtility.CELL_WIDTH / Constants.PLAYERS_NUM_4);
        }
        return new Position(0, 0);
    }

    private boolean circleIsNotAlone(final Position pos) {
        for (final var c : game.getBoardCells()) {
            if (pos.equals(c.getPosition()) && c.getPawns().size() > 1) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isPossibleSelling() {
        return this.game.getTurn().getCurrentPlayer().isDiceRolled();
    }

    @Override
    public void sellingItemToPlayer(final Item item) {
        final Item itemOfClickedButton = item;

        game.buyItem(this.game.getTurn().getCurrentPlayer(), itemOfClickedButton);
        setShopMessage(game.getShop().getShopMessage());
        if (!NOT_ENOUGH_SPACE.equals(outcome) && !NOT_ENOUGH_MONEY.equals(outcome) && !DUPLICATE.equals(outcome)) {
            this.view.getInventoryPane().addItem(itemOfClickedButton, this, view);
            setIsItemSelled(true);
        } else {
            setIsItemSelled(false);
        }
    }

    /**
     * Set if the item in the selling operetion got selled or not.
     * 
     * @param newvalue
     */
    private void setIsItemSelled(final boolean newvalue) {
        this.itemselled = newvalue;
    }

    @Override
    public boolean isItemSelled() {
        return this.itemselled;
    }

    @Override
    public Boolean clickBonusButton(final Item itemToUse) {
        if (itemToUse.getType().equals(ItemType.MALUS)) {
            malusClicked = true;
            getGame().getHumanPlayer().setMalusUsed(false);
            return false;
        }
        malusClicked = false;
        return true;
    }

    @Override
    public Item getItemToUse() {
        return this.itemToUse;
    }

    @Override
    public void setItemToUse(final Item itemClicked) {
        this.itemToUse = itemClicked;
    }

    @Override
    public String getShopMessage() {
        return this.outcome;
    }

    @Override
    public void setShopMessage(final String newMessage) {
        this.outcome = newMessage;
    }

    @Override
    public Item getNewShopItem() {
        return this.game.getShop().getNewItem();
    }
}
