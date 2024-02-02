package model; 

import model.api.Item; 
import model.api.Shop; 

/**
 * 
 */
public final class ShopImpl implements Shop { 
    private final InventoryImpl showcase = new InventoryImpl(); 

    /**
     * Constructor for the shop.
     */
    public ShopImpl() {
        showcase.inventoryCreation(); 
        showcaseCreation();
    }

    /** 
     * 
     * 
     * @param player
     * 
     * @param item
     * 
     * @return a String message
    */
    public String sellingItem(final PlayerImpl player, final Item item) { 
        final int coinplayer = player.getCoins(); 
        if (3 == player.getPlayerInventory().getInventory().size()) { 
            return "ATTENZIONE! NON HAI ABBASTANZA SPAZIO NELL'INVENTARIO!";
        } else if (coinplayer < item.getPrice()) { 
                return "ATTENZIONE! NON HAI ABBASTANZA LUDOLLARI!"; 
                } else { 
                    player.modifyCoins(-item.getPrice()); 
                    player.addItem(item.getId(), item); 
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
     * 
     * 
     * @param itemselled
     * 
     * @param itema
     * 
     * @param itemb
     * 
     * @return id
     * 
     */
    public Integer fillShowcase(final Item itemselled, final Item itema, final Item itemb) {
        final BasicDiceImpl mydice = new BasicDiceImpl(); 
        Integer a, b, c, id; 
        id = itemselled.getId(); 
        a = itema.getId(); 
        b = itemb.getId(); 
        c = id; 
        while (id.equals(a) || id.equals(b) || id.equals(c)) { // entra c che deve essere diverso da a e b
            id = mydice.roll(); 
        } 
        return id; 
    } 

    void showcaseCreation() {
        final BasicDiceImpl mydice = new BasicDiceImpl(); 
        showcase.setKeyA(mydice.roll()); 
        showcase.setKeyB(showcase.getKeyA()); //keyb = keya
        while (showcase.getKeyB().equals(showcase.getKeyA())) { // entra b che deve essere diverso da a
            showcase.setKeyB(mydice.roll()); 
        } 
        showcase.setKeyC(fillShowcase(showcase.getItemA(), showcase.getItemA(), showcase.getItemB()));
    } 
} 
