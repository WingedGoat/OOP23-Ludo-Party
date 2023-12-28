package model;

import model.api.Player;
import model.api.Wallet;

/**
 * 
 */
public final class PlayerImpl implements Player {

    private final String name;
    private final int coins;
    private final Color color;
    private final boolean isPlayerTurn;

    /**
     * Player constructor.
     * @param name  the player name
     */
    public PlayerImpl(final String name) {
        this.name = name;
        this.coins = 0;
        this.color = Color.GRAY;
        this.isPlayerTurn = false;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getCoins() {
        return this.coins;
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public boolean isPlayerTurn() {
        return this.isPlayerTurn;
    }

    @Override
    public int throwDice() {
        //TODO
        return 0;
    }

    @Override
    public Wallet getWallet() {
        // TODO
        throw new UnsupportedOperationException("Unimplemented method 'getWallet'");
    }

}
