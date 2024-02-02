package model.api;

/**
 * Rapresents the base of an item.
 */
public interface Item {

    /**
     * Enum item type.
     */
    enum ItemType {
        /**
         * Bonus item.
         */
        BONUS,
        /**
         * Malus item.
         */
        MALUS;
    }

    /**
     * Return the name of the item.
     * 
     * @return the name
     */
    String getName();

    /**
     * Return the description of an item and its interaction.
     * 
     * @return the description
     */
    String getDescription();

    /**
     * Return the price of the item.
     * 
     * @return the price
     */
    int getPrice();

    /**
     * Get the item type [BONUS, MALUS] as a string.
     * 
     * @return the Item type as a string
     */
    String getTypeString();

    /**
     * Return the id of the item.
     * 
     * @return the item id
     */
    int getId();

    /**
     * Returns the Item information in a String.
     * 
     * @return the Item information
     */
    String itemToString();

    /**
     * Return true if the item is a Bonus type.
     * 
     * @return true if the item is a Bonus
     */
    boolean isBonus();

}
