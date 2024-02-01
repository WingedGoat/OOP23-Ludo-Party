package controller;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import model.CellImpl;
import model.Game;
import model.Turn;
import model.api.Cell;
import model.api.Player;
import controller.api.Controller;
import view.BoardScene;
import view.ViewUtility;
import utility.Position;

/**
 * Controller used to coordinate model and view.
 */
public class ControllerImpl implements Controller {

    private final BoardScene board;
    private final String playerName;
    private final int playersNumber;
    private final Game game;
    private final Turn turn = new Turn();
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
        this.board = (BoardScene) ViewUtility.createBoardScene(this, stage, playerName);

        this.game.setCells(getCells().values());

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
        for (final Entry<Button, Cell> e : this.cells.entrySet()) {
            myCells.put(e.getKey(), e.getValue());
        }
        return myCells;
    }

    /**
     * Add to cells Map a new button and his Cell.
     * @param button the button to add to Map cells.
     * @param i the y coordinate of the new Button.
     * @param j the x coordinate of the new Button.
     */
    public void addToCells(final Button button, final int i, final int j) {
        this.cells.put(button, new CellImpl(new Position(j, i)));
    }

    /**
     * Handles the click of the roll-dice Button.
     * @return true if the Dice has been rolled.
     */
    public Boolean clickRollDiceButton() {
        if (this.diceRolled) {
            return false;
        }
        final Player currentPlayer = game.getPlayers().get(0);
        this.turn.changeTurn(currentPlayer);
        board.getPlayerPanel().getDiceLabel().setText("Risultato " + currentPlayer.getName() + ": " + turn.getDiceResult());
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
     * Handles the pression of ENTER key.
     * @return true if ENTER key is pressed when it's actually possible to change turn.
     */
    public Boolean pressEnterKey() {
        if (!this.diceRolled) {
            return false;
        }
        int i = 1;
        while (i < game.getPlayers().size()) {
            turn.changeTurn(game.getPlayers().get(i));
            board.getPlayerPanel().getDiceLabel().setText(
                board.getPlayerPanel().getDiceLabel().getText() + "\n"
                + "Risultato " + game.getPlayers().get(i).getName() + ": " + turn.getDiceResult()
            );
            i++;
        }
        this.diceRolled = false;
        return true;
    }

}
