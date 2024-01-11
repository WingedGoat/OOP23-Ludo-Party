package model;

import java.util.Random;
import model.api.Dice;

/**
 * A standard dice with 6 possible results.
 */
public class BasicDiceImpl implements Dice {

    /**
     * Number of faces.
     */
    private static final int FACES = 6;

    /**
     * Random variable to use for dice rolling.
     */
    private final Random r = new Random();

    /**
     * Returns a random number between 1 and 6.
     * @return the dice result.
     */
    @Override
    public int roll() {
        return r.nextInt(FACES) + 1;
    }

}
