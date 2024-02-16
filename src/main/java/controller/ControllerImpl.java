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
import utils.BColor;
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
    private boolean diceRolled;
    private boolean pawnMoved;
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
            if (e.getCode().equals(KeyCode.ENTER) && canPassTurn()) {
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

                    getGame().getTurn().passTurnTo(getGame().getPlayers().get(i));
                    ImageView diceImage = null;
                    if (getPlayersNumber() == 2) {
                        diceImage = (ImageView) ((Group) (this.view.getRightPane().getChildren().get(0)))
                                .getChildren().get(4);
                    } else {
                        switch (i) {
                            case 1:
                                diceImage = (ImageView) ((Group) (this.view.getRightPane().getChildren().get(0)))
                                        .getChildren().get(4);
                                break;
                            case 2:
                                diceImage = (ImageView) ((Group) (this.view.getLeftPane().getChildren().get(1)))
                                        .getChildren().get(4);
                                break;
                            default:
                                diceImage = (ImageView) ((Group) (this.view.getRightPane().getChildren().get(1)))
                                        .getChildren().get(4);
                                break;
                        }
                    }
                    final int diceResult = getGame().getTurn().getCurrentPlayer().rollDice();
                    this.view.getLeftPane().showDiceNumber(diceImage, diceResult);

                    int indexPawnToMove = r.nextInt(getGame().getPlayers().get(i).getPawns().size());
                    /*
                     * Il Computer cambia la scelta del Pawn da muovere, finché:
                     * continua a sceglierne uno che NON si può muovere, però
                     * ne ha altri che POSSONO effettuare un movimento
                     */
                    while (!getGame().getMovement().playerCanMoveThePawn(
                            getGame().getPlayers().get(i).getPawns().get(indexPawnToMove), diceResult)
                            && getGame().getMovement().playerCanMovePawns(
                                    diceResult, getGame().getPlayers().get(i))) {
                        indexPawnToMove = r.nextInt(getGame().getPlayers().get(i).getPawns().size());
                    }
                    /*
                     * final Position pos = controller.getGame().getPlayers().get(i)
                     * .getPawns().get(indexPawnToMove).getStartPosition();
                     */
                    final Pawn pawnToMove = getGame().getPlayers().get(i).getPawns().get(indexPawnToMove);
                    pawnToMove.move(diceResult, getGame());

                    updatePawnPositions();
                }
            }
        });

        for (int i = 0; i < this.view.getPawns().size(); i++) {
            final Circle circle = this.view.getPawns().get(i);
            final Player player = this.getGame().getPlayers().get(i / Constants.PLAYER_PAWNS);
            final Pawn pawn = player.getPawns().get(i % Constants.PLAYER_PAWNS);

            circle.setOnMouseEntered(event -> circle.setCursor(Cursor.HAND));

            circle.setOnMouseClicked(e -> {
                if (canMovePawn(pawn)) {
                    // final Position actualPos = player.getPawns().get(index).getPosition();
                    pawn.move(getGame().getTurn().getDiceResult(), getGame());
                    updatePawnPositions();
                } else if (getMalusClicked()
                        && !player.equals(getGame().getTurn().getCurrentPlayer())) {
                    final Label message = new Label(getGame().getTurn().getCurrentPlayer().getName() + " ha usato " 
                            + getItemToUse().getName() + " su " + player.getName());
                    message.setBackground(new Background(new BackgroundFill(Color.PURPLE, CornerRadii.EMPTY, Insets.EMPTY)));
                    view.getShopPane().getPopupMessage().getContent().add(message);
                    view.getShopPane().getPopupMessage().show(view.getWindow());
                    getGame().getTurn().getCurrentPlayer().useItem(itemToUse, player, pawn, game);
                    view.getBorderPane().requestFocus();
                }
            });
        }
    }

    @Override
    public void updatePawnPositions() {
        for (int j = 0; j < getPlayersNumber() * Constants.PLAYER_PAWNS; j++) {
            final Position startPos = getGame().getPlayers().get(j / Constants.PLAYER_PAWNS)
                    .getPawns().get(j % Constants.PLAYER_PAWNS).getStartPosition();
            final Position actualPos = getGame().getPlayers().get(j / Constants.PLAYER_PAWNS)
                    .getPawns().get(j % Constants.PLAYER_PAWNS).getPosition();
            this.view.getPawns().get(j).setTranslateX((actualPos.getX() - startPos.getX()) * ViewUtility.CELL_WIDTH);
            this.view.getPawns().get(j).setTranslateY((actualPos.getY() - startPos.getY()) * ViewUtility.CELL_WIDTH);
        }
    }

    @Override
    public Boolean humanClickShopButton(final Button clickedButton, final Item item) {
        if (!this.diceRolled) {
            return false;
        }
        // implementare controllo se la pedina appena mossa è arrivata su una cella shop
        final Player humanPlayer = game.getPlayers().get(0);
        final Item itemOfClickedButton = item;

        setShopMessage(game.buyItem(humanPlayer, itemOfClickedButton));
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

    @Override //FIXME sta venendo utilizzato ? 
    public Boolean clickPlayerTargetOfMalus(final Button targetPlayer) {
        if (!malusClicked) {
            return false;
        }
        // implementare in base a quale stringa conterrà il Button dei Player avversari
        malusClicked = false;
        return true;
    }

    @Override
    public boolean canRollDice() {
        if (this.diceRolled) {
            return false;
        }
        this.diceRolled = true;
        return true;
    }

    @Override
    public boolean canMovePawn(final Pawn pawn) {
        if (!this.diceRolled || this.pawnMoved) {
            return false;
        }
        if (pawn.getColor() != BColor.BLUE) {
            return false;
        }
        final int diceResult = getGame().getTurn().getDiceResult();
        /*
         * Se è possibile muovere la pedina cliccata, imposto pawnMoved a true.
         * Già verificata (in PlayerPanelLeft) la casistica in cui non è possibile
         * muovere nessuna pedina.
         */
        if (getGame().getMovement().playerCanMoveThePawn(pawn, diceResult)) {
            this.pawnMoved = true;
        }
        return true;
    }

    /**
     * Checks if it's the right moment to press ENTER.
     * 
     * @return true if ENTER key is pressed when it's actually possible to change
     *         turn
     */
    @Override
    public boolean canPassTurn() {
        if (!this.pawnMoved) {
            return false;
        }
        this.diceRolled = false;
        this.pawnMoved = false;
        return true;
    }

    @Override
    public void setPawnMoved(final boolean b) {
        this.pawnMoved = b;
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
