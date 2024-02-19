package ludoparty.model;

import java.util.Random;
import ludoparty.model.api.Dice;

/**
 * A standard dice with 6 possible results.
 */
public final class BasicDiceImpl implements Dice {

    /**
     * Number of faces.
     */
    private static final int FACES = 6;

    /**
     * Random variable to use for dice rolling.
     */
    private final Random r = new Random();

    @Override
    public int roll() {
        return r.nextInt(FACES) + 1;
    }

}
