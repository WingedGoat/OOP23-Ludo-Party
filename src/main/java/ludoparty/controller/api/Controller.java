package ludoparty.controller.api;

import ludoparty.model.api.Game;
import ludoparty.model.api.Item;
import ludoparty.view.BoardScene;

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
     * Returns the board view.
     * @return the board view
     */
    BoardScene getView();

    /**
     * Saves the score of the player.
     * @param name the player name
     */
    void saveScore(String name);

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
