package jordan.game;

import java.util.*;
import java.util.stream.Collectors;

public class OccupiedBoard {
    private final Map<Side, BitSet> boards;
    private final Map<Side, Player> players;
    private final int width;
    private final int height;

    public OccupiedBoard(Map<Side, BitSet> boards, Map<Side, Player> players, final int width, final int height) {
        this.boards = boards;
        this.players = Collections.unmodifiableMap(players);
        this.width = width;
        this.height = height;
    }

    public Optional<Occupier> getPlayerAt(Coordinate coordinate) {
        for (Map.Entry<Side, BitSet> entry : boards.entrySet()) {
            if (entry.getValue().get(coordinateToIndex(coordinate))) {
                return Optional.of(players.get(entry.getKey()));
            }
        }
        return Optional.empty();
    }

    private int coordinateToIndex(Coordinate coordinate) {
        return coordinate.getX() + (coordinate.getY() * height);
    }

    public OccupiedBoard boardAfter(Move move) {
        Map<Side, BitSet> newBoard = new HashMap<>(boards);
        for (Map.Entry<Side, BitSet> entry : boards.entrySet()) {
            newBoard.put(entry.getKey(), (BitSet)entry.getValue().clone());
        }
        BitSet bitSet = newBoard.get(move.getSide());
        for (Coordinate coordinate : move.affectedCoordinates()) {
            bitSet.set(coordinateToIndex(coordinate));
        }
        return new OccupiedBoard(newBoard, players, width, height);
    }

    public static OccupiedBoard newBoard(int boardWidth, int boardHeight, List<Player> players) {
        Map<Side, Player> playerMap = new HashMap<>();
        players.stream().forEach(player -> playerMap.put(player.getSide(), player));

        Map<Side, BitSet> boards = new LinkedHashMap<>();
        playerMap.keySet().forEach(side -> boards.put(side, new BitSet(boardWidth * boardHeight)));
        return new OccupiedBoard(boards, playerMap, boardWidth, boardHeight);
    }
}
