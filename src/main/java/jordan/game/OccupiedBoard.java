package jordan.game;

import java.util.*;
import java.util.stream.Collectors;

public class OccupiedBoard {
    private final Map<Side, BitSet> boards;
    private final Map<Side, List<Move>> availableMoves;
    private static Map<Side, Player> players;
    private static int width;
    private static int height;

    public OccupiedBoard(Map<Side, BitSet> boards, Map<Side, List<Move>> availableMoves) {
        this.boards = boards;
        this.availableMoves = availableMoves;
    }

    public Optional<Occupier> getPlayerAt(Coordinate coordinate) {
        for (Map.Entry<Side, BitSet> entry : boards.entrySet()) {
            if (entry.getValue().get(coordinateToIndex(coordinate))) {
                return Optional.of(players.get(entry.getKey()));
            }
        }
        return Optional.empty();
    }

    private static int coordinateToIndex(Coordinate coordinate) {
        return coordinate.getX() + (coordinate.getY() * height);
    }

    public OccupiedBoard boardAfter(Move move) {
        Map<Side, BitSet> newBoard = copyBoardAndApplyMove(move);

        Map<Side, List<Move>> availableMoves = pruneAvailableMoves(newBoard);
        return new OccupiedBoard(newBoard, availableMoves);
    }

    private Map<Side, List<Move>> pruneAvailableMoves(Map<Side, BitSet> newBoard) {
        Map<Side, List<Move>> prunedMoves = new HashMap<>();

        BitSet combinedBoard = getCombinedBoard(newBoard);

        for (Map.Entry<Side, List<Move>> entry : availableMoves.entrySet()) {
            List<Move> moves = entry.getValue().stream()
                    .filter(move -> moveIsValid(move, combinedBoard))
                    .collect(Collectors.toList());
            prunedMoves.put(entry.getKey(), moves);
        }
        return prunedMoves;
    }

    private BitSet getCombinedBoard(Map<Side, BitSet> newBoard) {
        BitSet combinedBoard = new BitSet();
        newBoard.values().forEach(combinedBoard::or);
        return combinedBoard;
    }

    private boolean moveIsValid(Move move, BitSet combinedBoard) {
        return !move.getBitSet().intersects(combinedBoard);
    }

    private Map<Side, BitSet> copyBoardAndApplyMove(Move move) {
        Map<Side, BitSet> newBoard = new HashMap<>(boards);
        for (Map.Entry<Side, BitSet> entry : boards.entrySet()) {
            newBoard.put(entry.getKey(), (BitSet)entry.getValue().clone());
        }

        BitSet bitSet = newBoard.get(move.getSide());
        for (Coordinate coordinate : move.affectedCoordinates()) {
            bitSet.set(coordinateToIndex(coordinate));
        }

        return newBoard;
    }

    public static OccupiedBoard newBoard(int boardWidth, int boardHeight, List<Player> playerList) {
        players = new HashMap<>();
        playerList.stream().forEach(player -> players.put(player.getSide(), player));
        width = boardWidth;
        height = boardHeight;

        Map<Side, BitSet> boards = new LinkedHashMap<>();
        players.keySet().forEach(side -> boards.put(side, new BitSet(boardWidth * boardHeight)));
        return new OccupiedBoard(boards, calculateInitialAvailableMoves(players.keySet()));
    }

    private static Map<Side, List<Move>> calculateInitialAvailableMoves(Set<Side> sides) {
        Map<Side, List<Move>> validMoves = new HashMap<>();
        for (Side side : sides) {
            List<Move> moves = new ArrayList<>();
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    if (side.relevantCoordinatesFrom(i, j).stream().allMatch(OccupiedBoard::isOnBoard)) {
                        BitSet bitSet = new BitSet(width * height);
                        for (Coordinate coordinate : side.relevantCoordinatesFrom(i, j)) {
                            bitSet.set(coordinateToIndex(coordinate));
                        }
                        moves.add(new Move(side, i, j, bitSet));
                    }
                }
            }
            validMoves.put(side, moves);
        }
        return validMoves;
    }

    private static boolean isOnBoard(Coordinate coordinate) {
        return coordinate.getX() >= 0 && coordinate.getX() < width && coordinate.getY() >= 0 && coordinate.getY() < height;
    }

    public List<Move> getAvailableMoves(Side side) {
        return availableMoves.get(side);
    }
}
