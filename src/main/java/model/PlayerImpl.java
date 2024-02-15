package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import model.api.Dice;
import model.api.Item;
import model.api.Cell.Type;
import model.api.Pawn;
import model.api.Player;
import utility.BColor;
import utility.Index;

/**
 * Player Implementation class.
 */
public final class PlayerImpl implements Player {

    private final String name;
    private final PlayerType type;
    private final BColor color;
    private final Type playerHouse;
    private final Set<Position> safePath;
    private final List<Pawn> pawns;
    private final Dice dice;
    private int coins;
    private boolean isPlayerTurn;

    private final List<Item> playerItems = new ArrayList<>();
    private final List<Item> itemsApplied = new ArrayList<>();
    private boolean firstTurn = true;

    /**
     * Player constructor.
     * 
     * @param name the player name
     * @param type the player type
     * @param color the player color
     * @param playerHouse the position of the player's house
     * @param safePath the safe path
     * @param pawnsStartPos the player pawns start positions
     */
    public PlayerImpl(final String name, final PlayerType type,
            final BColor color, final Type playerHouse, final Set<Position> safePath, final List<Position> pawnsStartPos) {
        this.name = name;
        this.type = type;
        this.color = color;
        this.playerHouse = playerHouse;
        this.safePath = Set.copyOf(safePath);
        this.pawns = new ArrayList<>();

        for (int i = 0; i < pawnsStartPos.size(); i++) {
            this.pawns.add(new PawnImpl(pawnsStartPos.get(i), i, playerHouse, color));
        }

        this.coins = 0;
        this.isPlayerTurn = false;
        this.dice = new BasicDiceImpl();
    }

    /**
     * Constructor.
     * @param p the player
     */
    public PlayerImpl(final Player p) {
        this.name = p.getName();
        this.type = p.getType();
        this.color = p.getColor();
        this.playerHouse = p.getPlayerHouse();
        this.safePath = p.getSafePath();
        this.pawns = p.getPawns();
        this.coins = p.getCoins();
        this.isPlayerTurn = p.isPlayerTurn();
        this.dice = p.getDice();
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
    public BColor getColor() {
        return color;
    }

    @Override
    public Type getPlayerHouse() {
        return playerHouse;
    }

    @Override
    public Set<Position> getSafePath() {
        return safePath;
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
    public void setPlayerTurn() {
        this.isPlayerTurn = true;
    }

    @Override
    public Dice getDice() {
        return this.dice;
    }

    @Override
    public String toString() {
        return "PlayerImpl [name=" + name + ", type=" + type + ", color=" + color + ", playerHouse=" + playerHouse
                + ", safePath=" + safePath + ", pawns=" + pawns + ", dice=" + dice + ", coins=" + coins
                + ", isPlayerTurn=" + isPlayerTurn + ", playerItems=" + playerItems + ", itemsApplied=" + itemsApplied
                + "]";
    }

    @Override
    public int rollDice() {
        if (firstTurn) {  // force 6 at start of game in order to move at least the first pawn
            firstTurn = !firstTurn;
            return Index.SIX;
        }
        if (itemsApplied.contains(Item.DADUPLO) && itemsApplied.contains(Item.TAGLIATELO)) {
            return this.getDice().roll();
        } else if (itemsApplied.contains(Item.DADUPLO)) {
            return this.getDice().roll() + this.getDice().roll();
        } else if (itemsApplied.contains(Item.TAGLIATELO)) {
            return this.getDice().roll() / 2;
        }
        return this.getDice().roll();
    }

    @Override
    public void modifyCoins(final int value) { 
        this.coins = this.coins + value;
    }

    @Override
    public List<Item> getPlayerItems() {
        return this.playerItems;
    }
    @Override
    public void addItemPlayer(final Item item) { 
        this.playerItems.add(item);
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
    public void useItem(final Item item, final Player player /*final Pawn pawn*/) { 
        this.playerItems.remove(item);

        if (item.getType().equals(Item.Type.BONUS)) {
            player.addToItemsApplied(item);
        } else if (!player.getItemsApplied().contains(Item.BASTIONE)) {
            player.addToItemsApplied(item);
        } else if (item.getId() == Item.ARIETE.getId()) {
            player.getItemsApplied().remove(Item.BASTIONE);
            player.addToItemsApplied(item);
        } //else if (item.getId() == Item.REGOLA_DEI_4.getId()) {
           // pawn.move(pawn, -regolaId);
       // }
    } 

    @Override
    public void malusExpired() { 
        for (final Item i : itemsApplied) { 
            if (i.getType().equals(Item.Type.MALUS)) {
                itemsApplied.remove(i); 
            }
        }
    }

    @Override
    public void bonusExpired() { 
        for (final Item i : itemsApplied) { 
            if (i.getType().equals(Item.Type.BONUS)) {
                itemsApplied.remove(i); 
            }
        }
    }

}
