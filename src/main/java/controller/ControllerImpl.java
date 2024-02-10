package controller;

import javafx.scene.control.Button;
import javafx.stage.Stage;

import controller.api.Controller;
import model.Game;
import model.api.Item;
import model.api.Player;
import view.ViewUtility;

/**
 * Controller used to coordinate model and view.
 */
public class ControllerImpl implements Controller {
/*
    private static final int CELL_ONE = 1;
    private static final int CELL_TWO = 2;
    private static final int CELL_SIX = 6;
    private static final int CELL_EIGHT = 8;
    private static final int CELL_NINE = 9;
    private static final int CELL_TWELVE = 12;
    private static final int CELL_THIRTEEN = 13;
    private static final String NOT_ENOUGH_SPACE = "ATTENZIONE! NON HAI ABBASTANZA SPAZIO NELL'INVENTARIO!";
    private static final String NOT_ENOUGH_MONEY = "ATTENZIONE! NON HAI ABBASTANZA LUDOLLARI!";
    private static final String DUPLICATE = "ATTENZIONE! HAI GIA' QUESTO OGGETTO NEL TUO INVENTARIO!";

    private final String playerName;
*/
    private final int playersNumber;
    private final Game game;
//    private final Turn turn;
//    private final Map<Button, Cell> cells = new HashMap<>();
//    private boolean diceRolled;

    private boolean malusClicked;
    private Item itemToUse;

    /**
     * Constructor.
     * 
     * @param stage the stage
     * @param playerName the player name
     * @param playersNumber the number of players of the game
     */
    public ControllerImpl(final Stage stage, final String playerName, final int playersNumber) {

        this.playersNumber = playersNumber; //FIXME potrei prenderlo direttamente dal Game
        this.game = new Game(playerName, playersNumber);

        ViewUtility.createBoardScene(this, stage);

        //this.game.setCells(getCells().values());
        //this.turn = new Turn();

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
     * Returns the cells of the board.
     * 
     * @return the cells of the board

    public final Map<Button, Cell> getCells() {
        return new HashMap<>(this.cells);
    }

    /**
     * Add to cells Map a new button and his Cell.
     * 
     * @param button the button to add to Map cells
     * @param i      the y coordinate of the new Button
     * @param j      the x coordinate of the new Button

    public void addToCells(final Button button, final int i, final int j) {
        final Cell cell;
        if (i < CELL_SIX && (j < CELL_SIX || j >= CELL_NINE)
                || i >= CELL_NINE && (j < CELL_SIX || j >= CELL_NINE)) { // celle home
            cell = new CellImpl(new Position(j, i), true, false, true);
        } else if (i == CELL_SIX && j == CELL_ONE || i == CELL_ONE && j == CELL_EIGHT
                || i == CELL_EIGHT && j == CELL_THIRTEEN || i == CELL_THIRTEEN && j == CELL_SIX) { // celle safe (inizio
                                                                                                   // percorso)
            cell = new CellImpl(new Position(j, i), true, false, false);
        } else if (i == CELL_EIGHT && j == CELL_TWO || i == CELL_TWO && j == CELL_SIX
                || i == CELL_SIX && j == CELL_TWELVE || i == CELL_TWELVE && j == CELL_EIGHT) { // celle shop
            cell = new CellImpl(new Position(j, i), true, true, false);
        } else {
            cell = new CellImpl(new Position(j, i));
        }
        this.cells.put(button, cell);
    }
     */
    /**
     * Handles the click of each of the Shop buttons.
     * They are clicked when User tries to buy an Item.
     * 
     * @param clickedButton the Shop Button which was clicked
     * 
     * @return true if the User manages to buy the Item

    public Boolean clickShopButton(final Button clickedButton) {
        if (!this.diceRolled) {
            return false;
        }
        // implementare controllo se si ha già effettuato la propria mossa
        // implementare controllo se la pedina appena mossa è arrivata su una cella shop
        final Player humanPlayer = game.getPlayers().get(0);
        Item itemOfClickedButton = null;
        for (final Item item : game.getShowcase().values()) {
            if (item.getName().equals(clickedButton.getText())) {
                itemOfClickedButton = item;
            }
        }
        final String outcome = game.sellingItem(humanPlayer, itemOfClickedButton);
        if (NOT_ENOUGH_SPACE.equals(outcome) || NOT_ENOUGH_MONEY.equals(outcome) || DUPLICATE.equals(outcome)) {
            return false;
        }
        return true;
    }
    */

    /**
     * Checks whether the User clicked an Item Button with a bonus.
     * If so, tells the model to activate this bonus for the User player.
     * @param clickedButton the Item Button which was clicked.
     * @return true if the Button contains a bonus, false otherwise.
     */
    public Boolean clickBonusButton(final Button clickedButton) {
        final Player humanPlayer = game.getPlayers().get(0);
        for (final Item item : humanPlayer.getPlayerInventory().values()) {
            if (item.getName().equals(clickedButton.getText())) {
                this.itemToUse = item;
            }
        }
        if (!itemToUse.isBonus()) {
            malusClicked = true;
            return false;
        }
        humanPlayer.useItem(itemToUse, (Player) humanPlayer);
        return true;
    }

    /**
     * Checks if the User has correctly targeted an opponent after having clicked a MALUS Item.
     * @param targetPlayer the opponent Player who receives a malus for next turn.
     * @return true if clicked at the right time.
     */
    public Boolean clickPlayerTargetOfMalus(final Button targetPlayer) {
        if (!malusClicked) {
            return false;
        }
        //implementare in base a quale stringa conterrà il Button dei Player avversari
        malusClicked = false;
        return true;
    }

    /**
     * Checks if the User can roll the Dice.
     * 
     * @return true if the roll-Dice Button has been clicked at the right time

    public boolean clickRollDiceButton() {
        if (this.diceRolled) {
            return false;
        }
        this.diceRolled = true;
        return true;
    }
     */
    /**
     * Handles the click of any Button of the board.
     * 
     * @param clickedButton the button of the board which was clicked
     * 
     * @return true if the board Button was rightfully clicked

    public boolean clickBoardButton(final Button clickedButton) {
        return true;
    }
     */
    /**
     * Checks if it's the right moment to press ENTER.
     * 
     * @return true if ENTER key is pressed when it's actually possible to change turn

    public boolean pressEnterKey() {
        if (!this.diceRolled) {
            return false;
        }
        this.diceRolled = false;
        return true;
    }
     */
    /**
     * A computer player plays its turn.
     * 
     * @param i the computer player's index

    public void playTurn(final int i) {
        turn.setShowcase(game.getShowcase());
        turn.play(game.getPlayers().get(i), this.game);
    }
    */

    /**
     * Provides the GUI a String containing a Player and its Dice result.
     * 
     * @param i index of the current player
     * 
     * @return a String with the player's name and its dice result

    public String getDiceResult(final int i) {
        final String result = "Risultato " + game.getPlayers().get(i).getName() + ": ";
        if (i == 0) {
            return result + game.getPlayers().get(i).rollDice();
        }
        return result + turn.getDiceResult();
    }
     */
}
