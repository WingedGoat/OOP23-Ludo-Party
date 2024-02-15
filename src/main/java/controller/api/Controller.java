package controller.api;

import model.api.Game;
import model.api.Pawn;

/**
 * Controller.
 */
public interface Controller {

    /**
     * Returns the players number.
     * 
     * @return playersNumber
     */
    int getPlayersNumber();

    /**
     * Returns the game.
     * 
     * @return the game
     */
    Game getGame();

    /**
     * Checks if the User can roll the Dice.
     * 
     * @return true if the roll-Dice Button has been clicked at the right time.
     */
    boolean canRollDice();

    /**
     * Checks if the User can move the clicked Pawn.
     * @param pawn the clicked Pawn.
     * @return true if the clicked Pawn can be moved.
     */
    boolean canMovePawn(Pawn pawn);

    /**
     * Checks if the User can pass the Turn by pressing ENTER.
     * 
     * @return true if ENTER key is pressed when it's actually possible to change turn
     */
    boolean canPassTurn();

}
