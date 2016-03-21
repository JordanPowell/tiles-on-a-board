package jordan.game;

import java.util.Arrays;
import java.util.Collection;

public enum Side {
    VERTICAL,
    HORIZONTAL;

    public Collection<Coordinate> relevantCoordinatesFrom(int x, int y) {
        if (this == VERTICAL)
        {
            return Arrays.asList(new Coordinate(x, y), new Coordinate(x, y + 1));
        }
        else if (this == HORIZONTAL)
        {
            return Arrays.asList(new Coordinate(x, y), new Coordinate(x + 1, y));
        }
        throw new RuntimeException("Wrong side...");
    }
}
