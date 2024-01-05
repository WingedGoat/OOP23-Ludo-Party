package utility;

/**
 * A standard generic Pair, with getters, hashCode, equals, and toString well
 * implemented.
 */
public final class Position {

    private final int x;
    private final int y;

    /**
     * Constructor.
     * 
     * @param x x value
     * @param y y value
     */
    public Position(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets X value.
     * 
     * @return X
     */
    public int getX() {
        return x;
    }

    /**
     * Gets Y value.
     * 
     * @return Y
     */
    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Pair [x=" + x + ", y=" + y + "]";
    }

}
