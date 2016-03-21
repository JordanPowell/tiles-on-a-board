package jordan.game;


import java.util.Collections;

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
        boardState = new BoardState(Collections.emptyMap(), boardWidth, boardHeight, nextPlayer.getSide());
    }

    public void playNextTurn() {
        assert nextPlayer.getSide() == boardState.getSideToPlay();
        Move move = nextPlayer.getMove(boardState);
        playMove(move, nextPlayer);
    }

    private void playMove(Move move, Player movingPlayer) {
        switchPlayers();
        boardState = boardState.stateAfter(move, movingPlayer, nextPlayer.getSide());
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
