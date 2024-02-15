package controller;

import javafx.scene.control.Button;
import javafx.stage.Stage;

import controller.api.Controller;
import model.GameImpl;
import model.api.Game;
import model.api.Item;
import model.api.Pawn;
import model.api.Player;
import utility.BColor;
import view.ViewUtility;

/**
 * Controller used to coordinate model and view.
 */
public final class ControllerImpl implements Controller {

    private static final String NOT_ENOUGH_SPACE = "ATTENZIONE! NON HAI ABBASTANZA SPAZIO NELL'INVENTARIO!";
    private static final String NOT_ENOUGH_MONEY = "ATTENZIONE! NON HAI ABBASTANZA LUDOLLARI!";
    private static final String DUPLICATE = "ATTENZIONE! HAI GIA' QUESTO OGGETTO NEL TUO INVENTARIO!";

    private final int playersNumber;
    private final Game game;
    private boolean diceRolled;
    private boolean pawnMoved;

    private boolean malusClicked;
    private Item itemToUse;
    private String outcome;

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

        ViewUtility.createBoardScene(this, stage);

        // giocatore muove pedina
        // giocatore completa turno (compra o usa carte)
        // giocatore segnala fine turno (boolean turnIsOver) e win/continue
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
        return true;
    }

    @Override
    public Boolean clickBonusButton(final Item itemToUse) { 
        if (itemToUse.getType() == Item.Type.MALUS) {
            malusClicked = true;
            return false;
        }
        return true;
    }

    @Override
    public Boolean clickPlayerTargetOfMalus(final Button targetPlayer) {
        if (!malusClicked) {
            return false;
        }
        //implementare in base a quale stringa conterrà il Button dei Player avversari
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
         * Già verificata (in PlayerPanelLeft) la casistica in cui non è possibile muovere nessuna pedina.
         */
        if (getGame().getMovement().playerCanMoveThePawn(pawn, diceResult)) {
            this.pawnMoved = true;
        }
        return true;
    }

    /**
     * Checks if it's the right moment to press ENTER.
     * 
     * @return true if ENTER key is pressed when it's actually possible to change turn
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
     *         public String getDiceResult(final int i) {
     *         final String result = "Risultato " +
     *         game.getPlayers().get(i).getName() + ": ";
     *         if (i == 0) {
     *         return result + game.getPlayers().get(i).rollDice();
     *         }
     *         return result + turn.getDiceResult();
     *         }
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

}
