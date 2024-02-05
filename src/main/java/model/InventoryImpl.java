package model;

import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;

import model.ItemImpl.ItemDescription;
import model.ItemImpl.ItemName;
import model.api.Inventory;
import model.api.Item;
import model.api.Item.ItemType;

/**
 * 
 */
public final class InventoryImpl implements Inventory { 
    private final Map<Integer, Item> inventory = new HashMap<>();
    private Integer keya, keyb, keyc;
    private final Map<Integer, Item> items;

    /**
     * Constructor of the inventory.
     */
    public InventoryImpl() { 
        final Item daduplo = new ItemImpl(ItemName.DADUPLO, ItemDescription.DADUPLO_DESC, 150, ItemType.BONUS, 1);
        final Item abbondanza = new ItemImpl(ItemName.ABBONDANZA, ItemDescription.ABBONDANZA_DESC, 250, ItemType.BONUS, 2);
        final Item bastione = new ItemImpl(ItemName.BASTIONE, ItemDescription.BASTIONE_DESC, 500, ItemType.BONUS, 3);
        final Item tagliatelo = new ItemImpl(ItemName.TAGLIATELO, ItemDescription.TAGLIATELO_DESC, 300, ItemType.MALUS, 4);
        final Item laRegolaDei4 = new ItemImpl(ItemName.REGOLA_DEI_4, ItemDescription.REGOLA_DEI_4_DESC, 450, ItemType.MALUS, 5);
        final Item ariete = new ItemImpl(ItemName.ARIETE, ItemDescription.ARIETE_DESC, 600, ItemType.MALUS, 6);
        items = Map.of(daduplo.getId(), daduplo, abbondanza.getId(), abbondanza, bastione.getId(), bastione,
                        tagliatelo.getId(), tagliatelo, laRegolaDei4.getId(), laRegolaDei4, ariete.getId(), ariete);
    }

    //getters
    @Override 
    public Map<Integer, Item> getItems() { 
        final Map<Integer, Item> copyItemsMap = new HashMap<>();
        for (final Entry<Integer, Item> entry : items.entrySet()) {
            copyItemsMap.put(entry.getKey(), entry.getValue());
        }
        return copyItemsMap;
    }

    @Override
    public Map<Integer, Item> getInventory() { 
        return new HashMap<>();
        //return inventory;
    }

    @Override
    public Item getItemA() { 
        return inventory.get(getKeyA());
    }

    @Override
    public Item getItemB() { 
        return inventory.get(getKeyB());
    }

    @Override
    public Item getItemC() { 
        return inventory.get(getKeyC());
    }

    @Override
    public Integer getKeyA() { 
        return keya;
    }

    @Override
    public Integer getKeyB() { 
        return keyb;
    }

    @Override
    public Integer getKeyC() { 
        return keyc;
    }

    //setters
    @Override
    public void setKeyA(final Integer newkeya) { 
        if (3 == inventory.size()) {
        inventory.remove(keya, inventory.get(keya));
        }
        inventory.put(newkeya, getItems().get(newkeya));
        keya = newkeya;
    }

    @Override
    public void setKeyB(final Integer newkeyb) { 
        if (3 == inventory.size()) {
            inventory.remove(keyb, inventory.get(keyb));
        }
        inventory.put(newkeyb, getItems().get(newkeyb));
        keyb = newkeyb;
    }

    @Override
    public void setKeyC(final Integer newkeyc) { 
        if (3 == inventory.size()) {
            inventory.remove(keyc, inventory.get(keyc));
        }
        inventory.put(newkeyc, getItems().get(newkeyc));
        keyc = newkeyc;
    }

    @Override
    public void addItem(final Integer id, final Item item) {
        inventory.put(id, item);
    }
}
