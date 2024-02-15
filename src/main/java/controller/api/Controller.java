package controller.api;

import javafx.scene.control.Button;
import model.api.Game;
import model.api.Item;
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
     * Return true if is possible for the human player to use the shop.
     * 
     * @param clickedButton
     * @param item
     * @return true if the shop was clicked
     */
    Boolean humanClickShopButton(Button clickedButton, Item item);

    /**
     * Checks whether the player clicked an item button with a bonus.
     * If true, tells the model to activate this bonus for the player.
     * 
     * @param itemUsed the Item in the Button which was clicked.
     * @return true if the Button contains a bonus, false otherwise.
     */
    Boolean clickBonusButton(Item itemUsed);

    /**
     * Checks if the player has correctly targeted an opponent after having clicked a MALUS Item.
     * 
     * @param targetPlayer the opponent player who receives a malus for next turn
     * @return true if clicked at the right time
     */
    Boolean clickPlayerTargetOfMalus(Button targetPlayer);

    /**
     * Set the last item used for the next apllication.
     * 
     * @param itemClicked
     */
    void setItemToUse(Item itemClicked);

    /**
     * Return the last item used.
     * 
     * @return the last item used 
     */
    Item getItemToUse();

    /**
     * Return true if the last item used is a malus,
     * false if is a Bonus.
     * 
     * @return if the last item ised is a malus
     */
    Boolean getMalusClicked();

    /**
     * Return the shop message after the shop action.
     * 
     * @return the shop message
     */
    String getShopMessage();

    /**
     * Set the new message from the last shop action.
     * 
     * @param newMessage
     */
    void setShopMessage(String newMessage);

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

    /**
     * Set the pawnMoved field of controller.
     * @param b the boolean value to set.
     */
    void setPawnMoved(boolean b);

    /**
     * Update pawns positions.
     */
    void updatePawnPositions();

}
