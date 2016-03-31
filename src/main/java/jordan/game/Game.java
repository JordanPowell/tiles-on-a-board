package jordan.game;


import java.util.Arrays;

public class Game {
    private final Player verticalPlayer;
    private final Player horizontalPlayer;
    private BoardState boardState;
    private Player nextPlayer;

    public Game(Player verticalPlayer, Player horizontalPlayer, int boardWidth, int boardHeight) {
        this.verticalPlayer = verticalPlayer;
        this.horizontalPlayer = horizontalPlayer;
        // TODO: ew
        assert this.verticalPlayer.getSide() == Side.VERTICAL;
        assert this.horizontalPlayer.getSide() == Side.HORIZONTAL;
        nextPlayer = this.verticalPlayer; // TODO: How to decide who goes first?
        boardState = new BoardState(OccupiedBoard.newBoard(boardWidth, boardHeight, Arrays.asList(verticalPlayer, horizontalPlayer)), boardWidth, boardHeight, nextPlayer.getSide());
    }

    public void playNextTurn() {
        assert nextPlayer.getSide() == boardState.getSideToPlay();
        Move move = nextPlayer.getMove(boardState);
        playMove(move);
    }

    private void playMove(Move move) {
        switchPlayers();
        boardState = boardState.stateAfter(move, nextPlayer.getSide());
    }

    private void switchPlayers() {
        nextPlayer = nextPlayer == verticalPlayer ? horizontalPlayer : verticalPlayer;
    }

    public boolean isFinished() {
        return boardState.getAvailableMoves().isEmpty();
    }

    public BoardState getBoardState() {
        return boardState;
    }

    public Player getNextPlayer() {
        return nextPlayer;
    }

    public Player getWinner() {
        return nextPlayer == verticalPlayer ? horizontalPlayer : verticalPlayer;
    }
}
