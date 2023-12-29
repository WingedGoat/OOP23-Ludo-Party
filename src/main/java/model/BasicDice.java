package model;

import java.util.Random;

import model.api.Dice;

public class BasicDice implements Dice {

    /**
     * Returns a random number between 1 and 6
     * @return the dice result
     */
    @Override
    public int roll() {
        Random r = new Random();
        return r.nextInt(6) + 1;
    }

}
