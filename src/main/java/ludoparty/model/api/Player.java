package ludoparty.model.api;

import java.util.List;
import ludoparty.utils.BColor;

/**
 * Represents a player.
 */
public interface Player {

    /**
     * Player Type.
     * 
     * @see HUMAN
     * @see COMPUTER
     */
    enum PlayerType {
        /**
         * Human player type.
         */
        HUMAN,
        /**
         * Computer player type.
         */
        COMPUTER;
    }

    /**
     * Gets the name of the player.
     * 
     * @return the name
     */
    String getName();

    /**
     * Gets the player type {@link PlayerType}.
     * 
     * @return the player type
     */
    PlayerType getType();

    /**
     * Gets the color of the home box.
     * 
     * @return the color
     */
    BColor getColor();

    /**
     * Gets the pawns of the player.
     * 
     * @return the pawns list
     */
    List<Pawn> getPawns();

    /**
     * Gets the amount of coins owned by the player.
     * 
     * @return the amount of coins
     */
    int getCoins();

    /**
     * Rolls the dice.
     */
    void rollDice();

    /**
     * Gets the dice result.
     * 
     * @return the dice result
     */
    int getDiceResult();

    /**
     * Gets the number of steps of the pawn.
     * 
     * @return the number of steps
     */
    int getSteps();

    /**
     * Returns true if dice is rolled.
     * 
     * @return true if dice is rolled
     */
    boolean isDiceRolled();

    /**
     * Checks if the player can roll the dice.
     * 
     * @return true if the dice has been clicked at the right time
     */
    boolean canRollDice();

    /**
     * Checks if it's the right moment to press ENTER.
     * 
     * @param game the Game.
     * @return true if ENTER key is pressed when it's actually possible to change
     *         turn
     */
    boolean canPassTurn(Game game);

    /**
     * Checks if the User can move the clicked Pawn.
     * 
     * @param pawn the clicked Pawn.
     * @param game the Game.
     * @return true if the clicked Pawn can be moved.
     */
    boolean canMovePawn(Pawn pawn, Game game);

    /**
     * Checks if the player can move one of its pawns.
     * 
     * @param game the Game.
     * @return true if the player can move one of its pawns
     */
    boolean canMovePawns(Game game);

    /**
     * Modify the amount of coins.
     * 
     * @param value the amount of coins
     */
    void updateCoins(int value);

    /**
     * Earn coins based on the cells advanced in this turn.
     */
    void earnCoins();

    /**
     * Gets the amount of coins earned during last turn.
     * 
     * @return the amount of coins just earned
     */
    int getEarnedCoins();

    /**
     * Gets the player items.
     * 
     * @return the player items
     */
    List<Item> getPlayerItems();

    /**
     * Add an item {@link Item} in the player's inventory.
     * 
     * @param item the item
     */
    void addItemPlayer(Item item);

    /**
     * Adds the {@link Item} in the items list activated on the player.
     * 
     * @param item the item
     */
    void addToItemsApplied(Item item);

    /**
     * Gets the list of items applied.
     * 
     * @return the list of applied items
     */
    List<Item> getItemsApplied();

    /**
     * Active the {@link Item} on the designed player.
     * 
     * @param item   the item
     * @param player the designed player
     * @param pawn   the pawn affected by the item
     * @param game   the game
     */
    void useItem(Item item, Player player, Pawn pawn, Game game);

    /**
     * Return True if the last malus selected is used, False instead.
     * 
     * @return if the last malus selected is used
     */
    boolean isMalusUsed();

    /**
     * Set the new value for the malusUsed.
     * 
     * @param value the new value
     */
    void setMalusUsed(boolean value);

    /**
     * Remove the {@link Item.ItemType#MALUS} on the player after it is expired.
     */
    void malusExpired();

    /**
     * Remove the {@link Item.ItemType#BONUS} on the player after it is expired.
     */
    void bonusExpired();

}
