package controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import model.CellImpl;
import model.Game;
import model.Turn;
import model.api.Cell;
import model.api.Player;
import utility.Position;
import controller.api.Controller;
import view.BoardScene;
import view.ViewUtility;

/**
 * Controller used to coordinate model and view.
 */
public class ControllerImpl implements Controller {

    private final BoardScene board;
    private final String playerName;
    private final int playersNumber;
    private final List<Player> players;
    private final Turn turn = new Turn();
    private final Map<Button, Cell> cells = new HashMap<>();

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
        this.board = (BoardScene) ViewUtility.createBoardScene(this, stage, playerName);
        final Game game = new Game(playerName, playersNumber);
        this.players = game.getPlayers();

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
    public Map<Button, Cell> getCells() {
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
     */
    public void clickRollDiceButton() {
        final Player currentPlayer = players.get(0);
        this.turn.changeTurn(currentPlayer);
        board.getPlayerPanel().getDiceLabel().setText("Risultato " + currentPlayer.getName() + ": " + turn.getDiceResult());
        board.getPlayerPanel().getRollDiceButton().setDisable(true);
    }

    /**
     * Handles the pression of ENTER key.
     */
    public void pressEnterKey() {
        int i = 1;
        while (i < players.size()) {
            turn.changeTurn(players.get(i));
            board.getPlayerPanel().getDiceLabel().setText(
                board.getPlayerPanel().getDiceLabel().getText() + "\n"
                + "Risultato " + players.get(i).getName() + ": " + turn.getDiceResult()
            );
            i++;
        }
        board.getPlayerPanel().getRollDiceButton().setDisable(false);
    }

}
