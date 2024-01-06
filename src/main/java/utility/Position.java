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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Position other = (Position) obj;
        return x == other.x;
    }

    @Override
    public String toString() {
        return "Pair [x=" + x + ", y=" + y + "]";
    }

}
