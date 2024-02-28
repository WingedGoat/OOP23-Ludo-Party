package ludoparty.model; 

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import ludoparty.model.api.Item;
import ludoparty.model.api.Player;
import ludoparty.model.api.Shop;
import ludoparty.utils.Index; 

/**
 * 
 */
public final class ShopImpl implements Shop { 

    private final Random r = new Random();
    private final Map<Integer, Item> showcase = new HashMap<>(); //3
    private final Map<Integer, Item> items = new HashMap<>(); //6
    private Item newItem;
    private String shopmessage;

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
    public void sellItem(final Player player, final Item item) { 
        final int coinplayer = player.getCoins(); 
        if (player.getPlayerItems().size() == 3) { 
            setShopMessage("ATTENZIONE! NON HAI ABBASTANZA SPAZIO NELL'INVENTARIO!");
        } else if (coinplayer < item.getPrice()) { 
            setShopMessage("ATTENZIONE! NON HAI ABBASTANZA LUDOLLARI!"); 
        } else if (player.getPlayerItems().contains(item)) {
            setShopMessage("ATTENZIONE! HAI GIA' QUESTO OGGETTO NEL TUO INVENTARIO!");
        } else {
            player.updateCoins(-item.getPrice()); 
            player.addItemPlayer(item);
            newItem(item);
            setShopMessage("Item " + item.getName() + " venduto a " + player.getName() + ".");
        }
    }
    /**
     * Set a new message Shop for the player. 
     * @param newShopMessage the new message
     */
    private void setShopMessage(final String newShopMessage) {
        this.shopmessage = newShopMessage;
    }

    @Override
    public String getShopMessage() {
        return this.shopmessage;
    }
    @Override
    public void fillShowcase() {

        final Set<Integer> keys = new HashSet<>();
        keys.add(getNewKey());

        Integer currentkey = getNewKey();
        while (keys.size() < 3) {
            keys.add(currentkey);
            currentkey = getNewKey();
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

        setNewItem(items.get(newkey));
        showcase.remove(itemselled.getId(), itemselled);
        showcase.put(newkey, items.get(newkey));
    }

    @Override
    public Map<Integer, Item> getShowcase() {
        return this.showcase;
    }

    @Override
    public Item getNewItem() {
        return this.newItem;
    }

    /**
     * Creates a new random key. 
     * 
     * @return a new random key
     */
    private int getNewKey() {
        return this.r.nextInt(Index.FIVE) + 1;
    }

    /**
     * Sets the new item put in the showcase.
     * 
     * @param item the new item
     */
    private void setNewItem(final Item item) {
        this.newItem = item;
    }

} 
