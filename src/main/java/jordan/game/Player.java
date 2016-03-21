package jordan.game;


public abstract class Player extends Occupier {
    private final Side side;

    public Player(Side side, String printCharacter) {
        super(printCharacter);
        this.side = side;
    }

    public abstract Move getMove(BoardState boardState);

    public Side getSide() {
        return side;
    }
}
