package model.api;

import java.util.Map;
/**
 * The interface for the shop.
 */
public interface Shop {

    /** 
     * Sell the item to the specified player.
     * 
     * @param player the player
     * @param item the item to buy
     * 
     * @return a String message
    */
    String sellItem(Player player, Item item);

    /**
     * Creates the showcase.
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
     * Returns the new item putted in the showcase.
     * 
     * @return the new item
     */
    Item getNewItem();

}
