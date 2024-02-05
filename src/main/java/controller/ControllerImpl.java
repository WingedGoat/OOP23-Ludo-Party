package controller;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.Map;

import model.CellImpl;
import model.Game;
import model.PlayerImpl;
import model.Turn;
import model.api.Cell;
import model.api.Item;
import controller.api.Controller;
import view.ViewUtility;
import utility.Position;

/**
 * Controller used to coordinate model and view.
 */
public class ControllerImpl implements Controller {

    private static final int CELL_ONE = 1;
    private static final int CELL_TWO = 2;
    private static final int CELL_SIX = 6;
    private static final int CELL_EIGHT = 8;
    private static final int CELL_NINE = 9;
    private static final int CELL_TWELVE = 12;
    private static final int CELL_THIRTEEN = 13;

    private final String playerName;
    private final int playersNumber;
    private final Game game;
    private final Turn turn;
    private final Map<Button, Cell> cells = new HashMap<>();
    private boolean diceRolled;

    /**
     * Controller Impl constructor.
     * @param stage
     *              the stage
     * @param playerName
     *              the player name
     * @param playersNumber 
     *              the number of players of the game
     */
    public ControllerImpl(final Stage stage, final String playerName, final int playersNumber) {
        this.playerName = playerName;
        this.playersNumber = playersNumber;

        // initGame()
        this.game = new Game(playerName, playersNumber);
        ViewUtility.createBoardScene(this, stage, playerName);

        this.game.setCells(getCells().values());
        this.turn = new Turn();

            // giocatore muove pedina
            // giocatore completa turno (compra o usa carte)
            // giocatore segnala fine turno (boolean turnIsOver) e win/continue
    }

    /**
     * Returns the player name.
     * @return playerName.
     */
    public final String getPlayerName() {
        return this.playerName;
    }

    /**
     * Returns the players number.
     * @return playersNumber.
     */
    public final int getPlayersNumber() {
        return this.playersNumber;
    }

    /**
     * Returns the cells of the board.
     * @return the board cells.
     */
    public final Map<Button, Cell> getCells() {
        final Map<Button, Cell> myCells = new HashMap<>();
        myCells.putAll(this.cells);
        return myCells;
    }

    /**
     * Add to cells Map a new button and his Cell.
     * @param button the button to add to Map cells.
     * @param i the y coordinate of the new Button.
     * @param j the x coordinate of the new Button.
     */
    public void addToCells(final Button button, final int i, final int j) {
        final CellImpl cell;
        if (i < CELL_SIX && (j < CELL_SIX || j >= CELL_NINE)
                || i >= CELL_NINE && (j < CELL_SIX || j >= CELL_NINE)) { // celle home
            cell = new CellImpl(new Position(j, i), true, false, true);
        } else if (i == CELL_SIX && j == CELL_ONE || i == CELL_ONE && j == CELL_EIGHT
                || i == CELL_EIGHT && j == CELL_THIRTEEN || i == CELL_THIRTEEN && j == CELL_SIX) { // celle safe (inizio percorso)
            cell = new CellImpl(new Position(j, i), true, false, false);
        } else if (i == CELL_EIGHT && j == CELL_TWO || i == CELL_TWO && j == CELL_SIX
                || i == CELL_SIX && j == CELL_TWELVE || i == CELL_TWELVE && j == CELL_EIGHT) { // celle shop
            cell = new CellImpl(new Position(j, i), true, true, false);
        } else {
            cell = new CellImpl(new Position(j, i));
        }
        this.cells.put(button, cell);
    }

    /**
     * Handles the click of each of the Shop buttons.
     * They are clicked when User tries to buy an Item.
     * @param clickedButton the Shop Button which was clicked.
     * @return true if the User manages to buy the Item.
     */
    public Boolean clickShopButton(final Button clickedButton) {
        if (!this.diceRolled) {
            return false;
        }
        // implementare controllo se si ha già effettuato la propria mossa
        // implementare controllo se la pedina appena mossa è arrivata su una cella shop
        final PlayerImpl humanPlayer = (PlayerImpl) game.getPlayers().get(0);
        Item itemOfClickedButton = null;
        for (final Item item : game.getShowcase().values()) {
            if (item.getName().equals(clickedButton.getText())) {
                itemOfClickedButton = item;
            }
        }
        game.sellingItem(humanPlayer, itemOfClickedButton);
        return true;
    }

    /**
     * Checks if the User can roll the Dice.
     * @return true if the roll-Dice Button has been clicked at the right time.
     */
    public Boolean clickRollDiceButton() {
        if (this.diceRolled) {
            return false;
        }
        this.diceRolled = true;
        return true;
    }

    /**
     * Handles the click of any Button of the board.
     * @param clickedButton the button of the board which was clicked.
     * @return true if the board Button was rightfully clicked.
     */
    public Boolean clickBoardButton(final Button clickedButton) {
        return true;
    }

    /**
     * Checks if it's the right moment to press ENTER.
     * @return true if ENTER key is pressed when it's actually possible to change turn.
     */
    public Boolean pressEnterKey() {
        if (!this.diceRolled) {
            return false;
        }
        this.diceRolled = false;
        return true;
    }

    /**
     * A computer plays its turn.
     * @param i the computer player's index.
     */
    public void playTurn(final int i) {
        turn.setShowcase(game.getShowcase());
        turn.play(game.getPlayers().get(i), this.game);
    }

    /**
     * Provides the GUI a String containing a Player and its Dice result.
     * @param i index of the current Player.
     * @return a String with the Player's name and its Dice result.
     */
    public String getDiceResult(final int i) {
        final String result = "Risultato " + game.getPlayers().get(i).getName() + ": ";
        if (i == 0) {
            return result + game.getPlayers().get(i).rollDice();
        }
        return result + turn.getDiceResult();
    }

}
