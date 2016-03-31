package jordan.game;

import java.time.Instant;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        int boardWidth = 8;
        int boardHeight = 8;
        AIPlayer ai = new AIPlayer(Side.HORIZONTAL, new BasicEvaluator());
        AIPlayer ai2 = new AIPlayer(Side.VERTICAL, ai, new BasicEvaluator());
        ai.setOpponent(ai2);
        Game game = new Game(ai, ai2, boardWidth, boardHeight);

//        HumanPlayer jordan = new HumanPlayer(Side.VERTICAL, "Jordan");
//        ai.setOpponent(jordan);
//        Game game = new Game(ai, jordan, boardWidth, boardHeight);

        Instant startTime = Instant.now();
        while (!game.isFinished())
        {
            printGameState(game);
            game.playNextTurn();
            System.out.println();
        }
        printEndResult(game);
        System.out.println("Game took " + (Instant.now().toEpochMilli() - startTime.toEpochMilli()) + "ms");
    }

    private static void printEndResult(Game game) {
        System.out.println("Final state: ");
        printGameState(game);
        System.out.println(game.getWinner() + " is the winner because " + game.getNextPlayer() + " cannot play!");
    }

    private static void printGameState(Game game) {
        System.out.println(game.getNextPlayer() + " to play");
        BoardState boardState = game.getBoardState();
        printBoardState(boardState);
        printNextPlayer(game.getNextPlayer());
    }

    private static void printNextPlayer(Player player) {
        System.out.println("It's " + player + "'s turn");
    }

    private static void printTile(Tile tile) {
        System.out.print(tile.getOccupier().getPrintCharacter() + " ");
    }

    private static void printBoardState(BoardState boardState) {
        int rowIndex = 0;
        for (Row row : boardState.getRows()) {
            System.out.print(rowIndex++ + " ");
            row.getTiles().forEach(Main::printTile);
            System.out.println();
        }
        System.out.print("  ");
        for (Integer colIndex : IntStream.range(0, boardState.getWidth()).toArray())
        {
            System.out.print(colIndex + " ");
        }
        System.out.println();
    }
}
