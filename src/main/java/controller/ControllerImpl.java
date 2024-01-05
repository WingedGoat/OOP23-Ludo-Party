package controller;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.util.List;

import controller.api.Controller;
import view.ViewUtility;
import model.Game;
import model.api.Player;

/**
 * Controller used to coordinate model and view.
 */
public class ControllerImpl implements Controller {

    private final Scene board;
    private final String playerName;
    private final int playersNumber;
    private Player currentPlayer;

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
        this.board = ViewUtility.createBoardScene(stage, playerName);
        this.setInputHandler();
        final Game game = new Game(playerName, playersNumber);
        final List<Player> players = game.getPlayers();

        int turn = 0;
        int count = 0;
        while (count < players.size()) {
            if (players.size() > turn) {
                currentPlayer = players.get(turn);
            }
            if ("pi".equals(currentPlayer.getName())) {
                break;
            }
            // giocatore lancia dado
            // giocatore muove
            // giocatore completa turno (compra o usa carte)
            // giocatore segnala fine turno (boolean turnIsOver) e win/continue
            turn = (turn + 1) % playersNumber;
            count++;
        }
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
     * Metodo che imposta i comandi ottenibili da tastiera.
     */
    //TODO
    private void setInputHandler() {
        this.board.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                switch (e.getCode()) {
                case ENTER: // THROW_DICE
                    //this.board.getDice().throw();
                    break;
                default:
                    //System.out.println("Key Pressed: " + e.getCode());
                    break;
                }
            }
        });
    }

}
