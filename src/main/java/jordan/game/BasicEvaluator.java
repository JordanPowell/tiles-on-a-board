package jordan.game;

public class BasicEvaluator implements BoardStateEvaluator {
    @Override
    public int evaluate(BoardState boardState, Player player, Player opponent) {
        return boardState.getAvailableMoves(player.getSide()).size() - boardState.getAvailableMoves(opponent.getSide()).size();
    }
}
