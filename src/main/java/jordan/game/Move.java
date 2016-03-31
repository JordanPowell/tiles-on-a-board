package jordan.game;

import java.util.BitSet;
import java.util.Collection;

public class Move {
    private final Side side;
    private final int originX;
    private final int originY;
    private final BitSet bitSet;

    public Move(Side side, int originX, int originY, BitSet bitSet) {
        this.side = side;
        this.originX = originX;
        this.originY = originY;
        this.bitSet = bitSet;
    }

    public Side getSide() {
        return side;
    }

    public Collection<Coordinate> affectedCoordinates() {
        return side.relevantCoordinatesFrom(originX, originY);
    }

    public Coordinate getCoordinate() {
        return new Coordinate(originX, originY);
    }

    public BitSet getBitSet() {
        return bitSet;
    }
}
