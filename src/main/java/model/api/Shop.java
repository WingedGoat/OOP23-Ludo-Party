package model.api;

import java.util.Map;
/**
 * The interface for the shop.
 */
public interface Shop {

    /** 
     * Sell item to the specified player.
     * 
     * @param player
     *          the player
     * @param item
     *          the item to buy
     * 
     * @return a String message
    */
    String sellItem(Player player, Item item);

    /**
     * Create showcase.
     */
    void fillShowcase();

    /**
     * Modify the showcase removing the item selled and adding a new one.
     * 
     * @param itemselled
     */
    void newItem(Item itemselled);

    /**
     * Returns the showcase as a Map.
     * 
     * @return the showcase
     */
    Map<Integer, Item> getShowcase();

    /**
     * Create a new random key. 
     * 
     * @return a new random key
     */
    int getNewKey();

    /**
     * Retun the new item putted in the showcase.
     * 
     * @return the new item
     */
    Item getNewItem();

    /**
     * Set the new item putted in teh showcase.
     * 
     * @param item the new item
     */
    void setNewItem(Item item);
}
