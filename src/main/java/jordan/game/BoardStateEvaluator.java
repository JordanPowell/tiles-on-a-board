package jordan.game;

public interface BoardStateEvaluator {
    int evaluate(BoardState boardState, Player player, Player opponent);
}
