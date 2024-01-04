package controller;

import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import controller.api.Controller;
import model.PlayerImpl;
import model.api.Player;
import view.BoardView;

/**
 * Controller used to coordinate model and view.
 */
@SuppressWarnings("PMD")
public class ControllerImpl implements Controller {

    private final Stage board;
    private final int playersNumber;

    /**
     * Controller Impl constructor.
     * @param playerName
     *                  the player name
     * @param playersNumber 
     *                  the number of players of the game
     */
    public ControllerImpl(final String playerName, final int playersNumber) {
        Player player = new PlayerImpl(playerName);
        this.playersNumber = playersNumber;

        // initGame()
        this.board = new BoardView(player, this.playersNumber);
        this.setInputHandler();
    }

    /**
     * Metodo che imposta i comandi ottenibili da tastiera.
     */
    //TODO
    private void setInputHandler() {
        this.board.getScene().setOnKeyPressed(e -> {
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
