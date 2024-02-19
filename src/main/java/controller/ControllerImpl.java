package controller;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
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

import controller.api.Controller;
import controller.api.PanelObserver;
import model.GameImpl;
import model.Movement;
import model.Position;
import model.api.Game;
import model.api.Game.Result;
import model.api.Item;
import model.api.Item.ItemType;
import model.api.Pawn;
import model.api.Player;
import utils.Constants;
import view.BoardScene;
import view.utils.ViewUtility;

/**
 * Controller used to coordinate model and view.
 */
public final class ControllerImpl implements Controller, Runnable {

    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final int MILLISECS = 200;

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
                    final int coinsBottom, final int coinsTop,
                    final int diceBottomNum, final int diceTopNum) {
                view.getLeftPane().refresh(coinsBottom, coinsTop, diceBottomNum, diceTopNum);
            }

            @Override
            public void updateRightPlayerPanel(
                    final int coinsBottom, final int coinsTop,
                    final int diceBottomNum, final int diceTopNum) {
                view.getRightPane().refresh(coinsBottom, coinsTop, diceBottomNum, diceTopNum);
            }

        });
    }

    @Override
    public void run() {

        while (this.game.getResult() != Result.WIN) {
            if (playersNumber == Constants.PLAYERS_NUM_2) {
                obs.updateLeftPlayerPanel(
                        this.game.getHumanPlayer().getCoins(), 0,
                        this.game.getHumanPlayer().getDiceResult(), 0);
                obs.updateRightPlayerPanel(
                        0, this.game.getPlayers().get(1).getCoins(),
                        0, this.game.getPlayers().get(1).getDiceResult());
            } else {
                obs.updateLeftPlayerPanel(
                        this.game.getHumanPlayer().getCoins(),
                        this.game.getPlayers().get(1).getCoins(),
                        this.game.getHumanPlayer().getDiceResult(),
                        this.game.getPlayers().get(1).getDiceResult());
                obs.updateRightPlayerPanel(
                        this.game.getPlayers().get(2).getCoins(),
                        this.game.getPlayers().get(3).getCoins(),
                        this.game.getPlayers().get(2).getDiceResult(),
                        this.game.getPlayers().get(3).getDiceResult());
            }

            try {
                // Refresh each 200 milliseconds
                Thread.sleep(MILLISECS);
            } catch (InterruptedException e) {
                LOGGER.error("Refresh Thread is not sleeping");
            }
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                createSaveScoreView();
            }
        });
    }

    private void createSaveScoreView() {
        ViewUtility.createSaveScoreScene(this);
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
            ScoreManager.getInstance().saveScore(name, game.getHumanPlayer().getCoins());
        } catch (FileNotFoundException e) {
            LOGGER.error("File not found: " + e.getMessage());
        } catch (IOException e) {
            LOGGER.error("IO Exception: " + e.getMessage());
        }
    }

    private void setInputHandler() {
        // when finish the turn
        this.view.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER) && this.getGame().getTurn().getCurrentPlayer().canPassTurn()) {
                this.view.getShopPane().disableShop();
                // LOGGER.error(" -- end of turn -- ");
                for (int i = 1; i < getPlayersNumber(); i++) {

                    final Player player = this.game.getPlayers().get(i);
                    this.game.getTurn().passTurnTo(player);

                    final int diceResult = player.rollDice();
                    int indexPawnToMove = r.nextInt(Constants.PLAYER_PAWNS);
                    /*
                     * Il Computer cambia la scelta del Pawn da muovere, finché:
                     * continua a sceglierne uno che NON si può muovere, però
                     * ne ha altri che POSSONO effettuare un movimento
                     */
                    while (player.canMovePawns(diceResult)
                            && !player.getPawns().get(indexPawnToMove).canMove(diceResult)) {
                        indexPawnToMove = r.nextInt(Constants.PLAYER_PAWNS);
                    }

                    final Pawn pawnToMove = player.getPawns().get(indexPawnToMove);
                    pawnToMove.move(diceResult, this.game);

                    updatePawnPositions();
                    player.earnCoins(diceResult);
                }

                this.game.getTurn().passTurnTo(this.game.getHumanPlayer());
                LOGGER.error("Human coins: " + this.game.getHumanPlayer().getCoins());
            }
        });

        for (int i = 0; i < this.view.getPawns().size(); i++) {
            final Circle circle = this.view.getPawns().get(i);
            final Player player = this.game.getPlayers().get(i / Constants.PLAYER_PAWNS);
            final Pawn pawn = player.getPawns().get(i % Constants.PLAYER_PAWNS);

            circle.setOnMouseEntered(event -> circle.setCursor(Cursor.HAND));

            circle.setOnMouseClicked(e -> {

                if (player.canMovePawn(pawn) && pawn.canMove(player.getDiceResult())) {
                    pawn.move(player.getDiceResult(), this.game);
                    updatePawnPositions();
                    player.earnCoins(this.game.getTurn().getCurrentPlayer().getDiceResult());

                    if (this.game.getBoard().getShops().contains(pawn.getPosition())) {
                        this.view.getShopPane().ableShop();
                    }
                } else if (getMalusClicked()
                        && clickBonusButton(itemToUse)) {
                    final Label message = new Label(this.game.getTurn().getCurrentPlayer().getName() + " ha usato "
                            + getItemToUse().getName() + " su " + player.getName());
                    message.setBackground(
                            new Background(new BackgroundFill(Color.PURPLE, CornerRadii.EMPTY, Insets.EMPTY)));
                    view.getShopPane().setPopupMessage(message);
                    view.getShopPane().getPopupMessage().show(view.getWindow());
                    this.game.getTurn().getCurrentPlayer().useItem(itemToUse, player, pawn, game);
                    view.getBorderPane().requestFocus();
                }
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
            this.view.getPawns().get(j)
                    .setTranslateX((actualPos.getX() - startPos.getX()) * ViewUtility.CELL_WIDTH + centerPawn(j));
            this.view.getPawns().get(j).setTranslateY((actualPos.getY() - startPos.getY()) * ViewUtility.CELL_WIDTH);
        }
    }

    private double centerPawn(final int j) {

        this.view.getPawns().get(j).setRadius(ViewUtility.CIRCLE_RADIUS);

        if (Movement.enemyPawnOntoPawn(game) && j / Constants.PLAYER_PAWNS > 0) {
            this.view.getPawns().get(j).setRadius(
                    this.view.getPawns().get(j).getRadius()
                            - ViewUtility.RADIUS_DECREASE * Double.valueOf(j) / Double.valueOf(Constants.PLAYER_PAWNS));
            final Double circleDiameter = this.view.getPawns().get(j).getRadius() * ViewUtility.RADIUS_TO_DIAMETER;
            return (Double.valueOf(ViewUtility.CELL_WIDTH - circleDiameter)) / 4.0;
        }
        return 0.0;
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
        if (itemToUse.getType() == ItemType.MALUS 
            && getGame().getHumanPlayer().equals(getGame().getTurn().getCurrentPlayer())) {
            malusClicked = true;
            return false;
        }
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
    public Boolean getMalusClicked() {
        return this.malusClicked;
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
