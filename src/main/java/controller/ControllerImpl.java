package controller;

import java.util.List;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import model.Game;
import model.api.Player;
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
    private Player currentPlayer;
    private final List<Player> players;
    private int diceResult;

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
        this.board = (BoardScene) ViewUtility.createBoardScene(stage, playerName);
        this.setInputHandler();
        final Game game = new Game(playerName, playersNumber);
        this.players = game.getPlayers();

        board.getPlayerPanel().getRollDiceButton().setOnAction(e -> {
            currentPlayer = players.get(0);
            this.diceResult = currentPlayer.throwDice();
            board.getPlayerPanel().getRollDiceButton().setDisable(true);
        });

            // giocatore muove pedina
            // giocatore completa turno (compra o usa carte)
            // giocatore segnala fine turno (boolean turnIsOver) e win/continue
    }

    private void changeTurn() {
        int turn = 1;
        while (turn < players.size()) {
            currentPlayer = players.get(turn);
            this.diceResult = currentPlayer.throwDice();
            // giocatore muove pedina
            turn++;
        }
        board.getPlayerPanel().getRollDiceButton().setDisable(false);
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
     * Returns the dice result.
     * @return the result of the dice.
     */
    public int getDiceResult() {
        return this.diceResult;
    }

    /**
     * Set input handler for ENTER key pressed.
     */
    private void setInputHandler() {
        this.board.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                this.changeTurn();
            }
        });
    }

}
