package model; 

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import model.api.Item;
import model.api.Player;
import model.api.Shop;
import utility.Index; 

/**
 * 
 */
public final class ShopImpl implements Shop { 

    private final Random r = new Random();
    private final Map<Integer, Item> showcase = new HashMap<>(); //3
    private final Map<Integer, Item> items = new HashMap<>(); //6

    /**
     * Constructor for the shop.
     */
    public ShopImpl() { 
        this.convertItemsToMap();
        this.fillShowcase();
    }

    void convertItemsToMap() {
        for (final Item i : Item.values()) {
            this.items.put(i.getId(), i);
        }
    }

    @Override
    public String sellItem(final Player player, final Item item) { 
        final int coinplayer = player.getCoins(); 
        if (player.getPlayerItems().size() == 3) { 
            return "ATTENZIONE! NON HAI ABBASTANZA SPAZIO NELL'INVENTARIO!";
        } else if (coinplayer < item.getPrice()) { 
                return "ATTENZIONE! NON HAI ABBASTANZA LUDOLLARI!"; 
        } else if (player.getPlayerItems().contains(item)) {
            return "ATTENZIONE! HAI GIA' QUESTO OGGETTO NEL TUO INVENTARIO!";
        } else {
            player.modifyCoins(-item.getPrice()); 
            player.addItemPlayer(item);
            newItem(item);
            return "Item " + item.getName() + " venduto a " + player.getName() + ".";
        }
    }

    @Override
    public void fillShowcase() {

        final Set<Integer> keys = new HashSet<>();
        keys.add(getNewKey());

        Integer currentkey = getNewKey();
        while (keys.contains(currentkey) || keys.size() > 2) {
            currentkey = getNewKey();
            keys.add(currentkey);
        }
        for (final Integer k : keys) {
            showcase.put(k, items.get(k));
        }
    }

    @Override
    public void newItem(final Item itemselled) {
        Integer newkey = itemselled.getId();

        while (showcase.containsKey(newkey)) {
            newkey = getNewKey();
        }

        showcase.remove(itemselled.getId(), itemselled);
        showcase.put(newkey, items.get(newkey));
    }

    @Override
    public Map<Integer, Item> getShowcase() {
        return this.showcase;
    }

    @Override
    public int getNewKey() {
        return this.r.nextInt(Index.SIX);
    } 
} 
