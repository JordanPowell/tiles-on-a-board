package jordan.game;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BoardState {
    private final OccupiedBoard occupiedBoard;
    private final int width;
    private final int height;
    private final Side sideToPlay;

    public BoardState(final OccupiedBoard occupiedBoard, final int width, final int height, final Side sideToPlay) {
        this.occupiedBoard = occupiedBoard;
        this.width = width;
        this.height = height;
        this.sideToPlay = sideToPlay;
    }

    public List<Row> getRows() {
        return mapHeight(y -> new Row(mapWidth(x -> getTileAt(x, y))));
    }

    private <T> List<T> mapHeight(Function<Integer, T> func) {
        return IntStream.range(0, height).mapToObj(func::apply).collect(Collectors.toList());
    }

    private <T> List<T> mapWidth(Function<Integer, T> func) {
        return IntStream.range(0, width).mapToObj(func::apply).collect(Collectors.toList());
    }

    public List<Move> getAvailableMoves() {
        return getAvailableMoves(sideToPlay);
    }

    public List<Move> getAvailableMoves(Side side) {
        return occupiedBoard.getAvailableMoves(side);
    }

    private Tile getTileAt(int x, int y) {
        return new Tile(getOccupierAt(x, y), x, y);
    }

    private Occupier getOccupierAt(int x, int y) {
        return getOccupierAt(new Coordinate(x, y));
    }

    private Occupier getOccupierAt(Coordinate coordinate) {
        Optional<Occupier> occupier = occupiedBoard.getPlayerAt(coordinate);
        return occupier.orElse(Occupier.EMPTY_OCCUPIER);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Side getSideToPlay() {
        return sideToPlay;
    }

    public BoardState stateAfter(Move move, Occupier occupier, Side sideToPlay) {
        return new BoardState(occupiedBoard.boardAfter(move), width, height, sideToPlay);
    }

    public Side getNextSideToPlay() {
        return sideToPlay;
    }
}
