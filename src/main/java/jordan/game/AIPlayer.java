package jordan.game;

import java.util.List;

public class AIPlayer extends Player {
    static int playerCounter = 1;
    private final static int DEPTH = 2;
    private Player opponent;
    private final BoardStateEvaluator evaluator;

    public AIPlayer(Side side, BoardStateEvaluator boardStateEvaluator) {
        // TODO: is there a nicer way of getting int->char in Java without some annoying ascii offset addition?
        super(side, String.valueOf(playerCounter).charAt(0));
        this.evaluator = boardStateEvaluator;
        playerCounter++;
    }

    public AIPlayer(Side side, Player opponent, BoardStateEvaluator boardStateEvaluator) {
        this(side, boardStateEvaluator);
        this.opponent = opponent;
    }

    @Override
    public String toString() {
        return "AI #" + getPrintCharacter();
    }

    @Override
    public Move getMove(BoardState boardState) {
        List<Move> availableMoves = boardState.getAvailableMoves();
        Move bestMove = availableMoves.get(0);
        int bestValue = Integer.MIN_VALUE;
        for (Move move : availableMoves)
        {
            int val = minMaxTree(boardState.stateAfter(move, this, opponent.getSide()), DEPTH, true);
            if (val > bestValue)
            {
                bestMove = move;
                bestValue = val;
            }
        }
        return bestMove;
    }

    // TODO: Clean up all this horrendous duplication - it's 1:17 AM so it's not my fault
    private int minMaxTree(BoardState boardState, int depth, boolean maximising) {
        if (depth == 0) {
            return evaluateBoardState(boardState);
        }

        List<Move> availableMoves = boardState.getAvailableMoves();
        if (availableMoves.isEmpty()) {
            return evaluateBoardState(boardState);
        }

        if (maximising) {
            int bestValue = Integer.MIN_VALUE;
            for (Move move : availableMoves)
            {
                int val = minMaxTree(boardState.stateAfter(move, this, opponent.getSide()), depth - 1, false);
                bestValue = Math.max(bestValue, val);
            }
            return bestValue;
        }
        else
        {
            int bestValue = Integer.MAX_VALUE;
            for (Move move : availableMoves)
            {
                int val = minMaxTree(boardState.stateAfter(move, opponent, this.getSide()), depth - 1, true);
                bestValue = Math.min(bestValue, val);
            }
            return bestValue;
        }
    }

    private int evaluateBoardState(BoardState boardState) {
        if (boardState.getAvailableMoves().isEmpty())
        {
            if (boardState.getNextSideToPlay() == getSide()) {
                return Integer.MIN_VALUE;
            }
            return Integer.MAX_VALUE;
        }

        return evaluator.evaluate(boardState, this, opponent);
    }

    // TODO: ew
    public void setOpponent(AIPlayer opponent) {
        this.opponent = opponent;
    }
}
