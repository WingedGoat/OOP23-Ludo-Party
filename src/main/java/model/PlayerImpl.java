package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import model.api.Dice;
import model.api.Game;
import model.api.Item;
import model.api.Cell.CellType;
import utils.BColor;
import utils.Constants;
import utils.Index;
import model.api.Pawn;
import model.api.Player;

/**
 * Player Implementation class.
 */
public final class PlayerImpl implements Player {

    private final String name;
    private final PlayerType type;
    private final BColor color;
    private final CellType playerHouse;
    private final Set<Position> safePath;
    private final List<Pawn> pawns;
    private final Dice dice;
    private int coins;
    private boolean isPlayerTurn;

    private final List<Item> playerItems = new ArrayList<>();
    private final List<Item> itemsApplied = new ArrayList<>();
    private boolean firstTurn = true;
    private int dice1, dice2;

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
            final BColor color, final CellType playerHouse, final Set<Position> safePath, final List<Position> pawnsStartPos) {
        this.name = name;
        this.type = type;
        this.color = color;
        this.playerHouse = playerHouse;
        this.safePath = Set.copyOf(safePath);
        this.pawns = new ArrayList<>();

        for (int i = 0; i < Constants.PLAYER_PAWNS; i++) {
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
    public CellType getPlayerHouse() {
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
            setDice1(this.getDice().roll() / 2); 
            setDice2(this.getDice().roll() / 2);
            return getDice1() + getDice2();
        } else if (itemsApplied.contains(Item.DADUPLO)) {
            setDice1(this.getDice().roll()); 
            setDice2(this.getDice().roll());
            return getDice1() + getDice2();
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
    public void useItem(final Item item, final Player player, final Pawn pawn, final Game game) { 

        this.playerItems.remove(item);

        if (item.getType().equals(Item.Type.BONUS)) {

            player.addToItemsApplied(item);

        } else if (!player.getItemsApplied().contains(Item.BASTIONE)) {

            player.addToItemsApplied(item);

        } else if (item.getId() == Item.ARIETE.getId()) {

            player.getItemsApplied().remove(Item.BASTIONE);
            player.addToItemsApplied(item);

        } else if (item.getId() == Item.REGOLA_DEI_4.getId()) {

            final Movement move = new Movement();
            move.move(pawn, Index.FOUR, game);
       }
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

    @Override
    public void setDice1(final int diceResult) {
        this.dice1 = diceResult;
    }

    @Override
    public void setDice2(final int diceResult) {
        this.dice2 = diceResult;
    }

    @Override
    public int getDice1() {
        return this.dice1;
    }

    @Override
    public int getDice2() {
        return this.dice2;
    }
}
