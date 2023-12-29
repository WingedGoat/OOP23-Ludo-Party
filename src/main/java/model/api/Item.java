package model.api;

/**
 * Rapresents the base of an item
 */
public interface Item {

    /**
     * Return the name of the item
     * @return the name
     */
    String getName();

    /**
     * Return the description of an item and its interaction
     * @return the description
     */
    String getDescription();

    /**
     * Return the price of the item
     * @return the price
     */
    int getPrice();

    /**
     * Returns true if it's a bonus item,
     * false mean is a malus.
     * @return true if it's a bonus
     */
    boolean isBonus();

    
}