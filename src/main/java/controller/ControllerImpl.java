package controller;

import javafx.scene.control.Button;
import javafx.stage.Stage;

import controller.api.Controller;
import model.GameImpl;
import model.api.Game;
import model.api.Item;
import model.api.Player;
import view.ViewUtility;

/**
 * Controller used to coordinate model and view.
 */
public class ControllerImpl implements Controller {
    /*
     * private static final String NOT_ENOUGH_SPACE =
     * "ATTENZIONE! NON HAI ABBASTANZA SPAZIO NELL'INVENTARIO!";
     * private static final String NOT_ENOUGH_MONEY =
     * "ATTENZIONE! NON HAI ABBASTANZA LUDOLLARI!";
     * private static final String DUPLICATE =
     * "ATTENZIONE! HAI GIA' QUESTO OGGETTO NEL TUO INVENTARIO!";
     */
    private final int playersNumber;
    private final Game game;
    private boolean diceRolled;
    private boolean pawnMoved;

    private boolean malusClicked;
    private Item itemToUse;

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
    public final int getPlayersNumber() {
        return this.playersNumber;
    }

    @Override
    public final Game getGame() {
        return this.game;
    }

    /**
     * Handles the click of each of the Shop buttons.
     * They are clicked when User tries to buy an Item.
     * 
     * @param clickedButton the Shop Button which was clicked
     * 
     * @return true if the User manages to buy the Item
     * 
     *         public Boolean clickShopButton(final Button clickedButton) {
     *         if (!this.diceRolled) {
     *         return false;
     *         }
     *         // implementare controllo se si ha già effettuato la propria mossa
     *         // implementare controllo se la pedina appena mossa è arrivata su una
     *         cella shop
     *         final Player humanPlayer = game.getPlayers().get(0);
     *         Item itemOfClickedButton = null;
     *         for (final Item item : game.getShowcase().values()) {
     *         if (item.getName().equals(clickedButton.getText())) {
     *         itemOfClickedButton = item;
     *         }
     *         }
     *         final String outcome = game.sellingItem(humanPlayer,
     *         itemOfClickedButton);
     *         if (NOT_ENOUGH_SPACE.equals(outcome) ||
     *         NOT_ENOUGH_MONEY.equals(outcome) || DUPLICATE.equals(outcome)) {
     *         return false;
     *         }
     *         return true;
     *         }
     */

    /**
     * Checks whether the player clicked an item button with a bonus.
     * If true, tells the model to activate this bonus for the player.
     * 
     * @param clickedButton the Item Button which was clicked.
     * @return true if the Button contains a bonus, false otherwise.
     */
    public Boolean clickBonusButton(final Button clickedButton) {
        final Player humanPlayer = game.getPlayers().get(0);
        for (final Item item : humanPlayer.getPlayerItems()) {
            if (item.getName().equals(clickedButton.getText())) {
                this.itemToUse = item;
            }
        }
        if (itemToUse.getType() == Item.Type.MALUS) {
            malusClicked = true;
            return false;
        }
        humanPlayer.useItem(itemToUse, humanPlayer);
        return true;
    }

    /**
     * Checks if the player has correctly targeted an opponent after having clicked
     * a MALUS Item.
     * 
     * @param targetPlayer the opponent player who receives a malus for next turn
     * @return true if clicked at the right time
     */
    public Boolean clickPlayerTargetOfMalus(final Button targetPlayer) {
        if (!malusClicked) {
            return false;
        }
        // implementare in base a quale stringa conterrà il Button dei Player avversari
        malusClicked = false;
        return true;
    }

    @Override
    public final boolean canRollDice() {
        if (this.diceRolled) {
            return false;
        }
        this.diceRolled = true;
        return true;
    }

    @Override
    public final boolean canMovePawn() {
        if (!this.diceRolled || this.pawnMoved) {
            return false;
        }
        this.pawnMoved = true;
        return true;
    }

    @Override
    public final boolean canPassTurn() {
        if (!this.pawnMoved) {
            return false;
        }
        this.diceRolled = false;
        this.pawnMoved = false;
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

    /**
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
}
