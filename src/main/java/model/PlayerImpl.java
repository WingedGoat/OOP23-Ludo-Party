package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import model.api.Cell.CellType;
import model.api.Dice;
import model.api.Game;
import model.api.Item;
import model.api.Item.ItemType;
import model.api.Pawn;
import model.api.Player;
import utils.BColor;
import utils.Constants;
import utils.Index;

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
    private int firstDice;
    private int secondDice;

    /**
     * Player constructor.
     * 
     * @param name          the player name
     * @param type          the player type
     * @param color         the player color
     * @param playerHouse   the position of the player's house
     * @param safePath      the safe path
     * @param pawnsStartPos the player pawns start positions
     */
    public PlayerImpl(final String name, final PlayerType type,
            final BColor color, final CellType playerHouse, final Set<Position> safePath,
            final List<Position> pawnsStartPos) {
        this.name = name;
        this.type = type;
        this.color = color;
        this.playerHouse = playerHouse;
        this.safePath = Set.copyOf(safePath);
        this.pawns = new ArrayList<>();

        for (int i = 0; i < Constants.PLAYER_PAWNS; i++) {
            this.pawns.add(new PawnImpl(pawnsStartPos.get(i), playerHouse, color));
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
    public int rollDice() {
        if (this.firstTurn) { // force 6 at start of game in order to move at least the first pawn
            this.firstTurn = !this.firstTurn;
            return Index.SIX;
        }
        if (this.itemsApplied.contains(Item.DADUPLO) && this.itemsApplied.contains(Item.TAGLIATELO)) {
            this.setFirstDice(this.getDice().roll() / 2);
            this.setSecondDice(this.getDice().roll() / 2);
            return getFirstDice() + getSecondDice();
        } else if (this.itemsApplied.contains(Item.DADUPLO)) {
            this.setFirstDice(this.getDice().roll());
            this.setSecondDice(this.getDice().roll());
            return getFirstDice() + getSecondDice();
        } else if (this.itemsApplied.contains(Item.TAGLIATELO)) {
            return this.getDice().roll() / 2;
        }
        return this.getDice().roll();
    }

    @Override
    public void updateCoins(final int value) {
        this.setCoins(this.coins + value);
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

        if (item.getType().equals(ItemType.BONUS)) {
            player.addToItemsApplied(item);
        } else if (!player.getItemsApplied().contains(Item.BASTIONE)) {
            player.addToItemsApplied(item);
            if (item.getId() == Item.REGOLA_DEI_4.getId()) {
                pawn.move(-Index.FOUR, game);
            }
        } else if (item.getId() == Item.ARIETE.getId()) {
            player.getItemsApplied().remove(Item.BASTIONE);
            player.addToItemsApplied(item);
        } else if (item.getId() == Item.REGOLA_DEI_4.getId()) {
            pawn.move(-Index.FOUR, game);
        }
    }

    @Override
    public void malusExpired() {
        for (final Item i : itemsApplied) {
            if (i.getType().equals(Item.ItemType.MALUS)) {
                itemsApplied.remove(i);
            }
        }
    }

    @Override
    public void bonusExpired() {
        for (final Item i : itemsApplied) {
            if (i.getType().equals(ItemType.BONUS)) {
                itemsApplied.remove(i);
            }
        }
    }

    /**
     * Sets the value of the first dice when {@link Item#DADUPLO} is activated.
     * 
     * @param diceResult the value of the first dice
     */
    public void setFirstDice(final int diceResult) {
        this.firstDice = diceResult;
    }

    /**
     * Sets the value of the second dice when {@link Item#DADUPLO} is activated.
     * 
     * @param diceResult the value of the second dice
     */
    public void setSecondDice(final int diceResult) {
        this.secondDice = diceResult;
    }

    /**
     * Gets the value of the first dice when {@link Item#DADUPLO} is activated.
     * 
     * @return the value of the first dice
     */
    private int getFirstDice() {
        return this.firstDice;
    }

    /**
     * Gets the value of the second dice when {@link Item#DADUPLO} is activated.
     * 
     * @return the value of the second dice
     */
    private int getSecondDice() {
        return this.secondDice;
    }

}
