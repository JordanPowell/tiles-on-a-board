package jordan.game;

import java.util.List;
import java.util.Scanner;

public class HumanPlayer extends Player {
    private final String name;

    public HumanPlayer(Side side, String name) {
        super(side, name.substring(0, 1));
        this.name = name;
    }

    @Override
    public Move getMove(BoardState boardState) {
        List<Move> availableMoves = boardState.getAvailableMoves();
        while (true) {
            Coordinate userCoordinate = askUserForCoordinate();
            for (Move move : availableMoves) {
                if (move.getCoordinate().equals(userCoordinate)) {
                    return move;
                }
            }
            indicateInvalidMove();
        }
    }

    private void indicateInvalidMove() {
        System.out.println("The move you entered was invalid, please try again");
    }

    private Coordinate askUserForCoordinate() {
        while (true) {
            System.out.println("Please enter a coordinate in the form of x,y");
            Scanner sc = new Scanner(System.in);
            String next = sc.next();
            String[] split = next.split(",");
            try {
                return new Coordinate(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
            }
            catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.out.println("Invalid input - please make sure it matches the format x,y");
            }
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
