package jordan.game;

import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        int boardWidth = 8;
        int boardHeight = 8;
        Player jordan = new HumanPlayer(Side.VERTICAL, "Jordan");
        Player ai = new AIPlayer(Side.HORIZONTAL, jordan, new BasicEvaluator());

        Game game = new Game(jordan, ai, boardWidth, boardHeight);
        while (!game.isFinished())
        {
            printGameState(game);
            game.playNextTurn();
            System.out.println();
        }
        printEndResult(game);
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
