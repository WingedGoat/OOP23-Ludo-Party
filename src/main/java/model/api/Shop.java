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
     * Returns the showcase as a Map.
     * 
     * @return the showcase
     */
    Map<Integer, Item> getShowcase();
}
