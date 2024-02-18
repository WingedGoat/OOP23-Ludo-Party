package controller.api;

import javafx.scene.control.Button;
import model.api.Game;
import model.api.Item;

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
     * @return true if is possible selling to the current player
     */
    boolean isPossibleSelling();

    /**
     * Start the oparatio of selling item to the current player.
     * @param item
     */
    void sellingItemToPlayer(Item item);

    /**
     * Return treu if the item in the selling operation got selled, false instead.
     * @return true or false
     */
    boolean isItemSelled();

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
     * Update pawns positions.
     */
    void updatePawnPositions();

    /**
     * Return the new item added in the showcase.
     * 
     * @return the new shop's item
     */
    Item getNewShopItem();
}
