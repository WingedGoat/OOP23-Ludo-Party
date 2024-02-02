package model.api;

import java.util.Map;

/**
 * Rapresent an inventory.
 */
public interface Inventory {

    /**
     * Return a map whit all the items.
     * 
     * @return all the items
     */
    Map<Integer, Item> getItems();

    /**
     * Return a map whit the items in the inventory.
     * 
     * @return the inventory
     */
    Map<Integer, Item> getInventory();

    /**
     * Returns the first item of the inventory.
     * 
     * @return the first item
     */
    Item getItemA();

    /**
     * Returns the second item of the inventory.
     * 
     * @return the second item
     */
    Item getItemB();

    /**
     * Returns the third item of the inventory.
     * 
     * @return the third item
     */
    Item getItemC();

    /**
     * Returns the key of the first item.
     * 
     * @return the key of the first item
     */
    Integer getKeyA();

    /**
     * Returns the key of the second item.
     * 
     * @return the key of the second item
     */
    Integer getKeyB();

    /**
     * Returns the key of the third item.
     * 
     * @return the key of the third item
     */
    Integer getKeyC();

    /**
     * Modify the key and the first item in the inventory.
     * 
     * @param newkeya
     */
    void setKeyA(Integer newkeya);

    /**
     * Modify the key and the second item in the inventory.
     * 
     * @param newkeyb
     */
    void setKeyB(Integer newkeyb);

    /**
     * Modify the key and the third item in the inventory.
     * 
     * @param newkeyc
     */
    void setKeyC(Integer newkeyc);
}

