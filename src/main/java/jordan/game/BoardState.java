package jordan.game;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BoardState {
    private final Map<Coordinate, Occupier> occupiedTiles;
    private final int width;
    private final int height;
    private final Side sideToPlay;
    private final Map<Side, List<Move>> moveCache;

    public BoardState(final Map<Coordinate, Occupier> occupiedTiles, final int width, final int height, final Side sideToPlay) {
        this.occupiedTiles = Collections.unmodifiableMap(occupiedTiles);
        this.width = width;
        this.height = height;
        this.sideToPlay = sideToPlay;
        moveCache = precacheMoves();
    }

    private Map<Side, List<Move>> precacheMoves() {
        Map<Side, List<Move>> moves = new HashMap<>();
        for (Side side : Side.values())
        {
            moves.put(side, calculateAvailableMoves(side));
        }
        return Collections.unmodifiableMap(moves);
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
        return moveCache.get(side);
    }

    private List<Move> calculateAvailableMoves(Side side) {
        List<Move> moves = new ArrayList<>();
        forEachTile((x, y) -> validMoveForSideAt(side, x, y).ifPresent(moves::add));
        return moves;
    }

    private Optional<Move> validMoveForSideAt(Side side, int x, int y) {
        if (side.relevantCoordinatesFrom(x, y).stream().anyMatch(this::coordinateIsOccupiedOrInvalid)) {
            return Optional.empty();
        }
        return Optional.of(new Move(side, x, y));
    }

    private boolean coordinateIsOccupiedOrInvalid(Coordinate coordinate) {
        return !isOnBoard(coordinate) || getOccupierAt(coordinate) != Occupier.EMPTY_OCCUPIER;
    }

    private boolean isOnBoard(Coordinate coordinate) {
        return coordinate.getX() >= 0 && coordinate.getX() < width && coordinate.getY() >= 0 && coordinate.getY() < height;
    }

    private void forEachTile(BiConsumer<Integer, Integer> func) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                func.accept(x, y);
            }
        }
    }

    private Tile getTileAt(int x, int y) {
        return new Tile(getOccupierAt(x, y), x, y);
    }

    private Occupier getOccupierAt(int x, int y) {
        return getOccupierAt(new Coordinate(x, y));
    }

    private Occupier getOccupierAt(Coordinate coordinate) {
        Optional<Occupier> occupier = Optional.ofNullable(occupiedTiles.get(coordinate));
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
        Map<Coordinate, Occupier> tiles = new HashMap<>(this.occupiedTiles);
        for (Coordinate coord : move.affectedCoordinates()) {
            tiles.put(coord, occupier);
        }
        return new BoardState(tiles, width, height, sideToPlay);
    }

    public Side getNextSideToPlay() {
        return sideToPlay;
    }
}
