package jordan.game;

import java.util.Collection;

public class Move {
    private final Side side;
    private final int originX;
    private final int originY;

    public Move(Side side, int originX, int originY) {
        this.side = side;
        this.originX = originX;
        this.originY = originY;
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
}
