package model.api;

/**
 * A small cube with each side having a different number of spots on it, ranging from one to six.
 */
public interface Dice {

    /**
     * Returns a random number between 1 and 6.
     * @return the dice result
     */
    int roll();
}
