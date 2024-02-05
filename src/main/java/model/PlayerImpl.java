package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.PlayerHome.HomePosition;
import model.api.Dice;
import model.api.Inventory;
import model.api.Item;
import model.api.Pawn;
import model.api.Player;
import model.api.Wallet;

/**
 * Player Implementation class.
 */
@SuppressWarnings("all")
public final class PlayerImpl implements Player {

    private final String name;
    private final PlayerType type;
    private final Color color;
    private final HomePosition homePosition;
    // private final List<Pair<Integer,Integer>> safePath;
    private final List<Pawn> pawns;
    private int coins;
    private boolean isPlayerTurn;
    private Dice dice;
    private Inventory inventory = new InventoryImpl();
    private List<Item> itemsApplied = new ArrayList<>();

    /**
     * Player constructor.
     * 
     * @param name
     *          the player name
     * @param type
     *          the player type
     * @param color
     *          the player color
     * @param homePosition
     *          the position of the player's house
     */
    public PlayerImpl(final String name, final PlayerType type,
            final Color color, final HomePosition homePosition) {
        this.name = name;
        this.type = type;
        this.color = color;
        this.homePosition = homePosition;

        this.pawns = new ArrayList<>();
        for (int i = 0; i < homePosition.getPawnPositions().size(); i++) {
            pawns.add(new PawnImpl(homePosition.getPawnPositions().get(i), i, homePosition, color));
        }

        this.coins = 0;
        this.isPlayerTurn = false;
        this.dice = new BasicDiceImpl();
    }

    // getters

    @Override
    public String getName() {
        return name;
    }

    @Override
    public PlayerType getType() {
        return type;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public HomePosition getHomePosition() {
        return homePosition;
    }

    @Override
    public List<Pawn> getPawns() {
        return List.copyOf(pawns);
    }

    @Override
    public int getCoins() {
        return coins;
    }

    @Override
    public void setCoins(final int coins) {
        this.coins = coins;
    }

    @Override
    public boolean isPlayerTurn() {
        return isPlayerTurn;
    }

    @Override
    public Dice getDice() {
        return this.dice;
    }


    @Override
    public String toString() {
        return "PlayerImpl [name=" + name + ", type=" + type + ", color=" + color + ", homePosition=" + homePosition
                + ", pawns=" + pawns + ", coins=" + coins + ", isPlayerTurn=" + isPlayerTurn + "]";
    }

    @Override
    public int rollDice() {
        if (itemsApplied.contains(inventory.getItems().get(1)) && itemsApplied.contains(inventory.getItems().get(4))) {
            return this.getDice().roll();
        } else if (itemsApplied.contains(inventory.getItems().get(1))) {
            return this.getDice().roll() + this.getDice().roll();
        } else if (itemsApplied.contains(inventory.getItems().get(4))) {
            return this.getDice().roll() / 2;
        } 
        return this.getDice().roll();
    }

    @Override
    public Wallet getWallet() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getWallet'");
    }

    @Override 
    public Map<Integer, Item>  getPlayerInventory() { 
        return inventory.getInventory();
    }

    @Override
    public void modifyCoins(final int value) { 
        this.coins = this.coins + value;
    }

    @Override
    public void addItemPlayer(final Integer id, final Item item) { 
        inventory.addItem(id, item);
    } 

    @Override
    public void addToItemsApplied(final Item item) { 
        itemsApplied.add(item);
    }

    @Override
    public List<Item> getItemsApplied() {
        return List.copyOf(this.itemsApplied);
    }

    @Override
    public void useItem(final Item item, final Player player) { 
        final int bastioneId = 3;
        final int arieteId = 6;
        inventory.getInventory().remove(item.getId(), item);
        if (item.isBonus()) {
            player.addToItemsApplied(item);
        } else if (!player.getItemsApplied().contains(inventory.getItems().get(bastioneId))) {
            player.addToItemsApplied(item);
        } else if (item.getId() == arieteId) {
            player.getItemsApplied().remove(inventory.getItems().get(bastioneId));
            player.addToItemsApplied(item);
        }
    } 

    @Override
    public void malusExpired() { 
        for (Item i : itemsApplied) { 
            if (!i.isBonus()) {
                itemsApplied.remove(i); 
            }
        }
    }

    @Override
    public void bonusExpired() { 
        for (Item i : itemsApplied) { 
            if (i.isBonus()) {
                itemsApplied.remove(i); 
            }
        }
    }

}
