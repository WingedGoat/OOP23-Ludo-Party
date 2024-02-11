package utility;

import java.util.List;
import java.util.ArrayList;

import model.Position;

/**
 * This class handles the path construction.
 */
public final class Builder {

    private int x;
    private int y;
    private final List<Position> positions = new ArrayList<>();

    /**
     * The constructor of Builder class.
     * 
     * @param x
     * @param y
     */
    public Builder(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Adds a position on a direction given the number of times.
     * 
     * @param length
     * @param increase
     * @param horizontal
     */
    public void addPositions(final int length, final boolean increase, final boolean horizontal) {
        for (int i = 0; i < length; i++) {
            addPosition(increase, horizontal);
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
     * Gives the actual path.
     * 
     * @return copy of positions
     */
    public List<Position> getList() {
        return List.copyOf(positions);
    }
}
