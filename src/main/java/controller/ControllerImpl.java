package controller;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import controller.api.Controller;
import view.ViewUtility;

/**
 * Controller used to coordinate model and view.
 */
public class ControllerImpl implements Controller {

    private final Scene board;
    private final String playerName;
    private final int playersNumber;

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
        this.board = ViewUtility.createBoardScene(stage);
        this.setInputHandler();
    }

    /**
     * Returns the player name.
     * @return playerName
     */
    public String getPlayerName() {
        return this.playerName;
    }

    /**
     * Returns the players number.
     * @return playersNumber
     */
    public int getPlayersNumber() {
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
