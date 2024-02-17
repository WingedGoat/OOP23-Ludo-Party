package controller;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.Random;

import controller.api.Controller;
import model.GameImpl;
import model.Position;
import model.api.Game;
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

    private static final String NOT_ENOUGH_SPACE = "ATTENZIONE! NON HAI ABBASTANZA SPAZIO NELL'INVENTARIO!";
    private static final String NOT_ENOUGH_MONEY = "ATTENZIONE! NON HAI ABBASTANZA LUDOLLARI!";
    private static final String DUPLICATE = "ATTENZIONE! HAI GIA' QUESTO OGGETTO NEL TUO INVENTARIO!";

    private final int playersNumber;
    private final Game game;
    private final BoardScene view;
    private boolean malusClicked;
    private Item itemToUse;
    private String outcome;
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
    }

    @Override
    public int getPlayersNumber() {
        return this.playersNumber;
    }

    @Override
    public Game getGame() {
        return this.game;
    }

    private void setInputHandler() {
        // when finish the turn
        this.view.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER) && this.getGame().getTurn().getCurrentPlayer().canPassTurn()) {
                this.view.getShopPane().disableShop();
                // LOGGER.error(" -- end of turn -- ");
                for (int i = 1; i < getPlayersNumber(); i++) {
                    /*
                     * change inner player avatar color FIXME
                     * Circle c = (Circle) ((Group)
                     * (rightPane.getChildren().get(0))).getChildren().get(1);
                     * c.setFill(BColor.GREY.toString()));
                     * ((Group) (rightPane.getChildren().get(0))).getChildren().remove(1);
                     * ((Group) (rightPane.getChildren().get(0))).getChildren().add(1, c);
                     */

                    final Player player = this.game.getPlayers().get(i);
                    this.game.getTurn().passTurnTo(player);

                    ImageView diceImage = null;
                    Label coinsLabel = null;
                    if (getPlayersNumber() == 2) {
                        diceImage = (ImageView) ((Group) (this.view.getRightPane().getChildren().get(0)))
                                .getChildren().get(4);
                        coinsLabel = (Label) ((Group) (this.view.getRightPane().getChildren().get(0)))
                                .getChildren().get(3);
                    } else {
                        switch (i) {
                            case 1:
                                diceImage = (ImageView) ((Group) (this.view.getRightPane().getChildren().get(0)))
                                        .getChildren().get(4);
                                coinsLabel = (Label) ((Group) (this.view.getRightPane().getChildren().get(0)))
                                        .getChildren().get(3);
                                break;
                            case 2:
                                diceImage = (ImageView) ((Group) (this.view.getLeftPane().getChildren().get(1)))
                                        .getChildren().get(4);
                                coinsLabel = (Label) ((Group) (this.view.getLeftPane().getChildren().get(1)))
                                        .getChildren().get(3);
                                break;
                            default:
                                diceImage = (ImageView) ((Group) (this.view.getRightPane().getChildren().get(1)))
                                        .getChildren().get(4);
                                coinsLabel = (Label) ((Group) (this.view.getRightPane().getChildren().get(1)))
                                        .getChildren().get(3);
                                break;
                        }
                    }
                    final int diceResult = player.rollDice();
                    this.view.getLeftPane().showDiceNumber(diceImage, diceResult);

                    int indexPawnToMove = r.nextInt(Constants.PLAYER_PAWNS);
                    /*
                     * Il Computer cambia la scelta del Pawn da muovere, finché:
                     * continua a sceglierne uno che NON si può muovere, però
                     * ne ha altri che POSSONO effettuare un movimento
                     */
                    while (!player.canMovePawns(diceResult) && player.getPawns().get(indexPawnToMove).canMove(diceResult)) {
                        indexPawnToMove = r.nextInt(Constants.PLAYER_PAWNS);
                    }

                    final Pawn pawnToMove = player.getPawns().get(indexPawnToMove);
                    pawnToMove.move(diceResult, this.game);

                    updatePawnPositions();
                    player.earnCoins(diceResult);
                    coinsLabel.setText("Ludollari: " + player.getCoins());
                }

                this.game.getTurn().passTurnTo(this.game.getHumanPlayer());
            }
        });

        for (int i = 0; i < this.view.getPawns().size(); i++) {
            final Circle circle = this.view.getPawns().get(i);
            final Player player = this.game.getPlayers().get(i / Constants.PLAYER_PAWNS);
            final Pawn pawn = player.getPawns().get(i % Constants.PLAYER_PAWNS);

            circle.setOnMouseEntered(event -> circle.setCursor(Cursor.HAND));

            circle.setOnMouseClicked(e -> {
                if (player.canMovePawn(pawn)) {
                    // final Position actualPos = player.getPawns().get(index).getPosition();
                    pawn.move(player.getDiceResult(), this.game);
                    updatePawnPositions();
                    final Label coinsLabel = (Label) ((Group) (this.view.getLeftPane().getChildren().get(0)))
                        .getChildren().get(3);
                    player.earnCoins(this.game.getTurn().getDiceResult());
                    coinsLabel.setText("Ludollari: " + player.getCoins());

                    if (this.game.getBoard().getShops().contains(pawn.getPosition())) {
                        this.view.getShopPane().ableShop();
                    }
                } else if (getMalusClicked()
                        && !player.equals(this.game.getTurn().getCurrentPlayer())) {
                    final Label message = new Label(this.game.getTurn().getCurrentPlayer().getName() + " ha usato "
                            + getItemToUse().getName() + " su " + player.getName());
                    message.setBackground(
                            new Background(new BackgroundFill(Color.PURPLE, CornerRadii.EMPTY, Insets.EMPTY)));
                    view.getShopPane().getPopupMessage().getContent().add(message);
                    view.getShopPane().getPopupMessage().show(view.getWindow());
                    this.game.getTurn().getCurrentPlayer().useItem(itemToUse, player, pawn, game);
                    view.getBorderPane().requestFocus();
                }
            });
        }
    }

    @Override
    public void updatePawnPositions() {
        for (int j = 0; j < getPlayersNumber() * Constants.PLAYER_PAWNS; j++) {
            final Position startPos = this.game.getPlayers().get(j / Constants.PLAYER_PAWNS)
                    .getPawns().get(j % Constants.PLAYER_PAWNS).getStartPosition();
            final Position actualPos = this.game.getPlayers().get(j / Constants.PLAYER_PAWNS)
                    .getPawns().get(j % Constants.PLAYER_PAWNS).getPosition();
            this.view.getPawns().get(j).setTranslateX((actualPos.getX() - startPos.getX()) * ViewUtility.CELL_WIDTH);
            this.view.getPawns().get(j).setTranslateY((actualPos.getY() - startPos.getY()) * ViewUtility.CELL_WIDTH);
        }
    }

    @Override
    public boolean humanClickShopButton(final Button clickedButton, final Item item) {
        if (!this.game.getTurn().getCurrentPlayer().isDiceRolled()) {
            return false;
        }
        // implementare controllo se la pedina appena mossa è arrivata su una cella shop
        final Item itemOfClickedButton = item;

        setShopMessage(game.buyItem(this.game.getHumanPlayer(), itemOfClickedButton));
        if (NOT_ENOUGH_SPACE.equals(outcome) || NOT_ENOUGH_MONEY.equals(outcome) || DUPLICATE.equals(outcome)) {
            return false;
        }
        this.view.getInventoryPane().addItem(itemOfClickedButton, this, view);
        return true;
    }

    @Override
    public Boolean clickBonusButton(final Item itemToUse) {
        if (itemToUse.getType() == ItemType.MALUS) {
            malusClicked = true;
            return false;
        }
        return true;
    }

    @Override // FIXME sta venendo utilizzato ?
    public Boolean clickPlayerTargetOfMalus(final Button targetPlayer) {
        if (!malusClicked) {
            return false;
        }
        // implementare in base a quale stringa conterrà il Button dei Player avversari
        malusClicked = false;
        return true;
    }

    /**
     * A computer player plays its turn.
     * 
     * @param i the computer player's index
     * 
     *          public void playTurn(final int i) {
     *          turn.setShowcase(game.getShowcase());
     *          turn.play(game.getPlayers().get(i), this.game);
     *          }
     */

    /*
     * Provides the GUI a String containing a Player and its Dice result.
     * 
     * @param i index of the current player
     * 
     * @return a String with the player's name and its dice result
     * 
     * public String getDiceResult(final int i) {
     * final String result = "Risultato " +
     * game.getPlayers().get(i).getName() + ": ";
     * if (i == 0) {
     * return result + game.getPlayers().get(i).rollDice();
     * }
     * return result + turn.getDiceResult();
     * }
     */

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

    @Override
    public void run() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'run'");
    }

}
