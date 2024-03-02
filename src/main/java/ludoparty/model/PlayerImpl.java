package ludoparty.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ludoparty.model.api.Cell.CellType;
import ludoparty.model.api.Dice;
import ludoparty.model.api.Game;
import ludoparty.model.api.Item;
import ludoparty.model.api.Item.ItemType;
import ludoparty.model.api.Pawn;
import ludoparty.model.api.Player;
import ludoparty.utils.BColor;
import ludoparty.utils.Constants;
import ludoparty.utils.Index;

/**
 * Player Implementation class.
 */
public final class PlayerImpl implements Player {

    private static final Logger LOGGER = LogManager.getRootLogger();

    private final String name;
    private final PlayerType type;
    private final BColor color;
    private final List<Pawn> pawns;
    private final Dice dice;
    private int steps;
    private int diceResult;
    private boolean diceRolled;
    private boolean pawnMoved;
    private boolean malusUsed;
    private boolean isfirstTurn;
    private int coins;
    private int currentEarnAmount;
    // Item flags
    private final List<Item> playerItems = new ArrayList<>();
    private final List<Item> itemsApplied = new ArrayList<>();
    private final Random r = new Random();

    /**
     * Player constructor.
     * 
     * @param name          the player name
     * @param type          the player type
     * @param color         the player color
     * @param playerHouse   the position of the player's house
     * @param pawnsStartPos the player pawns start positions
     */
    public PlayerImpl(final String name, final PlayerType type,
            final BColor color, final CellType playerHouse, final List<Position> pawnsStartPos) {
        this.name = name;
        this.type = type;
        this.color = color;
        this.pawns = new ArrayList<>();
        this.malusUsed = true;

        for (int i = 0; i < Constants.PLAYER_PAWNS; i++) {
            this.pawns.add(new PawnImpl(pawnsStartPos.get(i), playerHouse, color));
        }

        this.coins = 0;
        this.currentEarnAmount = 0;
        this.dice = new DiceImpl();
        this.isfirstTurn = true;
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
    public List<Pawn> getPawns() {
        return List.copyOf(pawns);
    }

    @Override
    public int getCoins() {
        return coins;
    }

    /**
     * Sets the coins of the player.
     * 
     * @param coins the amount of coins
     */
    private void setCoins(final int coins) {
        this.coins = coins;
        LOGGER.error("coins: " + coins);
    }

    private Dice getDice() {
        return this.dice;
    }

    @Override
    public void rollDice() {

        if (this.isfirstTurn) { // force 6 at start of game in order to move at least the first pawn
            this.diceResult = Index.SIX;
            this.steps = this.diceResult;
            this.isfirstTurn = !this.isfirstTurn;
        } else {
            this.diceResult = this.getDice().roll();
            this.steps = this.diceResult;
            // if (this.itemsApplied.contains(Item.DADUPLO) &&
            // this.itemsApplied.contains(Item.TAGLIATELO)) {
            if (this.itemsApplied.contains(Item.DADUPLO)) {
                this.steps = this.diceResult * Index.TWO;
            } else if (this.itemsApplied.contains(Item.TAGLIATELO)) {
                this.steps = this.diceResult / Index.TWO;
            }
        }

        this.diceRolled = true;
    }

    @Override
    public int getDiceResult() {
        return this.diceResult;
    }

    @Override
    public int getSteps() {
        return this.steps;
    }

    @Override
    public boolean isDiceRolled() {
        return this.diceRolled;
    }

    @Override
    public boolean canRollDice() {
        return !this.diceRolled;
    }

    @Override
    public boolean canPassTurn(final Game game) {
        if (!this.pawnMoved && canMovePawns(game)) {
            return false;
        } else if (!isMalusUsed()) {
            return false;
        } else {
            this.diceRolled = false;
            this.pawnMoved = false;
            return true;
        }
    }

    @Override
    public boolean canMovePawn(final Pawn pawn, final Game game) {
        if (!this.diceRolled || this.pawnMoved || this.getType() != PlayerType.HUMAN) {
            this.pawnMoved = false;
        } else if (pawn.canMove(this.steps, game)) {
            this.pawnMoved = true;
        }
        return this.pawnMoved;
    }

    @Override
    public boolean canMovePawns(final Game game) {
        boolean canMove = false;
        for (final Pawn pawn : this.getPawns()) {
            if (pawn.canMove(this.steps, game)) {
                canMove = true;
                break;
            }
        }

        return canMove;
    }

    @Override
    public void updateCoins(final int value) {
        this.setCoins(this.coins + value);
    }

    @Override
    public void earnCoins() {
        currentEarnAmount = 0;
        for (int i = 0; i < this.steps; i++) {
            currentEarnAmount += r.nextInt(Index.TWELVE) + Index.SIX; // each cell contains from 6 to 17 ludollari
        }
        LOGGER.error("earn amt totale: " + currentEarnAmount);
        if (getItemsApplied().contains(Item.ABBONDANZA)) {
            currentEarnAmount *= 2;
            LOGGER.error("earn amt ABBONDANZA: " + currentEarnAmount);
        }
        updateCoins(currentEarnAmount);
    }

    @Override
    public int getEarnedCoins() {
        return this.currentEarnAmount;
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
            if (item.equals(Item.REGOLA_DEI_4)) {
                pawn.move(-Index.FOUR, game);
            }
        }
    }

    @Override 
    public boolean isMalusUsed() {
        return this.malusUsed;
    }

    @Override
    public void setMalusUsed(final boolean value) {
        this.malusUsed = value;
    }

    @Override
    public void malusExpired() {
        final List<Item> malus = new ArrayList<>();
        for (final Item i : itemsApplied) {
            if (i.getType().equals(Item.ItemType.MALUS)) {
                malus.add(i);
            }
        }
        itemsApplied.removeAll(malus);
    }

    @Override
    public void bonusExpired() {
        final List<Item> bonus = new ArrayList<>();
        for (final Item i : itemsApplied) {
            if (i.getType().equals(Item.ItemType.BONUS)) {
                bonus.add(i);
            }
        }
        itemsApplied.removeAll(bonus);
    }

    @Override
    public String toString() {
        return "PlayerImpl [name=" + name + ", type=" + type + ", color=" + color + ", steps=" + steps + ", diceResult="
                + diceResult + ", coins=" + coins + ", currentEarnAmount=" + currentEarnAmount + "]";
    }

}
