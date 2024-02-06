package model; 

import java.util.Map;

import model.api.Dice;
import model.api.Inventory;
import model.api.Item;
import model.api.Player;
import model.api.Shop; 

/**
 * 
 */
public final class ShopImpl implements Shop { 

    private final Inventory showcase; 

    /**
     * Constructor for the shop.
     */
    public ShopImpl() { 
        showcase = new InventoryImpl();
        showcaseCreation();
    }

    /** 
     * The operation for selling an item.
     * 
     * @param player
     * @param item
     * 
     * @return a String message
    */
    public String sellingItem(final Player player, final Item item) { 
        final int coinplayer = player.getCoins(); 
        if (3 == player.getPlayerInventory().size()) { 
            return "ATTENZIONE! NON HAI ABBASTANZA SPAZIO NELL'INVENTARIO!";
        } else if (coinplayer < item.getPrice()) { 
                return "ATTENZIONE! NON HAI ABBASTANZA LUDOLLARI!"; 
        } else if (player.getPlayerInventory().containsKey(item.getId())) {
            return "ATTENZIONE! HAI GIA' QUESTO OGGETTO NEL TUO INVENTARIO!";
        } else {
            player.modifyCoins(-item.getPrice()); 
            player.addItemPlayer(item.getId(), item); 
            if (item.equals(showcase.getItemA())) { 
                showcase.setKeyA(fillShowcase(item, showcase.getItemB(), showcase.getItemC())); 
            } else if (item.equals(showcase.getItemB())) { 
                showcase.setKeyB(fillShowcase(item, showcase.getItemA(), showcase.getItemC())); 
            } else if (item.equals(showcase.getItemC())) { 
                showcase.setKeyC(fillShowcase(item, showcase.getItemA(), showcase.getItemB())); 
            } 
        }

        return "Item " + item.getName() + " venduto a " + player.getName() + ".";
    }

    /**
     * Fill the showcase returning the new item's id.
     * 
     * @param itemselled
     * @param itema
     * @param itemb
     * 
     * @return id
     */
    public Integer fillShowcase(final Item itemselled, final Item itema, final Item itemb) {
        final Dice mydice = new BasicDiceImpl(); 
        Integer a, b, c, id; 
        id = itemselled.getId(); 
        a = itema.getId(); 
        b = itemb.getId(); 
        c = id; 
        while (id.equals(a) || id.equals(b) || id.equals(c)) { 
            id = mydice.roll(); 
        }

        return id; 
    } 

    private void showcaseCreation() {
        final Dice mydice = new BasicDiceImpl(); 
        showcase.setKeyA(mydice.roll()); 
        showcase.setKeyB(showcase.getKeyA()); 
        while (showcase.getKeyB().equals(showcase.getKeyA())) { 
            showcase.setKeyB(mydice.roll()); 
        } 
        showcase.setKeyC(fillShowcase(showcase.getItemA(), showcase.getItemA(), showcase.getItemB()));
    } 

    /**
     * Returns a copy of the shop inventory.
     * 
     * @return the showcase
     */
    public Map<Integer, Item> getShowcase() {
        return showcase.getInventory();
    }

} 
