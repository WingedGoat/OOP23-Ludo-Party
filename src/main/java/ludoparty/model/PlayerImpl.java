package ludoparty.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    private final String name;
    private final PlayerType type;
    private final BColor color;
    private final List<Pawn> pawns;
    private final Dice dice;
    private int diceResult;
    private boolean diceRolled;
    private boolean pawnMoved;
    private boolean isfirstTurn = true;
    private int coins;
    // Item flags
    private final List<Item> playerItems = new ArrayList<>();
    private final List<Item> itemsApplied = new ArrayList<>();
    private int firstDice;
    private int secondDice;
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

        for (int i = 0; i < Constants.PLAYER_PAWNS; i++) {
            this.pawns.add(new PawnImpl(pawnsStartPos.get(i), playerHouse, color));
        }

        this.coins = 0;
        this.dice = new DiceImpl();
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
    }

    private Dice getDice() {
        return this.dice;
    }

    @Override
    public int rollDice() {
        if (this.isfirstTurn) { // force 6 at start of game in order to move at least the first pawn
            this.diceResult = Index.SIX;
            this.isfirstTurn = !this.isfirstTurn;

            return this.diceResult;
        }
        if (this.itemsApplied.contains(Item.DADUPLO) && this.itemsApplied.contains(Item.TAGLIATELO)) {
            this.setFirstDice(this.getDice().roll() / 2);
            this.setSecondDice(this.getDice().roll() / 2);
            this.diceResult = getFirstDice() + getSecondDice();

            return this.diceResult;
        } else if (this.itemsApplied.contains(Item.DADUPLO)) {
            this.setFirstDice(this.getDice().roll());
            this.setSecondDice(this.getDice().roll());
            this.diceResult = getFirstDice() + getSecondDice();

            return this.diceResult;
        } else if (this.itemsApplied.contains(Item.TAGLIATELO)) {
            this.diceResult = this.getDice().roll() / 2;

            return this.diceResult;
        }
        this.diceResult = this.getDice().roll();

        return this.diceResult;
    }

    @Override
    public int getDiceResult() {
        return this.diceResult;
    }

    @Override
    public void setDiceResult(final int result) {
        this.diceResult = result;
    }

    @Override
    public boolean isDiceRolled() {
        return this.diceRolled;
    }

    @Override
    public boolean canRollDice() {
        if (this.diceRolled) {
            return false;
        }
        this.diceRolled = true;
        return true;
    }

    @Override
    public boolean canPassTurn() {
        if (!this.pawnMoved) {
            return false;
        }
        this.diceRolled = false;
        this.pawnMoved = false;
        return true;
    }

    @Override
    public void setPawnMoved(final boolean b) {
        this.pawnMoved = b;
    }

    @Override
    public boolean canMovePawn(final Pawn pawn) {
        if (!this.diceRolled || this.pawnMoved || pawn.getColor() != BColor.BLUE) {
            return false;
        }
        /*
         * Se è possibile muovere la pedina cliccata, imposto pawnMoved a true.
         * Già verificata (in PlayerPanelLeft) la casistica in cui non è possibile
         * muovere nessuna pedina.
         */
        if (pawn.canMove(this.diceResult)) {
            this.pawnMoved = true;
        }
        return true;
    }

    @Override
    public boolean canMovePawns(final int diceResult) {
        boolean canMove = false;
        for (final Pawn pawn : this.getPawns()) {
            if (pawn.canMove(diceResult)) {
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
    public void earnCoins(final int diceResult) {
        for (int i = 0; i < diceResult; i++) {
            final int earnAmount = r.nextInt(Index.TWELVE) + Index.SIX; //ogni cella dà ludollari da 6 a 17
            updateCoins(earnAmount);
        }
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
        } else if (item.equals(Item.ARIETE)) {
            player.getItemsApplied().remove(Item.BASTIONE);
            player.addToItemsApplied(item);
            if (item.equals(Item.REGOLA_DEI_4)) {
                pawn.move(-Index.FOUR, game);
            }
        } 
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

    /**
     * Sets the value of the first dice when {@link Item#DADUPLO} is activated.
     * 
     * @param diceResult the value of the first dice
     */
    private void setFirstDice(final int diceResult) {
        this.firstDice = diceResult;
    }

    /**
     * Sets the value of the second dice when {@link Item#DADUPLO} is activated.
     * 
     * @param diceResult the value of the second dice
     */
    private void setSecondDice(final int diceResult) {
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

    @Override
    public String toString() {
        return "PlayerImpl [name=" + name + ", type=" + type + ", color=" + color + ", pawns=" + pawns + ", diceResult="
                + diceResult + ", coins=" + coins + ", playerItems=" + playerItems + "]";
    }

}
