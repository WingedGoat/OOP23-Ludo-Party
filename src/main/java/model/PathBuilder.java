package model;

import java.util.List;

import java.util.ArrayList;

/**
 * This class handles the path construction.
 */
public final class PathBuilder {

    private int x;
    private int y;
    private final List<Position> positions = new ArrayList<>();

    /**
     * The constructor of Builder class.
     * 
     * @param x
     * @param y
     */
    public PathBuilder(final int x, final int y) {
        positions.clear();
        positions.add(new Position(x, y));
        this.x = x;
        this.y = y;
    }

    /**
     * Adds a position above for a number of times.
     * 
     * @param length
     */
    public void addUp(final int length) {
        for (int i = 0; i < length; i++) {
            addPosition(false, false);
        }
    }

    /**
     * Adds a position below for a number of times.
     * 
     * @param length
     */
    public void addDown(final int length) {
        for (int i = 0; i < length; i++) {
            addPosition(true, false);
        }
    }

    /**
     * Adds a position on the left for a number of times.
     * 
     * @param length
     */
    public void addLeft(final int length) {
        for (int i = 0; i < length; i++) {
            addPosition(false, true);
        }
    }

    /**
     * Adds a position on the right for a number of times.
     * 
     * @param length
     */
    public void addRight(final int length) {
        for (int i = 0; i < length; i++) {
            addPosition(true, true);
        }
    }

    private void addPosition(final boolean increase, final boolean horizontal) {
        if (increase && horizontal) {
            positions.add(new Position(++x, y));
        } else if (increase) {
            positions.add(new Position(x, ++y));
        } else if (horizontal) {
            positions.add(new Position(--x, y));
        } else {
            positions.add(new Position(x, --y));
        }
    }

    /**
     * sets the X coord.
     * 
     * @param x
     */
    public void setX(final int x) {
        this.x = x;
    }

    /**
     * sets the Y coord.
     * 
     * @param y
     */
    public void setY(final int y) {
        this.y = y;
    }

    /**
     * Gets the actual path.
     * 
     * @return the actual path
     */
    public List<Position> getPath() {
        return List.copyOf(positions);
    }
}
